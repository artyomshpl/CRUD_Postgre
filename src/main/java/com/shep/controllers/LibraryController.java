package com.shep.controllers;

import com.shep.entities.Book;
import com.shep.entities.Library;
import com.shep.repositories.BookRepository;
import com.shep.repositories.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libraries")
public class LibraryController {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Library> getAllLibraries() {
        return libraryRepository.findAll();
    }

    @PostMapping
    public Library createLibrary(@RequestBody Library library) {
        return libraryRepository.save(library);
    }

    @GetMapping("/{id}")
    public Library getLibraryById(@PathVariable Long id) {
        return libraryRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Library updateLibrary(@PathVariable Long id, @RequestBody Library libraryDetails) {
        Library library = libraryRepository.findById(id).orElse(null);
        if (library != null) {
            library.setName(libraryDetails.getName());
            return libraryRepository.save(library);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteLibrary(@PathVariable Long id) {
        libraryRepository.deleteById(id);
    }

    @PostMapping("/{libraryId}/books")
    public Book createBook(@PathVariable Long libraryId, @RequestBody Book book) {
        Library library = libraryRepository.findById(libraryId).orElse(null);
        if (library != null) {
            book.setLibrary(library);
            return bookRepository.save(book);
        }
        return null;
    }
}