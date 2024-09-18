package com.example.challengelocaweb.presentation.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challengelocaweb.R
import com.example.challengelocaweb.presentation.auth.components.HeadingText
import com.example.challengelocaweb.presentation.auth.components.NormalText
import com.example.challengelocaweb.presentation.auth.components.PombitaIcon
import com.example.challengelocaweb.presentation.auth.components.RegisterButton
import com.example.challengelocaweb.presentation.auth.components.TermsAndConditions
import com.example.challengelocaweb.presentation.nvgraph.Route
import androidx.navigation.NavHostController
import com.example.challengelocaweb.presentation.auth.components.SelectIdiomas

@Composable
fun SignUpScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
) {
    var email by remember { mutableStateOf("") }
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val isUserLoggedIn by authViewModel.isUserLoggedIn.collectAsState()

    LaunchedEffect(isUserLoggedIn) {
        if (isUserLoggedIn) {
            navController.navigate(Route.HomeScreen.route) {
                popUpTo(Route.SingUpScreen.route) { inclusive = true }
            }
            authViewModel.resetRegisterSuccess()
        }
    }



    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            SelectIdiomas()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                PombitaIcon()

                NormalText(value = stringResource(id = R.string.welcome_message))
                HeadingText(value = stringResource(id = R.string.sign_up_now))

                Spacer(modifier = Modifier.height(50.dp))


                OutlinedTextField(
                    value = user,
                    onValueChange = { user = it },
                    placeholder = { Text(text = stringResource(id = R.string.create_username), fontSize = 12.sp) },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text(text = stringResource(id = R.string.create_email), fontSize = 12.sp) },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text(text = stringResource(id = R.string.create_password), fontSize = 12.sp) },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            val icon = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            Icon(imageVector = icon, contentDescription = if (isPasswordVisible) stringResource(id = R.string.hide_password) else stringResource(id = R.string.show_password))
                        }
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                TermsAndConditions(stringResource(id = R.string.agree_terms))

                Spacer(modifier = Modifier.height(30.dp))

                RegisterButton(value = stringResource(id = R.string.register)) {
                    authViewModel.register(user, email, password)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(id = R.string.msg_possui_conta_login),
                    modifier = Modifier.clickable {
                        navController.navigate(Route.LoginScreen.route)
                    },
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = if (isSystemInDarkTheme()) colorResource(id = R.color.secondaryButtonsDark) else colorResource(
                            id = R.color.navDark
                        ),
                        textDecoration = TextDecoration.Underline
                    )
                )

            }
        }
    }
}
