package com.example.saintfam.com.example.saintfam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.saintfam.AppUtils
import com.example.saintfam.GlobalNavigation
import com.example.saintfam.models.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType

@Composable
fun ProductDetailsScreen(
    modifier: Modifier = Modifier,
    productId: String
) {

    val backgroundColor = Color(0xFFF2F2F2)
    val cardColor = Color(0xFF111214)
    val black = Color.Black
    val gray = Color(0xFF9E9E9E)
    val lightGray = Color(0xFFEAEAEA)
    val white = Color(0xFFF2F2F2)

    val context = LocalContext.current
    var products by remember {
        mutableStateOf(ProductModel())
    }

    LaunchedEffect(Unit) {

        Firebase.firestore.collection("data")
            .document("stock")
            .collection("products")
            .document(productId)
            .get()
            .addOnCompleteListener {

                if (it.isSuccessful) {

                    val result = it.result.toObject(ProductModel::class.java)

                    if (result != null) {
                        products = result
                    }
                }
            }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

        // PRODUCT IMAGE SECTION
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp),
            contentAlignment = Alignment.Center
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp),
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(
                    containerColor = lightGray
                )
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    val pageState = rememberPagerState(
                        initialPage = 0,
                        pageCount = { products.image.size }
                    )

                    HorizontalPager(
                        state = pageState,
                        pageSpacing = 24.dp,
                        modifier = Modifier.height(340.dp)
                    ) { page ->

                        AsyncImage(
                            model = products.image[page],
                            contentDescription = "Product Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    DotsIndicator(
                        dotCount = products.image.count(),
                        pagerState = pageState,
                        type = ShiftIndicatorType(
                            DotGraphic(
                                color = Color.Black,
                                size = 6.dp
                            )
                        )
                    )
                }
            }
        }

        // DETAILS CARD
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(
                topStart = 35.dp,
                topEnd = 35.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = cardColor
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {

                // SCROLLABLE CONTENT
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {

                    Text(
                        text = products.title,
                        color = gray,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = products.title,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        color = white
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${products.price} Rwf",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Light,
                        color = gray
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Product Description :",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = white
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = products.description,
                        fontSize = 16.sp,
                        color = white
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    products.otherDetails.forEach { (key, value) ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {

                            Text(
                                text = "$key : ",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = gray
                            )

                            Text(
                                text = value,
                                fontSize = 16.sp,
                                color = white
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }

                // FIXED BUTTONS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Button(
                        onClick = {
                            AppUtils.addToCart(context,productId)
                            GlobalNavigation.navController.navigate("")
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = black
                        )
                    ) {

                        Text(
                            text = "Add Cart",
                            color = white,
                            fontSize = 18.sp
                        )
                    }

                    Button(
                        onClick = {
                            AppUtils.addToCart(context,productId)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = white
                        )
                    ) {

                        Text(
                            text = "Buy Now",
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}