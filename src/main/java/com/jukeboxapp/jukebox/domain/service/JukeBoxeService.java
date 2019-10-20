package com.jukeboxapp.jukebox.domain.service;

import java.util.List;

import com.jukeboxapp.jukebox.api.rest.v1.ressource.JukeBox;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.Settings;

public interface JukeBoxeService {

	public List<JukeBox> getJukeBox();

	public Settings getAllSettings();

	public List<JukeBox> getListComponentsFromJukeGivenSettingId(String settingId);

}
