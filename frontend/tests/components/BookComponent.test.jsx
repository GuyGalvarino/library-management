import { render, screen } from "@testing-library/react";
import BookComponent from "../../src/components/BookComponent";
import { testBook } from "../testHelpers";

let rerenderBookComponent;

beforeEach(() => {
  const { rerender } = render(
    <BookComponent book={testBook} handler={() => {}} isIssued={true} />
  );
  rerenderBookComponent = rerender;
});

test("proper button shown", () => {
  screen.getByRole("button", { name: /remove book/i });
  expect(screen.queryByRole("button", { name: /add book/i })).toBeNull();

  rerenderBookComponent(
    <BookComponent book={testBook} handler={() => {}} isIssued={false} />
  );
  screen.getByRole("button", { name: /add book/i });
  expect(screen.queryByRole("button", { name: /remove book/i })).toBeNull();
});
