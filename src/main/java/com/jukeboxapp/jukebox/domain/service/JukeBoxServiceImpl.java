package com.jukeboxapp.jukebox.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.jukeboxapp.jukebox.api.rest.v1.ressource.Components;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.JukeBox;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.Requires;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.Settings;
import com.jukeboxapp.jukebox.domain.client.JukeBoxClient;
import com.jukeboxapp.jukebox.domain.dto.JukeBoxDto;
import com.jukeboxapp.jukebox.domain.exception.DomainExceptionHandler;
import com.jukeboxapp.jukebox.domain.exception.JukeBoxNotFoundException;
import com.jukeboxapp.jukebox.domain.exception.SettingNotFoundException;
import com.jukeboxapp.jukebox.domain.mapper.JukeBoxMapper;

@Service
public class JukeBoxServiceImpl implements JukeBoxeService {

	@Autowired
	private JukeBoxClient client;

	@Autowired
	private JukeBoxMapper mapper;

	@Override
	public List<JukeBox> getListOfJukes() {

		List<JukeBox> listJukes = new ArrayList<JukeBox>();
		try {
			listJukes = new ArrayList<>();

			for (JukeBoxDto dto : client.getListOfJukes()) {

				listJukes.add(mapper.dtoToJukeBox(dto));

			}
		} catch (Exception e) {
			DomainExceptionHandler.handleException(e);
		}

		return listJukes.stream().collect(Collectors.toList());

	}

	@Override
	public List<JukeBox> getListOfJukesWithModel(Optional<String> model) {

		List<JukeBox> listJukes = new ArrayList<JukeBox>();

		try {
			for (JukeBoxDto dto : client.getListOfJukes()) {

				listJukes.add(mapper.dtoToJukeBox(dto));

			}
		} catch (Exception e) {
			DomainExceptionHandler.handleException(e);
		}

		return listJukes.stream().filter(j -> j.getModel().equals(model.get())).collect(Collectors.toList());
	}

	@Override
	public Settings getAllSettings() {

		Settings settings = new Settings();

		try {
			settings = mapper.settingsDtoToSettings(client.getSettings());
		} catch (Exception e) {
			DomainExceptionHandler.handleException(e);
		}
		return settings;
	}

	@Override
	public List<JukeBox> getListComponentsFromJukesGivenSettingIdAndModel(Optional<String> settingId,
			Optional<String> model) {

		return listOfJukeRequireSettingWithModel(settingId, model);
	}

	@Override
	public List<JukeBox> getListComponentsFromJukesGivenSettingId(Optional<String> settingId) {
		// TODO Auto-generated method stub
		return listOfJukesWithRequiredSettings(settingId);
	}

	private List<JukeBox> listOfJukeRequireSettingWithModel(Optional<String> settingId, Optional<String> model) {

		List<JukeBox> jukeBoxList = new ArrayList<>();

		for (JukeBox juke : getListOfJukesWithModel(model)) {

			if (CollectionUtils.containsAll(buildTreeSetOfUniqueComponentsFromListNamesOfJukes(juke),
					buildTreeSetOfUniqueSettingsFromListofRequireSetting(settingId))) {
				jukeBoxList.add(juke);
			}

		}

		return jukeBoxList;

	}

	private List<JukeBox> listOfJukesWithRequiredSettings(Optional<String> settingId) {
		// add comment
		List<JukeBox> jukeBoxList = new ArrayList<>();

		for (JukeBox juke : getListOfJukes()) {

			if (CollectionUtils.containsAll(buildTreeSetOfUniqueComponentsFromListNamesOfJukes(juke),
					buildTreeSetOfUniqueSettingsFromListofRequireSetting(settingId))) {
				jukeBoxList.add(juke);
			}

		}

		return jukeBoxList;
	}

	private Set<String> buildTreeSetOfUniqueComponentsFromListNamesOfJukes(JukeBox juke) {

		return juke.getComponents().stream().map(Components::getName).collect(Collectors.toList()).stream()
				.collect(Collectors.toSet());

	}

	private Set<String> buildTreeSetOfUniqueSettingsFromListofRequireSetting(Optional<String> settingId) {

		return getListOfRequiresFromSettingId(settingId).stream().collect(Collectors.toSet());
	}

	private List<String> getListOfRequiresFromSettingId(Optional<String> settingId) {

		List<String> requires = new ArrayList<>();

		for (Requires req : getAllSettings().getSettings()) {

			if (req.getId().equals(settingId.get())) {

				requires = new ArrayList<String>(req.getRequires());

			}
		}

		return requires;

	}

	@Override
	public List<JukeBox> getPaginatedListWithSettingIdandModel(Optional<String> settingId, Optional<String> model,
			Optional<Integer> offset, Optional<Integer> limit) {

		return createPaginatedListOfJukes(settingId, model, offset, limit).getPageList();

	}

	private PagedListHolder<JukeBox> createPaginatedListOfJukes(Optional<String> settingId, Optional<String> model,
			Optional<Integer> offset, Optional<Integer> limit) {
		PagedListHolder<JukeBox> page = new PagedListHolder<>();

		if (!model.isPresent()) {

			page = new PagedListHolder<JukeBox>(getListComponentsFromJukesGivenSettingId(settingId));

		} else {

			page = new PagedListHolder<JukeBox>(getListComponentsFromJukesGivenSettingIdAndModel(settingId, model));
		}

		if (limit.isPresent()) {
			page.setPageSize(limit.get());
		} // number of items per page
		if (offset.isPresent()) {
			page.setPage(offset.get());
		}
		return page;
	}

}
