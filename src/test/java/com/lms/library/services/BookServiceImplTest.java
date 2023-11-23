package com.lms.library.services;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.lms.library.dao.BookDao;
import com.lms.library.dao.UserDao;
import com.lms.library.entities.Book;
import com.lms.library.entities.User;

public class BookServiceImplTest {
	
	@Test
	public void testAddBook_SuccessfullSave_ReturnBook() {
		//Arrange
		BookDao bookDao = mock(BookDao.class);
		UserDao userDao = mock(UserDao.class);
		BookServiceImpl bookServiceImpl = new BookServiceImpl();
		String name = "Sample";
		String author = "Swapnil";
		String publisher = "Saample_Pubisher";
		Book expectedBook =  new Book(name,author,publisher);
		when(bookDao.save(any(Book.class))).thenReturn(expectedBook);
		
		//Act
		Book result = bookServiceImpl.addBook(name, author, publisher);
		
		//Assert
		assertNotNull(result);
		assertEquals(expectedBook, result);
		
		verify(bookDao, times(1)).save(any(Book.class));
	}
	
	@Test
	public void testRemoveBook_BookExists_RemoveBookAndReturnBook() {
		//Arrange
		BookDao bookDao = mock(BookDao.class);
		UserDao userDao = mock(UserDao.class);
		BookServiceImpl bookServiceImpl = new BookServiceImpl();
	    Integer bookId =1;
	    Book expectedBook = new Book("Sample Book", "Swapnil", "Sample_Publisher");
	    when(bookDao.findById(eq(bookId))).thenReturn(java.util.Optional.of(expectedBook));
	    when(userDao.findAll()).thenReturn(new ArrayList<>());
		
		//Act
		Book result = bookServiceImpl.removeBook(bookId);
		
		//Assert
		assertNotNull(result);
		assertEquals(expectedBook, result);
		
		verify(bookDao, times(1)).findById(eq(bookId));
		verify(bookDao, times(1)).findAll();
		verify(bookDao, times(1)).saveAll(anyList());
		verify(bookDao, times(1)).deleteById(eq(bookId));

	}
	
	
	@Test
	public void testRemoveBook_BookDoesNotExists_ReturnsNull() {
		//Arrange
		BookDao bookDao = mock(BookDao.class);
		UserDao userDao = mock(UserDao.class);
		BookServiceImpl bookServiceImpl = new BookServiceImpl();
	    Integer bookId =1;
	    when(bookDao.findById(eq(bookId))).thenReturn(Optional.empty());
		
		//Act
		Book result = bookServiceImpl.removeBook(bookId);
		
		//Assert
		assertNull(result);
		
		verify(bookDao, times(1)).findById(eq(bookId));
		verify(bookDao, times(1)).findAll();
		verify(bookDao, times(1)).saveAll(anyList());
		verify(bookDao, times(1)).deleteById(eq(bookId));
	}
	
	@Test
	public void testGetBookById_BookExists_ReturnsBook() {
		//Arrange
		BookDao bookDao = mock(BookDao.class);
		BookServiceImpl bookServiceImpl = new BookServiceImpl();
		Integer bookId = 3;
		Book expectedBook = new Book("Sample Book", "Swapnil", "Sample_Publisher");
		when(bookDao.findById(eq(bookId))).thenReturn(java.util.Optional.of(expectedBook));
		
		//Act
	    Book result = bookServiceImpl.getBook(bookId);
		
		//Assert
		assertNotNull(result);
		assertEquals(expectedBook, result);
		
		verify(bookDao,times(1)).findById(eq(bookId));
	}
	
	@Test
	public void testGetBookById_BookDoesNotExists_ReturnsNull() {
		//Arrange
		BookDao bookDao = mock(BookDao.class);
		BookServiceImpl bookServiceImpl = new BookServiceImpl();
		Integer bookId = 3;
		when(bookDao.findById(eq(bookId))).thenReturn(Optional.empty());
		
		//Act
		Book result = bookServiceImpl.getBook(bookId);
		
		//Assert
		assertNull(result);
		
		verify(bookDao,times(1)).findById(eq(bookId));
	
	}
	
	

}
