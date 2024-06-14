package com.example.challengelocaweb.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.paging.compose.LazyPagingItems
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.challengelocaweb.R
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.presentation.Dimens.MediumPadding1
import com.example.challengelocaweb.presentation.common.CustomNavigationBar
import com.example.challengelocaweb.presentation.common.EmailsList
import com.example.challengelocaweb.presentation.common.SearchBar
import com.example.challengelocaweb.presentation.nvgraph.Route


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    emails: LazyPagingItems<Email>,
    navigate:(String) -> Unit,

) {

    val titles by remember {
        derivedStateOf {
            if(emails.itemCount > 10){
                emails.itemSnapshotList.items
                    .slice(IntRange(start = 0, endInclusive = 9))
                    .joinToString(separator = "\uD83d\uDF35") { it.title }
            }else{
                ""
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
//            .padding(top = MediumPadding1)
            .statusBarsPadding()
    ){



//        SearchBar(
//            modifier = Modifier
//                .fillMaxWidth(0.6f)
//                .align(Alignment.CenterHorizontally),
//            text = "",
//            readOnly = false,
//            onValueChange = {},
//            onClick = {
//                navigate(Route.SearchScreen.route)
//            },
//            onSearch = {}
//        )

//        Spacer(modifier = Modifier.height(MediumPadding1))

//        Text( text= titles, modifier = Modifier
//            .fillMaxWidth()
//            .padding(start = MediumPadding1)
//            .basicMarquee(),
//            fontSize = 12.sp,
//            color = colorResource(id = R.color.placeholder)
//        )

//        Spacer(modifier = Modifier.height(MediumPadding1))

        EmailsList(
            modifier = Modifier.padding(horizontal = MediumPadding1),
            emails = emails,
            onClick = {
                navigate(Route.DetailScreen.route)
            }
        )



    }

}