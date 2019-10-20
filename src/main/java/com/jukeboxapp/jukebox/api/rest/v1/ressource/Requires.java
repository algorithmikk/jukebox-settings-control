package com.jukeboxapp.jukebox.api.rest.v1.ressource;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Requires {

	private String id;

	private List<String> requires;

}
