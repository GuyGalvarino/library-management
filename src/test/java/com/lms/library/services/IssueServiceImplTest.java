package com.lms.library.services;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lms.library.dao.BookDao;
import com.lms.library.dao.UserDao;
import com.lms.library.entities.Book;
import com.lms.library.entities.User;

@ExtendWith(MockitoExtension.class)
public class IssueServiceImplTest {
	
    @Mock
    private UserDao userDao;

    @Mock
    private BookDao bookDao;
    
    private UserService userService;
    
    private BookService bookService;

    private IssueService issueService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        // Initialize sample data for testing
    	bookService = new BookServiceImpl(bookDao, userDao);
    	issueService = new IssueServiceImpl(bookService, userService, userDao);	
        sampleUser = new User();
        sampleUser.setUserId(1);
        sampleUser.issueBook(1);
        sampleUser.issueBook(2);
    }
    
	    @Test
	    void testGetIssues() {
	        // Mock BookService's getBook method
	    	when(bookDao.findById(1)).thenReturn(Optional.of(new Book("Book1","Rio","RkPublication")));
	    	when(bookDao.findById(2)).thenReturn(Optional.of(new Book("Book2","Mio","RkPublication")));
	    	when(userDao.findById(1)).thenReturn(Optional.of(sampleUser));

	        // Call the getIssues method
	        List<Book> issuedBooks = issueService.getIssues(1);

	        // Verify that the list of issued books is returned correctly
	        assertEquals(2, issuedBooks.size());
	        assertEquals("Book1", issuedBooks.get(0).getName());
	        assertEquals("Book2", issuedBooks.get(1).getName());
	    }

	    @Test
	    void testAddIssue() {
	        // Mock BookService's getBook method
	        when(bookDao.getBook(1)).thenReturn(sampleBook);
	        when(bookDao.getBook(2)).thenReturn(null); // Simulate a book not found case

	        // Mock UserService's getUser method
	        when(userService.getUser(1)).thenReturn(sampleUser);

	        // Execute the addIssue method
	        Book addedBook = issueService.addIssue(1, 1);

	        // Verify that the book is added successfully
	        assertEquals(sampleBook, addedBook);

	        // Verify that the userDao.save method is called
	        verify(userDao, times(1)).save(sampleUser);
	    }
//
//	   // @Test
//	    void testRemoveIssue() {
//	        // Mock BookService's getBook method
//	        when(bookService.getBook(1)).thenReturn(sampleBook);
//	        when(bookService.getBook(2)).thenReturn(null); // Simulate a book not found case
//
//	        // Mock UserService's getUser method
//	        when(userService.getUser(1)).thenReturn(sampleUser);
//
//	        // Execute the removeIssue method
//	        Book removedBook = issueService.removeIssue(1, 1);
//
//	        // Verify that the book is removed successfully
//	        assertEquals(sampleBook, removedBook);
//
//	        // Verify that the userDao.save method is called
//	        verify(userDao, times(1)).save(sampleUser);
//	    }

}
