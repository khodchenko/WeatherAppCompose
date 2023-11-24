package com.khodchenko.weatherappcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TabRowDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.khodchenko.weatherappcompose.R
import kotlinx.coroutines.launch


@Preview(showBackground = true)
@Composable
fun MainCard() {
    Column(
        modifier = Modifier
            .padding(6.dp)
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp

            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "20 Jun 2022 13:00",
                        modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                        style = TextStyle(fontSize = 15.sp)
                    )
                    AsyncImage(
                        model = "https://cdn.weatherapi.com/weather/64x64/day/116.png",
                        contentDescription = "Weather icon",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(top = 4.dp, end = 8.dp),
                    )

                }

                Text(
                    text = "Madrid",
                    style = TextStyle(fontSize = 24.sp)
                )

                Text(
                    text = "23°C",
                    style = TextStyle(fontSize = 64.sp)
                )

                Text(
                    text = "Partly cloudy",
                    style = TextStyle(fontSize = 16.sp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "image"
                        )
                    }

                    Text(
                        text = "23°C/12°C",
                        style = TextStyle(fontSize = 16.sp)
                    )

                    IconButton(onClick = {

                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sync),
                            contentDescription = "image"
                        )
                    }
                }
            }

        }

    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
fun TabLayout() {
    val tabList = listOf("HOURS", "DAYS")
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(start = 6.dp, end = 6.dp)
            .clip(RoundedCornerShape(6.dp))
    ) {
        TabRow(
            selectedTabIndex = tabIndex, indicator = { pos ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(
                        pagerState = pagerState,
                        tabPositions = pos
                    )
                )
            },
            modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant), //todo change color
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = text) })
            }
        }
        HorizontalPager(
            count = tabList.size,
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) { index ->
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(15) {
                    ListItem()
                }
            }

        }
    }
}