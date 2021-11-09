package kaan.tabcorp.data

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class RetrofitProvider(
    private val baseUrl: String,
    private val converterFactory: Converter.Factory,
    private val interceptors: List<Interceptor>
) {

    val retrofit: Retrofit
        get() = with(Retrofit.Builder()) {
            baseUrl(baseUrl)
            client(getClient())
            addConverterFactory(converterFactory)
            build()
        }

    private fun getClient(): OkHttpClient =
        with(OkHttpClient.Builder()) {
            connectTimeout(15000, TimeUnit.MILLISECONDS)
            readTimeout(15000, TimeUnit.MILLISECONDS)
            writeTimeout(15000, TimeUnit.MILLISECONDS)
            addInterceptors()
            build()
        }


    private fun OkHttpClient.Builder.addInterceptors() {
        interceptors.forEach {
            addInterceptor(it)
        }
    }

}

//    private fun OkHttpClient.Builder.addCertificates() {
//        if (!domain.certificate.enablePinning) {
//            return
//        }
//        val certificate = domain.certificate
//        val certificatePinner =
//            with(CertificatePinner.Builder()) {
//                certificate.certificates.forEach { entry ->
//                    entry.value.forEach {
//                        add(entry.key, it)
//                    }
//                }
//                build()
//            }
//        this.certificatePinner(certificatePinner)
//    }

//    private fun OkHttpClient.Builder.addDefaultHeaders(domain: Domain) {
//        addInterceptor { chain: Interceptor.Chain ->
//            val builder = chain.request().newBuilder()
//            domain.defaultHeaders.forEach {
//                builder.addHeader(it.key, it.value)
//            }
//            chain.proceed(builder.build())
//        }
//    }
