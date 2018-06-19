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
    def xml = """<issue><status_id>$statusId</status_id></issue>"""
    
    new Utils().put("${data.easyredmineBaseUrl}/issues/${data.ticketNo.toString()}.xml",
            xml, data.authKey)
}

private def mapStatusNameToId(Map data) {
    def xml = new Utils().get("${data.easyredmineBaseUrl}/issue_statuses.xml", data.authKey)
    def xmlObject = new XmlSlurper().parseText(xml)
    def result = xmlObject."issue_status".find{it.name == data.status}.id.text()
    return result
    
    
}

return this
