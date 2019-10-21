package com.jukeboxapp.jukebox.domain.mapper;

import com.jukeboxapp.jukebox.api.rest.v1.ressource.Components;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.JukeBox;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.Requires;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.Settings;
import com.jukeboxapp.jukebox.domain.dto.JukeBoxDto;
import com.jukeboxapp.jukebox.domain.dto.SettingsDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-10-21T00:21:30-0400",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 11.0.4 (Oracle Corporation)"
)
@Component
public class JukeBoxMapperImpl implements JukeBoxMapper {

    @Override
    public JukeBox dtoToJukeBox(JukeBoxDto dto) {
        if ( dto == null ) {
            return null;
        }

        JukeBox jukeBox = new JukeBox();

        List<Components> list = dto.getComponents();
        if ( list != null ) {
            jukeBox.setComponents( new ArrayList<Components>( list ) );
        }
        jukeBox.setModel( dto.getModel() );
        jukeBox.setId( dto.getId() );

        return jukeBox;
    }

    @Override
    public Settings settingsDtoToSettings(SettingsDto dto) {
    	
    	//change
        if ( dto == null ) {
            return null;
        }

        Settings settings = new Settings();

        List<Requires> list = dto.getSettings();
        if ( list != null ) {
            settings.setSettings( new ArrayList<Requires>( list ) );
        }

        return settings;
    }
}
