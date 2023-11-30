import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import Issue from "../../../src/pages/Issue/Issue";
import {
  signedInUser,
  setSignedInUser,
  bookList,
  setBookList,
  issuedBookList,
  setIssuedBookList,
  testSignedInUser,
  testBookList,
  testIssuedBookList,
} from "../../testHelpers";

beforeEach(() => {
  setSignedInUser(testSignedInUser);
  setBookList(testBookList);
  setIssuedBookList(testIssuedBookList);
  render(
    <MemoryRouter>
      <Issue
        signedInUser={signedInUser}
        bookList={bookList}
        setBookList={setBookList}
        issuedBookList={issuedBookList}
        setIssuedBookList={setIssuedBookList}
      />
    </MemoryRouter>
  );
});

test("issue book heading is present", () => {
  screen.getByRole("heading", { name: /issue book/i });
});

test("back links to /", () => {
  const backButton = screen.getByRole("link", { name: /back/i });
  expect(backButton).toHaveProperty("href", "http://localhost:3000/");
});
