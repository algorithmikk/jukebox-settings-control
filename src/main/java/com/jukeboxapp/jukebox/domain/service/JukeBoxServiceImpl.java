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
import org.springframework.stereotype.Service;

import com.jukeboxapp.jukebox.api.rest.v1.ressource.Components;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.JukeBox;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.Requires;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.Settings;
import com.jukeboxapp.jukebox.domain.client.JukeBoxClient;
import com.jukeboxapp.jukebox.domain.dto.JukeBoxDto;
import com.jukeboxapp.jukebox.domain.mapper.JukeBoxMapper;

@Service
public class JukeBoxServiceImpl implements JukeBoxeService {

	@Autowired
	private JukeBoxClient client;

	@Autowired
	private JukeBoxMapper mapper;

	@Override
	public List<JukeBox> getListOfJukes(Optional<String> model) {
		// TODO Auto-generated method stub

		List<JukeBox> listJukes = new ArrayList<>();

		for (JukeBoxDto dto : client.getListOfJukes()) {

			listJukes.add(mapper.dtoToJukeBox(dto));

		}

		return listJukes.stream().filter(j -> j.getModel().equals(model.get())).collect(Collectors.toList());
	}

	@Override
	public Settings getAllSettings() {

		return mapper.settingsDtoToSettings(client.getSettings());
	}

	@Override
	public List<JukeBox> getListComponentsFromJukesGivenSettingId(Optional<String> settingId, Optional<String> model) {

		return filterListToSet(settingId, model);
	}

	private List<JukeBox> filterListToSet(Optional<String> settingId, Optional<String> model) {
		// add comment
		List<JukeBox> jukeBoxList = new ArrayList<>();
		Set<String> setOfUniqueComponents = new TreeSet<>();
		Set<String> setOfUniqueSettings = new TreeSet<>();

		for (JukeBox juke : getListOfJukes(model)) {

			List<String> mylist = juke.getComponents().stream().map(Components::getName).collect(Collectors.toList());

			setOfUniqueComponents = mylist.stream().collect(Collectors.toSet());

			setOfUniqueSettings = getListOfRequiresFromSettingId(settingId).stream().collect(Collectors.toSet());

			if (CollectionUtils.containsAll(setOfUniqueComponents, setOfUniqueSettings)) {
				jukeBoxList.add(juke);
			}

		}
		return jukeBoxList;
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

		PagedListHolder<JukeBox> page = createPaginatedListOfJukes(settingId, model, offset, limit);

		return page.getPageList();

	}

	private PagedListHolder<JukeBox> createPaginatedListOfJukes(Optional<String> settingId, Optional<String> model,
			Optional<Integer> offset, Optional<Integer> limit) {
		PagedListHolder<JukeBox> page = new PagedListHolder<>();

		if (model.isPresent()) {

			page = new PagedListHolder<JukeBox>(getListComponentsFromJukesGivenSettingId(settingId, model));
			// .stream().filter(j ->
			// j.getModel().equals(model.get())).collect(Collectors.toList()));

		} else {

			page = new PagedListHolder<JukeBox>(getListComponentsFromJukesGivenSettingId(settingId, model));
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
