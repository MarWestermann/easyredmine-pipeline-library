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
    void testScriptContent() {
        def branch = 'er-12345'
        assert branch.toUpperCase().replaceFirst('-', '_').startsWith("ER_")
        
        
        def diff = """.gitignore
Dockerfile
Jenkinsfile
app/code/InterSales/MyProject/composer.json
app/code/InterSales/MyProject/datei.json
app/design/frontend/Fipa/core
app/design/frontend/Fipa/core/.gitignore
app/design/frontend/Fipa/core/InterSales_CustomForms/templates/webforms/fields/file.phtml
app/design/frontend/Fipa/core/InterSales_CustomForms/web/css/source/_extend.less
app/design/frontend/Fipa/core/InterSales_Fileuploader/templates/uploader.phtml
app/design/frontend/Fipa/core/InterSales_FipaBluefoot/web/css/source/_extend.less
app/design/frontend/Fipa/core/Magento_Catalog/layout/catalog_category_view.xml
app/design/frontend/Fipa/core/Magento_Catalog/layout/catalog_product_view.xml
app/design/frontend/Fipa/core/Magento_Catalog/templates/product/list.phtml
app/design/frontend/Fipa/core/Magento_Catalog/templates/product/list_print_catalog.phtml
""".trim()
        
        def diffList = diff.split("\n").toList()
        
        def changedModules = diffList.findAll {it.startsWith("app/code/InterSales")}.collect { it =~ /app\/code\/InterSales\/(\w+)\/.+/}.collect{it[0][1]}.toSet()
        
        assert changedModules.size() == 1
        
        changedModules.each { module ->
            if (diffList.findAll{it == "app/code/InterSales/$module/composer.json"}.isEmpty()) {
                throw new Exception("Module $module changed but composer.json does not")
            }
        }
        
        
        
    
    }
}
