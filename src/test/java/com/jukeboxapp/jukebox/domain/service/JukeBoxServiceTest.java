package com.jukeboxapp.jukebox.domain.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jukeboxapp.jukebox.api.rest.v1.ressource.Components;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.JukeBox;
import com.jukeboxapp.jukebox.domain.client.JukeBoxClient;
import com.jukeboxapp.jukebox.domain.mapper.JukeBoxMapper;

@SpringJUnitConfig
public class JukeBoxServiceTest {

	private JukeBoxService service;

	private JukeBoxClient client;

	private JukeBoxMapper mapper;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		service = mock(JukeBoxServiceImpl.class);
		client = mock(JukeBoxClient.class);
		mapper = mock(JukeBoxMapper.class);

	}

	@org.junit.Test
	public void getPaginatedListWithSettingIdandModelTest() {

		List<JukeBox> listofJukes = new ArrayList<JukeBox>();
		List<Components> listComp = new ArrayList<Components>();

		JukeBox juke = new JukeBox();

		Components comp = new Components();

		comp.setName("speaker");

		juke.setId("5ca94a8acc046e7aa8040605");

		juke.setModel("angelina");

		listComp.add(comp);

		juke.setComponents(listComp);
		listofJukes.add(juke);

		PagedListHolder<JukeBox> page = new PagedListHolder<JukeBox>(listofJukes);

		doCallRealMethod().when(service).createPaginatedListOfJukes(Optional.of("5ca94a8acc046e7aa8040605"),
				Optional.of("angelina"), Optional.of(0), Optional.of(10));

		when(service.createPaginatedListOfJukes(Optional.of("5ca94a8acc046e7aa8040605"), Optional.of("angelina"),
				Optional.of(0), Optional.of(10)).getPageList()).thenReturn(listofJukes);

		assertEquals(1, page.getPageList().size());

		verify(service, times(1)).createPaginatedListOfJukes(Optional.of("5ca94a8acc046e7aa8040605"),
				Optional.of("angelina"), Optional.of(0), Optional.of(10));
	}

}
