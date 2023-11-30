import BookList from "../../components/BookList";
import IssuedBookList from "../../components/IssuedBookList";
import { Link } from "react-router-dom";

const Issue = ({
  signedInUser,
  bookList,
  setBookList,
  issuedBookList,
  setIssuedBookList,
}) => {
  return (
    <div>
      <h2>Issue Book</h2>
      <Link to="/">Back</Link>
      <div>
        <BookList
          signedInUser={signedInUser}
          bookList={bookList}
          setBookList={setBookList}
          issuedBookList={issuedBookList}
          setIssuedBookList={setIssuedBookList}
        />
      </div>
    </div>
  );
};

export default Issue;
