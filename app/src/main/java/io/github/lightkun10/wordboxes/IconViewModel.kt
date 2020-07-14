package io.github.lightkun10.wordboxes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject


/**
 * Right now, I would test for fetching the data for compassion.
 */

class IconViewModel : ViewModel() {

    // global variable for MutableLiveData
    val listIcons = MutableLiveData<ArrayList<Icon>>()

    /**
     * Setter
     */
    fun setIcons() {
        val query: String = Rand(10).returnRandomTerm()
        val url = "https://api.iconfinder.com/v4/icons/search?query=$query&count=20"

        val listItems = ArrayList<Icon>()
        // val testUrl = "https://api.iconfinder.com/v4/icons/search?query=compassion&count=20" // ONLY FOR TESTING, limit to 20 per-fetch and keyword is static

        val client = AsyncHttpClient()
        client.addHeader("authorization", "Bearer ${BuildConfig.ApiKey}")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    // parsing json
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)

                    // Get icons array
                    val icons = responseObject.getJSONArray("icons")

                    // Total count of searched term
                    val totalCount: Int = icons.length() - 1

//                    // Get random icons from icons array
//                    val randomIndex: Int =
//                        Rand(totalCount).returnRandomInt()

                    // iterate through icons array
                    for (i in 0 until icons.length()) {

                        // Get random icons from icons array
                        // Get random index each iteration(?)
                        val randomIndex: Int =
                            Rand(totalCount).returnRandomInt()

                        // Fetch icons on randon index each iteration
                        val fetchIcon = icons.getJSONObject(randomIndex)
                        val icon = Icon()
                        // Get icon id
                        icon.iconId = fetchIcon.getString("icon_id")
                        // Get icon image
                        val desiredSize = fetchIcon.getJSONArray("raster_sizes").length() - 2
                        icon.iconImg = fetchIcon.getJSONArray("raster_sizes").getJSONObject(desiredSize)
                        // icon.iconImg = fetchIcon.getJSONArray("raster_sizes").getJSONObject(7)
                            .getJSONArray("formats").getJSONObject(0).getString("preview_url")
                        // Get icon tags
                        val arrTags = fetchIcon.getJSONArray("tags")
                        for (tag in 0 until arrTags.length()) {
                            icon.iconTags.add(arrTags[tag].toString())
                        }

                        listItems.add(icon)
                    }
                    listIcons.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }

    /**
     * Getter
     */
    fun getIcons(): LiveData<ArrayList<Icon>> {
        return listIcons
    }
}