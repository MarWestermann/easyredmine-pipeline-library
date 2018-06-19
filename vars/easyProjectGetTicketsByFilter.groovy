#!/usr/bin/groovy
import de.intersales.jenkins.easyredmine.EasyredmineHelper
import de.intersales.jenkins.easyredmine.Utils

/**
 *
 * @param data
 * -easyredmineBaseUrl
 * -authKey
 * -filter (map from key value to filter)
 * @return List of ticket ids
 */
List<String> call(Map data) {
    
    Map<String,Object> filterMap = data.filter
    def filterString = filterMap.collect {entry ->
        def key = entry.key
        def value = entry.value
        
        if (key == "status") {
            key = "status_id"
            value = new EasyredmineHelper().mapStatusNameToId(data.easyredmineBaseUrl as String, data.authKey as String, value as String)
        }
        
        "${key}=${value}"}.join("&")
    
    def response = new Utils().get("${data.easyredmineBaseUrl}/issues.xml?${filterString}".toString(), data.authKey.toString())
    println("GOT TICKETS: $response")
    
    def xmlObject = new XmlSlurper().parseText(response)
    
    return xmlObject.issue.collect{it.id.text()}
    
}
