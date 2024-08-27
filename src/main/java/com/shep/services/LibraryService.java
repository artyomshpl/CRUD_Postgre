package com.shep.services;

import com.shep.DTO.BookDTO;
import com.shep.DTO.LibraryDTO;
import com.shep.entities.Book;
import com.shep.entities.Library;
import com.shep.mappers.BookMapper;
import com.shep.mappers.LibraryMapper;
import com.shep.repositories.BookRepository;
import com.shep.repositories.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<LibraryDTO> getAllLibraries() {
        List<Library> libraries = libraryRepository.findAll();
        return LibraryMapper.INSTANCE.toDtoList(libraries);
    }

    public LibraryDTO createLibrary(LibraryDTO libraryDTO) {
        Library library = LibraryMapper.INSTANCE.toEntity(libraryDTO);
        Library savedLibrary = libraryRepository.save(library);
        return LibraryMapper.INSTANCE.toDto(savedLibrary);
    }

    public LibraryDTO getLibraryById(Long id) {
        Library library = libraryRepository.findById(id).orElse(null);
        return library != null ? LibraryMapper.INSTANCE.toDto(library) : null;
    }

    public LibraryDTO updateLibrary(Long id, LibraryDTO libraryDetails) {
        Library library = libraryRepository.findById(id).orElse(null);
        if (library != null) {
            library.setName(libraryDetails.getName());
            Library savedLibrary = libraryRepository.save(library);
            return LibraryMapper.INSTANCE.toDto(savedLibrary);
        }
        return null;
    }

    public void deleteLibrary(Long id) {
        libraryRepository.deleteById(id);
    }

    public BookDTO createBook(Long libraryId, BookDTO bookDTO) {
        Library library = libraryRepository.findById(libraryId).orElse(null);
        if (library != null) {
            Book book = BookMapper.INSTANCE.toEntity(bookDTO);
            book.setLibrary(library);
            Book savedBook = bookRepository.save(book);
            return BookMapper.INSTANCE.toDto(savedBook);
        }
        return null;
    }
}