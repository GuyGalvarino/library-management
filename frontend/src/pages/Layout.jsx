import { Outlet, Link } from "react-router-dom";

const Layout = ({ signedInUser }) => {
  return (
    <div>
      <h1>Library Management System</h1>
      {signedInUser ? (
        <div>
          <span>{signedInUser.name}</span>
          <Link to="/profile">
            <button>Profile</button>
          </Link>
        </div>
      ) : null}
      <Outlet />
    </div>
  );
};

export default Layout;
