package com.example.maple.memberlistapp

import com.example.maple.memberlistapp.api.User
import retrofit2.http.GET
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers

interface UserService {

    //ユーザデータを取得
    @FormUrlEncoded
    @Headers("Authorization: Token GfjrNvFaqj6Ebhda")
    @GET("api/v1/employees")
    fun getUsers(
            @Field("lastUpDate") lastUpDate: String //最終更新日
    ): Observable<List<User>>
}