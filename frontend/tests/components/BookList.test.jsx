import { beforeEach, expect } from "vitest";
import {
  signedInUser,
  bookList,
  setBookList,
  issuedBookList,
  setIssuedBookList,
  testBookList,
  testIssuedBookList,
} from "../testHelpers";
import { render } from "@testing-library/react";
import BookList from "../../src/components/BookList";

test("list is present", () => {
  setBookList(testBookList);
  setIssuedBookList(testIssuedBookList);
  const { container } = render(
    <BookList
      signedInUser={signedInUser}
      bookList={bookList}
      setBookList={setBookList}
      issuedBookList={issuedBookList}
      setIssuedBookList={setIssuedBookList}
    />
  );
  expect(container.firstChild).toHaveClass("list");
});
