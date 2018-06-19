#!/usr/bin/groovy
import de.intersales.jenkins.easyredmine.EasyredmineHelper
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
    
    def statusId = new EasyredmineHelper().mapStatusNameToId(data)
    def xml = """<issue><status_id>$statusId</status_id></issue>"""
    
    new Utils().put("${data.easyredmineBaseUrl}/issues/${data.ticketNo.toString()}.xml",
            xml, data.authKey)
}



return this
