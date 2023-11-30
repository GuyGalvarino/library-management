import axios from "axios";

let userEmail;

export const requestOtp = async (name, email, password) => {
  try {
    const res = await axios.post("http://localhost:8080/users/signin", {
      name,
      email,
      password,
    });
    userEmail = email;
    return true;
  } catch (e) {
    console.error(e);
    if (e.response && e.response.status === 403) {
      throw new Error("User already exists");
    }
    throw new Error("Couldn't send OTP");
  }
};

export const verifySignIn = async (otp) => {
  try {
    const res = await axios.post(`http://localhost:8080/users/verify-otp`, {
      email: userEmail,
      otp: otp,
    });
    return res.data;
  } catch (e) {
    console.error(e);
    throw new Error("Couldn't verify");
  }
};
