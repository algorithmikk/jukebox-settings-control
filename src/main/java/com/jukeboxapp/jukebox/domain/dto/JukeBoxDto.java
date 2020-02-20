package com.jukeboxapp.jukebox.domain.dto;

import java.util.List;

import com.jukeboxapp.jukebox.api.rest.v1.ressource.Components;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JukeBoxDto {

	private String id;

	private String model;

	private List<Components> components;

}
