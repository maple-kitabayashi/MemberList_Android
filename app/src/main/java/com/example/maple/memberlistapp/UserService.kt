package com.example.maple.memberlistapp

import com.example.maple.memberlistapp.api.MyAccount
import com.example.maple.memberlistapp.api.Skill
import com.example.maple.memberlistapp.api.User
import io.reactivex.Observable
import retrofit2.http.*

interface UserService {

    //ユーザデータを取得
    @Headers("Authorization: Token GfjrNvFaqj6Ebhda")
    @GET("api/v1/employees")
    fun getUsers(
            @Query("lastUpDate") lastUpDate: String //最終更新日
    ): Observable<List<User>>

    //ログイン処理
    @Headers("Authorization: Token GfjrNvFaqj6Ebhda")
    @POST("login/login")
    fun tryLogin(
            @Query("email")    email:    String,
            @Query("password") password: String
    ): Observable<List<MyAccount>>

    //スキル一覧取得
    @Headers("Authorization: Token GfjrNvFaqj6Ebhda")
    @GET("api/v1/employeeskills")
    fun getSkills(
            @Query("lastUpDate") lastUpDate: String
    ): Observable<List<Skill>>
}