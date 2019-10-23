package com.jukeboxapp.jukebox.domain.service;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.support.PagedListHolder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.jukeboxapp.jukebox.api.rest.v1.ressource.Components;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.JukeBox;

import java.util.Optional;

public class JukeBoxServiceTest {

	@InjectMocks
	JukeBoxService service;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
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

		PagedListHolder<JukeBox> page = new PagedListHolder<JukeBox>(listofJukes);

		when(service.createPaginatedListOfJukes(Optional.of("5ca94a8acc046e7aa8040605"), Optional.of("angelina"),
				Optional.of(0), Optional.of(10))).thenReturn(page);

		assertEquals(1, page.getPageList().size());
		verify(service, times(1)).createPaginatedListOfJukes(Optional.of("5ca94a8acc046e7aa8040605"),
				Optional.of("angelina"), Optional.of(0), Optional.of(10));
	}

}
