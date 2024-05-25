
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bikezone.data.cartItems.CartRepository
import com.example.bikezone.data.items.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository,
    private val itemRepository: ItemRepository,
) : ViewModel() {


    private val _cartUiState = MutableStateFlow(CartUiState(emptyList(), 0.0))
    val cartUiState: StateFlow<CartUiState> get() = _cartUiState.asStateFlow()

    init {
        updateUiState()
    }

    private fun updateUiState() {
        viewModelScope.launch {
            cartRepository.getAllCartItemsStream().collect { cartItems ->
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
                            count = cartItem.count
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

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun deleteAllCartItems() {
        viewModelScope.launch {
            val allCartItems = cartRepository.getAllCartItemsStream().first()
            cartRepository.deleteAllItems(allCartItems)
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
    val count: Int
)

data class CartUiState(
    val cartItems: List<CartItemDetail> = emptyList(),
    val finalPrice: Double = 0.0
)