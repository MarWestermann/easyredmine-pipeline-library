#!/usr/bin/groovy
package de.intersales.jenkins.easyredmine

import com.cloudbees.groovy.cps.NonCPS
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class Utils {
    
    @NonCPS
    String get(String resourceUrl, String token) {
        def url = addKeyParamToUrl(resourceUrl, token)
        def client = new OkHttpClient()
        def request = new Request.Builder()
                .url(url)
                .addHeader("content-type", "application/xml; charset=utf-8")
                .build()
        def response = client.newCall(request).execute()
        if (!(200..299).contains(response.code())) {
            throw new Exception("POST to easyredmine with url $url failed with status code ${response.code()}")
        }
        return response.body().string()
    }
    
    @NonCPS
    String post(String resourceUrl, String body, String token) {
        def url = addKeyParamToUrl(resourceUrl, token)
        def client = new OkHttpClient()
        def XML = MediaType.parse("application/xml; charset=utf-8")
        def requestBody = RequestBody.create(XML, body)
        def request = new Request.Builder()
                .url(url)
                .post(requestBody)
                
                .build()
        def response = client.newCall(request).execute()
        if (!(200..299).contains(response.code())) {
            throw new Exception("POST to easyredmine with url $url failed with status code ${response.code()}")
        }
        return response.body().string()
    }
    
    @NonCPS
    String put(String resourceUrl, String body, String token) {
        def url = addKeyParamToUrl(resourceUrl, token)
        def client = new OkHttpClient()
        def XML = MediaType.parse("application/xml")
        def requestBody = RequestBody.create(XML, body)
        def request = new Request.Builder()
                .url(url)
                .put(requestBody)
                
                .build()
        def response = client.newCall(request).execute()
        if (!(200..299).contains(response.code())) {
            throw new Exception("PUT to easyredmine with url $url failed with status code ${response.code()}\n${response.body().string()}")
        }
        return response.body().string()
    }
    
    @NonCPS
    private String addKeyParamToUrl(String resourceUrl, String token) {
        if (resourceUrl.contains("?") && resourceUrl.contains("key="))
            return resourceUrl
        else if (resourceUrl.contains("?")) {
            return resourceUrl + "&key=$token"
        } else {
            return resourceUrl + "?key=$token"
        }
    }
}