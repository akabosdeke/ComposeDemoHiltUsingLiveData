package com.mespl.bletms20.view.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.mespl.bletms20.R

@Composable
fun SplashScreen(navController: NavController) {
    var isVisible by remember { mutableStateOf(true) }
    var imageSize by remember { mutableStateOf(80.dp) }

    val animatedSize by animateDpAsState(
        targetValue = imageSize,
        animationSpec = tween(durationMillis = 5000, easing = FastOutSlowInEasing),
        label = "Image Zoom"
    )

    LaunchedEffect(Unit) {
        delay(500)  // Small delay before animation starts
        imageSize = 200.dp  // Increase image size for zoom-in effect
        delay(3000)  // Keep screen for 5 seconds
        isVisible = false

        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF00BCD4)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center, // Keeps text in place
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.max_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(animatedSize) // Animated size
                )
                Text(
                    text = "TMS",
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
