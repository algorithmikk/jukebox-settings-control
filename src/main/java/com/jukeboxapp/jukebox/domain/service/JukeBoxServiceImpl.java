package com.jukeboxapp.jukebox.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jukeboxapp.jukebox.api.rest.v1.ressource.Components;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.JukeBox;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.Requires;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.Settings;
import com.jukeboxapp.jukebox.domain.client.JukeBoxClient;
import com.jukeboxapp.jukebox.domain.dto.JukeBoxDto;
import com.jukeboxapp.jukebox.domain.dto.SettingsDto;
import com.jukeboxapp.jukebox.domain.mapper.JukeBoxMapper;

@Service
public class JukeBoxServiceImpl implements JukeBoxeService {

	@Autowired
	private JukeBoxClient client;

	@Autowired
	private JukeBoxMapper mapper;

	@Override
	public List<JukeBox> getJukeBox() {
		// TODO Auto-generated method stub

		List<JukeBox> listJukes = new ArrayList<>();

		List<JukeBoxDto> listDto = client.getListOfJukes();

		for (JukeBoxDto dto : listDto) {

			listJukes.add(mapper.dtoToJukeBox(dto));

		}

		return listJukes;
	}

	@Override
	public Settings getAllSettings() {

		SettingsDto obj = client.getSettings();

		return mapper.settingsDtoToSettings(obj);
	}

	@Override
	public List<JukeBox> getListComponentsFromJukeGivenSettingId(String settingId) {

		List<JukeBox> jukeBoxList = new ArrayList<>();

		filterListToSet(settingId, jukeBoxList);

		return jukeBoxList;
	}

	private void filterListToSet(String settingId, List<JukeBox> jukeBoxList) {
		Set<String> setOfUniqueComponents = new TreeSet<>();
		Set<String> setOfUniqueSettings = new TreeSet<>();

		for (JukeBox juke : getJukeBox()) {

			List<String> mylist = juke.getComponents().stream().map(Components::getName).collect(Collectors.toList());

			setOfUniqueComponents = mylist.stream().collect(Collectors.toSet());

			setOfUniqueSettings = getListOfRequiresFromSettingId(settingId).stream().collect(Collectors.toSet());

			// If List A = {A, B, C, A, A} --> Set of A = {A,B,C}
			// IF List B = {A,B,C,C} -- Set of B = {A,B,C}
			// ListA.containsALL(LISTB) ? --> true

			if (CollectionUtils.containsAll(setOfUniqueComponents, setOfUniqueSettings)) {
				jukeBoxList.add(juke);
			}

		}
	}

	private List<String> getListOfRequiresFromSettingId(String settingId) {

		List<String> requires = new ArrayList<>();

		for (Requires req : getAllSettings().getSettings()) {

			if (req.getId().equals(settingId)) {

				requires = new ArrayList<String>(req.getRequires());

			}
		}

		return requires;
	}

	@Override
	public List<JukeBox> getPaginatedListWithSettingIdandModel(String settingId, Optional<String> model, int offset,
			int limit) {

		return model.isPresent() ? getListComponentsFromJukeGivenSettingId(settingId).stream()
				.filter(j -> j.getModel().equals(model)).collect(Collectors.toList())
				: getListComponentsFromJukeGivenSettingId(settingId);

	}

}
