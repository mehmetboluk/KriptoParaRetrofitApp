package com.example.kriptopararetrofitapp.service

import com.example.kriptopararetrofitapp.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {

    //GET, POST, UPDATE, DELETE
    //05e411de274bda9528ad2b48dd97e9ce9d7a0f24
    //https://api.nomics.com/v1/
    // prices?key=05e411de274bda9528ad2b48dd97e9ce9d7a0f24

    @GET("prices?key=05e411de274bda9528ad2b48dd97e9ce9d7a0f24")

    //fun getData():Call<List<CryptoModel>>

    fun getData() : Observable<List<CryptoModel>>

}