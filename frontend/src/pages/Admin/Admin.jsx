import { useEffect, useState } from "react";
import { Outlet, Router, useNavigate } from "react-router-dom";
import { Route, Routes } from "react-router-dom";
import AdminOtp from "./Login/AdminOtp";
const Admin = ({ signedInAdmin, setSignedInAdmin }) => {

  return (
    <div>
      <h2>Admin</h2>
      <Outlet />
    </div>
  );
};

export default Admin;
