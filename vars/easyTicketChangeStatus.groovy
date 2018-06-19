#!/usr/bin/groovy
import de.intersales.jenkins.easyredmine.Utils
import groovy.xml.MarkupBuilder

/**
 *
 * @param data
 * -authKey
 * -status
 * -easyredmineBaseUrl
 * -ticketNo
 * @return
 */

def call(Map data) {
    
    def statusId = mapStatusNameToId(data)
    
    def sw = new StringWriter()
    def mb = new MarkupBuilder(sw)
    mb.issue {
        status_id( statusId )
    }
    
    println("using the following put request data:\n${sw.toString()}")
    
    new Utils().put("${data.easyredmineBaseUrl}/issues/${data.ticketNo.toString()}.xml", sw.toString(), data.authKey)
}

private def mapStatusNameToId(Map data) {
    def xml = new Utils().get("${data.easyredmineBaseUrl}/issue_statuses.xml", data.authKey)
    def xmlObject = new XmlSlurper().parseText(xml)
    def result = xmlObject."issue_status".find{it.name == data.status}.id.text()
    return result
    
    
}

return this
