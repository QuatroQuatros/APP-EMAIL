package com.example.challengelocaweb.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.challengelocaweb.R
import com.example.challengelocaweb.ui.theme.ChallengeLocaWebTheme
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TopAppBarWithSearchBar(
//    modifier: Modifier = Modifier,
//    text: String,
//    readOnly: Boolean,
//    onValueChange: (String) -> Unit,
//    onSearch: () -> Unit
//) {
//    val interactionSource = remember {
//        MutableInteractionSource()
//    }
//
//    val isClicked = interactionSource.collectIsPressedAsState().value
//    LaunchedEffect(key1 = isClicked) {
//        if (isClicked) {
//            onSearch()
//        }
//    }
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//        modifier = modifier
//            .fillMaxWidth()
//            .height(100.dp)
//
//    ) {
//        TopAppBar(
//            title = {
//                Text(
//                    text = "",
//                    style = MaterialTheme.typography.titleSmall,
//                    color = colorResource(id = R.color.white)
//                )
//            },
//            colors = TopAppBarDefaults.topAppBarColors(
//                containerColor = colorResource(id = R.color.primary),
//                scrolledContainerColor = colorResource(id = R.color.primary),
//                navigationIconContentColor = colorResource(id = R.color.white),
//                titleContentColor = colorResource(id = R.color.white),
//                actionIconContentColor =  colorResource(id = R.color.white)
//            ),
//            modifier = Modifier
//                .background(color = Color.Green)
//                .zIndex(0f)
//                .fillMaxWidth()
////                .fillMaxWidth()
//                .height(100.dp),
//            actions = {
//                IconButton(
//                    onClick = {
//
//                    }
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_home),
//                        contentDescription = "Menu Icon",
//                        tint = colorResource(id = R.color.white)
//                    )
//                }
//            }
//        )
//        // Barra de Pesquisa
//        SearchBar(
//            modifier = Modifier
//                .background(color = Color.Magenta)
//                .fillMaxWidth()
//                .zIndex(1f),
//                //.padding(horizontal = 16.dp),
//
//            text = text,
//            readOnly = readOnly,
//            onValueChange = onValueChange,
//            onSearch = onSearch
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
//@Composable
//fun TopAppBarWithSearchBarPreview() {
//    ChallengeLocaWebTheme {
//        TopAppBarWithSearchBar(
//            text = "",
//            readOnly = false,
//            onValueChange = {},
//            onSearch = {}
//        )
//    }
//}


@Composable
fun TopBarWithSearchBar(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)

    ) {
        // Top Bar
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()

                .background(color = colorResource(id =R.color.primary))
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                text = "",
                readOnly = false,
                onValueChange = {},
                onSearch = {}
            )
        }


    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TopBarWithSearchBarPreview() {
    ChallengeLocaWebTheme {
        TopBarWithSearchBar()
    }
}