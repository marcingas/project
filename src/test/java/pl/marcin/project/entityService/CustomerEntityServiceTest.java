package pl.marcin.project.entityService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class CustomerEntityServiceTest {
    //jak spring połączył to repozytorium do serwisu
    //jak to repozytorium łączy się z testową bazą danych i co serwis zwraca
    @Autowired
    private CustomerEntityService customerEntityService;

    @Test
    public void shouldReturnCustomers() {
//uruchomić pierwsze a później dodać save.
//   w drugim teście->      customerEntityService.addCustomer();
    }


}