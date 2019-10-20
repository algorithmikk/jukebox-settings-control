package com.jukeboxapp.jukebox.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jukeboxapp.jukebox.api.rest.v1.ressource.JukeBox;
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
	public Settings getSettings() {

		SettingsDto obj = client.getSettings();

		return mapper.settingsDtoToSettings(obj);
	}

}
