package com.jukeboxapp.jukebox.domain.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jukeboxapp.jukebox.domain.dto.JukeBoxDto;
import com.jukeboxapp.jukebox.domain.dto.SettingsDto;

@FeignClient(name = "jukeboxsetting", url = "http://my-json-server.typicode.com/touchtunes/tech-assignment")
@Primary
public interface JukeBoxClient {

	// @GetMapping(value = "/touchtunes/tech-assignment/jukes", consumes =
	// MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(method = RequestMethod.GET, value = "/jukes")
	List<JukeBoxDto> getListOfJukes();

	@RequestMapping(method = RequestMethod.GET, value = "/settings")
	SettingsDto getSettings();

}
