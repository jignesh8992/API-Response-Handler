package com.example.responsehandler.network

import com.example.responsehandler.network.dao.ResponseForceUpdateModel
import com.example.responsehandler.network.utilites.METHOD_APK_VERSION
import com.example.responsehandler.network.utilites.keyPkgName
import com.example.responsehandler.network.utilites.keyVerCode
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * APIInterface.kt - Retrofit API Interface.
 * @author:  Jignesh N Patel
 * @date: 22-Jan-2021 8:36 PM
 */

interface APIInterface {

    // To check if need to force update
    @FormUrlEncoded
    @POST(METHOD_APK_VERSION)
    fun isNeedToForceUpdateAsync(@Field(keyPkgName) packageName: String, @Field(keyVerCode) versionCode: Double): Deferred<Response<ResponseForceUpdateModel>>

}