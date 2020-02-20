package com.jukeboxapp.jukebox.domain.dto;

import java.util.List;

import com.jukeboxapp.jukebox.api.rest.v1.ressource.Requires;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettingsDto {

	private List<Requires> settings;

}
