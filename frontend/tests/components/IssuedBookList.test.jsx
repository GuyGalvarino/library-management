import {
  issuedBookList,
  testIssuedBookList,
  setIssuedBookList,
  signedInUser,
  setSignedInUser,
  testSignedInUser,
} from "../testHelpers";
import IssuedBookList from "../../src/components/IssuedBookList";
import { render, screen } from "@testing-library/react";
import { beforeEach, expect } from "vitest";

let containerIssuedBookList;

beforeEach(() => {
  setSignedInUser(testSignedInUser);
  setIssuedBookList(testIssuedBookList);
  const { container } = render(
    <IssuedBookList
      signedInUser={signedInUser}
      issuedBookList={issuedBookList}
      setIssuedBookList={setIssuedBookList}
    />
  );
  containerIssuedBookList = container;
});

test("list is present", () => {
  expect(containerIssuedBookList.firstChild).toHaveClass("list");
});

test("heading is present", () => {
  screen.getByRole("heading", { name: /issued books/i });
});
