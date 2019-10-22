package com.jukeboxapp.jukebox.api.rest.v1.exception;

import java.time.LocalDateTime;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jukeboxapp.jukebox.domain.exception.JukeBoxNotFoundException;
import com.jukeboxapp.jukebox.domain.exception.SettingNotFoundException;

import feign.FeignException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalControllerExceptionHandler {

	@ExceptionHandler({ JukeBoxNotFoundException.class, SettingNotFoundException.class })
	public final ResponseEntity<JukeBoxApiError> handleRessourceNotFoundException(final RuntimeException ex) {

		final JukeBoxApiError apiError = new JukeBoxApiError(HttpStatus.NOT_FOUND, LocalDateTime.now(),
				ex.getMessage());

		return ResponseEntity.ok(apiError);
	}

	@ExceptionHandler({ FeignException.class })
	public final ResponseEntity<JukeBoxApiError> handleInternalServerError(final RuntimeException ex) {

		final JukeBoxApiError apiError = new JukeBoxApiError(HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now(),
				ex.getMessage());

		return ResponseEntity.ok(apiError);
	}

}
