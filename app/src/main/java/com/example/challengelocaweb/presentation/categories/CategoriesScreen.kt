package com.example.challengelocaweb.presentation.categories

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.example.challengelocaweb.presentation.nvgraph.Route
import com.example.challengelocaweb.ui.theme.ChallengeLocaWebTheme

data class Category(
    val name: String,
    val icon: Painter,
    val color: Color,
    val subcategories: List<String> = emptyList(),
    val onClick: () -> Unit
)

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CategoriesScreen(
    navController: NavHostController
) {

    val mainCategories = listOf(
        Category(
            "Enviados",
            painterResource(id = R.drawable.ic_send),
            color = if (isSystemInDarkTheme()) colorResource(id = R.color.detailsFirst) else colorResource(
                id = R.color.navLight
            ),
            onClick = {}
        ),
        Category("Rascunhos", painterResource(id = R.drawable.ic_draft), color = if (isSystemInDarkTheme()) colorResource(id = R.color.detailsFirst) else colorResource(
            id = R.color.navLight
        ), onClick = {}),
    )

    val categories = listOf(
        Category("Favoritos", painterResource(id = R.drawable.ic_star_favorite), colorResource(id = R.color.favorite), onClick = {}),
        Category("Spam", painterResource(id = R.drawable.ic_spam), color= colorResource(id = R.color.delete_color), onClick = {}),
        Category("FIAP", painterResource(id = R.drawable.ic_bookmark), color= Color.Red, onClick = {}),
        Category("Alura", painterResource(id = R.drawable.ic_bookmark), color= Color(0xFF007BFF), onClick = {}),
        Category("Vagas de emprego", painterResource(id = R.drawable.ic_bookmark), color= colorResource(
            id = R.color.detailsSecond), listOf("InfoJobs", "Linkedin"), onClick = {}),
        Category("Interesses", painterResource(id = R.drawable.ic_bookmark), color= Color.Gray, onClick = {}),
        Category("Lixeira", painterResource(id = R.drawable.ic_delete_outline), color= colorResource(
            id = R.color.danger), onClick = {})
    )


    ChallengeLocaWebTheme(dynamicColor = true, darkTheme = isSystemInDarkTheme()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(onClick = { })

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(50.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(vertical = 16.dp),
                            text = "Categorias",
                            fontSize = 24.sp,
                            color = if (isSystemInDarkTheme()) colorResource(id = R.color.textDark) else colorResource(id = R.color.textLight),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start
                        )
                        Icon(imageVector = Icons.Default.Settings,
                            contentDescription = "Configurações",
                            tint = if (isSystemInDarkTheme()) colorResource(id = R.color.textDark) else colorResource(id = R.color.shimmer),
                            modifier = Modifier
                                .padding(vertical = 17.dp))
                    }


                    Box(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(
                                color = if (isSystemInDarkTheme()) colorResource(id = R.color.detailsFirst) else colorResource(
                                    id = R.color.shimmer
                                )
                            ))
                    }

                    Spacer(modifier = Modifier.height(19.dp))

                    LazyColumn(
                        //verticalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .weight(3f)
                            .fillMaxHeight()
                            .padding(vertical = 10.dp),
                    )
                    {
                        items(mainCategories) { category ->
                            Spacer(modifier = Modifier.height(16.dp))

                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .fillMaxHeight()
                                    .background(
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(start = 20.dp)
                            )
                            {
                                CategoryItem(
                                    category = category,
                                    onClick = {
                                        when (category.name) {
                                            "Enviados" -> navController.navigate(Route.SentEmailsScreen.route)
                                        }
                                    }
                                )
                            }
                        }

                        items(categories) { category ->
                            Spacer(modifier = Modifier.height(16.dp))

                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .fillMaxHeight()
                                    .border(
                                        BorderStroke(
                                            1.dp,
                                            color = MaterialTheme.colorScheme.surfaceVariant
                                        ),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(start = 20.dp, end = 10.dp)
                            )
                            {
                                CategoryItem(
                                    category = category,
                                    onClick = {
                                        when (category.name) {
                                            "Favoritos" -> navController.navigate(Route.FavoriteEmailsScreen.route)
                                            "Spam" -> navController.navigate(Route.SpamEmailsScreen.route)
                                        }
                                    }
                                )
                            }
                        }
                    }

                }
            }
        }

    }
}





@Composable
fun CategoryItem(category: Category, onClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
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
            Column(modifier = Modifier
                .padding(start = 32.dp)
                .fillMaxSize()
            ) {
                category.subcategories.forEach { subcategory ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    CategoriesScreen(navController = NavHostController(LocalContext.current))
}