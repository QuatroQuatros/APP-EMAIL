package com.example.challengelocaweb.presentation.onBoarding

import androidx.annotation.DrawableRes
import com.example.challengelocaweb.R


data class Page(
    val title:String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "batata",
        description = "batata boladona",
        image = R.drawable.batata
    ),
    Page(
        title = "pepino",
        description = "pepinão",
        image = R.drawable.pepino
    ),
    Page(
        title = "tomate",
        description = "teste3",
        image = R.drawable.batata
    )
)
