package de.intersales.jenkins.easyredmine

import com.cloudbees.groovy.cps.NonCPS

class EasyredmineHelper {
    
    @NonCPS
    String mapStatusNameToId(Map data) {
        def xml = new Utils().get("${data.easyredmineBaseUrl}/issue_statuses.xml", data.authKey)
        def xmlObject = new XmlSlurper().parseText(xml)
        def result = "0"
        
        xmlObject.children().each {child ->
            if (child.name == data.status) {
                println("found status id")
                result = child.'id'.text()
            }
        }
        
        println "mapped status name ${data.status} to id ${result}"
        
        return "10"
        
    }
}
