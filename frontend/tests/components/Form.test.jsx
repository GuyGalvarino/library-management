import { render, screen, fireEvent, logRoles } from "@testing-library/react";
import Form from "../../src/components/Form";
import { MemoryRouter } from "react-router-dom";
import { beforeEach } from "vitest";

beforeEach(() => {
  render(
    <MemoryRouter>
      <Form />
    </MemoryRouter>
  );
});

test("name field is present", () => {
  screen.getByRole("textbox", { name: /name/i });
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
