package com.lms.library.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.lms.library.entities.Book;
import com.lms.library.services.AuthorizationService;
import com.lms.library.services.IssueService;

@WebMvcTest(IssueController.class)
public class IssueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssueService issueService;

    @MockBean
    private AuthorizationService authorizationService;

    @Test
    public void testGetIssues_Success() throws Exception {
        when(authorizationService.verifyToken(any(), any())).thenReturn(true);
        when(issueService.getIssues(any())).thenReturn(Arrays.asList(new Book("Book1", "Author1", "Publisher1")));

        mockMvc.perform(get("/issues/{userId}", 123)
                .header("Authorization", "Bearer validToken")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Book1"))
                .andExpect(jsonPath("$[0].author").value("Author1"))
                .andExpect(jsonPath("$[0].publisher").value("Publisher1"));
    }

    @Test
    public void testAddIssue_Success() throws Exception {
        when(authorizationService.verifyToken(any(), any())).thenReturn(true);
        when(issueService.addIssue(any(), any())).thenReturn(new Book("NewBook", "NewAuthor", "NewPublisher"));

        mockMvc.perform(post("/issues/{userId}", 456)
                .header("Authorization", "Bearer validToken")
                .content("{\"bookId\": 789}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NewBook"))
                .andExpect(jsonPath("$.author").value("NewAuthor"))
                .andExpect(jsonPath("$.publisher").value("NewPublisher"));
    }

    @Test
    public void testRemoveIssue_Success() throws Exception {
        when(authorizationService.verifyToken(any(), any())).thenReturn(true);
        when(issueService.removeIssue(any(), any())).thenReturn(new Book("RemovedBook", "RemovedAuthor", "RemovedPublisher"));

        mockMvc.perform(delete("/issues/{userId}", 789)
                .header("Authorization", "Bearer validToken")
                .content("{\"bookId\": 987}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("RemovedBook"))
                .andExpect(jsonPath("$.author").value("RemovedAuthor"))
                .andExpect(jsonPath("$.publisher").value("RemovedPublisher"));
    }
}
