package com.shep.DTO;

import lombok.Data;

import java.util.List;

@Data
public class LibraryDTO {
    private Long id;
    private String name;
    private List<BookDTO> books;
}