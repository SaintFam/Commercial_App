package com.example.saintfam.pages

import AppUtils
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.saintfam.models.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType

@Composable
fun ProductDetailsPage(modifier: Modifier = Modifier, productId: String){

    val context = LocalContext.current

    var products by remember {
        mutableStateOf(ProductModel())
    }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data")
            .document("stock")
            .collection("products")
            .document(productId).get()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    var result = it.result.toObject(ProductModel::class.java)
                    if (result!=null){
                        products = result
                    }
                }
            }
    }
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(18.dp))
        Column {
            val pageState = rememberPagerState(0) {
                products.image.size
            }
            HorizontalPager(
                state = pageState,
                pageSpacing = 24.dp
            ) {
                AsyncImage(
                    model = products.image.get(it),
                    contentDescription = "Product Image",
                    modifier = Modifier.fillMaxWidth()
                        .height(320.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            DotsIndicator(
                dotCount = products.image.count(),
                type = ShiftIndicatorType(
                    DotGraphic(
                        color = MaterialTheme.colorScheme.primary,
                        size = 6.dp
                    )
                ),
                pagerState = pageState
            )
            Spacer(modifier = Modifier.height(18.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween){
                Text( text = products.title,
                    fontWeight = FontWeight.Black,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(8.dp).weight(1f))
                Text( text = products.price + "Rwf",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(8.dp))
            }
            Spacer(modifier = Modifier.height(18.dp))
            }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button( onClick = {},
                modifier = Modifier.weight(3f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )) {
                Text( text = "Buy Now ")
            }
            Button( onClick = {
                AppUtils.addToCart(context,productId)
            },
                modifier = Modifier.weight(2f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,
                contentColor = Color.White
            )) {
                Text( text = "Add to Cart")
            }


        }
           Column(modifier = Modifier.padding(8.dp)) {
               Spacer(modifier = Modifier.height(16.dp))
               Text(
                   text = "Product Description :",
                   fontWeight = FontWeight.SemiBold,
                   fontSize = 18.sp
               )
               Spacer(modifier = Modifier.height(8.dp))
               Text( text = products.description, fontSize = 16.sp)
               Spacer(modifier = Modifier.height(8.dp))
               products.otherDetails.forEach { (key, value) ->
                   Row(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                       Text( text = "$key :", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                       Text( text = value, fontSize = 16.sp)
                   }
               }
           }

        }
    }

