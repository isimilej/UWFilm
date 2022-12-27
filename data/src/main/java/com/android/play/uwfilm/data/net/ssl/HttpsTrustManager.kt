package com.android.play.uwfilm.data.net.ssl

import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

class HttpsTrustManager : X509TrustManager {

    private val _AcceptedIssuers = arrayOf<X509Certificate>()

    @Throws(CertificateException::class)
    override fun checkClientTrusted(x509Certificates: Array<X509Certificate?>?, s: String?) {
        // nothing to do
    }

    @Throws(CertificateException::class)
    override fun checkServerTrusted(x509Certificates: Array<X509Certificate?>?, s: String?) {
        // nothig to do
    }

    fun isClientTrusted(chain: Array<X509Certificate?>?): Boolean {
        return true
    }

    fun isServerTrusted(chain: Array<X509Certificate?>?): Boolean {
        return true
    }

    override fun getAcceptedIssuers(): Array<X509Certificate>? {
        return _AcceptedIssuers
    }

}