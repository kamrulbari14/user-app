package com.kamrul.userapp.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class responsible for defining beans.
 */
@Configuration
public class BeanConfig {

  /**
   * Defines a bean for ModelMapper.
   *
   * @return An instance of ModelMapper.
   */
  @Bean
  public ModelMapper getModelMapper() {
    return new ModelMapper();
  }
}