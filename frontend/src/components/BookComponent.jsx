import Book from "./Book";
import "./components.css";

const BookComponent = ({ book, handler, isIssued }) => {
  return (
    <div className="list-item">
      <Book name={book.name} author={book.author} publisher={book.publisher}>
        {isIssued ? (
          <button onClick={handler} className="list-function red-bg">
            Remove Book
          </button>
        ) : (
          <button onClick={handler} className="list-function green-bg">
            Add Book
          </button>
        )}
      </Book>
    </div>
  );
};

export default BookComponent;
