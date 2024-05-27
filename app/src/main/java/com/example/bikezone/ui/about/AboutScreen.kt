package com.example.bikezone.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bikezone.BikeZoneBottomAppBar
import com.example.bikezone.BikeZoneTopAppBar
import com.example.bikezone.R
import com.example.bikezone.ui.theme.BikeZoneTheme

@Composable
fun AboutScreen(navController: NavHostController) {
   BikeZoneTheme {
      Scaffold(
         topBar = {
            BikeZoneTopAppBar(
               title = R.string.str_about,
               canNavigateBack = true,
               navigateBack = {navController.navigateUp() },
               modifier = Modifier
            )
         },
         bottomBar = {
            BikeZoneBottomAppBar(
               navController = navController
            )
         },
         content = { innerPadding ->
            AboutLayout(
               contentPadding = innerPadding
            )
         })
   }
}

@Composable
fun AboutLayout(
   contentPadding: PaddingValues = PaddingValues(0.dp)
) {
   Column(
      modifier = Modifier
         .fillMaxSize()
         .padding(contentPadding)
         .padding(16.dp)
         .verticalScroll(rememberScrollState()),

      verticalArrangement = Arrangement.spacedBy(8.dp),
   ) {
      Image(
         painter = painterResource(id = R.drawable.logo),
         contentDescription = stringResource(id = R.string.app_name),
         alignment = Alignment.Center
      )
      Text(
         text = stringResource(id = R.string.app_name),
         color = MaterialTheme.colorScheme.primary
      )
      Text(
         text = stringResource(R.string.str_desc_of_app),
      )
      Text(
         text = stringResource(R.string.str_desc_of_app_2),
      )
      Spacer(modifier = Modifier.height(16.dp))
      Text(
         text = stringResource(R.string.str_contact),
         color = MaterialTheme.colorScheme.secondary
      )
      Text(
         text = stringResource(R.string.str_email_desc),
      )
      Text(
         text = stringResource(R.string.str_phone_desc),
      )
      Spacer(modifier = Modifier.height(16.dp))
      Text(
         text = stringResource(R.string.str_rights),
         color = MaterialTheme.colorScheme.onBackground
      )
   }
}