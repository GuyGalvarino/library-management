package com.lms.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.library.entities.Book;
import com.lms.library.services.AuthorizationService;
import com.lms.library.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorizationService authorizationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllBooks() throws Exception {
        when(bookService.getBooks()).thenReturn(Collections.singletonList(new Book("Book1", "Author1", "Publisher1")));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Book1"))
                .andExpect(jsonPath("$[0].author").value("Author1"))
                .andExpect(jsonPath("$[0].publisher").value("Publisher1"));
    }

    @Test
    public void testGetBookById() throws Exception {
        when(bookService.getBook(1)).thenReturn(new Book("Book1", "Author1", "Publisher1"));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Book1"))
                .andExpect(jsonPath("$.author").value("Author1"))
                .andExpect(jsonPath("$.publisher").value("Publisher1"));
    }

    @Test
    public void testAddBook() throws Exception {
        when(bookService.addBook(any(), any(), any())).thenReturn(new Book("NewBook", "NewAuthor", "NewPublisher"));
        when(authorizationService.verifyAdminToken(any(), any())).thenReturn(true);
        mockMvc.perform(post("/books")
                        .header(HttpHeaders.AUTHORIZATION, "testToken")
                        .content(objectMapper.writeValueAsString(new BookController.BookRequest("NewBook", "NewAuthor", "NewPublisher", "admin@example.com")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NewBook"))
                .andExpect(jsonPath("$.author").value("NewAuthor"))
                .andExpect(jsonPath("$.publisher").value("NewPublisher"));
    }

    // Add tests for other endpoints as needed
}
