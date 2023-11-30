import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import { beforeEach, expect } from "vitest";
import Signin from "../../../src/pages/Signin/Signin";
import { signedInUser } from "../../testHelpers";

beforeEach(() => {
  render(
    <MemoryRouter>
      <Signin signedInUser={signedInUser} />
    </MemoryRouter>
  );
});

test("back links to /", () => {
  const backButton = screen.getByRole("link", { name: /back/i });
  expect(backButton).toHaveProperty("href", "http://localhost:3000/");
});
