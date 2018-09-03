#!/usr/bin/groovy
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

/**
 *
 * @param data
 * - url: the url to post data to
 * - requestMessage: body of the request
 * @return
 */
def call(Map data) {
    String url = data.url

    def client = new OkHttpClient()
    def request = new Request.Builder()
            .url(url)
            .get()
            .build()
    
    def response = client.newCall(request).execute()
    if (!(200..299).contains(response.code())) {
        throw new Exception("POST to url $url failed with status code ${response.code()}")
    }
    return response.body().string()
}

return this