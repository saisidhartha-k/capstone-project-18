// package com.capstone.licencelifecyclemanagement.integrationtest;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.time.LocalDate;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.web.server.LocalServerPort;
// import org.springframework.web.client.RestTemplate;

// import com.capstone.licencelifecyclemanagement.entitys.Software;
// import com.capstone.licencelifecyclemanagement.entitys.SoftwareCompany;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// public class SoftwareIntegrationTest {
    
//     @LocalServerPort
//     private int port;

//     private String baseUrl;

    
//     private RestTemplate restTemplate;

//     @Autowired
//     private TestSoftwareRepository testH2Repository;

//     @Autowired
//     private CompanyRepoitory companyRepoitory;

//     @BeforeEach
//     public void setUp() {
//         baseUrl = "http://localhost:8080/software/addsoftware";
//         restTemplate = new RestTemplate();
//     }

//     @Test
//     public void testSoftwareAdd() {
//         SoftwareCompany mockCompany1 = new SoftwareCompany();
//         mockCompany1.setName("Company Name 1");
//         mockCompany1.setDescription("Description for Company 1");

//         Software mockSoftware1 = new Software();
//         mockSoftware1.setName("SoftwareName1");
//         mockSoftware1.setCompany(mockCompany1);
//         mockSoftware1.setNumberOfEmployees(50);
//         mockSoftware1.setCost(1000);
//         mockSoftware1.setExpiryDate(LocalDate.now().plusMonths(6));

//         Software response = restTemplate.postForObject(baseUrl, mockSoftware1, Software.class);

//         assertEquals(mockSoftware1.getName(), response.getName());
//     }
// }
