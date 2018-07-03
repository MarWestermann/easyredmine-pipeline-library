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
                status: 'Zum Test'
        
        def ticketData = new easyTicketGet().call easyredmineBaseUrl: EASY_BASE_URL,
                authKey: EASY_AUTH_TOKEN,
                ticketNo: '1979'
        assert ticketData.status.@id.text() == "10"
    
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
        
        
        def notExistingTicket = easyTicketGet easyredmineBaseUrl: EASY_BASE_URL,
                    authKey: EASY_AUTH_TOKEN,
                    ticketNo: '-1'
        
        
        assert notExistingTicket == null
    }
    
    @Test
    void testGetTicketsByFilter() {
        def easyProjectGetTicketsByFilter = new easyProjectGetTicketsByFilter()
        def result = easyProjectGetTicketsByFilter easyredmineBaseUrl: EASY_BASE_URL,
                authKey: EASY_AUTH_TOKEN,
                filter: [project_id: 76, status_id: 1]
        
        assert result instanceof List
        
        println result
        assert result.size() == 2
        assert result.contains("1981")
        assert result.contains("2151")
        
    }
    
    @Test
    void testGetTicketsByFilterWithSpecialStatusFilter() {
        def easyProjectGetTicketsByFilter = new easyProjectGetTicketsByFilter()
        def result = easyProjectGetTicketsByFilter easyredmineBaseUrl: EASY_BASE_URL,
                authKey: EASY_AUTH_TOKEN,
                filter: [project_id: 76, status: "Neu"]
        
        assert result instanceof List
        
        println result
        assert result.size() == 2
        assert result.contains("1981")
        assert result.contains("2151")
        
    }
    
    @Test
    void testFileAsBase64() {
        def fileAsBase64 = new fileAsBase64()
        def filename = new File("testresources/test.zip").absolutePath
        def result = fileAsBase64 filename: filename
        print(result)
    }
}
