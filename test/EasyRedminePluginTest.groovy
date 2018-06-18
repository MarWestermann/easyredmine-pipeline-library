import org.junit.Test

class EasyRedminePluginTest {
    
    @Test
    void testAddComment() {
        Map data = [
                easyredmineBaseUrl: "",
                authKey           : "",
                ticketNo: "2",
                comment: "Kommentar von Mr. Jenkins"
        ]
        easyTicketAddComment(data)
    }
}
