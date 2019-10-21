package com.jukeboxapp.jukebox.api.rest.v1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jukeboxapp.jukebox.api.rest.v1.ressource.JukeBox;
import com.jukeboxapp.jukebox.domain.service.JukeBoxeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/jukeboxapp/v1")
public class JukeboxController {

	@Autowired
	private JukeBoxeService service;

	@ApiOperation(value = "Return a setting for a jukebox", nickname = "getSettingJukeBox")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The execution has been successfull", response = JukeBox.class),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 404, message = "The Setting for that jukebox has not been found") })
	@GetMapping(value = { "/jukebox/setting/{id}", "/jukebox/{model}/setting/{id}" }, produces = { "application/json" })
	public ResponseEntity<List<JukeBox>> getSettingJukeBox(
			@PathVariable(value = "id") @ApiParam(value = "The ID of the Setting", required = true) final String id,
			@PathVariable(value = "model") @ApiParam(value = "The model of the Jukebox", required = false) final Optional<String> model,
			@RequestParam(value = "offset", defaultValue = "0") @ApiParam(value = "Offset for paginated results", required = false) final int offset,
			@RequestParam(value = "limit", defaultValue = "10") @ApiParam(value = "Limit for paginated results", required = false) final int limit) {

		return ResponseEntity.ok().body(service.getPaginatedListWithSettingIdandModel(id, model, offset, limit));

	}

}
