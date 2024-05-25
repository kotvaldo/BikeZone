package com.example.bikezone.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bikezone.BikeZoneBottomAppBar
import com.example.bikezone.BikeZoneTopAppBar
import com.example.bikezone.R
import com.example.bikezone.navigation.HomeDestination
import com.example.bikezone.ui.AppViewModelProvider
import com.example.bikezone.ui.theme.BikeZoneTheme
import java.text.NumberFormat

@Composable
fun CartScreen(
    navController: NavHostController,
    viewModel: CartViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val cartUiState by viewModel.cartUiState.collectAsState()
    BikeZoneTheme {
        Scaffold(
            topBar = {
                BikeZoneTopAppBar(
                    title = R.string.str_cart,
                    canNavigateBack = true,
                    navigateBack = {navController.navigate(HomeDestination.route)},
                    modifier = Modifier
                )
            },
            bottomBar = {
                BikeZoneBottomAppBar(
                    navController = navController
                )
            },
            content = { innerPadding ->
                CartBody(
                    contentPadding = innerPadding,
                    cartItems = cartUiState.cartItems,
                    totalPrice = cartUiState.finalPrice,
                    onDeleteAllClick = viewModel::deleteAllCartItems,
                    onDeleteClick = viewModel::deleteItemCartById,
                    onMinusClick = viewModel::reduceCountByOne,
                    onAddClick = viewModel::increaseCountByOne
                )
            })
    }
}

@Composable
private fun CartBody(
    cartItems: List<CartItemDetail>,
    totalPrice: Double,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onMinusClick: (Int) -> Unit = {},
    onDeleteClick: (Int) -> Unit = {},
    onAddClick: (Int) -> Unit = {},
    onDeleteAllClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (cartItems.isEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.emptycart),
                contentDescription = null,
                modifier = modifier.fillMaxSize()
            )
        } else {
            CartItemList(
                cartItems = cartItems,
                contentPadding = contentPadding,
                modifier = Modifier.padding(20.dp),
                totalPrice = totalPrice,
                onAddClick = onAddClick,
                onMinusClick = onMinusClick,
                onDeleteClick =  onDeleteClick,
                onDeleteAllClick = onDeleteAllClick
            )
        }
    }
}

@Composable
private fun CartItemList(
    cartItems: List<CartItemDetail>,
    totalPrice: Double,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    onAddClick: (Int) -> Unit,
    onMinusClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onDeleteAllClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = cartItems, key = { it.cartItemId }) { cartItemDetail ->
            CartItemRow(
                cartItemDetail = cartItemDetail,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onDeleteClick = onDeleteClick,
                onAddClick = onAddClick,
                onMinusClick = onMinusClick
            )
        }
        item {
            IconButton(
                onClick = { onDeleteAllClick() },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape)
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    text = stringResource(R.string.str_total_price),
                    style = MaterialTheme.typography.titleLarge,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = NumberFormat.getCurrencyInstance().format(totalPrice).toString(),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(8.dp)
                )
            }

        }
    }
}

@Composable
private fun CartItemRow(
    cartItemDetail: CartItemDetail,
    modifier: Modifier = Modifier,
    onAddClick: (Int) -> Unit,
    onMinusClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    Card(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = cartItemDetail.itemDetail.picture),
                contentDescription = cartItemDetail.itemDetail.name,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape),

                )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cartItemDetail.itemDetail.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = stringResource(id = R.string.str_price),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = NumberFormat.getCurrencyInstance().format(cartItemDetail.itemDetail.price * cartItemDetail.count).toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = stringResource(id = R.string.str_count),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = cartItemDetail.count.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = { onAddClick(cartItemDetail.cartItemId) },
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                CircleShape
                            )
                            .size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(
                        onClick = { onMinusClick(cartItemDetail.cartItemId) },
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                CircleShape
                            )
                            .size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(
                        onClick = { onDeleteClick(cartItemDetail.cartItemId) },
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                CircleShape
                            )
                            .size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}