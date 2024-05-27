package com.example.bikezone.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bikezone.data.orders.Order
import com.example.bikezone.data.orders.OrderRepository
import com.example.bikezone.data.users.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Date
import java.util.concurrent.TimeUnit

class OrderViewModel(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _orderUiState = MutableStateFlow(OrderUiState())
    val orderUiState: StateFlow<OrderUiState> get() = _orderUiState.asStateFlow()

    init {
        initializeOrderUiState()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun initializeOrderUiState() {
        viewModelScope.launch {
            userRepository.getAuthUserStream(true)
                .flatMapLatest { user ->
                    user?.let {
                        orderRepository.getOrdersByUserIdStream(it.id)
                    } ?: flowOf(emptyList())
                }
                .map { orders ->
                    orders.map { it.toOrderDetail() }
                }
                .collect { orderDetails ->
                    _orderUiState.value = OrderUiState(orders = orderDetails)
                }
        }
    }

    suspend fun deleteOrder(orderDetail: OrderDetail) {
           orderRepository.delete(orderDetail.toOrder())
    }

    fun updateOrderDetailExpanded(orderDetail: OrderDetail) {
        val updatedOrders = _orderUiState.value.orders.map {
            if (it.id == orderDetail.id) {
                it.copy(expanded = !it.expanded)
            } else {
                it
            }
        }
        _orderUiState.value = OrderUiState(orders = updatedOrders)
    }
}

data class OrderUiState(
    val orders: List<OrderDetail> = emptyList()
)

data class OrderDetail(
    val id: Int,
    val price: Double,
    val count: Int,
    val userId: Int,
    val date: Date,
    val expanded: Boolean = false
)

fun Order.toOrderDetail(): OrderDetail = OrderDetail(
    id = id,
    price = price,
    count = count,
    userId = userId,
    date = date
)

fun OrderDetail.toOrder(): Order = Order(
    id = id,
    price = price,
    count = count,
    userId = userId,
    date = date
)
fun isLessThanFiveHours(orderDate: Date): Boolean {
    val currentTime = Date().time
    val orderTime = orderDate.time
    val diffInMillis = currentTime - orderTime
    val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
    return diffInHours <= 3
}