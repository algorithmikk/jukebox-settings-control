package com.jukeboxapp.jukebox.api.rest.v1.ressource;

import java.util.List;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class JukeBoxSettingWrapper {

	private List<JukeBox> listOfJukes;

	private List<Settings> listOfSettings;

}
