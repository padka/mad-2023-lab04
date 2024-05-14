package com.example.myapplication4

import java.io.Serializable

data class Card(
    val id: String,
    val question: String,
    val example: String,
    val answer: String,
    val translate: String,
    val imageUri: String
) : Serializable
