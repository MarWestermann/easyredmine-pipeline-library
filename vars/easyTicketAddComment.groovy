#!/usr/bin/groovy
import de.intersales.jenkins.easyredmine.Utils
import groovy.xml.MarkupBuilder

/**
 * Add comment to easyredmine ticket
 * @param data
 *
 * @return void
 */
def call(Map data) {
    
    def xml = """<issue><notes>${data.comment}</notes><private_notes>1</private_notes></issue>"""
    
    new Utils().put("${data.easyredmineBaseUrl}/issues/${data.ticketNo.toString()}.xml",
            xml, data.authKey)
}

return this
