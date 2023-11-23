package com.lms.library.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.lms.library.dao.UserDao;
import com.lms.library.entities.Book;
import com.lms.library.entities.User;

@SpringBootTest
class IssueServiceImplTest {

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private IssueServiceImpl issueService;

    @Test
    void testGetIssues() {
        // Mocking data
        Integer userId = 1;
        User mockUser = new User();
        mockUser.setUserId(userId);
        mockUser.issueBook(1);
        mockUser.issueBook(2);

        when(userDao.findById(userId)).thenReturn(java.util.Optional.ofNullable(mockUser));
        when(bookService.getBook(1)).thenReturn(new Book("Book1", "Author1","Publisher1"));
        when(bookService.getBook(102)).thenReturn(new Book("Book1", "Author1","Publisher1"));

        // Test
        assertEquals(2, issueService.getIssues(userId).size());
    }

    @Test
    void testAddIssue() {
        // Mocking data
        Integer bookId = 101;
        Integer userId = 1;

        Book mockBook = new Book("Book1", "Author1","Publisher1");
        User mockUser = new User();
        mockUser.setUserId(userId);
        mockBook.setBookId(bookId);
        

        when(bookService.getBook(bookId)).thenReturn(mockBook);
        when(userService.getUser(userId)).thenReturn(mockUser);

        // Test
        assertNotNull(issueService.addIssue(bookId, userId));
        assertTrue(mockUser.getIssuedBooks().contains(bookId));
    }

    @Test
    void testRemoveIssue() {
        // Mocking data
        Integer bookId = 101;
        Integer userId = 1;

        Book mockBook = new Book("Book1", "Author1","Publisher1");
        User mockUser = new User();
        mockUser.setUserId(userId);
        mockBook.setBookId(bookId);
        mockUser.issueBook(bookId);

        when(userService.getUser(userId)).thenReturn(mockUser);
        when(bookService.getBook(bookId)).thenReturn(mockBook);

        // Test
        assertNotNull(issueService.removeIssue(bookId, userId));
        assertFalse(mockUser.getIssuedBooks().contains(bookId));
    }
}
