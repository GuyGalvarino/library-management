import { Link, useNavigate } from "react-router-dom";
import BookListAdmin from "../../../components/BookListAdmin";
const AdminHome = ({
  signedInAdmin,
  setSignedInAdmin,
  bookListAdmin,
  setBookListAdmin,
}) => {
  const navigate = useNavigate();

  const loginHandler = () => {
    navigate("/admin/login");
  };
  const logoutHandler = () => {
    window.localStorage.removeItem("signedInAdmin");
    setSignedInAdmin(null);
  };

  return (
    <div>
      Admin Home
      {signedInAdmin ? (
        <div>
          {signedInAdmin.name}
          <div>
            <button onClick={logoutHandler}>Log Out</button>
          </div>
          <Link to="/admin/add-book">
            <button>Add Book</button>
          </Link>
          <BookListAdmin
            signedInAdmin={signedInAdmin}
            bookListAdmin={bookListAdmin}
            setBookListAdmin={setBookListAdmin}
          />
        </div>
      ) : (
        <div>
          <button onClick={loginHandler}>Log In</button>
        </div>
      )}
    </div>
  );
};

export default AdminHome;
