package com.epn.godoyjotcelyn_moviles
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.epn.godoyjotcelyn_moviles.ui.screens.CrudScreen
import com.epn.godoyjotcelyn_moviles.ui.screens.StorageScreen
import androidx.compose.material.icons.filled.Lock

import com.epn.godoyjotcelyn.ui.screens.NetworkScreen

import com.epn.godoyjotcelyn_moviles.ui.theme.GodoyJotcelyn_MovilesTheme



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            GodoyJotcelyn_MovilesTheme {

                AppNavigation()

            }

        }

    }
    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()
        var selectedTab by remember { mutableIntStateOf(0) }

        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0; navController.navigate("network") },
                        icon = { Icon(Icons.Default.Search, contentDescription = "Red") },
                        label = { Text("Módulo 1") }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1; navController.navigate("crud") },
                        icon = { Icon(Icons.Default.List, contentDescription = "CRUD") },
                        label = { Text("Módulo 2") }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2; navController.navigate("storage") },
                        icon = { Icon(Icons.Default.Lock, contentDescription = "Secretos") },
                        label = { Text("Módulo 3") }
                    )
                }
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "network",
                modifier = Modifier.padding(padding)
            ) {
                composable("network") { NetworkScreen() }
                composable("crud") { CrudScreen() }
                composable("storage") { StorageScreen() }
            }
        }
    }

}

