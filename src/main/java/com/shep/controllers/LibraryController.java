package com.shep.controllers;

import com.shep.DTO.BookDTO;
import com.shep.DTO.LibraryDTO;
import com.shep.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libraries")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping
    public List<LibraryDTO> getAllLibraries() {
        return libraryService.getAllLibraries();
    }

    @PostMapping
    public LibraryDTO createLibrary(@RequestBody LibraryDTO libraryDTO) {
        return libraryService.createLibrary(libraryDTO);
    }

    @GetMapping("/{id}")
    public LibraryDTO getLibraryById(@PathVariable Long id) {
        return libraryService.getLibraryById(id);
    }

    @PutMapping("/{id}")
    public LibraryDTO updateLibrary(@PathVariable Long id, @RequestBody LibraryDTO libraryDetails) {
        return libraryService.updateLibrary(id, libraryDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteLibrary(@PathVariable Long id) {
        libraryService.deleteLibrary(id);
    }

    @PostMapping("/{libraryId}/books")
    public BookDTO createBook(@PathVariable Long libraryId, @RequestBody BookDTO bookDTO) {
        return libraryService.createBook(libraryId, bookDTO);
    }
}