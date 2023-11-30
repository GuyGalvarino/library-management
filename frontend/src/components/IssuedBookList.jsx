import { useEffect } from "react";
import { removeBook, getIssuedBooks } from "../services/issues";
import { getAllBooksFromLibrary } from "../services/book";
import BookComponent from "./BookComponent";

const IssuedBookList = ({
  signedInUser,
  issuedBookList,
  setIssuedBookList,
}) => {
  useEffect(() => {
    const fetchBooks = async () => {
      if (!issuedBookList) {
        const resIssuedBookList = await getIssuedBooks(
          signedInUser.userId,
          signedInUser.token
        );
        setIssuedBookList(resIssuedBookList);
      }
    };
    if (signedInUser) {
      fetchBooks();
    }
  }, [signedInUser]);

  const removeHandler = (bookId) => {
    return async () => {
      try {
        const res = await removeBook(
          signedInUser.userId,
          bookId,
          signedInUser.token
        );
        setIssuedBookList(
          issuedBookList.filter((book) => book.bookId !== res.bookId)
        );
      } catch (e) {
        console.error(e);
      }
    };
  };

  return (
    <div className="list">
      <h3>Issued Books</h3>
      {issuedBookList &&
        issuedBookList.map((book) => (
          <BookComponent
            book={book}
            handler={removeHandler(book.bookId)}
            isIssued={true}
            key={book.bookId}
          />
        ))}
    </div>
  );
};

export default IssuedBookList;
