package com.jukeboxapp.jukebox.domain.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import feign.FeignException;

public class DomainExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(DomainExceptionHandler.class);

	public static void handleException(Exception e) {

		if (e instanceof FeignException) {
			LOG.error("Error occured when calling the the FEIGN REST client", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not reach the feign client", e);

		}

		if (e instanceof JukeBoxNotFoundException || e instanceof SettingNotFoundException) {

			LOG.error("Error occured when searching for the demanded Ressouce", e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ressource Not Found", e);
		}

	}

}
