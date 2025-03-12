package com.mespl.bletms20.view.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.mespl.bletms20.R
import com.mespl.bletms20.data.model.LoginRequest
import com.mespl.bletms20.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel : UserViewModel) {

    LaunchedEffect(Unit) {
       // viewModel.fetchUsers() // Fetch users when screen loads
        viewModel.userLogin(
            request = LoginRequest(
                password = "1234",
                plantId = 1,
                UserName = "2"
            )
        )
    }
    viewModel.loginResponse.observeForever {user->
        Log.d("aaaaa", "LoginScreen: $user")

    }

    viewModel.users.observeForever {user->
        user?.forEach{it->
            Log.d("aaaaa", "LoginScreen: $it")
        }

    }

    Scaffold(modifier = Modifier.background(Color.White), topBar = {
        TopAppBar(
            title = { Text("Login", fontSize = 24.sp, color = Color.Black) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorResource(id = R.color.light_blue), // Background color of the app bar
                titleContentColor = Color.White),
            modifier = Modifier.height(60.dp)
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White) // Ensure no white screen appears
                .padding(paddingValues) // Avoid overlap with top bar
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            val passwordVisible by remember { mutableStateOf(false) }

            // Username Field
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Password Field with Visibility Toggle
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {},
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Login Button
            Button(
                onClick = {
                    viewModel.userLogin(
                        request = LoginRequest(
                            password = "1234",
                            plantId = 1,
                            UserName = "2"
                        )
                    )
                  //check if username and password are not empty
                    if (username.isNotEmpty() && password.isNotEmpty()) {
                        println("Login Successful")
                    } else {
                        println("Enter Username and Password")
                    }
                },
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.light_blue),
                    contentColor = Color.White
                )
            ) {
                Text("Login")
            }
        }
    }
}