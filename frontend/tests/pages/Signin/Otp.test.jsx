import { render, screen } from "@testing-library/react";
import { beforeEach } from "vitest";
import { signedInUser, setSignedInUser } from "../../testHelpers";
import { MemoryRouter } from "react-router-dom";
import Otp from "../../../src/pages/Signin/Otp";

beforeEach(() => {
  render(
    <MemoryRouter>
      <Otp signedInUser={signedInUser} setSignedInUser={setSignedInUser} />
    </MemoryRouter>
  );
});

test("otp heading is present", () => {
  screen.getByRole("heading", { name: /otp/i });
});

test("text field is present", () => {
  screen.getByRole("textbox", { name: /otp/i });
});

test("submit button is present", () => {
  screen.getByRole("button", { name: /submit/i });
});
