package com.jukeboxapp.jukebox.domain.service;

import java.util.List;
import java.util.Optional;

import com.jukeboxapp.jukebox.api.rest.v1.ressource.JukeBox;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.Settings;

public interface JukeBoxeService {

	public List<JukeBox> getListOfJukes();

	public Settings getAllSettings();

	public List<JukeBox> getListComponentsFromJukesGivenSettingId(String settingId);

	public List<JukeBox> getPaginatedListWithSettingIdandModel(String settingId, Optional<String> model,
			Optional<Integer> offset, Optional<Integer> limit);

}
