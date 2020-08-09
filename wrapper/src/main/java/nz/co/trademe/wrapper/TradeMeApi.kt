package nz.co.trademe.wrapper

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import nz.co.trademe.wrapper.auth.SecurityInterceptor
import nz.co.trademe.wrapper.service.ListingService
import nz.co.trademe.wrapper.service.SingleListService
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://api.tmsandbox.co.nz/"
private const val USER_AGENT = "TradeMeTechTest"

object TradeMeApi {

    private val securityInterceptor: SecurityInterceptor
        get() = SecurityInterceptor(USER_AGENT)

    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .addInterceptor(securityInterceptor)
            .build()

    private val converterFactory: Converter.Factory
        get() = MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build())

     private val retrofit
        get() = Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()

    val listingService: ListingService
        get() = retrofit.create(ListingService::class.java)

    val singleListService: SingleListService
        get() = retrofit.create(SingleListService::class.java)

}
