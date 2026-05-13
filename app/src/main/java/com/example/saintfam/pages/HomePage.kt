package com.example.saintfam.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saintfam.components.BannerView
import com.example.saintfam.components.CategoriesView
import com.example.saintfam.components.HeaderView


@Composable
fun HomePage(modifier : Modifier = Modifier){
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        HeaderView(modifier = Modifier.padding(top = 20.dp))
        BannerView( )
        Text( text = "Categories", modifier = Modifier.padding(horizontal = 0.dp, vertical = 18.dp), style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        ))
        CategoriesView()
    }
}