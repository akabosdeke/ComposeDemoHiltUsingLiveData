package com.mespl.bletms20

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mespl.bletms20.ui.theme.BLETMS20Theme
import com.mespl.bletms20.view.login.LoginScreen
import com.mespl.bletms20.view.splash.SplashScreen
import com.mespl.bletms20.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel:UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            BLETMS20Theme {
                val navController = rememberNavController()
                Box( // Wrap NavHost in Box and set a background color
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF00BCD4)) // Same as splash screen color
                ) {
                    NavHost(navController, startDestination = "splash") {
                        composable("splash") { SplashScreen(navController) }
                        composable("login") { LoginScreen(viewModel) }
                    }
                }
            }
        }
    }
}


