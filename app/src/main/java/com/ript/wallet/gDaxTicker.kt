package com.ript.wallet

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import com.squareup.moshi.Json
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.Stream
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.retry.ExponentialWithJitterBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable
import io.reactivex.internal.operators.flowable.FlowableBlockingSubscribe.subscribe

//import kotlinx.android.synthetic.main.activity_g_dax_ticker.*
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class gDaxTicker : AppCompatActivity() {

    val GDAX_URL = "wss://ws-feed.gdax.com"
    //val lifecycle = AndroidLifecycle.ofApplicationForeground(application)
    val BACKOFF_STRATEGY = ExponentialWithJitterBackoffStrategy(5000, 5000)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_g_dax_ticker)
//        setSupportActionBar(toolbar)

        val okHttpClientKot = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()

        val scarletSocket = Scarlet.Builder()
                .webSocketFactory(okHttpClientKot.newWebSocketFactory(GDAX_URL))
                .addMessageAdapterFactory(MoshiMessageAdapter.Factory())
                .backoffStrategy(BACKOFF_STRATEGY)
                .build()

        val gdaxService = scarletSocket.create<GdaxService>()


        val dis = gdaxService.observeTicker().subscribe({
            if (it is TickerResponse) {
                // Initialise parameters
                val productIds = listOf("ETH-BTC")
                val tickerRequests = listOf(TickerRequest(productIds = productIds))
                val bitcoinSubscribeAction = Subscribe(productIds = productIds, channels = tickerRequests)

                // Subscribe to Bitcoin ticker
                gdaxService.sendSubscribe(bitcoinSubscribeAction)
            }
        }, { error ->
            Log.e("Tag", "Error while observing socket ${error.cause}")
        })


    }

    interface GdaxService {
        @Send
        fun sendSubscribe(subscribe: Subscribe)
        @Receive
        fun observeTicker(): Flowable<TickerResponse>
        @Receive
        fun observeOnConnectionOpenedEvent(): Flowable<WebSocket.Event>
    }



    data class Subscribe(
            @Json(name = "type") val type: String = "subscribe",
            @Json(name = "product_ids") val productIds: List<String>,
            @Json(name = "channels") val channels: List<TickerRequest>
    )

//    val BITCOIN_TICKER_SUBSCRIBE_MESSAGE = Subscribe(
//            productIds = listOf("BTC-USD"),
//            channels = listOf("ticker")
//    )

    data class TickerRequest(
            @Json(name = "name") val name: String = "ticker",
            @Json(name = "product_ids") val productIds: List<String>
    )

    data class TickerResponse(
            @Json(name = "price") val price: Double
    )

//    fun createAppForegroundAndUserLoggedInLifecycle(): Lifecycle {
//        return AndroidLifecycle.ofApplicationForeground(application)
//                .combineWith(loggedInLifecycle)
//    }
}
