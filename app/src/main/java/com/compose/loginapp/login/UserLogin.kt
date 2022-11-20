package com.compose.loginapp.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.compose.loginapp.R
import com.compose.loginapp.ui.widgets.BasicTextField
import com.jetpack.textfielddemo.ui.widgets.textField.PassWordTextField

@Composable
fun InitLogin(context: Context,viewModel: UserViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){
    when (val state = viewModel.loginResponseState.collectAsState().value) {
        is UserViewModel.LoginUiState.Loaded -> {
            UserLoginScreen(context,viewModel)
        }
        is UserViewModel.LoginUiState.Success -> {
            UserLoginScreen(context,viewModel)
            Toast.makeText(context,state.message,Toast.LENGTH_LONG).show()
        }
        is UserViewModel.LoginUiState.Error -> {
            UserLoginScreen(context,viewModel)
            Toast.makeText(context,state.error,Toast.LENGTH_LONG).show()
        }
        else -> {
            UserLoginScreen(context,viewModel)
        }
    }
}
@Composable
fun UserLoginScreen(context:Context,viewModel: UserViewModel) {
    var email  by rememberSaveable { mutableStateOf("") }
    var password    by rememberSaveable { mutableStateOf("") }
    val isInputValid by remember {
        derivedStateOf {
            email.isNotBlank() && password.isNotBlank() && password.length >= 5

        }
    }

    Surface(
        color = Color.LightGray, modifier = Modifier.fillMaxSize()
    ) {
        ConstraintLayout(
            constraintSet = SetUserLoginConstraints(),
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)) {
            val focusManager = LocalFocusManager.current
            Image(
                painter = painterResource(id = R.drawable.cart),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .layoutId("loginIcon")
                    .clip(CircleShape),
            )

            BasicTextField(
                text = email, onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .layoutId("userEmail"), label = { Text("Email") },
                placeHolderText = { Text("Enter Email") }, imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email, keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                })
            )
            PassWordTextField(text = password, onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .layoutId("userPassword"),
                label = { Text("Password") }, placeHolderText = { Text("Enter Password") },
                imeAction = ImeAction.Next, keyboardType = KeyboardType.Password,
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                })
            )

            Button(
                onClick = { viewModel.validateLogin(userEmail = email, userPassword = password) },
                enabled = isInputValid,
                modifier = Modifier.layoutId("loginBtn"),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
            ) {
                Text(text = "Login",
                     fontSize = 12.sp,
                     fontWeight = FontWeight.Medium

                   )
            }

        }
    }
}

@Composable
fun SetUserLoginConstraints():ConstraintSet {
    return ConstraintSet {
        val loginImageConstraint = createRefFor("loginIcon")
        val loginUserEmailConstraint = createRefFor("userEmail")
        val loginPasswordConstraint = createRefFor("userPassword")
        val loginBtnConstraint = createRefFor("loginBtn")

        constrain(loginImageConstraint) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }

        constrain(loginUserEmailConstraint){
            top.linkTo(loginImageConstraint.bottom, margin=10.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(loginPasswordConstraint){
            top.linkTo(loginUserEmailConstraint.bottom, margin=10.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(loginBtnConstraint){
            top.linkTo(loginPasswordConstraint.bottom, margin=10.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }
}