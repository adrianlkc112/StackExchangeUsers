package com.cityline.server.ssl

import android.os.Build
import com.adrianlkc112.stackexchangeusers.util.LogE
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import java.net.UnknownHostException
import java.security.KeyStore
import javax.net.ssl.*

//https://ankushg.com/posts/tls-1.2-on-android/
//https://gist.github.com/ankushg/8c0c3144318b1c17abb228d6211ba996
//https://developer.android.com/reference/javax/net/ssl/SSLSocket.html
//https://stackoverflow.com/a/42468981
class Tls12SocketFactory(private val delegate: SSLSocketFactory) : SSLSocketFactory() {
    companion object {
        /**
         * @return [X509TrustManager] from [TrustManagerFactory]
         *
         * @throws [NoSuchElementException] if not found. According to the Android docs for [TrustManagerFactory], this
         * should never happen because PKIX is the only supported algorithm
         */
        private val trustManager by lazy {
            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(null as KeyStore?)
            trustManagerFactory.trustManagers
                    .first { it is X509TrustManager } as X509TrustManager
        }

        /**
         * If on [Build.VERSION_CODES.LOLLIPOP] or lower, sets [OkHttpClient.Builder.sslSocketFactory] to an instance of
         * [Tls12SocketFactory] that wraps the default [SSLContext.getSocketFactory] for [TlsVersion.TLS_1_2].
         *
         * Does nothing when called on [Build.VERSION_CODES.LOLLIPOP_MR1] or higher.
         *
         * For some reason, Android supports TLS v1.2 from [Build.VERSION_CODES.JELLY_BEAN], but the spec only has it
         * enabled by default from API [Build.VERSION_CODES.KITKAT]. Furthermore, some devices on
         * [Build.VERSION_CODES.LOLLIPOP] don't have it enabled, despite the spec saying they should.
         *
         * @return the (potentially modified) [OkHttpClient.Builder]
         */
        @JvmStatic
        //fun OkHttpClient.Builder.enableTls12() = apply {
        fun enableTls12(builder: OkHttpClient.Builder): OkHttpClient.Builder {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
                try {
                    val sslContext = SSLContext.getInstance(TlsVersion.TLS_1_2.javaName())
                    sslContext.init(null, arrayOf(trustManager), null)

                    builder.sslSocketFactory(Tls12SocketFactory(sslContext.socketFactory), trustManager)
                } catch (e: Exception) {
                    LogE("Error while setting TLS 1.2 compatibility: $e")
                }
            }

            return builder
        }
    }

    /**
     * Forcefully adds [TlsVersion.TLS_1_2] as an enabled protocol if called on an [SSLSocket]
     *
     * @return the (potentially modified) [Socket]
     */
    private fun enableTLSOnSocket(socket: Socket?): Socket? {
        if (socket != null && socket is SSLSocket) {
            socket.enabledProtocols = arrayOf(TlsVersion.TLS_1_1.javaName(), TlsVersion.TLS_1_2.javaName())
        }
        return socket
    }

    override fun getDefaultCipherSuites(): Array<String> {
        return delegate.defaultCipherSuites
    }

    override fun getSupportedCipherSuites(): Array<String> {
        return delegate.supportedCipherSuites
    }

    @Throws(IOException::class)
    override fun createSocket(s: Socket, host: String, port: Int, autoClose: Boolean): Socket? {
        return enableTLSOnSocket(delegate.createSocket(s, host, port, autoClose))
    }

    @Throws(IOException::class, UnknownHostException::class)
    override fun createSocket(host: String, port: Int): Socket? {
        return enableTLSOnSocket(delegate.createSocket(host, port))
    }

    @Throws(IOException::class, UnknownHostException::class)
    override fun createSocket(host: String, port: Int, localHost: InetAddress, localPort: Int): Socket? {
        return enableTLSOnSocket(delegate.createSocket(host, port, localHost, localPort))
    }

    @Throws(IOException::class)
    override fun createSocket(host: InetAddress, port: Int): Socket? {
        return enableTLSOnSocket(delegate.createSocket(host, port))
    }

    @Throws(IOException::class)
    override fun createSocket(address: InetAddress, port: Int, localAddress: InetAddress, localPort: Int): Socket? {
        return enableTLSOnSocket(delegate.createSocket(address, port, localAddress, localPort))
    }
}