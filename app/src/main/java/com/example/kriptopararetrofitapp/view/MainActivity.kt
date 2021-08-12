package com.example.kriptopararetrofitapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kriptopararetrofitapp.R
import com.example.kriptopararetrofitapp.adaptor.AdaptorRecycler
import com.example.kriptopararetrofitapp.model.CryptoModel
import com.example.kriptopararetrofitapp.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), AdaptorRecycler.Listener {

    private val BASE_URL = "https://api.nomics.com/v1/"
    private lateinit var dataArrayList : ArrayList<CryptoModel>
    private lateinit var recyclerViewAdaptor : AdaptorRecycler

    //Disposable
    private var compositeDisposable : CompositeDisposable?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataArrayList = ArrayList()

        compositeDisposable = CompositeDisposable()

        //05e411de274bda9528ad2b48dd97e9ce9d7a0f24
        //https://api.nomics.com/v1/prices?key=05e411de274bda9528ad2b48dd97e9ce9d7a0f24

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager


        loadData()

    }

    private fun loadData(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)

        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handlerResponse))



        /*
        val service = retrofit.create(CryptoAPI::class.java)

        val call = service.getData()


        call.enqueue(object : Callback<List<CryptoModel>> {
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {

                        dataArrayList = ArrayList(it)

                        recyclerViewAdaptor = AdaptorRecycler(dataArrayList, this@MainActivity)
                        recyclerView.adapter = recyclerViewAdaptor
                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })

         */
    }

    private fun handlerResponse(cryptoList: List<CryptoModel>){

       dataArrayList = ArrayList(cryptoList)

        dataArrayList?.let {
        recyclerViewAdaptor = AdaptorRecycler(dataArrayList, this@MainActivity)
        recyclerView.adapter = recyclerViewAdaptor
        }

    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this, "Clicked on : ${cryptoModel.currency}", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable!!.clear()
    }
}