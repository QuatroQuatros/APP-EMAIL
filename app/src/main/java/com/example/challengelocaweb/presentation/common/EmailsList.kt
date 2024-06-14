package com.example.challengelocaweb.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.presentation.Dimens.ExtraSmallPadding2
import com.example.challengelocaweb.presentation.Dimens.MediumPadding1

@Composable
fun EmailsList(
    modifier: Modifier = Modifier,
    emails: LazyPagingItems<Email>,
    onClick:(Email) -> Unit
) {

    val handlePagingResult = handlePagingResult(emails = emails)


    if(handlePagingResult){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.spacedBy(2.dp),
//            verticalArrangement = Arrangement.spacedBy(MediumPadding1),
            contentPadding = PaddingValues(all = ExtraSmallPadding2)
        ) {
            items(emails.itemCount) {
                emails[it]?.let { email ->
                    EmailCard(
                        email = email,
                        onClick = { onClick(email) }
                    )
                }
            }
        }
    }
}


@Composable
fun handlePagingResult(
    emails: LazyPagingItems<Email>,
): Boolean
{

    val loadState = emails.loadState

    val error = when{
        loadState.refresh is LoadState.Error -> {
            loadState.refresh as LoadState.Error
        }
        loadState.append is LoadState.Error -> {
            loadState.append as LoadState.Error
        }
        loadState.prepend is LoadState.Error -> {
            loadState.prepend as LoadState.Error
        }
        else -> null
    }

    return when{
        loadState.refresh is LoadState.Loading -> {
            shimmerEffect()
            false
        }
        error != null -> {
            EmptyScreen(error = error)
            false
        }
        else -> {
            true
        }
    }

}

@Composable
private fun shimmerEffect(){
    Column(verticalArrangement = Arrangement.spacedBy(MediumPadding1)) {
        repeat(10) {
            EmailCardShimmer(
                modifier = Modifier.padding(horizontal = MediumPadding1)
            )
        }
    }
}