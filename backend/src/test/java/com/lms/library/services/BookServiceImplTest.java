package com.lms.library.services;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import com.lms.library.dao.BookDao;
import com.lms.library.dao.UserDao;
import com.lms.library.entities.Book;
import com.lms.library.entities.User;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class BookServiceImplTest {
	@Test
	public void testAddBook_SuccessfullSave_ReturnBook() {
		BookDao bookDao = mock(BookDao.class);
		UserDao userDao = mock(UserDao.class);
		BookServiceImpl bookServiceImpl = new BookServiceImpl(bookDao,userDao);
		String name = "Sample";
		String author = "Swapnil";
		String publisher = "Sample_Pubisher";
		Book expectedBook =  new Book(name,author,publisher);
		when(bookDao.save(any(Book.class))).thenReturn(expectedBook);

		Book result = bookServiceImpl.addBook(name, author, publisher);

		assertNotNull(result);
		assertEquals(expectedBook, result);
		
		verify(bookDao, times(1)).save(any(Book.class));
	}
	
	@Test
	public void testAddBook_ExceptionThrown_ReturnsNull() {
		BookDao bookDao = mock(BookDao.class);
		UserDao userDao = mock(UserDao.class);
		BookServiceImpl bookServiceImpl = new BookServiceImpl(bookDao,userDao);
		String name = "Sample";
		String author = "Swapnil";
		String publisher = "Saample_Pubisher";

	    when(bookDao.save(any(Book.class))).thenThrow(new RuntimeException("Database error"));

	    Book result = bookServiceImpl.addBook(name, author, publisher);

	    assertNull(result);
	    verify(bookDao, times(1)).save(any(Book.class));
	}
	
	@Test
	public void testRemoveBook_BookExists_RemoveBookAndReturnBook() {
		BookDao bookDao = mock(BookDao.class);
		UserDao userDao = mock(UserDao.class);
		BookServiceImpl bookServiceImpl = new BookServiceImpl(bookDao,userDao);
	    Book expectedBook = new Book("Sample Book", "Swapnil", "Sample_Publisher");
	    Integer bookId = 1;
	    expectedBook.setBookId(bookId);
	    User user = new User("Sagnik", "sagnik@gmail.com", "password");
	    Integer userId=1;
	    user.setUserId(userId);
	   
	    when(bookDao.findById(eq(bookId))).thenReturn(java.util.Optional.of(expectedBook));
	    when(userDao.findAll()).thenReturn(List.of(user));

		Book result = bookServiceImpl.removeBook(bookId);

		assertNotNull(result);
		assertEquals(expectedBook, result);
		
		verify(bookDao, times(1)).findById(eq(bookId));
		verify(userDao, times(1)).saveAll(anyList());
		verify(bookDao, times(1)).deleteById(eq(bookId));
	}
	
	@Test
	public void testRemoveBook_BookDoesNotExists_ReturnsNull() {
		BookDao bookDao = mock(BookDao.class);
		UserDao userDao = mock(UserDao.class);
		BookServiceImpl bookServiceImpl = new BookServiceImpl(bookDao,userDao);
	    Integer bookId =1;
	    when(bookDao.findById(eq(bookId))).thenReturn(Optional.empty());

		Book result = bookServiceImpl.removeBook(bookId);

		assertNull(result);
		
		verify(bookDao, times(1)).findById(eq(bookId));
		verify(bookDao, times(0)).findAll();
		verify(bookDao, times(0)).saveAll(anyList());
		verify(bookDao, times(0)).deleteById(eq(bookId));
	}
	
	@Test
	public void testGetBookById_BookExists_ReturnsBook() {
		BookDao bookDao = mock(BookDao.class);
		UserDao userDao = mock(UserDao.class);
		BookServiceImpl bookServiceImpl = new BookServiceImpl(bookDao,userDao);
		Book expectedBook = new Book("Sample Book", "Swapnil", "Sample_Publisher");
		Integer bookId = expectedBook.getBookId();
		when(bookDao.findById(eq(bookId))).thenReturn(java.util.Optional.of(expectedBook));
		
	    Book result = bookServiceImpl.getBook(bookId);

		assertNotNull(result);
		assertEquals(expectedBook, result);
		
		verify(bookDao,times(1)).findById(eq(bookId));
	}
	
	@Test
	public void testGetBookById_BookDoesNotExists_ReturnsNull() {
		BookDao bookDao = mock(BookDao.class);
		UserDao userDao = mock(UserDao.class);
		BookServiceImpl bookServiceImpl = new BookServiceImpl(bookDao,userDao);
		Integer bookId = 3;
		when(bookDao.findById(eq(bookId))).thenReturn(Optional.empty());

		Book result = bookServiceImpl.getBook(bookId);

		assertNull(result);
		verify(bookDao,times(1)).findById(eq(bookId));
	}
}
