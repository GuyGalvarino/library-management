import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { addBookToLibrary } from "../../../services/book";

const AdminAddBook = ({ signedInAdmin, bookListAdmin, setBookListAdmin }) => {
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [author, setAuthor] = useState("");
  const [publisher, setPublisher] = useState("");

  const nameHandler = (e) => {
    setName(e.target.value);
  };
  const authorHandler = (e) => {
    setAuthor(e.target.value);
  };
  const publisherHandler = (e) => {
    setPublisher(e.target.value);
  };
  const submitHandler = async (e) => {
    e.preventDefault();
    try {
      const newBook = await addBookToLibrary(
        name,
        author,
        publisher,
        signedInAdmin.email,
        signedInAdmin.token
      );
      setBookListAdmin([...bookListAdmin, newBook]);
      navigate("/admin");
    } catch (e) {
      console.error(e);
    }
  };
  return (
    <div>
      <Link to="/admin">Back</Link>
      <form onSubmit={submitHandler}>
        <div>
          <label htmlFor="name">Name</label>
          <input value={name} onChange={nameHandler} type="text" id="name" />
        </div>
        <div>
          <label htmlFor="author">Author</label>
          <input
            value={author}
            onChange={authorHandler}
            type="text"
            id="author"
          />
        </div>
        <div>
          <label htmlFor="name">Publisher</label>
          <input
            value={publisher}
            onChange={publisherHandler}
            type="text"
            id="publisher"
          />
        </div>
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default AdminAddBook;
