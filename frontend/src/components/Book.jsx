import { useState } from "react";
import { issueBook, removeBook } from "../services/issues";
import { removeBookFromLibrary } from "../services/book";
import "./components.css";

const Book = ({ name, author, publisher, children }) => {
  return (
    <div>
      {name ? <h3 className="list-header">{name}</h3> : null}
      <div className="list-body">
        {author ? <div>Author: {author}</div> : null}
        {publisher ? <div>Publisher: {publisher}</div> : null}
      </div>
      {children}
    </div>
  );
};

export default Book;
