package de.intersales.jenkins.easyredmine

import com.cloudbees.groovy.cps.NonCPS

class EasyredmineHelper {
    
    @NonCPS
    String mapStatusNameToId(Map data) {
        def xml = new Utils().get("${data.easyredmineBaseUrl}/issue_statuses.xml", data.authKey)
        def xmlObject = new XmlSlurper().parseText(xml)
        def result = "0"
        xmlObject."issue_status".each {issueStatus ->
            if (issueStatus.name == data.status) {
                println("found status id")
                result = issueStatus.'id'.text()
            }
        }
        
        println("mapped status name ${data.status} to id ${result}")
        
        return result
        
    }
}
