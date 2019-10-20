package com.jukeboxapp.jukebox.domain.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		return jukeBoxList;
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

	private boolean containsAllRequiredComponent(String settingId) {
		// Note that setting is considered to be supported if jukebox has all required
		// components.

		// Check if list of Components of JukeBox bean contains all the required
		// components of list of settings of requires

		// if the current jukebox i iterate on is having the required component i return
		// this jukebox

		// return mySetOfComp.containsAll(mySetOfRequires);

		return false;
	}

	private boolean settingSupported() {
		return false;
	}

}
