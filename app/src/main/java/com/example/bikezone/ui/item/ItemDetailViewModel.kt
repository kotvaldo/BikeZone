package com.example.bikezone.ui.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bikezone.R
import com.example.bikezone.data.items.Item
import com.example.bikezone.data.items.ItemRepository
import com.example.bikezone.navigation.ItemDetailsDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ItemDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val itemsRepository: ItemRepository,
) : ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[ItemDetailsDestination.ITEM_ID_ARG])
    val uiState: StateFlow<ItemDetailsUiState> =
        itemsRepository.getItemByIdStream(itemId)
            .filterNotNull()
            .map {
                ItemDetailsUiState(itemDetails = it.toItemDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ItemDetailsUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class ItemDetailsUiState(
    val itemDetails: ItemDetails = ItemDetails()
)
data class ItemDetails(
    val id: Int = 0,
    val name: String = "",
    val price: Double = 0.0,
    val picture: Int = R.drawable.bike3,
    val desc: Int = R.string.str_desc_of_item
)
fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    name = name,
    price = price,
    picture = picture,
    desc =  desc
)
