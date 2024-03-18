package ru.netelogy.springboot;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class SpringbootApplicationTests {



    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    void contextLoads() {

        ResponseEntity<String> forEntity = restTemplate.getForEntity(
                "http://localhost:" + devApp.getMappedPort(8080) + "/",
                String.class
        );
        System.out.println(forEntity.getBody());
    }
    @Container
    public static GenericContainer<?> devApp = new GenericContainer<>("devapp:latest")
            .withExposedPorts(8080);

    @Container
    public static GenericContainer<?> prodApp = new GenericContainer<>("prodapp:latest")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();
    }

    @Test
    public void testDevApp() {
        String baseUrl = "http://localhost:" + devApp.getMappedPort(8080);
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/", String.class);
        assertEquals("Ожидаемый ответ от dev окружения", response.getBody());
    }

    @Test
    public void testProdApp() {
        String baseUrl = "http://localhost:" + prodApp.getMappedPort(8081);
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/", String.class);
        assertEquals("Ожидаемый ответ от prod окружения", response.getBody());
    }
}
