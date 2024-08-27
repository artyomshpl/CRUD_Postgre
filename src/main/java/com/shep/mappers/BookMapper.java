package com.shep.mappers;

import com.shep.DTO.BookDTO;
import com.shep.entities.Book;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "library.id", target = "libraryId")
    BookDTO toDto(Book book);

    @Mapping(source = "libraryId", target = "library.id")
    Book toEntity(BookDTO bookDTO);

    List<BookDTO> toDtoList(List<Book> books);
    List<Book> toEntityList(List<BookDTO> bookDTOs);
}