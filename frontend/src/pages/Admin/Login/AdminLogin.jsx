import { requestAdminOtp } from "../../../services/admin";
import { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";

const AdminLogin = ({ signedInAdmin }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    if (signedInAdmin) {
      navigate("/admin");
    }
  }, [signedInAdmin]);

  const emailHandler = (e) => {
    setEmail(e.target.value);
  };
  const passwordHandler = (e) => {
    setPassword(e.target.value);
  };
  const submitForm = async (e) => {
    e.preventDefault();
    try {
      const res = await requestAdminOtp(email, password);
      navigate("/admin/otp");
    } catch (e) {
      console.error("could not send otp");
    }
  };
  return (
    <div>
      <Link to="/admin">Back</Link>
      <form onSubmit={submitForm}>
        <div>
          <label htmlFor="email">Email: </label>
          <input
            value={email}
            onChange={emailHandler}
            id="email"
            type="email"
          />
        </div>
        <div>
          <label htmlFor="password">Password: </label>
          <input
            value={password}
            onChange={passwordHandler}
            id="password"
            type="password"
          />
        </div>
        <button type="submit">Log In</button>
      </form>
    </div>
  );
};

export default AdminLogin;
