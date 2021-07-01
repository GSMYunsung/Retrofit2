package com.example.retrofit2.api

//Json 파일로 가져오는 값들을 담을 데이터변수

data class CatJaon(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val deleted: Boolean,
    val source: String,
    val status: Status,
    val text: String,
    val type: String,
    val updatedAt: String,
    val used: Boolean,
    val user: String
)