package com.example.maple.memberlistapp

interface IAPI {
    //APIの種類によって、処理を変更するため用意
    enum class TYPE {
        NOMAL, SKILL, USER
    }

    fun onApiCompleted(type: TYPE = TYPE.NOMAL)
    fun onApiFailed(type: TYPE = TYPE.NOMAL)
}