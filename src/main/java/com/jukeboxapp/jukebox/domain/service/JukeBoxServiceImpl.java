package com.jukeboxapp.jukebox.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
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
	public List<JukeBox> getListOfJukes() {
		// TODO Auto-generated method stub

		List<JukeBox> listJukes = new ArrayList<>();

		for (JukeBoxDto dto : client.getListOfJukes()) {

			listJukes.add(mapper.dtoToJukeBox(dto));

		}

		return listJukes;
	}

	@Override
	public Settings getAllSettings() {

		return mapper.settingsDtoToSettings(client.getSettings());
	}

	@Override
	public List<JukeBox> getListComponentsFromJukesGivenSettingId(String settingId) {

		return filterListToSet(settingId);
	}

	private List<JukeBox> filterListToSet(String settingId) {
        //add comment
		List<JukeBox> jukeBoxList = new ArrayList<>();
		Set<String> setOfUniqueComponents = new TreeSet<>();
		Set<String> setOfUniqueSettings = new TreeSet<>();

		for (JukeBox juke : getListOfJukes()) {

			List<String> mylist = juke.getComponents().stream().map(Components::getName).collect(Collectors.toList());

			setOfUniqueComponents = mylist.stream().collect(Collectors.toSet());

			setOfUniqueSettings = getListOfRequiresFromSettingId(settingId).stream().collect(Collectors.toSet());

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

	@Override
	public List<JukeBox> getPaginatedListWithSettingIdandModel(String settingId, Optional<String> model,
			Optional<Integer> offset, Optional<Integer> limit) {

		PagedListHolder<JukeBox> page = createPaginatedListOfJukes(settingId, model, offset, limit);

		return page.getPageList();

	}

	private PagedListHolder<JukeBox> createPaginatedListOfJukes(String settingId, Optional<String> model,
			Optional<Integer> offset, Optional<Integer> limit) {
		PagedListHolder<JukeBox> page = new PagedListHolder<>();

		if (model.isPresent()) {

			page = new PagedListHolder<JukeBox>(getListComponentsFromJukesGivenSettingId(settingId).stream()
					.filter(j -> j.getModel().equals(model.get())).collect(Collectors.toList()));

		} else {

			page = new PagedListHolder<JukeBox>(getListComponentsFromJukesGivenSettingId(settingId));
		}

		page.setPageSize(limit.get()); // number of items per page
		page.setPage(offset.get());
		return page;
	}

}
