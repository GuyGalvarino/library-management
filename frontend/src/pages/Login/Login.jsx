import { useEffect, useState } from "react";
import { loginRequest } from "../../services/login";
import { useNavigate, Link } from "react-router-dom";

const Login = ({ signedInUser, setSignedInUser }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    if (signedInUser) {
      navigate("/");
    }
  }, [signedInUser]);

  const submitForm = async (e) => {
    e.preventDefault();
    console.log("Submitting form...");
    try {
      const res = await loginRequest(email, password);
      window.localStorage.setItem("signedInUser", JSON.stringify(res));
      setSignedInUser(res);
      navigate("/");
    } catch (e) {
      console.error(e);
    }
  };

  return (
    <div>
      <Link to="/">Back</Link>
      <form onSubmit={submitForm}>
        <div>
          <label htmlFor="email">Email</label>
          <input
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          ></input>
        </div>
        <div>
          <label htmlFor="password">Password</label>
          <input
            id="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          ></input>
        </div>
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default Login;
