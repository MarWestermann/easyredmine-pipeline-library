#!/usr/bin/groovy
import de.intersales.jenkins.easyredmine.Utils

/**
 *
 * @param data
 * - easyredmineBaseUrl
 * - ticketNo
 * - authKey
 * @return GPathResult containing ticket data inclusive journal entries
 */
def call(Map data) {
    
    def resultXml = new Utils().get("${data.easyredmineBaseUrl}/issues/${data.ticketNo.toString()}.xml?include=journals", data.authKey)
    println "Got $resultXml"
    return new XmlSlurper().parseText(resultXml)
}

return this