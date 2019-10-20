package com.jukeboxapp.jukebox.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.jukeboxapp.jukebox.api.rest.v1.ressource.JukeBox;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.Settings;
import com.jukeboxapp.jukebox.domain.dto.JukeBoxDto;
import com.jukeboxapp.jukebox.domain.dto.SettingsDto;

@Mapper(componentModel = "spring")
public interface JukeBoxMapper {

	@Mappings({ @Mapping(source = "id", target = "id"), @Mapping(source = "model", target = "model"),
			@Mapping(source = "components", target = "components") })
	JukeBox dtoToJukeBox(JukeBoxDto dto);

	@Mappings({ @Mapping(source = "settings", target = "settings") })
	Settings settingsDtoToSettings(SettingsDto dto);

}
