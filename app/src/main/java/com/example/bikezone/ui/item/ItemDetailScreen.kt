package com.example.bikezone.ui.item
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bikezone.BikeZoneBottomAppBar
import com.example.bikezone.BikeZoneTopAppBar
import com.example.bikezone.R
import com.example.bikezone.navigation.ItemDetailsDestination
import com.example.bikezone.ui.AppViewModelProvider
import com.example.bikezone.ui.theme.BikeZoneTheme
import java.text.NumberFormat

@Composable
fun ItemDetailScreen(
    navController: NavHostController,
    viewModel: ItemDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    BikeZoneTheme {
        Scaffold(
            topBar = {
                BikeZoneTopAppBar(
                    title = ItemDetailsDestination.titleRes,
                    canNavigateBack = true,
                    navigateBack = { navController.navigateUp() },
                    modifier = Modifier
                )
            },
            bottomBar = {
                BikeZoneBottomAppBar(
                    navController = navController
                )
            },
            content = { innerPadding ->
                ItemDetailLayout(
                    itemDetails = uiState.itemDetails,
                    contentPadding = innerPadding
                )
            }
        )
    }
}

@Composable
fun ItemDetailLayout(
    itemDetails: ItemDetails,
    contentPadding: PaddingValues = PaddingValues(0.dp),

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = itemDetails.picture),
            contentDescription = null,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = itemDetails.name,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier.padding(bottom = 24.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(id = R.string.str_price),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = NumberFormat.getCurrencyInstance().format(itemDetails.price),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 24.dp),
            color = Color.Green
        )
        Text(
            text = stringResource(id = R.string.str_item_detail),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        val descriptionLines = stringResource(id = itemDetails.desc).split("\n")
        descriptionLines.forEach { line ->
            Text(
                text = line,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
    }
}