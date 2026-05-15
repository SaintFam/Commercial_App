package com.example.saintfam.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.saintfam.models.ProductModel

@Composable
fun ProductView(modifier: Modifier = Modifier, product: ProductModel){
    Card(modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)){
        Column(modifier = Modifier.padding(8.dp)){
            AsyncImage(
                model = product.image.firstOrNull(),
                contentDescription = product.title,
                modifier = Modifier.height(120.dp)
                    .fillMaxWidth()

            )
            Text( text = product.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp))
        }
    }
}