package com.shep.mappers;

import com.shep.DTO.LibraryDTO;
import com.shep.entities.Library;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LibraryMapper {
    LibraryMapper INSTANCE = Mappers.getMapper(LibraryMapper.class);

    LibraryDTO toDto(Library library);

    Library toEntity(LibraryDTO libraryDTO);

    List<LibraryDTO> toDtoList(List<Library> libraries);
    List<Library> toEntityList(List<LibraryDTO> libraryDTOs);
}