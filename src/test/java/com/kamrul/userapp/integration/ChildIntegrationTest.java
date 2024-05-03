package com.kamrul.userapp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamrul.userapp.dto.user.ChildDto;
import com.kamrul.userapp.dto.user.ParentDto;
import com.kamrul.userapp.entity.address.Address;
import com.kamrul.userapp.entity.user.Child;
import com.kamrul.userapp.entity.user.Parent;
import com.kamrul.userapp.enums.ActiveStatus;
import com.kamrul.userapp.repository.ChildRepository;
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
 * Integration tests for Child API endpoints.
 */
@SpringBootTest
public class ChildIntegrationTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;
  private ObjectMapper objectMapper;

  @Autowired
  private ParentRepository parentRepository;

  @Autowired
  private ChildRepository childRepository;

  private Parent parent = new Parent();

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
   * Set up a parent user before each test.
   */
  @BeforeEach
  private void setUpParent() {
    Address address = new Address();
    address.setStreet("Road-2");
    address.setCity("Dhaka");
    address.setState("Bangladesh");
    address.setZip("1207");

    parent.setFirstName("Test");
    parent.setLastName("Parent");
    parent.setAddress(address);

    parent = parentRepository.save(parent);
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
  public void testSaveChild() throws Exception {
    // Create a ChildDto object to send as request body
    ChildDto childDto = new ChildDto();
    childDto.setFirstName("Test");
    childDto.setLastName("Child");

    ParentDto parentDto = ParentDto.parentBuilder().id(parent.getId()).build();
    childDto.setParent(parentDto);

    // Convert ChildDto object to JSON
    String jsonChild = objectMapper.writeValueAsString(childDto);

    // Perform POST request to save the child
    MvcResult resultActions = mockMvc.perform(
            post("/api/v1/child").contentType(MediaType.APPLICATION_JSON).content(jsonChild))
        .andExpect(status().isCreated()).andReturn();

    int statusCode = resultActions.getResponse().getStatus();
    assertEquals(HttpStatus.CREATED.value(), statusCode);
  }

  @Test
  public void testGetChildUserById() throws Exception {

    // Save a child entry for testing
    Child child = new Child();
    child.setParent(parent);
    child.setFirstName("Test");
    child.setLastName("By ID");
    child = childRepository.save(child);

    // Perform GET request to retrieve child user by ID
    MvcResult result = mockMvc.perform(get("/api/v1/child/{id}", child.getId()))
        .andExpect(status().isOk()).andReturn();

    int statusCode = result.getResponse().getStatus();
    assertEquals(HttpStatus.OK.value(), statusCode);

  }

  @Test
  public void testGetChildUserList() throws Exception {

    // Save a child entry for testing
    Child child = new Child();
    child.setParent(parent);
    child.setFirstName("Test");
    child.setLastName("Child 1");
    child = childRepository.save(child);

    // Perform GET request to retrieve child user list
    MvcResult result = mockMvc.perform(get("/api/v1/child", child.getId()))
        .andExpect(status().isOk()).andReturn();

    int statusCode = result.getResponse().getStatus();
    assertEquals(HttpStatus.OK.value(), statusCode);

  }

  @Test
  public void testUpdateChildUser() throws Exception {
    // Save a child entry for testing
    Child child = new Child();
    child.setParent(parent);
    child.setFirstName("Test");
    child.setLastName("By ID");
    child = childRepository.save(child);

    // Create a ChildDto object with updated information
    ChildDto updatedChildDto = new ChildDto();
    updatedChildDto.setFirstName("Test-Update");
    // Set updated properties of the child user

    // Convert ChildDto object to JSON
    String jsonUpdatedChild = objectMapper.writeValueAsString(updatedChildDto);

    // Perform PUT request to update the child user
    MvcResult result = mockMvc.perform(
        put("/api/v1/child/{id}", child.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(jsonUpdatedChild)).andExpect(status().isAccepted()).andReturn();

    int statusCode = result.getResponse().getStatus();
    assertEquals(HttpStatus.ACCEPTED.value(), statusCode);
  }

  @Test
  public void testDeleteChildUser() throws Exception {

    // Save a child entry for testing
    Child child = new Child();
    child.setParent(parent);
    child.setFirstName("Test Delete");
    child.setLastName("By ID");
    child = childRepository.save(child);

    // Perform DELETE request to delete child user
    MvcResult result = mockMvc.perform(delete("/api/v1/child/{id}", child.getId()))
        .andExpect(status().isOk()).andReturn();

    int statusCode = result.getResponse().getStatus();
    assertEquals(HttpStatus.OK.value(), statusCode);

    // Verify that the child user has been deleted
    // Attempt to retrieve the deleted child user from the database and ensure it's not found
    assertFalse(
        childRepository.findByIdAndActiveStatus(child.getId(), ActiveStatus.ACTIVE.getValue())
            .isPresent());
  }
}
