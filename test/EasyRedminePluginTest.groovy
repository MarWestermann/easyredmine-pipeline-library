import org.junit.Test
import easyTicketAddComment
class EasyRedminePluginTest {
    
    @Test
    void testAddComment() {
        def command = new easyTicketAddComment()
        command.call (easyredmineBaseUrl: '',
                authKey: '',
                ticketNo: '2',
                comment: 'Kommentar von Mr. Jenkins')
    }
}
