import groovy.util.slurpersupport.GPathResult
import org.junit.Test
import easyTicketAddComment
class EasyRedminePluginTest {
    
    // the env vars needs to be added to either your system or the run configuration of the test
    private static final String EASY_BASE_URL = System.getenv()["EASY_BASE_URL"]
    private static final String EASY_AUTH_TOKEN = System.getenv()["EASY_AUTH_TOKEN"]
    
    @Test
    void testAddComment() {
        def easyTicketAddComment = new easyTicketAddComment()
        easyTicketAddComment easyredmineBaseUrl: EASY_BASE_URL,
                authKey: EASY_AUTH_TOKEN,
                ticketNo: '1979',
                comment: 'Kommentar von Mr. Jenkins'
    }
    
    @Test
    void testChangeStatus() {
        def easyTicketChangeStatus = new easyTicketChangeStatus()
        easyTicketChangeStatus easyredmineBaseUrl: EASY_BASE_URL,
                authKey: EASY_AUTH_TOKEN,
                ticketNo: '1979',
                status: 'Neu'
        
        def ticketData = new easyTicketGet().call easyredmineBaseUrl: EASY_BASE_URL,
                authKey: EASY_AUTH_TOKEN,
                ticketNo: '1979'
        assert ticketData.status.@id.text() == "1"
    
        easyTicketChangeStatus easyredmineBaseUrl: EASY_BASE_URL,
                authKey: EASY_AUTH_TOKEN,
                ticketNo: '1979',
                status: 'Erledigt'
    
        ticketData = new easyTicketGet().call easyredmineBaseUrl: EASY_BASE_URL,
                authKey: EASY_AUTH_TOKEN,
                ticketNo: '1979'
        assert ticketData.status.@id.text() == "5"
    }
    
    @Test
    void testGetTicket() {
        def easyTicketGet = new easyTicketGet()
        def ticketData = easyTicketGet easyredmineBaseUrl: EASY_BASE_URL,
                authKey: EASY_AUTH_TOKEN,
                ticketNo: '1979'
        
        assert ticketData instanceof GPathResult
        assert ticketData.id == 1979
    }
}
