package com.example.saintfam.components

import com.example.saintfam.AppUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.saintfam.GlobalNavigation
import com.example.saintfam.models.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun CartItemView(modifier: Modifier = Modifier, productId: String, qty:Long){

   val context = LocalContext.current

    var product by remember {
        mutableStateOf(ProductModel())
    }
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data")
            .document("stock").collection("products")
            .document(productId).get().addOnCompleteListener {
                if (it.isSuccessful){
                    val result = it.result.toObject(ProductModel::class.java)
                    if(result!=null){
                        product = result
                    }                }
            }
    }
    Card(modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(8.dp),){
        Row(modifier = Modifier.padding(8.dp),verticalAlignment = Alignment.Top){
            AsyncImage(
                model = product.image.firstOrNull(),
                contentDescription = product.title,
                modifier = Modifier.height(100.dp).width(100.dp)

            )
            Column( modifier = Modifier.padding(start = 18.dp),
                verticalArrangement = Arrangement.Top) {
                Text( text = product.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,)
                Spacer(modifier = modifier.height(6.dp))
                Text( text = product.price +"Rwf",
                    fontSize = 19.sp,)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        AppUtils.removeToCart(context,productId)
                    }) {
                      Text( text = "-" , fontSize = 20.sp, fontWeight = FontWeight.Bold )
                    }
                    Text( text = "$qty" , fontSize = 16.sp)
                    IconButton(onClick = {AppUtils.addToCart(context,productId)}) {
                        Text( text = "+" , fontSize = 20.sp, fontWeight = FontWeight.Bold )
                    }
                }
            }
            IconButton(onClick = { AppUtils.removeToCart(context,productId, removeAlL = true)}) {
                Icon(imageVector = Icons.Default.Delete,
                    contentDescription = "Remove from Cart")
            }
        }
    }
}