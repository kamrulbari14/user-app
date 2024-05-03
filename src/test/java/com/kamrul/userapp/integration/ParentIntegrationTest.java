package com.kamrul.userapp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamrul.userapp.dto.address.AddressDto;
import com.kamrul.userapp.dto.user.ParentDto;
import com.kamrul.userapp.entity.address.Address;
import com.kamrul.userapp.entity.user.Parent;
import com.kamrul.userapp.enums.ActiveStatus;
import com.kamrul.userapp.repository.ParentRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;

/**
 * Integration tests for Parent API endpoints.
 */
@SpringBootTest
public class ParentIntegrationTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;
  private ObjectMapper objectMapper;

  @Autowired
  private ParentRepository parentRepository;

  private final Address address = new Address();

  static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:latest");

  /**
   * Configures the database properties before all tests.
   */
  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
    registry.add("spring.datasource.username", mySQLContainer::getUsername);
    registry.add("spring.datasource.password", mySQLContainer::getPassword);
  }

  /**
   * Starts the MySQL container before all tests.
   */
  @BeforeAll
  static void beforeAll() {
    mySQLContainer.start();
  }

  /**
   * Stops the MySQL container after all tests.
   */
  @AfterAll
  static void afterAll() {
    mySQLContainer.stop();
  }

  /**
   * Set up an Address before each test.
   */
  @BeforeEach
  private void setUpAddress() {
    address.setStreet("Road-2");
    address.setCity("Dhaka");
    address.setState("Bangladesh");
    address.setZip("1207");
  }

  /**
   * Set up the MockMvc instance and ObjectMapper before each test.
   */
  @BeforeEach
  private void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  public void testSaveParent() throws Exception {
    // Create a ParentDto object to send as request body
    ParentDto parentDto = new ParentDto();
    parentDto.setFirstName("Test");
    parentDto.setLastName("Parent");

    //Prepare AddressDto
    AddressDto addressDto = AddressDto.builder().street("Road-2").city("Dhaka").state("Bangladesh")
        .zip("1207").build();

    parentDto.setAddress(addressDto);

    // Convert ParentDto object to JSON
    String jsonParent = objectMapper.writeValueAsString(parentDto);

    // Perform POST request to save the parent
    MvcResult resultActions = mockMvc.perform(
            post("/api/v1/parents").contentType(MediaType.APPLICATION_JSON).content(jsonParent))
        .andExpect(status().isCreated()).andReturn();

    int statusCode = resultActions.getResponse().getStatus();
    assertEquals(HttpStatus.CREATED.value(), statusCode);
  }

  @Test
  public void testGetParentUserById() throws Exception {

    // Save a parent entry for testing
    Parent parent = new Parent();
    parent.setAddress(address);
    parent.setFirstName("Test");
    parent.setLastName("By ID");
    parent = parentRepository.save(parent);

    // Perform GET request to retrieve parent user by ID
    MvcResult result = mockMvc.perform(get("/api/v1/parents/{id}", parent.getId()))
        .andExpect(status().isOk()).andReturn();

    int statusCode = result.getResponse().getStatus();
    assertEquals(HttpStatus.OK.value(), statusCode);

  }

  @Test
  public void testGetParentUserList() throws Exception {

    // Save a parent entry for testing
    Parent parent = new Parent();
    parent.setAddress(address);
    parent.setFirstName("Test");
    parent.setLastName("Parent 1");
    parent = parentRepository.save(parent);

    // Perform GET request to retrieve parent user list
    MvcResult result = mockMvc.perform(get("/api/v1/parents", parent.getId()))
        .andExpect(status().isOk()).andReturn();

    int statusCode = result.getResponse().getStatus();
    assertEquals(HttpStatus.OK.value(), statusCode);

  }

  @Test
  public void testUpdateParentUser() throws Exception {
    // Save a parent entry for testing
    Parent parent = new Parent();
    parent.setAddress(address);
    parent.setFirstName("Test");
    parent.setLastName("By ID");
    parent = parentRepository.save(parent);

    // Create a ParentDto object with updated information
    ParentDto updatedParentDto = new ParentDto();
    updatedParentDto.setFirstName("Test-Update");
    // Set updated properties of the parent user

    // Convert ParentDto object to JSON
    String jsonUpdatedParent = objectMapper.writeValueAsString(updatedParentDto);

    // Perform PUT request to update the parent user
    MvcResult result = mockMvc.perform(
        put("/api/v1/parents/{id}", parent.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(jsonUpdatedParent)).andExpect(status().isAccepted()).andReturn();

    int statusCode = result.getResponse().getStatus();
    assertEquals(HttpStatus.ACCEPTED.value(), statusCode);
  }

  @Test
  public void testDeleteParentUser() throws Exception {

    // Save a parent entry for testing
    Parent parent = new Parent();
    parent.setAddress(address);
    parent.setFirstName("Test Delete");
    parent.setLastName("By ID");
    parent = parentRepository.save(parent);

    // Perform DELETE request to delete parent user
    MvcResult result = mockMvc.perform(delete("/api/v1/parents/{id}", parent.getId()))
        .andExpect(status().isOk()).andReturn();

    int statusCode = result.getResponse().getStatus();
    assertEquals(HttpStatus.OK.value(), statusCode);

    // Verify that the parent user has been deleted
    // Attempt to retrieve the deleted parent user from the database and ensure it's not found
    assertFalse(
        parentRepository.findByIdAndActiveStatus(parent.getId(), ActiveStatus.ACTIVE.getValue())
            .isPresent());
  }
}
