package com.ScalableDynamics.Jobs_Microservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * This test class verifies the configuration and connectivity of the DataSource bean,
 * ensuring that the database connection is established and operational. It also
 * checks the functionality of JdbcTemplate for basic query execution.
 */
@SpringBootTest
@DisplayName("Database Connectivity and Query Execution Tests")
public class DBConnectionTest {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  /**
   * Verifies that the DataSource bean is correctly configured and is able to provide
   * a valid connection. It checks if the connection can be established and validated
   * with a timeout of 1 second.
   *
   * @throws Exception if unable to retrieve a valid connection from DataSource
   */
  @Test
  @DisplayName("Valid DataSource Connection")
  public void testDataSourceIsConfigured() throws Exception {
    assertThat(dataSource).isNotNull();

    try (var connection = dataSource.getConnection()) {
      assertThat(connection).isNotNull();
      assertThat(connection.isValid(1)).isTrue(); // 1 second timeout for validation
    }
  }

  /**
   * Verifies that JdbcTemplate can execute a simple query ("SELECT 1") successfully.
   * This ensures that database connection and query execution via JdbcTemplate is functional.
   */
  @Test
  @DisplayName("JdbcTemplate Simple Query Execution")
  public void testJdbcTemplateQuery() {
    Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
    assertThat(result).isEqualTo(1);
  }
}

