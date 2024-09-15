package com.example.challengelocaweb.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.challengelocaweb.AuthViewModel
import com.example.challengelocaweb.R
import com.example.challengelocaweb.presentation.nvgraph.Route

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
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            iconSignUpScreen()

            NormalTextComposable(value = "Bem vindo(a) ao PombitaMail!")
            HeadingTextComposable(value = "Cadastre-se agora")

            Spacer(modifier = Modifier.height(50.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(text = "Crie seu e-mail", fontSize = 15.sp) },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = user,
                onValueChange = { user = it },
                placeholder = { Text(text = "Crie seu usuário", fontSize = 15.sp) },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(text = "Crie sua senha", fontSize = 15.sp) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        val icon = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        Icon(imageVector = icon, contentDescription = if (isPasswordVisible) "Esconder senha" else "Mostrar senha")
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

            TermsAndConditions("Concordo com a Política de Privacidade e os Termos de uso.")

            Spacer(modifier = Modifier.height(30.dp))

            RegisterAndLoginButton(value = "Cadastrar") {
                authViewModel.register(user, email, password)
            }

            Spacer(modifier = Modifier.height(20.dp))

            RegisterAndLoginButton("Faça Login") {
                navController.navigate(Route.LoginScreen.route)
            }
        }
    }
}


@Composable
fun NormalTextComposable(value: String) {
    Text(text = value,
        modifier = Modifier
            .fillMaxWidth()
            //.padding(top = 40.dp)
            .heightIn(min = 35.dp),
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal),
        textAlign = TextAlign.Center,
        color = if (isSystemInDarkTheme()) colorResource(id = R.color.textDark) else colorResource(id = R.color.textLight),
    )
}

@Composable
fun HeadingTextComposable(value: String) {
    Text(text = value,
        modifier = Modifier
            .fillMaxWidth(),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal),
        textAlign = TextAlign.Center,
        color = if (isSystemInDarkTheme()) colorResource(id = R.color.textDark) else colorResource(id = R.color.textLight),
    )
}

@Composable
fun TermsAndConditions(value: String){
    Row(modifier = Modifier
        .width(300.dp)
        .height(65.dp)
        .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically) {

        var checkedState = remember {
            mutableStateOf(false)
        }

        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = !checkedState.value },
        )

        Text(text = value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            style = TextStyle(
                fontSize = 12.sp,
                fontStyle = FontStyle.Normal),
            textAlign = TextAlign.Center,
            color = if (isSystemInDarkTheme()) colorResource(id = R.color.formBlue) else colorResource(id = R.color.textLight),
        )
    }
}

@Composable
fun RegisterAndLoginButton(value: String, onClick: () -> Unit) {
    Button(onClick = { onClick() },
        modifier = Modifier
            .width(150.dp)
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
        Box(modifier = Modifier
            .width(150.dp)
            .heightIn(48.dp)
            .background(
                color = if (isSystemInDarkTheme()) colorResource(id = R.color.secondaryButtonsDark) else colorResource(
                    id = R.color.navDark
                ),
                shape = RoundedCornerShape(30.dp)
            ),
            contentAlignment = Alignment.Center
            )
        {
            Text(text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun iconSignUpScreen() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.pombitaicon),
            contentDescription = "Pombita",
            modifier = Modifier
                .size(130.dp)
                .padding(16.dp)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SignUpScreenPreview(){
//    SignUpScreen()
//}