package com.jukeboxapp.jukebox.domain.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DomainExceptionHandlerTest {

	@Test
	public void exceptionJukeBoxNotFoundException() {

		Exception ex = new RuntimeException("Ressource Not Found");

		try {
			DomainExceptionHandler.handleException(ex);
		} catch (JukeBoxNotFoundException e) {

			assertEquals("Ressource Not Found", e.getMessage());
		}
	}

	@Test
	public void exceptionSettingNotFoundException() {

		Exception ex = new RuntimeException("Ressource Not Found");

		try {
			DomainExceptionHandler.handleException(ex);
		} catch (SettingNotFoundException e) {

			assertEquals("Ressource Not Found", e.getMessage());
		}
	}

}
