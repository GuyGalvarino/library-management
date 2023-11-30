import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import Login from "../../../src/pages/Login/Login";
import {
  signedInUser,
  setSignedInUser,
  testSignedInUser,
} from "../../testHelpers";
import { test } from "vitest";
beforeEach(() => {
  setSignedInUser(testSignedInUser);
  render(
    <MemoryRouter>
      <Login signedInUser={signedInUser} setSignedInUser={setSignedInUser} />
    </MemoryRouter>
  );
});

test("back links to /", () => {
  const backButton = screen.getByRole("link", { name: /back/i });
  expect(backButton).toHaveProperty("href", "http://localhost:3000/");
});

test("email field is present", () => {
  screen.getByRole("textbox", { name: /email/i });
});

test("password field is present", () => {
  screen.getByLabelText(/password/i);
});

test("submit button is present", () => {
  screen.getByRole("button", { name: /submit/i });
});
