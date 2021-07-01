package com.example.retrofit2

import com.example.retrofit2.api.CatJaon
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequests {

    // Retrofit 요청값 Api 사이트에서 확인 할 수 있음!
    @GET("/facts/random")
    //CatJaon 파일 가져오기
    fun getCatFacts() : Call<CatJaon>
}