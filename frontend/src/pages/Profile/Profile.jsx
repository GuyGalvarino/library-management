import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

const Profile = ({ signedInUser, setSignedInUser }) => {
  const navigate = useNavigate();
  const logout = () => {
    window.localStorage.removeItem("signedInUser");
    setSignedInUser(null);
    navigate("/");
  };
  return (
    <div>
      <Link to="/">Back</Link>
      <div>Name: {signedInUser.name}</div>
      <div>Email: {signedInUser.email}</div>
      <div>
        <button onClick={logout}>Log Out</button>
      </div>
    </div>
  );
};

export default Profile;

