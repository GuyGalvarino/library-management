import { verifyAdminLogIn } from "../../../services/admin";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

const AdminOtp = ({ signedInAdmin, setSignedInAdmin }) => {
  const [otp, setOtp] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    if (signedInAdmin) {
      navigate("/admin");
    }
  }, [signedInAdmin]);

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
      const res = await verifyAdminLogIn(otp);
      window.localStorage.setItem("signedInAdmin", JSON.stringify(res));
      setSignedInAdmin(res);
      navigate("/admin");
    } catch (e) {
      console.error(e);
    }
  };
  return (
    <div>
      <form onSubmit={submitForm}>
        <input type="text" value={otp} onChange={updateOtp} />
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default AdminOtp;
