package nz.co.trademe.wrapper.auth

import nz.co.trademe.wrapper.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class SecurityInterceptor(private val userAgent: String) : Interceptor {

    private val authorizationHeader
        get() = "OAuth " +
            "oauth_consumer_key=\"${BuildConfig.TRADEME_CONSUMER_KEY}\", " +
            "oauth_signature_method=\"PLAINTEXT\", " +
            "oauth_signature=\"${BuildConfig.TRADEME_CONSUMER_SECRET}&\""

    override fun intercept(chain: Interceptor.Chain): Response = with(chain.request()) {
        newBuilder()
            .header("User-Agent", userAgent)
            .apply {
                when (header("Authorization")) {
                    null -> header("Authorization", authorizationHeader)
                }
            }
            .build()
    }.let(chain::proceed)


}
