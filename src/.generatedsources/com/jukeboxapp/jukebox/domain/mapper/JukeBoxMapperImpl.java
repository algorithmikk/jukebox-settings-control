package com.jukeboxapp.jukebox.domain.mapper;

import com.jukeboxapp.jukebox.api.rest.v1.ressource.Component;
import com.jukeboxapp.jukebox.api.rest.v1.ressource.JukeBox;
import com.jukeboxapp.jukebox.domain.dto.JukeBoxDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-10-15T23:39:13-0400",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 11.0.4 (Oracle Corporation)"
)
@org.springframework.stereotype.Component
public class JukeBoxMapperImpl implements JukeBoxMapper {

    @Override
    public JukeBox dtoToJukeBox(JukeBoxDto dto) {
        if ( dto == null ) {
            return null;
        }

        JukeBox jukeBox = new JukeBox();

        jukeBox.setModel( dto.getModel() );
        jukeBox.setId( dto.getId() );
        List<Component> list = dto.getListOfComponents();
        if ( list != null ) {
            jukeBox.setListOfComponents( new ArrayList<Component>( list ) );
        }

        return jukeBox;
    }
}
