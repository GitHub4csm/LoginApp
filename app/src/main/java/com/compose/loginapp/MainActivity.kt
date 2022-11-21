package com.compose.loginapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.compose.loginapp.login.InitLogin
import com.compose.loginapp.login.UserLoginScreen
import com.compose.loginapp.ui.theme.LoginAppTheme
import com.compose.loginapp.users.UserList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginAppTheme { // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                   // Greeting("Android")
                  //  InitLogin(applicationContext)
                    UserInfoApp(applicationContext)
                }
            }
        }
    }
}

@Composable
fun UserInfoApp(context: Context){
    val navController = rememberNavController()
    NavHost(navController, startDestination = "userLogin") {
        composable(route = "userLogin") {
            InitLogin(context, onNavigateToList = {
                navController.navigate("userList"){
                    popUpTo("userLogin")
                }
            }
            )
        }
        composable(route="userList"){
            UserList()
        }
    }
}

@Composable fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LoginAppTheme {
    //   UserList()
    }
}