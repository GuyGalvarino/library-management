import { logRoles, render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import Layout from "../../src/pages/Layout";
import {
  signedInUser,
  testSignedInUser,
  setSignedInUser,
} from "../testHelpers";
import { beforeEach, expect, test } from "vitest";

let rerenderLayout;

beforeEach(() => {
  setSignedInUser(testSignedInUser);
  const { rerender } = render(
    <MemoryRouter>
      <Layout signedInUser={signedInUser} />
    </MemoryRouter>
  );
  rerenderLayout = rerender;
});

test("heading present", () => {
  const heading = screen.getByRole("heading");
  expect(heading).toHaveTextContent(/library management system/i);
});

test("profile button is shown properly", () => {
  screen.getByRole("button", { name: /profile/i });
  setSignedInUser(null);
  rerenderLayout(
    <MemoryRouter>
      <Layout signedInUser={signedInUser} />
    </MemoryRouter>
  );
  expect(screen.queryByRole("button", { name: /profile/i })).toBeNull();
});
