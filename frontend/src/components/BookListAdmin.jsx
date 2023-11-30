import { useState, useEffect } from "react";
import BookComponentAdmin from "./BookComponentAdmin";
import "./components.css";
import {
  getAllBooksFromLibrary,
  removeBookFromLibrary,
} from "../services/book";

const BookListAdmin = ({ signedInAdmin, bookListAdmin, setBookListAdmin }) => {
  useEffect(() => {
    const fetchBooksAdmin = async () => {
      const resBookList = await getAllBooksFromLibrary();
      setBookListAdmin(resBookList);
    };
    if (!bookListAdmin) {
      fetchBooksAdmin();
    }
  }, [signedInAdmin]);

  const removeHandler = (bookId) => {
    return async () => {
      try {
        const res = await removeBookFromLibrary(
          bookId,
          signedInAdmin.email,
          signedInAdmin.token
        );
        setBookListAdmin(
          bookListAdmin.filter((book) => book.bookId !== res.bookId)
        );
      } catch (e) {
        console.error(e);
      }
    };
  };

  return (
    <div className="list">
      {bookListAdmin && bookListAdmin.map((book) => (
        <BookComponentAdmin
          book={book}
          key={book.bookId}
          removeHandler={removeHandler(book.bookId)}
        />
      ))}
    </div>
  );
};

export default BookListAdmin;
