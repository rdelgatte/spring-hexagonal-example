package com.rdelgatte.hexagonal.infrastructure.restclient.configuration;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * All additional Jackson modules (modules used by Spring to marshal / unmarshal):
 *
 * - {@link VavrModule}
 * - {@link JavaTimeModule}
 */
@Configuration
public class JacksonConfiguration {

  @Bean
  public Module vavr() {
    return new VavrModule();
  }


  @Bean
  public Module javaTime() {
    return new JavaTimeModule();
  }

  @Bean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
    jsonConverter.setObjectMapper(objectMapper());
    return jsonConverter;
  }

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModules(vavr(), javaTime());
    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    objectMapper.setSerializationInclusion(Include.NON_EMPTY);

    return objectMapper;
  }
}
