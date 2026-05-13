package com.example.saintfam.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.delay

@Composable
fun BannerView(modifier: Modifier = Modifier) {

    var bannerList by remember {
        mutableStateOf<List<String>>(emptyList())
    }

    // Fetch banners from Firestore
    LaunchedEffect(Unit) {

        Firebase.firestore
            .collection("data")
            .document("banners")
            .get()
            .addOnSuccessListener { result ->

                bannerList =
                    result.get("urls") as? List<String>
                        ?: emptyList()
            }
    }

    // Pager State
    val pageState = rememberPagerState(
        initialPage = 0,
        pageCount = {
            bannerList.size
        }
    )

    // Auto Scroll
    LaunchedEffect(bannerList) {

        if (bannerList.isNotEmpty()) {

            while (true) {

                delay(4000)

                val nextPage =
                    (pageState.currentPage + 1) % bannerList.size

                pageState.animateScrollToPage(nextPage)
            }
        }
    }

    Column(
        modifier = modifier
    ) {

        HorizontalPager(
            state = pageState,
            pageSpacing = 12.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) { page ->

            AsyncImage(
                model = bannerList[page],
                contentDescription = "Banner Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}