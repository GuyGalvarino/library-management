package com.lms.library.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
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
    void testGetIssuesCorrectUser() {
        Integer userId = 1;
        User mockUser = new User();
        mockUser.setUserId(userId);
        mockUser.issueBook(1);
        mockUser.issueBook(2);

        when(userDao.findById(userId)).thenReturn(java.util.Optional.ofNullable(mockUser));
        when(bookService.getBook(1)).thenReturn(new Book("Book1", "Author1","Publisher1"));
        when(bookService.getBook(2)).thenReturn(new Book("Book2", "Author2","Publisher2"));
        
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
    	
        when(bookService.getBook(bookId1)).thenReturn(mockBook1);
        when(bookService.getBook(bookId2)).thenReturn(mockBook2);
        when(bookService.getBook(3)).thenReturn(null); // Simulate a book not found case

        Set<Integer> bookIds = new HashSet<>(Arrays.asList(bookId1, bookId2, 3));
        List<Book> issuedBooks = issueService.populateBooks(bookIds);

        assertEquals("Book1", issuedBooks.get(0).getName());
        assertEquals("Book2", issuedBooks.get(1).getName());

        verify(bookService, times(1)).getBook(1);
        verify(bookService, times(1)).getBook(2);
        verify(bookService, times(1)).getBook(3);

        verify(bookService, never()).getBook(4);
    }

    @Test
    void testPopulateBooksWithEmptySet() {
        Set<Integer> bookIds = new HashSet<>();
        List<Book> issuedBooks = issueService.populateBooks(bookIds);

        assertTrue(issuedBooks.isEmpty());

        verify(bookService, never()).getBook(anyInt());
    }

    @Test
    void testAddIssueWithValidBookAndUser() {
        Integer bookId = 101;
        Integer userId = 1;

        Book mockBook = new Book("Book1", "Author1","Publisher1");
        User mockUser = new User();
        mockUser.setUserId(userId);
        mockBook.setBookId(bookId);

        when(bookService.getBook(bookId)).thenReturn(mockBook);
        when(userService.getUser(userId)).thenReturn(mockUser);
  
        assertNotNull(issueService.addIssue(bookId, userId));
        assertTrue(mockUser.getIssuedBooks().contains(bookId));
        verify(userDao, times(1)).save(mockUser);
    }
    
    @Test
    void testAddIssueWithInvalidBook() {
        Integer bookId = 101;
        Integer userId = 1;
        User mockUser = new User();
        mockUser.setUserId(userId);
    	
        when(bookService.getBook(bookId)).thenReturn(null);

        Book addedBook = issueService.addIssue(bookId, userId);

        assertNull(addedBook);

        verify(userDao, never()).save(any(User.class));
    }
    
    void testAddIssueWithInvalidUser() {
        Integer bookId = 101;
        Integer userId = 1;
        Book mockBook = new Book("Book1", "Author1","Publisher1");
        mockBook.setBookId(bookId);

        when(bookService.getBook(1)).thenReturn(mockBook);

        when(userService.getUser(userId)).thenReturn(null);

        Book addedBook = issueService.addIssue(bookId, userId);

        assertNull(addedBook);

        verify(userDao, never()).save(any(User.class));
    }

    @Test
    void  testRemoveIssueWithValidUserAndBook() {
        Integer bookId = 101;
        Integer userId = 1;

        Book mockBook = new Book("Book1", "Author1","Publisher1");
        User mockUser = new User();
        mockUser.setUserId(userId);
        mockBook.setBookId(bookId);
        mockUser.issueBook(bookId);

        when(userService.getUser(userId)).thenReturn(mockUser);
        when(bookService.getBook(bookId)).thenReturn(mockBook);

        assertNotNull(issueService.removeIssue(bookId, userId));
        assertFalse(mockUser.getIssuedBooks().contains(bookId));

        verify(userDao, times(1)).save(mockUser);
    }
    
    @Test
    void testRemoveIssueWithInvalidUser() {
        Integer bookId = 101;
        Integer userId = 1;

        Book mockBook = new Book("Book1", "Author1","Publisher1");
        mockBook.setBookId(bookId);

        when(userService.getUser(userId)).thenReturn(null);

        Book removedBook = issueService.removeIssue(bookId,userId);

        assertNull(removedBook);

        verify(userDao, never()).save(any(User.class));
    }
}
