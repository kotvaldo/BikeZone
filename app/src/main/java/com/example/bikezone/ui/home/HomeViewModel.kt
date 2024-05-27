package com.example.bikezone.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bikezone.data.cartItems.CartItem
import com.example.bikezone.data.cartItems.CartRepository
import com.example.bikezone.data.items.Item
import com.example.bikezone.data.items.ItemRepository
import com.example.bikezone.data.users.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val itemRepository: ItemRepository,
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    ) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()


    init {
        viewModelScope.launch {
            itemRepository.getAllItemsStream().map { items ->
                HomeUiState(itemList = items, regex = _homeUiState.value.regex)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            ).collect {
                _homeUiState.value = it
            }
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L

    }

    fun updateUiState(regex: String) {
        _homeUiState.value = _homeUiState.value.copy(regex = regex)
        Log.d("REGEX that came from Parameter", regex)
        Log.d("REGEX that came", homeUiState.value.regex)
        viewModelScope.launch {
            val itemsFlow = if (regex.isBlank()) {
                itemRepository.getAllItemsStream()
            } else {
                itemRepository.getItemsBySubString(regex)
            }

            itemsFlow.map { items ->
                _homeUiState.value.copy(itemList = items)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState(regex = regex)
            ).collect { newState ->
                _homeUiState.value = newState
            }
        }
    }
    fun addItemToCart(itemId: Int) {
        viewModelScope.launch {
            val cartItem = cartRepository.getCartItemByItemIdStream(itemId).first()
            val authUser = userRepository.getAuthUserStream(true).first()
            if(authUser != null) {
                if (cartItem == null) {
                    cartRepository.insert(CartItem(itemId = itemId, count = 1, userId = authUser.id))
                } else {
                    cartRepository.update(cartItem.copy(count = cartItem.count + 1))
                }
            }

        }
    }

}

data class HomeUiState(
    val itemList: List<Item> = listOf(),
    val regex: String = ""
)