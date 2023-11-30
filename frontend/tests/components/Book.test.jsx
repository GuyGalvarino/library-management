import { render, screen, fireEvent, logRoles } from "@testing-library/react";
import { testBook } from "../testHelpers";
import { beforeEach, expect } from "vitest";
import Book from "../../src/components/Book";

beforeEach(() => {
  render(
    <Book
      name={testBook.name}
      author={testBook.author}
      publisher={testBook.publisher}
    />
  );
});

test("name is present", () => {
  const name = screen.getByRole("heading", { name: testBook.name });
  expect(name).toHaveClass("list-header");
});

test("author is present", () => {
  const author = screen.getByText(/author/i);
  expect(author).toHaveTextContent(testBook.author);
});

test("publisher is present", () => {
  const publisher = screen.getByText(/publisher/i);
  expect(publisher).toHaveTextContent(testBook.publisher);
});
