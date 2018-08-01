package feedrss.dev.aporia.com.rssfeed.data.network

import io.reactivex.Single
import me.toptas.rssconverter.RssFeed
import retrofit2.http.GET
import retrofit2.http.Url

interface WebService {

    @GET
    fun getRss(@Url url: String): Single<RssFeed>
}