package de.intersales.jenkins.easyredmine

class EasyredmineHelper {
    
    String mapStatusNameToId(Map data) {
        def xml = new Utils().get("${data.easyredmineBaseUrl}/issue_statuses.xml", data.authKey)
        def xmlObject = new XmlSlurper().parseText(xml)
        def result = xmlObject."issue_status".find{it.name == data.status}.id.text()
        return result
        
        
    }
}
