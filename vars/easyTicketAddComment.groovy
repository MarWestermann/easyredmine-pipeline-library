#!/usr/bin/groovy
import de.intersales.jenkins.easyredmine.Utils
import groovy.xml.MarkupBuilder

def call(Map data) {
    
    def sw = new StringWriter()
    def mb = new MarkupBuilder(sw)
    mb.issue {
        notes (data.comment)
    }
    
    println("using the following put request data:\n${sw.toString()}")
    
    new Utils().put("${data.easyredmineBaseUrl}/issues/${data.ticketNo}.xml", sw.toString(), data.authKey)
}

return this
