package com.shep;

import com.shep.DTO.BookDTO;
import com.shep.DTO.LibraryDTO;
import com.shep.entities.Book;
import com.shep.entities.Library;
import com.shep.mappers.BookMapper;
import com.shep.mappers.LibraryMapper;
import com.shep.repositories.BookRepository;
import com.shep.repositories.LibraryRepository;
import com.shep.services.LibraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LibraryServiceTest {

    @Mock
    private LibraryRepository libraryRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LibraryService libraryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllLibraries_Positive() {
        Library library1 = new Library();
        library1.setId(1L);
        library1.setName("Library 1");

        Library library2 = new Library();
        library2.setId(2L);
        library2.setName("Library 2");

        when(libraryRepository.findAll()).thenReturn(Arrays.asList(library1, library2));

        List<LibraryDTO> libraryDTOs = libraryService.getAllLibraries();

        assertEquals(2, libraryDTOs.size());
        assertEquals("Library 1", libraryDTOs.get(0).getName());
        assertEquals("Library 2", libraryDTOs.get(1).getName());
    }

    @Test
    public void testGetAllLibraries_Negative() {
        when(libraryRepository.findAll()).thenReturn(Arrays.asList());

        List<LibraryDTO> libraryDTOs = libraryService.getAllLibraries();

        assertTrue(libraryDTOs.isEmpty());
    }

    @Test
    public void testGetAllLibraries_CornerCase() {
        when(libraryRepository.findAll()).thenReturn(null);

        List<LibraryDTO> libraryDTOs = libraryService.getAllLibraries();

        assertNull(libraryDTOs);
    }

    @Test
    public void testCreateLibrary_Positive() {
        LibraryDTO libraryDTO = new LibraryDTO();
        libraryDTO.setName("New Library");

        Library library = LibraryMapper.INSTANCE.toEntity(libraryDTO);
        when(libraryRepository.save(any(Library.class))).thenReturn(library);

        LibraryDTO result = libraryService.createLibrary(libraryDTO);

        assertNotNull(result);
        assertEquals("New Library", result.getName());
    }

    @Test
    public void testCreateLibrary_Negative() {
        LibraryDTO libraryDTO = new LibraryDTO();
        libraryDTO.setName(null);

        when(libraryRepository.save(any(Library.class))).thenReturn(null);

        LibraryDTO result = libraryService.createLibrary(libraryDTO);

        assertNull(result);
    }

    @Test
    public void testCreateLibrary_CornerCase() {
        LibraryDTO libraryDTO = new LibraryDTO();
        libraryDTO.setName("");

        when(libraryRepository.save(any(Library.class))).thenReturn(null);

        LibraryDTO result = libraryService.createLibrary(libraryDTO);

        assertNull(result);
    }

    @Test
    public void testGetLibraryById_Positive() {
        Library library = new Library();
        library.setId(1L);
        library.setName("Library 1");

        when(libraryRepository.findById(1L)).thenReturn(Optional.of(library));

        LibraryDTO result = libraryService.getLibraryById(1L);

        assertNotNull(result);
        assertEquals("Library 1", result.getName());
    }

    @Test
    public void testGetLibraryById_Negative() {
        when(libraryRepository.findById(1L)).thenReturn(Optional.empty());

        LibraryDTO result = libraryService.getLibraryById(1L);

        assertNull(result);
    }

    @Test
    public void testGetLibraryById_CornerCase() {
        when(libraryRepository.findById(null)).thenReturn(Optional.empty());

        LibraryDTO result = libraryService.getLibraryById(null);

        assertNull(result);
    }

    @Test
    public void testUpdateLibrary_Positive() {
        Library library = new Library();
        library.setId(1L);
        library.setName("Library 1");

        LibraryDTO libraryDTO = new LibraryDTO();
        libraryDTO.setName("Updated Library");

        when(libraryRepository.findById(1L)).thenReturn(Optional.of(library));
        when(libraryRepository.save(any(Library.class))).thenReturn(library);

        LibraryDTO result = libraryService.updateLibrary(1L, libraryDTO);

        assertNotNull(result);
        assertEquals("Updated Library", result.getName());
    }

    @Test
    public void testUpdateLibrary_Negative() {
        when(libraryRepository.findById(1L)).thenReturn(Optional.empty());

        LibraryDTO libraryDTO = new LibraryDTO();
        libraryDTO.setName("Updated Library");

        LibraryDTO result = libraryService.updateLibrary(1L, libraryDTO);

        assertNull(result);
    }

    @Test
    public void testUpdateLibrary_CornerCase() {
        when(libraryRepository.findById(null)).thenReturn(Optional.empty());

        LibraryDTO libraryDTO = new LibraryDTO();
        libraryDTO.setName("Updated Library");

        LibraryDTO result = libraryService.updateLibrary(null, libraryDTO);

        assertNull(result);
    }

    @Test
    public void testDeleteLibrary_Positive() {
        doNothing().when(libraryRepository).deleteById(1L);

        libraryService.deleteLibrary(1L);

        verify(libraryRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteLibrary_Negative() {
        doThrow(new RuntimeException("Error")).when(libraryRepository).deleteById(1L);

        assertThrows(RuntimeException.class, () -> libraryService.deleteLibrary(1L));
    }

    @Test
    public void testDeleteLibrary_CornerCase() {
        doNothing().when(libraryRepository).deleteById(null);

        libraryService.deleteLibrary(null);

        verify(libraryRepository, times(1)).deleteById(null);
    }

    @Test
    public void testCreateBook_Positive() {
        Library library = new Library();
        library.setId(1L);
        library.setName("Library 1");

        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Book 1");

        Book book = BookMapper.INSTANCE.toEntity(bookDTO);
        book.setLibrary(library);

        when(libraryRepository.findById(1L)).thenReturn(Optional.of(library));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO result = libraryService.createBook(1L, bookDTO);

        assertNotNull(result);
        assertEquals("Book 1", result.getTitle());
    }

    @Test
    public void testCreateBook_Negative() {
        when(libraryRepository.findById(1L)).thenReturn(Optional.empty());

        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Book 1");

        BookDTO result = libraryService.createBook(1L, bookDTO);

        assertNull(result);
    }

    @Test
    public void testCreateBook_CornerCase() {
        when(libraryRepository.findById(null)).thenReturn(Optional.empty());

        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Book 1");

        BookDTO result = libraryService.createBook(null, bookDTO);

        assertNull(result);
    }
}