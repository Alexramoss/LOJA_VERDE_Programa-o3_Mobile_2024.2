package com.example.myapplication.view;

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.viewPages.CartPage
import com.example.myapplication.viewPages.HistoryPage
import com.example.myapplication.viewPages.HomePage
import com.example.myapplication.viewPages.ProfilePage

class HomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeScreen()
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun HomeScreen() {

        val navBarList = listOf(
            NavItem("Inicio", R.drawable.baseline_home_filled_24),
            NavItem("Carrinho", R.drawable.baseline_shopping_basket_24),
            NavItem("Compras", R.drawable.baseline_shopping_bag_24),
            NavItem("Perfil", R.drawable.baseline_account_circle_24)
        )

        var selectIndex by remember { mutableIntStateOf(0) }

        Scaffold(
            bottomBar = {
                NavigationBar {
                    navBarList.forEachIndexed { index, navItem ->
                        NavigationBarItem(
                            selected = index == selectIndex,
                            onClick = {
                                selectIndex = index
                            },
                            icon = {
                                Image(
                                    painter = painterResource(id = navItem.image),
                                    contentDescription = navItem.label,
                                    modifier = Modifier.height(30.dp)
                                )
                            },
                            label = { Text(text = navItem.label) }
                        )
                    }
                }
            }
        ) {
            HomeViewComponents(modifier = Modifier, selectIndex)
        }
    }

    }

    @Composable
    fun HomeViewComponents(modifier: Modifier, selectIndex: Int) {
        when(selectIndex){
            0 -> HomePage(modifier)
            1 -> CartPage(modifier)
            2 -> HistoryPage()
            3 -> ProfilePage(modifier)
            else -> Text("Página não encontrada", modifier)
        }
    }

    data class NavItem(
        val label: String,
        val image: Int
    )
