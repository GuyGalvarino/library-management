import { useEffect, useState } from "react";
import { verifySignIn } from "../../services/signin";
import { useNavigate } from "react-router-dom";

const Otp = ({ signedInUser, setSignedInUser }) => {
  const [otp, setOtp] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    if (signedInUser) {
      navigate("/");
    }
  }, [signedInUser]);

  const updateOtp = (e) => {
    let allDigits = true;
    for (let i = 0; i < e.target.value.length; ++i) {
      if (e.target.value[i] < "0" || e.target.value[i] > "9") {
        allDigits = false;
        break;
      }
    }
    if (e.target.value.length > 6 || !allDigits) {
      setOtp(otp);
    } else {
      setOtp(e.target.value);
    }
  };
  const submitForm = async (e) => {
    e.preventDefault();
    try {
      const res = await verifySignIn(otp);
      window.localStorage.setItem("signedInUser", JSON.stringify(res));
      setSignedInUser(res);
      navigate("/");
    } catch (e) {
      console.error(e);
    }
  };
  return (
    <div>
      <label htmlFor="otp">
        <h1>OTP</h1>
      </label>
      <form onSubmit={submitForm}>
        <input type="text" value={otp} id="otp" onChange={updateOtp} />
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default Otp;
