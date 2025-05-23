package eci.cvds.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import eci.cvds.demo.config.TestConfig;
import org.springframework.context.annotation.Import;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestConfig.class)
class EciSaludBackApplicationTests {

    @Test
    void contextLoads() {
        // Test that the application context loads successfully
    }

}
