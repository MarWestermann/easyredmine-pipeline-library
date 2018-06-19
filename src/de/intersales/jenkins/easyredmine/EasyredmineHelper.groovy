package de.intersales.jenkins.easyredmine

import com.cloudbees.groovy.cps.NonCPS

class EasyredmineHelper {
    
    @NonCPS
    String mapStatusNameToId(String easyredmineBaseUrl, String authKey, String statusName) {
        def statusXmlResult = new Utils().get("${easyredmineBaseUrl}/issue_statuses.xml", authKey)
        def xmlObject = new XmlSlurper().parseText(statusXmlResult)
        def result = "0"
        
        xmlObject.children().each {child ->
            if (child.name.text() == statusName) {
                println("found status id")
                result = child.'id'.text()
            }
        }
        
        println "mapped status name ${statusName} to id ${result}"
        
        return result
        
    }
}
