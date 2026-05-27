package com.example.saintfam.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saintfam.models.ProductModel
import com.example.saintfam.models.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firestore.v1.Value

@Composable
fun CheckOut(modifier: Modifier = Modifier){



 val userModel = remember {
     mutableStateOf(UserModel())
 }
    val productList = remember {
        mutableStateListOf<ProductModel>()
    }
    val subTotal = remember {
        mutableStateOf(0f)
    }
    fun calculateAndAssign(){
 productList.forEach {
     if (it.actualPrice.isNotEmpty()){
         val qty = userModel.value.cartItems[it.id] ?: 0
         subTotal.value += it.actualPrice.toFloat() * qty
     }
 }
    }


    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener {
                if (it.isSuccessful){
                    val result = it.result.toObject(UserModel::class.java)
                    if (result!=null){
                        userModel.value= result
                        Firebase.firestore.collection("data")
                            .document("stock").collection("products")
                            .whereIn("id",userModel.value.cartItems.keys.toList())
                            .get().addOnCompleteListener { task ->
                               if (task.isSuccessful){
                                   val  resultProduct = task.result.toObjects(ProductModel::class.java)
                                   productList.addAll(resultProduct)
                                   calculateAndAssign()
                               }
                            }
                    }
                }
            }
    }

    Column(modifier = modifier.fillMaxSize().padding( horizontal = 16.dp , vertical = 30.dp),) {
        Text( text = "CheckOut" , fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        // Bold black divider for maximum visibility
        HorizontalDivider(thickness = 2.dp, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        RowCheckOutItems( title = "subTotal", value = subTotal.value.toString())
        Spacer(modifier = Modifier.height(16.dp))
        RowCheckOutItems( title = "Tax", value = "0.00")
        Spacer(modifier = Modifier.height(16.dp))
        RowCheckOutItems( title = "Discount", value = "0.00")
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Total Amount",
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = subTotal.value.toString() + "Rwf",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
            )
        Spacer(modifier = Modifier.height(16.dp))
        Button( onClick = {} , modifier = Modifier.fillMaxWidth().height(50.dp)) {   Text( text ="Pay Now ")}

    }
}
@Composable
fun RowCheckOutItems(title: String , value: String){
    Row(modifier  = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text( text = title, fontSize = 20.sp , fontWeight = FontWeight.SemiBold)
        Text( text = value + "Rwf", fontSize = 18.sp)
    }
}