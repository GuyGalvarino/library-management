package com.lms.library.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lms.library.dao.UserDao;
import com.lms.library.entities.Book;
import com.lms.library.entities.User;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
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
        when(bookService.getBook(2)).thenReturn(new Book("Book2", "Author2","Publisher2"));

        // Test
        assertEquals(2, issueService.getIssues(userId).size());
    }
    
    @Test
    void testPopulateBooks() {
        Integer bookId1 = 1;
        Integer bookId2 = 2;
        Book mockBook1 = new Book("Book1", "Author1","Publisher1");
        mockBook1.setBookId(bookId1);
        Book mockBook2 = new Book("Book2", "Author2","Publisher2");
        mockBook2.setBookId(bookId2);
        
        // Mock BookService's getBook method
    	
        when(bookService.getBook(bookId1)).thenReturn(mockBook1);
        when(bookService.getBook(bookId2)).thenReturn(mockBook2);
        when(bookService.getBook(3)).thenReturn(null); // Simulate a book not found case

        // Call the populateBooks method with a set of book IDs
        Set<Integer> bookIds = new HashSet<>(Arrays.asList(bookId1, bookId2, 3));
        List<Book> issuedBooks = issueService.populateBooks(bookIds);

        // Verify the behavior of the method
        assertEquals("Book1", issuedBooks.get(0).getName());
        assertEquals("Book2", issuedBooks.get(1).getName());

        // Verify that the bookService.getBook method is called with the correct book IDs
        verify(bookService, times(1)).getBook(1);
        verify(bookService, times(1)).getBook(2);
        verify(bookService, times(1)).getBook(3);

        // Verify that the bookService.getBook method is not called with invalid book IDs
        verify(bookService, never()).getBook(4);
    }

    @Test
    void testPopulateBooksWithEmptySet() {
        // Call the populateBooks method with an empty set of book IDs
        Set<Integer> bookIds = new HashSet<>();
        List<Book> issuedBooks = issueService.populateBooks(bookIds);

        // Verify that an empty list is returned for an empty set of book IDs
        assertTrue(issuedBooks.isEmpty());

        // Verify that the bookService.getBook method is not called
        verify(bookService, never()).getBook(anyInt());
    }

    @Test
    void testAddIssueWithValidBookAndUser() {
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
    void testAddIssueWithInvalidBook() {
        // Mocking data
        Integer bookId = 101;
        Integer userId = 1;
        User mockUser = new User();
        mockUser.setUserId(userId);
    	
        // Mock BookService's getBook method
        when(bookService.getBook(bookId)).thenReturn(null);

        // Call the addIssue method
        Book addedBook = issueService.addIssue(bookId, userId);

        // Verify that null is returned for an invalid book
        assertNull(addedBook);

        // Verify that the userDao.save method is not called
        verify(userDao, never()).save(any(User.class));
    }
    
    void testAddIssueWithInvalidUser() {
        // Mocking data
        Integer bookId = 101;
        Integer userId = 1;
        Book mockBook = new Book("Book1", "Author1","Publisher1");
        mockBook.setBookId(bookId);
        // Mock BookService's getBook method
        when(bookService.getBook(1)).thenReturn(mockBook);

        // Mock UserService's getUser method
        when(userService.getUser(userId)).thenReturn(null);

        // Call the addIssue method
        Book addedBook = issueService.addIssue(bookId, userId);

        // Verify that null is returned for an invalid user
        assertNull(addedBook);

        // Verify that the userDao.save method is not called
        verify(userDao, never()).save(any(User.class));
    }

    @Test
    void  testRemoveIssueWithValidUserAndBook() {
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
        // Verify that the userDao.save method is called
        verify(userDao, times(1)).save(mockUser);
    }
    
    @Test
    void testRemoveIssueWithInvalidUser() {
        // Mocking data
        Integer bookId = 101;
        Integer userId = 1;

        Book mockBook = new Book("Book1", "Author1","Publisher1");
        mockBook.setBookId(bookId);
   
        // Mock UserService's getUser method
        when(userService.getUser(userId)).thenReturn(null);

        // Call the removeIssue method
        Book removedBook = issueService.removeIssue(bookId,userId);

        // Verify that null is returned for an invalid user
        assertNull(removedBook);

        // Verify that the userDao.save method is not called
        verify(userDao, never()).save(any(User.class));
    }
}
