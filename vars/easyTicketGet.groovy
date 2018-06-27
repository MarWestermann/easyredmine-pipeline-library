#!/usr/bin/groovy
import de.intersales.jenkins.easyredmine.NotFoundException
import de.intersales.jenkins.easyredmine.Utils
import org.omg.CosNaming.NamingContextPackage.NotFound

/**
 *
 * @param data
 * - easyredmineBaseUrl
 * - ticketNo
 * - authKey
 * @return GPathResult containing ticket data inclusive journal entries
 */
def call(Map data) {
    
    try {
        def resultXml = new Utils().get("${data.easyredmineBaseUrl}/issues/${data.ticketNo.toString()}.xml?include=journals", data.authKey)
        println "Got $resultXml"
        return new XmlSlurper().parseText(resultXml)
    } catch (NotFoundException nfe) {
        return null
    } catch(Exception e) {
        println "exception caught while getting ticket"
        throw e
    }
    
}

return this