package com.jukeboxapp.jukebox.domain.service;

import java.util.List;
import java.util.Optional;

import com.jukeboxapp.jukebox.api.rest.v1.ressource.JukeBox;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.Settings;

public interface JukeBoxeService {

	public Settings retrieveSettingsFromJukeBoxClient();

	public List<JukeBox> getListComponentsFromJukesGivenSettingIdAndModel(Optional<String> settingId,
			Optional<String> model);

	public List<JukeBox> getListComponentsFromJukesGivenSettingId(Optional<String> settingId);

	public List<JukeBox> getPaginatedListWithSettingIdandModel(Optional<String> settingId, Optional<String> model,
			Optional<Integer> offset, Optional<Integer> limit);

	List<JukeBox> retrieveListOfJukesFromJukeBoxClient();

	List<JukeBox> retrieveListOfJukesFromJukeBoxClientGivenModel(Optional<String> model);

}
