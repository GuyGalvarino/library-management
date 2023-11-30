import { render, screen } from "@testing-library/react";
import Home from "../../../src/pages/Home/Home";
import { beforeAll, beforeEach, expect, test } from "vitest";
import {
  signedInUser,
  setSignedInUser,
  testSignedInUser,
} from "../../testHelpers";
import { MemoryRouter } from "react-router-dom";

beforeEach(() => {
  setSignedInUser(testSignedInUser);
  window.localStorage.setItem("signedInUser", JSON.stringify(testSignedInUser));
  try {
    render(
      <MemoryRouter>
        <Home signedInUser={null} setSignedInUser={setSignedInUser} />
      </MemoryRouter>
    );
  }
  catch(e) {}
});

test("heading present", () => {
  const heading = screen.getByRole("heading");
  expect(heading).toHaveTextContent(/home/i);
});
