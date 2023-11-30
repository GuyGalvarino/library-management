import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import IssuedBookList from "../../components/IssuedBookList";
const Home = ({
  signedInUser,
  setSignedInUser,
  issuedBookList,
  setIssuedBookList,
}) => {
  const navigate = useNavigate();
  const issueBookHandler = () => {
    navigate("/issue");
  };
  return (
    <div>
      <h2>Home</h2>
      {signedInUser ? (
        <div>
          <button onClick={issueBookHandler}>Issue Book</button>
          <IssuedBookList
            signedInUser={signedInUser}
            issuedBookList={issuedBookList}
            setIssuedBookList={setIssuedBookList}
          />
        </div>
      ) : (
        <div>
          <button>
            <Link to="/signin">Register</Link>
          </button>
          <button>
            <Link to="/login">Login</Link>
          </button>
        </div>
      )}
    </div>
  );
};

export default Home;
