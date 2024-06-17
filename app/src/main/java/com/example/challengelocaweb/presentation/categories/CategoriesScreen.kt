package com.example.challengelocaweb.presentation.categories

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.challengelocaweb.R
import com.example.challengelocaweb.presentation.event.components.FloatingActionButton
import com.example.challengelocaweb.presentation.nvgraph.Route

data class Category(
    val name: String,
    val icon: Painter,
    val color: Color,
    val subcategories: List<String> = emptyList(),
    val onClick: () -> Unit
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CategoriesScreen(
    navController: NavHostController
) {
    val categories = listOf(
        Category(
            "Enviados",
            painterResource(id = R.drawable.ic_send),
            color = colorResource(id = R.color.primary),
            onClick = {}
        ),
        Category("Favoritos", painterResource(id = R.drawable.ic_star_favorite), colorResource(id = R.color.favorite), onClick = {}),
        Category("Rascunhos", painterResource(id = R.drawable.ic_draft), color= colorResource(id = R.color.reminder), onClick = {}),
        Category("Spam", painterResource(id = R.drawable.ic_spam), color= colorResource(id = R.color.delete_color), onClick = {}),
        Category("FIAP", painterResource(id = R.drawable.ic_bookmark), color= Color.Red, onClick = {}),
        Category("Alura", painterResource(id = R.drawable.ic_bookmark), color= Color(0xFF007BFF), onClick = {}),
        Category("Vagas de emprego", painterResource(id = R.drawable.ic_bookmark), color= colorResource(
            id = R.color.meeting), listOf("InfoJobs", "Linkedin"), onClick = {}),
        Category("Interesses", painterResource(id = R.drawable.ic_bookmark), color= Color.Gray, onClick = {}),
        Category("Lixeira", painterResource(id = R.drawable.ic_delete_outline), color= colorResource(
            id = R.color.danger), onClick = {})
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {  })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                text = "Categorias",
                fontSize = 24.sp,
                color = colorResource(id = R.color.primary),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center

            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(categories) { category ->
                    CategoryItem(
                        category = category,
                        onClick = {
                            if (category.name == "Favoritos") {
                                navController.navigate(Route.FavoriteEmailsScreen.route)
                            }
                            if (category.name == "Spam") {
                                navController.navigate(Route.SpamEmailsScreen.route)
                            }
                        }
                    )
                }
            }

        }
    }
}



@Composable
fun CategoryItem(category: Category, onClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                    onClick()
                }
                .padding(vertical = 8.dp)
        ) {
            Icon(
                painter = category.icon ?: painterResource(id = R.drawable.ic_bookmark),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = category.color ?: colorResource(id = R.color.primary)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = category.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.weight(1f))
            if (category.subcategories.isNotEmpty()) {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        if (expanded && category.subcategories.isNotEmpty()) {
            Column(modifier = Modifier.padding(start = 32.dp)) {
                category.subcategories.forEach { subcategory ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* Handle subcategory click */ }
                            .padding(vertical = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_bookmark),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = colorResource(id = R.color.primary)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = subcategory,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    CategoriesScreen(navController = NavHostController(LocalContext.current))
}