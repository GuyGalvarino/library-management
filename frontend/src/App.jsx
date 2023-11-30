import Home from "./pages/Home/Home";
import Layout from "./pages/Layout";
import Signin from "./pages/Signin/Signin";
import Otp from "./pages/Signin/Otp";
import Login from "./pages/Login/Login";
import { useState, useEffect } from "react";
import { BrowserRouter, Routes, Route, Outlet } from "react-router-dom";
import Profile from "./pages/Profile/Profile";
import Admin from "./pages/Admin/Admin";
import AdminOtp from "./pages/Admin/Login/AdminOtp";
import AdminHome from "./pages/Admin/Home/AdminHome";
import AdminLogin from "./pages/Admin/Login/AdminLogin";
import AddBookAdmin from "./pages/Admin/AddBook/AdminAddBook";
import Issue from "./pages/Issue/Issue";

const App = () => {
  const [signedInUser, setSignedInUser] = useState(null);
  const [bookList, setBookList] = useState(null);
  const [issuedBookList, setIssuedBookList] = useState(null);
  const [signedInAdmin, setSignedInAdmin] = useState(null);
  const [bookListAdmin, setBookListAdmin] = useState(null);

  useEffect(() => {
    if (!signedInUser) {
      const res = window.localStorage.getItem("signedInUser");
      if (res) {
        setSignedInUser(JSON.parse(res));
      }
    }
    if (!signedInAdmin) {
      const res = window.localStorage.getItem("signedInAdmin");
      if (res) {
        setSignedInAdmin(JSON.parse(res));
      }
    }
  }, []);

  useEffect(() => {
    if (!signedInAdmin) {
      const res = window.localStorage.getItem("signedInAdmin");
      if (res) {
        setSignedInAdmin(JSON.parse(res));
      }
    }
  }, []);

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout signedInUser={signedInUser} />}>
          <Route
            index
            element={
              <Home
                signedInUser={signedInUser}
                setSignedInUser={setSignedInUser}
                issuedBookList={issuedBookList}
                setIssuedBookList={setIssuedBookList}
              />
            }
          />
          <Route
            path="login"
            element={
              <Login
                signedInUser={signedInUser}
                setSignedInUser={setSignedInUser}
              />
            }
          />
          <Route path="signin">
            <Route index element={<Signin signedInUser={signedInUser} />} />
            <Route
              path="otp"
              element={
                <Otp
                  signedInUser={signedInUser}
                  setSignedInUser={setSignedInUser}
                />
              }
            />
          </Route>
          <Route
            path="profile"
            element={
              <Profile
                signedInUser={signedInUser}
                setSignedInUser={setSignedInUser}
              />
            }
          />
          <Route
            path="issue"
            element={
              <Issue
                signedInUser={signedInUser}
                bookList={bookList}
                setBookList={setBookList}
                issuedBookList={issuedBookList}
                setIssuedBookList={setIssuedBookList}
              />
            }
          />
          <Route
            path="admin"
            element={
              <Admin
                signedInAdmin={signedInAdmin}
                setSignedInAdmin={setSignedInAdmin}
              />
            }
          >
            <Route
              index
              element={
                <AdminHome
                  signedInAdmin={signedInAdmin}
                  setSignedInAdmin={setSignedInAdmin}
                  bookListAdmin={bookListAdmin}
                  setBookListAdmin={setBookListAdmin}
                />
              }
            />
            <Route
              path="login"
              element={
                <AdminLogin
                  signedInAdmin={signedInAdmin}
                  setSignedInAdmin={setSignedInAdmin}
                />
              }
            />
            <Route
              path="otp"
              element={
                <AdminOtp
                  signedInAdmin={signedInAdmin}
                  setSignedInAdmin={setSignedInAdmin}
                />
              }
            />
            <Route
              path="add-book"
              element={
                <AddBookAdmin
                  signedInAdmin={signedInAdmin}
                  bookListAdmin={bookListAdmin}
                  setBookListAdmin={setBookListAdmin}
                />
              }
            />
          </Route>
          {/* <Route path="*" element={<NoPage />} /> */}
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default App;
