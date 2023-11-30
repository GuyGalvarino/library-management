import axios from "axios";

export const removeBookFromLibrary = async (bookId, adminEmail, adminToken) => {
  try {
    const { data: book } = await axios.delete(
      `http://localhost:8080/books/${bookId}`,
      {
        headers: {
          Authorization: adminToken,
        },
        data: {
          adminEmail: adminEmail,
        },
      }
    );
    return book;
  } catch (e) {
    if (e.response && e.response.status === 403) {
      throw new Error("unauthorized access");
    }
    if (e.response && e.response.status === 404) {
      throw new Error("book does not exist");
    }
    throw new Error("could not delete book from the library");
  }
};

export const getAllBooksFromLibrary = async () => {
  try {
    const { data: bookList } = await axios.get("http://localhost:8080/books");
    return bookList;
  } catch (e) {
    console.error(e);
    throw new Error("could not get the books from the library");
  }
};

export const addBookToLibrary = async (
  name,
  author,
  publisher,
  adminEmail,
  adminToken
) => {
  try {
    const { data: book } = await axios.post(
      "http://localhost:8080/books",
      {
        name,
        author,
        publisher,
        adminEmail,
      },
      {
        headers: {
          Authorization: adminToken,
        },
      }
    );
    return book;
  } catch (e) {
    console.error(e);
    throw new Error("could not add the book to the library");
  }
};
