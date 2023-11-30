import axios from "axios";

export const getIssuedBooks = async (userId, token) => {
  try {
    const { data: bookList } = await axios.get(
      `http://localhost:8080/issues/${userId}`,
      {
        headers: {
          Authorization: token,
        },
      }
    );
    return bookList;
  } catch (e) {
    console.error(e);
    throw new Error("could not get book list");
  }
};

export const issueBook = async (userId, bookId, token) => {
  try {
    const { data: bookList } = await axios.post(
      `http://localhost:8080/issues/${userId}`,
      { bookId },
      {
        headers: {
          Authorization: token,
        },
      }
    );
    return bookList;
  } catch (e) {
    console.error(e);
    throw new Error("could not issue book");
  }
};

export const removeBook = async (userId, bookId, token) => {
  try {
    const { data: bookList } = await axios.delete(
      `http://localhost:8080/issues/${userId}`,
      {
        headers: {
          Authorization: token,
        },
        data: {
          bookId,
        },
      }
    );
    return bookList;
  } catch (e) {
    console.error(e);
    if (e.response && e.response.status === 404) {
      throw new Error("book was not issued");
    }
    throw new Error("could not remove book");
  }
};
