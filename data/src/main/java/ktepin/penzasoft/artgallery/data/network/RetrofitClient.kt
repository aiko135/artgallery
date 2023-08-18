package ktepin.penzasoft.artgallery.data.network

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        private const val AUTH_TOKEN = "N3W2xFRQEuWz3UFGqMqJjNtHo-8mvd-83UPovaW7dwU"
        private const val API_DOMAIN = "https://api.unsplash.com"

        private val client = OkHttpClient.Builder()
            .connectTimeout(200, TimeUnit.SECONDS)
            .readTimeout(200, TimeUnit.SECONDS)
            .addInterceptor {
                val req = it.request().newBuilder().addHeader("client_id", AUTH_TOKEN).build();
                it.proceed(req)
            }
            .build()

        fun <T: Any> getTypedRetrofitInstance(ofClass: Class<T>):T{
            return Retrofit.Builder()
                .baseUrl(API_DOMAIN).client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(ofClass)
        }
    }
}