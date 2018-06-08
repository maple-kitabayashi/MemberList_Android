package com.example.maple.memberlistapp

import com.example.maple.memberlistapp.api.User
import retrofit2.http.GET
import io.reactivex.Observable
import retrofit2.http.Headers

interface UserService {

    //ユーザデータを取得
    @Headers("Content-Type: application/json", "Authorization: Token FOO")
    @GET("api/v1/users")
    fun getUsers(): Observable<List<User>>
}