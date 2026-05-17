import android.content.Context
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
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


}