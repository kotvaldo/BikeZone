package com.example.bikezone.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bikezone.data.cartItems.CartRepository
import com.example.bikezone.data.items.ItemRepository
import com.example.bikezone.data.orders.Order
import com.example.bikezone.data.orders.OrderRepository
import com.example.bikezone.data.users.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.Date

class CartViewModel(
    private val cartRepository: CartRepository,
    private val itemRepository: ItemRepository,
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository
) : ViewModel() {


    private val _cartUiState = MutableStateFlow(CartUiState(emptyList(), 0.0))
    val cartUiState: StateFlow<CartUiState> get() = _cartUiState.asStateFlow()

    init {
        initializeCartUiState()
    }

    private fun initializeCartUiState() {
        viewModelScope.launch {
            val authUser = userRepository.getAuthUserStream(true).first()
            if(authUser != null) {
                cartRepository.getAllCartItemsStream(authUser.id).collect { cartItems ->
                    val cartItemDetails = cartItems.mapNotNull { cartItem ->
                        val item = itemRepository.getItemByIdStream(cartItem.itemId).firstOrNull()
                        if (item != null) {
                            CartItemDetail(
                                cartItemId = cartItem.id,
                                itemDetail = ItemDetail(
                                    id = item.id,
                                    name = item.name,
                                    price = item.price,
                                    picture = item.picture,
                                    desc = item.desc
                                ),
                                count = cartItem.count,
                                userId = authUser.id
                            )
                        } else {
                            null
                        }
                    }
                    val finalPrice = cartItemDetails.sumOf { it.itemDetail.price * it.count }
                    _cartUiState.value = CartUiState(cartItems = cartItemDetails, finalPrice = finalPrice)
            }

            }
        }
    }

    suspend fun addNewOrder() {
        val user = userRepository.getAuthUserStream(true).first()
        if(user != null) {
            val count = cartUiState.value.cartItems.sumOf { it.count }
            orderRepository.insert(Order(id = 0, price = cartUiState.value.finalPrice, count , date = Date(), userId = user.id))
        }
    }
    suspend fun deleteAllCartItems() {
            val user = userRepository.getAuthUserStream(true).first()
            if(user != null) {
                val allCartItems = cartRepository.getAllCartItemsStream(user.id).first()
                cartRepository.deleteAllItems(allCartItems)
            }

    }
    fun reduceCountByOne(cartItemId: Int) {
        viewModelScope.launch {
            val currentItem = cartRepository.getCartItemByIdStream(cartItemId).firstOrNull()
            if (currentItem != null) {
                if(currentItem.count - 1 > 0) {
                    cartRepository.update(currentItem.copy(count = currentItem.count - 1))
                } else {
                    deleteItemCartById(cartItemId)
                }
            }
        }
    }

    fun increaseCountByOne(cartItemId: Int) {
        viewModelScope.launch {
            val currentItem = cartRepository.getCartItemByIdStream(cartItemId).firstOrNull()
            if (currentItem != null) {
                cartRepository.update(currentItem.copy(count = currentItem.count + 1))
            }
        }
    }
    fun deleteItemCartById(cartItemId: Int) {
        viewModelScope.launch {
            val currentItem = cartRepository.getCartItemByIdStream(cartItemId).firstOrNull()
            if (currentItem != null) {
                cartRepository.delete(currentItem)
            }
        }
    }




}

data class ItemDetail(
    val id: Int,
    val name: String,
    val price: Double,
    val picture: Int,
    val desc: Int
)

data class CartItemDetail(
    val cartItemId: Int,
    val itemDetail: ItemDetail,
    val count: Int,
    val userId: Int
)

data class CartUiState(
    val cartItems: List<CartItemDetail> = emptyList(),
    val finalPrice: Double = 0.0
)