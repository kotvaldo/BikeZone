package com.example.bikezone.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bikezone.BikeZoneBottomAppBar
import com.example.bikezone.BikeZoneTopAppBar
import com.example.bikezone.R
import com.example.bikezone.data.items.Item
import com.example.bikezone.navigation.ItemDetailsDestination
import com.example.bikezone.ui.AppViewModelProvider
import com.example.bikezone.ui.theme.BikeZoneTheme
import java.text.NumberFormat

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val uiState by viewModel.homeUiState.collectAsState()
    BikeZoneTheme {
        Scaffold(
            topBar = {
                BikeZoneTopAppBar(
                    title = R.string.app_name,
                    canNavigateBack = false,
                    navigateBack = {},
                    modifier = Modifier,
                    hasLogo = true
                )
            },
            bottomBar = {
                BikeZoneBottomAppBar(
                    navController = navController
                )
            },
            content = { innerPadding ->
                HomeLayout(
                    contentPadding = innerPadding,
                    itemState = uiState,
                    onItemClick = {navController.navigate("${ItemDetailsDestination.route}/${it}")},
                    onAddClick = viewModel::addItemToCart,
                    onRegexChange = viewModel::updateUiState,
                )
            }
        )
    }
}

@Composable
fun HomeLayout(
    contentPadding: PaddingValues = PaddingValues(0.dp),
    itemState: HomeUiState,
    onAddClick: (Int) -> Unit,
    onItemClick: (Int) -> Unit,
    onRegexChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        // Search Bar
        TextField(
            value = itemState.regex,
            onValueChange = { onRegexChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { Text(text = "Search...") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            },
            singleLine = true,

        )

        // Filtered list
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(items = itemState.itemList, key = { it.id }) { item ->
                ItemCard(
                    item = item,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onItemClick(item.id) },
                    onAddClick = onAddClick
                )
            }
        }
    }
}

@Composable
private fun ItemCard(
    item: Item,
    onAddClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = item.picture),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .clip(CircleShape)
                ,
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(10.dp))
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                fontStyle = FontStyle.Italic,

            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(20.dp))
            Row(
            ) {
                Text(
                    text = stringResource(id = R.string.str_price),
                    style = MaterialTheme.typography.titleLarge,
                    color = colorResource(id = R.color.custom_orange),
                    fontFamily = FontFamily.Default,
                )
                Spacer(Modifier.width(15.dp))
                Text(
                    text = NumberFormat.getCurrencyInstance().format(item.price).toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface  ,
                    fontFamily = FontFamily.Default,

                )
            }

            IconButton(
                onClick = { onAddClick(item.id) },
                modifier = Modifier
                    .align(Alignment.End)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape)
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.str_cart),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}