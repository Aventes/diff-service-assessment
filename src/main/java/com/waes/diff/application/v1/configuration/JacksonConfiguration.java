package com.waes.diff.application.v1.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Defines Jackson's Object Mapper Configuration for the whole application
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@Configuration
public class JacksonConfiguration {

    /**
     * Defines Object Mapper Configuration for the internal application
     *
     * @return well configured {@link ObjectMapper} for the application
     */
    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        return mapper;
    }
}
