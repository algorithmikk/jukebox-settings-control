package com.jukeboxapp.jukebox.domain.service;

import org.springframework.beans.factory.annotation.Autowired;

public class Servicetest {
	@Autowired
	JukeBoxeService service;
	
	
	public void listJukes() {
		service.getJukeBox();
	}

}
