package com.example.saintfam.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.saintfam.GlobalNavigation
import com.example.saintfam.models.ProductModel

@Composable
fun ProductView(modifier: Modifier = Modifier, product: ProductModel){
    Card(modifier = modifier.padding(8.dp)
        .clickable{
            GlobalNavigation.navController.navigate("productDetailsPage/${product.id}")

        },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(8.dp)){
        Column(modifier = Modifier.padding(8.dp)){
            AsyncImage(
                model = product.image.firstOrNull(),
                contentDescription = product.title,
                modifier = Modifier.height(120.dp)
                    .fillMaxWidth()

            )
            Text( text = product.title,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp))
            Row(modifier =Modifier.fillMaxWidth()
                .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                ) {
                Text( text = product.price +"Rwf",
                    fontSize = 14.sp,)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {}) {
                  Icon(imageVector = Icons.Default.ShoppingCart,
                      contentDescription = "Buy Now")
                }
            }
        }
    }
}