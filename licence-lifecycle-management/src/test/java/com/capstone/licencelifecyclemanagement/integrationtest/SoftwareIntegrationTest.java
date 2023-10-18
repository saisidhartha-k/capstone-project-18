// package com.capstone.licencelifecyclemanagement.integrationtest;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;

// import java.time.LocalDate;
// import java.util.List;

// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.boot.test.web.server.LocalServerPort;
// import org.springframework.context.annotation.Import;
// import org.springframework.core.ParameterizedTypeReference;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpMethod;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.test.context.jdbc.Sql;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.web.client.RestTemplate;

// import com.capstone.licencelifecyclemanagement.controllers.SoftwareController;
// import com.capstone.licencelifecyclemanagement.entitys.Software;
// import com.capstone.licencelifecyclemanagement.entitys.SoftwareCompany;
// import com.capstone.licencelifecyclemanagement.repository.SoftwareRepository;
// import com.capstone.licencelifecyclemanagement.services.SoftwareService;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureMockMvc(addFilters = false)
// public class SoftwareIntegrationTest {

//     @LocalServerPort
//     private int port;

//     private String baseUrl;

//     @Autowired
//     private TestRestTemplate restTemplate;

//     @MockBean
//     private SoftwareController softwareController;

//     @Autowired
//     private SoftwareService softwareService;

//     @Autowired
//     private SoftwareRepository softwareRepository;

//     private static HttpHeaders headers;

//     @BeforeAll
//     public static void init() {
//         headers = new HttpHeaders();
//         headers.setContentType(MediaType.APPLICATION_JSON);
//     }

//     @Test
//     @Sql(statements = {
//            "INSERT INTO capstoneDB.software (id, cost, expiry_date, is_expired, license_number, name, number_of_employees, purchase_date, company_id) VALUES(10, 400, '2024-10-31', 0, '2000', 'a', 800, '2023-10-13', 3);"
//     }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//     @Sql(statements = "DELETE FROM software where id IN (10);", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//     void integrationTestGetSoftwares() {
//         HttpHeaders headers = new HttpHeaders();
//         HttpEntity<String> entity = new HttpEntity<>(null, headers);

//         ResponseEntity<List<Software>> response = restTemplate.exchange(
//                 createURLWithPort("/software/get"), HttpMethod.GET, entity,
//                 new ParameterizedTypeReference<List<Software>>() {
//                 });

//         List<Software> softwareList = response.getBody();
//         assert softwareList != null;
//         assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//         assertEquals(1, softwareList.size());
//     }

//     private String createURLWithPort(String partialUrl) {
//         return "http://localhost:" + port + partialUrl;
//     }
// }
