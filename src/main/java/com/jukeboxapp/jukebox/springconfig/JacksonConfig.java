package com.jukeboxapp.jukebox.springconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;

@Configuration
public class JacksonConfig {

	@Bean
	public ObjectMapper objectMapper() {

		final ObjectMapper mapper = new ObjectMapper();

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

		return mapper;

	}

	@Bean
	public Decoder feignDecoder() {
		return new JacksonDecoder(objectMapper());
	}

}
