package com.example.saintfam

import android.content.Context
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore


object AppUtils {
    fun showToast(context: Context, message: String){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }

    fun addToCart(context: Context , productId: String){
        val userDoc = Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful){
                val currentCart = it.result.get("cartItems") as? Map<String, Long> ?: emptyMap()
                val currentQuantity = currentCart[productId]?:0
                val updateQuantity = currentQuantity + 1
                val updatedCart = mapOf("cartItems.$productId" to updateQuantity)
                userDoc.update(updatedCart).addOnCompleteListener {
                    if (it.isSuccessful){
                        showToast(context,"Item Added To Cart")
                    }else{
                        showToast(context,"Failed Adding Item To Cart")
                    }
                }
            }
        }
    }

    fun removeToCart(context: Context , productId: String , removeAlL: Boolean = false){
        val userDoc = Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful){
                val currentCart = it.result.get("cartItems") as? Map<String, Long> ?: emptyMap()
                val currentQuantity = currentCart[productId]?:0
                val updateQuantity = currentQuantity - 1
                val updatedCart = if (updateQuantity<=0 || removeAlL)
                    mapOf("cartItems.$productId" to FieldValue.delete())
                else
                    mapOf("cartItems.$productId" to updateQuantity)
                userDoc.update(updatedCart).addOnCompleteListener {
                    if (it.isSuccessful){
                        showToast(context,"Item Remove To Cart")
                    }else{
                        showToast(context,"Failed To Remove Item To Cart")
                    }
                }
            }
        }
    }


}