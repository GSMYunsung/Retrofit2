package com.example.retrofit2

import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo.newInstance
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.retrofit2.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.zip.Inflater
import javax.xml.datatype.DatatypeFactory.newInstance

const val Base_URL = "https://cat-fact.herokuapp.com"

class MainActivity : AppCompatActivity() {

    private var TAG = "MainActivity"

    companion object{

        private lateinit var binding : ActivityMainBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        //binding 을 여기서 사용하겠다!
        binding.setonclick = this
        getCurrentData()

    }

   fun getCurrentData() {

        binding.tvTextView.visibility = View.VISIBLE
        binding.tvTimeStamp.visibility = View.VISIBLE
        binding.progressBar.visibility = View.VISIBLE

        val api = Retrofit.Builder()
            .baseUrl(Base_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        //데이터 관리를 위해 쓰레드에 배치하는 디스패처.
        //Api를 요청하고 응답을 기다림
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getCatFacts().awaitResponse()
                if (response.isSuccessful) {
                    val data = response.body()!!
                    Log.d(TAG, data.text)

                    //텍스트뷰 가시성 업데이트
                    //만약 요청이 완료되었을경우 값이 불러와졌다는 의미이므로!
                    withContext(Dispatchers.Main) {
                        binding.tvTextView.visibility = View.VISIBLE
                        binding.tvTimeStamp.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE

                        binding.tvTextView.text = data.text
                        binding.tvTimeStamp.text = data.createdAt
                    }
                }
            }
            catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(applicationContext,"이런... 인터넷연결이 잘못된거같아요 인터넷연결을 확인해주세요",Toast.LENGTH_SHORT).show()
                }
            }
        }
   }
}