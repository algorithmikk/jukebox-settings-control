package com.jukeboxapp.jukebox.api.rest.v1.ressource;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JukeBox {

	private String id;

	private String model;

	private List<Components> components;

}
