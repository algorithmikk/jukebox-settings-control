/*
 * 
 */
package com.jukeboxapp.jukebox.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.jukeboxapp.jukebox.api.rest.v1.ressource.Components;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.JukeBox;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.Requires;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.Settings;
import com.jukeboxapp.jukebox.domain.client.JukeBoxClient;
import com.jukeboxapp.jukebox.domain.dto.JukeBoxDto;
import com.jukeboxapp.jukebox.domain.exception.DomainExceptionHandler;
import com.jukeboxapp.jukebox.domain.exception.SettingNotFoundException;
import com.jukeboxapp.jukebox.domain.mapper.JukeBoxMapper;

@Service
public class JukeBoxServiceImpl implements JukeBoxService {

	@Autowired
	private JukeBoxClient client;

	@Autowired
	private JukeBoxMapper mapper;

	/**
	 * Gets the paginated list with setting id and model.
	 *
	 * @param settingId the setting id
	 * @param model     the model
	 * @param offset    the offset
	 * @param limit     the limit
	 * @return the paginated list with setting idand model
	 */
	@Override
	public List<JukeBox> getPaginatedListWithSettingIdandModel(Optional<String> settingId, Optional<String> model,
			Optional<Integer> offset, Optional<Integer> limit) {

		return createPaginatedListOfJukes(settingId, model, offset, limit).getPageList();

	}

	/**
	 * Retrieve list of jukes from juke box client.
	 *
	 * @return the list
	 */
	@Override
	public List<JukeBox> retrieveListOfJukesFromJukeBoxClient() {

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

	/**
	 * Retrieve list of jukes from juke box client given model.
	 *
	 * @param model the model
	 * @return the list
	 */
	@Override
	public List<JukeBox> retrieveListOfJukesFromJukeBoxClientGivenModel(Optional<String> model) {

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

	/**
	 * Retrieve settings from juke box client.
	 *
	 * @return the settings
	 */
	@Override
	public Settings retrieveSettingsFromJukeBoxClient() {

		Settings settings = new Settings();

		try {
			settings = mapper.settingsDtoToSettings(client.getSettings());
		} catch (Exception e) {
			DomainExceptionHandler.handleException(e);
		}
		return settings;
	}

	/**
	 * Gets the list components from jukes given setting id and model.
	 *
	 * @param settingId the setting id
	 * @param model     the model
	 * @return the list components from jukes given setting id and model
	 */
	@Override
	public List<JukeBox> getListComponentsFromJukesGivenSettingIdAndModel(Optional<String> settingId,
			Optional<String> model) {

		return listOfJukeRequireSettingWithModel(settingId, model);
	}

	/**
	 * Gets the list components from jukes given setting id.
	 *
	 * @param settingId the setting id
	 * @return the list components from jukes given setting id
	 */
	@Override
	public List<JukeBox> getListComponentsFromJukesGivenSettingId(Optional<String> settingId) {
		return listOfJukesWithRequiredSettings(settingId);
	}

	/**
	 * List of juke require setting with model.
	 *
	 * @param settingId the setting id
	 * @param model the model
	 * @return the list
	 */
	private List<JukeBox> listOfJukeRequireSettingWithModel(Optional<String> settingId, Optional<String> model) {

		List<JukeBox> jukeBoxList = new ArrayList<>();

		for (JukeBox juke : retrieveListOfJukesFromJukeBoxClientGivenModel(model)) {

			if (CollectionUtils.containsAll(populateSetOfUniqueComponentsFromListNamesOfJukes(juke),
					populateSetOfUniqueSettingsFromListRequiresOfSettings(settingId))) {
				jukeBoxList.add(juke);
			}

		}

		return jukeBoxList;

	}

	/**
	 * List of jukes with required settings.
	 *
	 * @param settingId the setting id
	 * @return the list
	 */
	private List<JukeBox> listOfJukesWithRequiredSettings(Optional<String> settingId) {

		List<JukeBox> jukeBoxList = new ArrayList<>();

		for (JukeBox juke : retrieveListOfJukesFromJukeBoxClient()) {

			if (CollectionUtils.containsAll(populateSetOfUniqueComponentsFromListNamesOfJukes(juke),
					populateSetOfUniqueSettingsFromListRequiresOfSettings(settingId))) {
				jukeBoxList.add(juke);
			}

		}

		return jukeBoxList;
	}

	/**
	 * Populate set of unique components from list names of jukes.
	 *
	 * @param juke the juke
	 * @return the sets the
	 */
	private Set<String> populateSetOfUniqueComponentsFromListNamesOfJukes(JukeBox juke) {

		return juke.getComponents().stream().map(Components::getName).collect(Collectors.toList()).stream()
				.collect(Collectors.toSet());

	}

	/**
	 * Populate set of unique settings from list requires of settings.
	 *
	 * @param settingId the setting id
	 * @return the sets the
	 */
	private Set<String> populateSetOfUniqueSettingsFromListRequiresOfSettings(Optional<String> settingId) {

		return getListOfRequiresFromSettingId(settingId).stream().collect(Collectors.toSet());
	}

	/**
	 * Gets the list of requires from setting id.
	 *
	 * @param settingId the setting id
	 * @return the list of requires from setting id
	 */
	private List<String> getListOfRequiresFromSettingId(Optional<String> settingId) {

		List<String> requires = new ArrayList<>();

		try {
			checkIfSettingIdExist(settingId);

			for (Requires req : retrieveSettingsFromJukeBoxClient().getSettings()) {

				if (req.getId().equals(settingId.get())) {

					requires = new ArrayList<String>(req.getRequires());

				}
			}
		} catch (Exception e) {
			DomainExceptionHandler.handleException(e);
		}

		return requires;

	}

	/**
	 * Check if setting id exist.
	 *
	 * @param settingId the setting id
	 * @return true, if successful
	 */
	private boolean checkIfSettingIdExist(Optional<String> settingId) {
		if (!retrieveSettingsFromJukeBoxClient().getSettings().stream().map(f -> f.getId()).collect(Collectors.toList())
				.contains(settingId.get())) {
			throw new SettingNotFoundException();

		}
		return true;
	}

	/**
	 * Creates the paginated list of jukes.
	 *
	 * @param settingId the setting id
	 * @param model the model
	 * @param offset the offset
	 * @param limit the limit
	 * @return the paged list holder
	 */
	public PagedListHolder<JukeBox> createPaginatedListOfJukes(Optional<String> settingId, Optional<String> model,
			Optional<Integer> offset, Optional<Integer> limit) {
		PagedListHolder<JukeBox> page = new PagedListHolder<>();

		if (!model.isPresent()) {

			page = new PagedListHolder<JukeBox>(getListComponentsFromJukesGivenSettingId(settingId));

		} else {

			page = new PagedListHolder<JukeBox>(getListComponentsFromJukesGivenSettingIdAndModel(settingId, model));
		}

		if (limit.isPresent()) {
			// if no limit present default is 10.
			page.setPageSize(limit.get());
		}
		if (offset.isPresent()) {
			// if no offse present default starts at 0.
			page.setPage(offset.get());
		}
		return page;
	}

}
