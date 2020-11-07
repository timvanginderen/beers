package be.tim.beers.data.remote

import android.content.Context
import be.tim.beers.data.local.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context?) : Interceptor {
//    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

//        sessionManager.fetchAuthToken()?.let {
//            requestBuilder.addHeader("Authorization", "Bearer $it")
//        }

        requestBuilder.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2Y2QzNmZmZS00M2NlLTExZTgtODQ2YS0wYjllZWNlOTZkNWEiLCJpYXQiOjE2MDQ1ODUyODgsImF1ZCI6IlNJTFZFUkJBQ0siLCJpc3MiOiJzaWx2ZXJiYWNrIn0.Gu0juCz8KiLAtrGWZuqRsmzOrP0DeyHd9ke8oMqK-F8")

        return chain.proceed(requestBuilder.build())
    }
}