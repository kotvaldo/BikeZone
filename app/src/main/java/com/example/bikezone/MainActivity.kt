package com.example.bikezone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.bikezone.ui.theme.BikeZoneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            BikeZoneTheme {
               /* val db = BikeZoneDatabase.getDatabase(this)
                CoroutineScope(Dispatchers.IO).launch {
                    val itemDao = db.itemDao()
                        val item :Item  = Item(name = "CTM WIRE 2024", price = 2500.0, picture = R.drawable.bike1)
                        val item2 :Item  = Item(name = "CTM WIRE XPERT 2024", price = 2800.0, picture = R.drawable.bike2)
                        val item3 :Item  = Item(name = "CTM WIRE PRO 2024", price = 3000.0, picture = R.drawable.bike3)
                        val item4 :Item  = Item(name = "KELLYS SOOT 20 2023", price = 999.0, picture = R.drawable.bike4)
                        val item5 :Item  = Item(name = "CTM BLADE Comp 2023", price = 1099.0, picture = R.drawable.bike5)
                        val item6 :Item  = Item(name = "LAPIERRE Pulsium SAT 5.0 2023", price = 2969.0, picture = R.drawable.bike6)
                        val item7 :Item  = Item(name = "MERIDA SCULTURA 6000 čierna metalíza(strieborný) 2022", price = 2969.0, picture = R.drawable.bike7)
                        val item8 :Item  = Item(name = "Brašna rámová KLS WEDGE ECO, blue", price = 14.90, picture = R.drawable.accesory1)
                        val item9 :Item  = Item(name = "Bunda KELLYS LEVANTE black 2022", price = 56.99, picture = R.drawable.accesory2)
                        val item10 :Item  = Item(name = "Cyklopočítač DIRECT WL black-red", price = 29.90, picture = R.drawable.accesory3)
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
                }*/

                //val dbName = "database-name"
                //applicationContext.deleteDatabase(dbName)
                BikeZoneApp()

            }
        }
    }
}
