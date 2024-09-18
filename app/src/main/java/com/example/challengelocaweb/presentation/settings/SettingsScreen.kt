package com.example.challengelocaweb.presentation.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.challengelocaweb.R
import com.example.challengelocaweb.presentation.auth.components.HeadingText
import com.example.challengelocaweb.presentation.auth.components.NormalText
import com.example.challengelocaweb.presentation.auth.components.RegisterButton
import com.example.challengelocaweb.presentation.auth.components.SelectIdiomas
import kotlinx.coroutines.launch
import java.util.Locale

@SuppressLint("RememberReturnType")
@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel
) {

    var name by remember { mutableStateOf(viewModel.userName.value) }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val userId by viewModel.userId.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val userPhoto by viewModel.userPhoto.collectAsState()
    val selectedTheme by viewModel.selectedTheme.collectAsState()

    // Estado para o Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    fun formatThemeName(theme: String): String {
        return theme.replace("_", " ")
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
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
            // Componente de idioma no topo
            SelectIdiomas()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = userPhoto),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Nome do usuário abaixo da imagem
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                NormalText(value = stringResource(id = R.string.user_settings))
                HeadingText(value = stringResource(id = R.string.change_settings))

                Spacer(modifier = Modifier.height(50.dp))

                // Campo para editar o nome
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text(text = stringResource(id = R.string.create_username), fontSize = 12.sp) },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Campo para editar a senha
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text(text = stringResource(id = R.string.update_password), fontSize = 12.sp) },
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

                // Seção de tema
                NormalText(value = stringResource(id = R.string.select_theme))

                Spacer(modifier = Modifier.height(10.dp))

                // Radio buttons para seleção de tema
                Column {
                    listOf("light", "dark", "system_default").forEach { theme ->
                        Row(
                            Modifier
                                .clickable { viewModel.applyUserThemePreference(theme) }
                                .padding(4.dp)
                        ) {
                            RadioButton(
                                selected = selectedTheme == theme,
                                onClick = { viewModel.applyUserThemePreference(theme) }
                            )
                            Text(text = formatThemeName(theme))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Botão para salvar as alterações
                RegisterButton(
                    value = stringResource(id = R.string.save_changes),
                    onClick = {
                        viewModel.updateUser(userId, name)

                        // Exibir mensagem de sucesso ao salvar
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Usuário atualizado com sucesso!",
                                actionLabel = "Fechar"
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        snackbar = { data ->
                            Snackbar(
                                modifier = Modifier.align(Alignment.Center),
                                snackbarData = data,
                                containerColor = colorResource(id = R.color.primary),
                                contentColor = Color.White
                            )
                        }
                    )
                }
            }
        }
    }
}
