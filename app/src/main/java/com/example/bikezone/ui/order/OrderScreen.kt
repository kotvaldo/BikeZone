package com.example.bikezone.ui.order

import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bikezone.BikeZoneBottomAppBar
import com.example.bikezone.BikeZoneTopAppBar
import com.example.bikezone.R
import com.example.bikezone.data.orders.formatDate
import com.example.bikezone.navigation.HomeDestination
import com.example.bikezone.notifications.showDeleteOrderNotification
import com.example.bikezone.ui.AppViewModelProvider
import com.example.bikezone.ui.theme.BikeZoneTheme
import kotlinx.coroutines.launch
import java.text.NumberFormat

@Composable
fun OrderScreen(
    navController: NavHostController,
    viewModel: OrderViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.orderUiState.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    BikeZoneTheme {
        Scaffold(
            topBar = {
                BikeZoneTopAppBar(
                    title = R.string.str_order,
                    canNavigateBack = true,
                    navigateBack = { navController.navigate(HomeDestination.route) },
                    modifier = Modifier
                )
            },
            bottomBar = {
                BikeZoneBottomAppBar(
                    navController = navController
                )
            },
            content = { innerPadding ->
                OrderLayout(
                    contentPadding = innerPadding,
                    onDeleteOrder = {
                        showDeleteOrderNotification(context, it)
                        coroutineScope.launch {
                            viewModel.deleteOrder(it)
                        }
                    },
                    onExpandToggle = viewModel::updateOrderDetailExpanded,
                    orders = uiState.orders
                )
            })
    }
}

@Composable
fun OrderLayout(
    contentPadding: PaddingValues = PaddingValues(0.dp),
    orders: List<OrderDetail>,
    onExpandToggle: (OrderDetail) -> Unit,
    onDeleteOrder: (OrderDetail) -> Unit
) {
    OrderList(
        orders = orders,
        contentPadding = contentPadding,
        onExpandToggle = onExpandToggle,
        onDeleteOrder = onDeleteOrder
    )
}

@Composable
fun OrderList(
    orders: List<OrderDetail>,
    contentPadding: PaddingValues,
    onExpandToggle: (OrderDetail) -> Unit,
    onDeleteOrder: (OrderDetail) -> Unit
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(orders) { orderDetail ->
            OrderCard(
                orderDetail = orderDetail,
                onExpandToggle = onExpandToggle,
                onDeleteOrder = onDeleteOrder
            )
        }
    }
}

@Composable
fun OrderCard(
    orderDetail: OrderDetail,
    onExpandToggle: (OrderDetail) -> Unit,
    onDeleteOrder: (OrderDetail) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onExpandToggle(orderDetail) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row {
                    Text(
                        text = stringResource(id = R.string.order_id),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = orderDetail.id.toString(),
                        fontSize = 20.sp,
                    )
                }

                Spacer(Modifier.weight(1f))
                Text(
                    text = NumberFormat.getCurrencyInstance().format(orderDetail.price).toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row {
                Text(
                    text = stringResource(id = R.string.order_count),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = orderDetail.count.toString(),
                )
            }


            AnimatedVisibility(
                visible = orderDetail.expanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text(
                            text = stringResource(id = R.string.order_date),
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Text(
                                text = formatDate(orderDetail.date)
                            )
                        } else {
                            Text(text = orderDetail.date.toString())
                        }
                    }
                    // Add more details if necessary
                    Button(
                        enabled = isLessThanFiveHours(orderDate = orderDetail.date),
                        onClick = { onDeleteOrder(orderDetail) },
                        modifier = Modifier.padding(top = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        )
                    ) {
                        Text(stringResource(id = R.string.order_delete))
                    }
                }
            }
        }
    }
}