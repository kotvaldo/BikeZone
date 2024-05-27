package com.example.bikezone

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.bikezone.data.BikeZoneDatabase
import com.example.bikezone.data.items.Item
import com.example.bikezone.data.users.User
import com.example.bikezone.notifications.checkNotificationPermission
import com.example.bikezone.notifications.requestNotificationPermission
import com.example.bikezone.ui.theme.BikeZoneTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        val activity = context as? Activity
        activity?.let {
            if (!checkNotificationPermission(it)) {
                requestNotificationPermission(it)
            }
        }
        //removeDatabase()
        setContent {
            BikeZoneTheme {
                BikeZoneApp()
            }
        }
        //setupDatabase(this)
       // addUser(this)
    }
    private fun removeDatabase() {
        val dbName = "bikezone_dtb"
        applicationContext.deleteDatabase(dbName)
    }
    private fun addUser(context: Context) {
        val db = BikeZoneDatabase.getDatabase(context)
        CoroutineScope(Dispatchers.IO).launch {
            val userDao = db.userDao()
            val userA = User(0,"a", "a", "a", "a", false)
            userDao.insert(userA)


        }
    }
    private fun setupDatabase(context: Context) {
        val db = BikeZoneDatabase.getDatabase(context)
        CoroutineScope(Dispatchers.IO).launch {
            val itemDao = db.itemDao()
            val userDao = db.userDao()
            val userA = User(0,"a", "a", "a", "a", false)
            val userB = User(0,"b", "b", "b", "b", false)
            val item = Item(name = "CTM WIRE 2024", price = 2500.0, picture = R.drawable.bike1, desc = R.string.str_desc_of_item)
            val item2 = Item(name = "CTM WIRE XPERT 2024", price = 2800.0, picture = R.drawable.bike2, desc = R.string.str_desc_of_item)
            val item3 = Item(name = "CTM WIRE PRO 2024", price = 3000.0, picture = R.drawable.bike3, desc = R.string.str_desc_of_item)
            val item4 = Item(name = "KELLYS SOOT 20 2023", price = 999.0, picture = R.drawable.bike4, desc = R.string.str_desc_of_item)
            val item5 = Item(name = "CTM BLADE Comp 2023", price = 1099.0, picture = R.drawable.bike5, desc = R.string.str_desc_of_item)
            val item6 = Item(name = "LAPIERRE Pulsium SAT 5.0 2023", price = 2969.0, picture = R.drawable.bike6, desc = R.string.str_desc_of_item)
            val item7 = Item(name = "MERIDA SCULTURA 6000 čierna metalíza(strieborný) 2022", price = 2969.0, picture = R.drawable.bike7, desc = R.string.str_desc_of_item)
            val item8 = Item(name = "Brašna rámová KLS WEDGE ECO, blue", price = 14.90, picture = R.drawable.accesory1, desc = R.string.str_desc_of_clothes)
            val item9 = Item(name = "Bunda KELLYS LEVANTE black 2022", price = 56.99, picture = R.drawable.accesory2, desc = R.string.str_desc_of_clothes)
            val item10 = Item(name = "Cyklopočítač DIRECT WL black-red", price = 29.90, picture = R.drawable.accesory3, desc = R.string.str_desc_of_cycle_cmp)
            itemDao.insert(item)
            itemDao.insert(item2)
            itemDao.insert(item3)
            itemDao.insert(item4)
            itemDao.insert(item5)
            itemDao.insert(item6)
            itemDao.insert(item7)
            itemDao.insert(item8)
            itemDao.insert(item9)
            itemDao.insert(item10)
            userDao.insert(userA)
            userDao.insert(userB)


        }
    }

}
