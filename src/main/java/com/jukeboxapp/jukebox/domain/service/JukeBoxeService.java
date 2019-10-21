package com.jukeboxapp.jukebox.domain.service;

import java.util.List;
import java.util.Optional;

import com.jukeboxapp.jukebox.api.rest.v1.ressource.JukeBox;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.Settings;

public interface JukeBoxeService {

	public List<JukeBox> getJukeBox();

	public Settings getAllSettings();

	public List<JukeBox> getListComponentsFromJukeGivenSettingId(String settingId);

	public List<JukeBox> getPaginatedListWithSettingIdandModel(String settingId, Optional<String> model, int offset,
			int limit);

}
