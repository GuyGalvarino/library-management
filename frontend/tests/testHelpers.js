export const testSignedInUser = {
  userId: "0",
  name: "test user",
  email: "test@test.xyz",
  token: "asldklajlajslkasdj",
};

export let signedInUser;

export const setSignedInUser = (newValue) => {
  signedInUser = newValue;
};

export const testBook = {
  bookId: 0,
  name: "Test Book 0",
  author: "Author 1",
  publisher: "Publisher 0",
};

export const testBookList = [
  {
    bookId: 1,
    name: "Test Book 1",
    author: "Author 1",
    publisher: "Publisher 1",
  },
  {
    bookId: 2,
    name: "Test Book 2",
    author: "Author 1",
    publisher: "Publisher 2",
  },
  {
    bookId: 3,
    name: "Test Book 3",
    author: "Author 2",
    publisher: "Publisher 1",
  },
];

export let bookList;

export const setBookList = (newBookList) => {
  bookList = newBookList;
};

export const testIssuedBookList = [
  {
    bookId: 2,
    name: "Test Book 2",
    author: "Author 1",
    publisher: "Publisher 2",
  },
  {
    bookId: 3,
    name: "Test Book 3",
    author: "Author 2",
    publisher: "Publisher 1",
  },
];

export let issuedBookList;

export const setIssuedBookList = (newIssuedBookList) => {
  issuedBookList = newIssuedBookList;
};