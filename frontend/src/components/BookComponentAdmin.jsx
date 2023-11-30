import Book from "./Book";
import "./components.css";

const BookComponentAdmin = ({ book, removeHandler }) => {
  return (
    <div className="list-item">
      <Book name={book.name} author={book.author} publisher={book.publisher}>
        <button onClick={removeHandler} className="list-function">Remove Book</button>
      </Book>
    </div>
  );
};

export default BookComponentAdmin;
