#!/usr/bin/groovy
import de.intersales.jenkins.easyredmine.Utils
import groovy.xml.MarkupBuilder

import static de.intersales.jenkins.easyredmine.Utils.*

def call(Map data) {
    def easyredmineBaseUrl = data["easyredmineBaseUrl"]
    def authKey = data["authKey"] as String
    def ticketNo = data["ticketNo"]
    def comment = data["comment"]
    
    def sw = new StringWriter()
    def mb = new MarkupBuilder(sw)
    mb.issue {
        notes {
            p {comment}
        }
    }
    
    def xml = '''<issue>
        <notes><p>$comment</p></notes>
    </issue>
    '''
    
    new Utils().put("$easyredmineBaseUrl/issues/$ticketNo",mb.toString(), authKey )
}
