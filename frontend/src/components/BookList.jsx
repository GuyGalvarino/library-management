import { useEffect, useState } from "react";
import { issueBook, getIssuedBooks } from "../services/issues";
import { getAllBooksFromLibrary } from "../services/book";
import BookComponent from "./BookComponent";

const BookList = ({
  signedInUser,
  bookList,
  setBookList,
  issuedBookList,
  setIssuedBookList,
}) => {
  const [unissuedBookList, setUnissuedBookList] = useState(null);
  useEffect(() => {
    const fetchBooks = async () => {
      let resIssuedBookList = issuedBookList;
      if (!issuedBookList) {
        resIssuedBookList = await getIssuedBooks(
          signedInUser.userId,
          signedInUser.token
        );
        setIssuedBookList(resIssuedBookList);
      }
      let resBookList = bookList;
      if (!bookList) {
        resBookList = await getAllBooksFromLibrary();
        setBookList(resBookList);
      }
      setUnissuedBookList(
        resBookList.filter((book) => {
          for (let issuedBook of resIssuedBookList) {
            if (book.bookId === issuedBook.bookId) {
              return false;
            }
          }
          return true;
        })
      );
    };
    if (signedInUser) {
      fetchBooks();
    }
  }, [signedInUser, issuedBookList]);

  const addHandler = (bookId) => {
    return async () => {
      try {
        const res = await issueBook(
          signedInUser.userId,
          bookId,
          signedInUser.token
        );
        setIssuedBookList([...issuedBookList, res]);
        setUnissuedBookList(
          bookList.filter((book) => book.bookId !== res.bookId)
        );
      } catch (e) {
        console.error(e);
      }
    };
  };

  return (
    <div className="list">
      {unissuedBookList &&
        unissuedBookList.map((book) => (
          <BookComponent
            book={book}
            handler={addHandler(book.bookId)}
            isIssued={false}
            key={book.bookId}
          />
        ))}
    </div>
  );
};

export default BookList;
