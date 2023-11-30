import axios from "axios";

let adminEmail;

export const requestAdminOtp = async (email, password) => {
  try {
    const { data: res } = await axios.post(`http://localhost:8080/admin`, {
      email,
      password,
    });
    adminEmail = email;
  } catch (e) {
    console.error(e);
    if (e.response && e.response.status === 404) {
      throw new Error("admin does not exist");
    }
    if (e.response && e.response.status === 403) {
      throw new Error("incorrect password");
    }
    throw new Error("could not send otp");
  }
};

export const verifyAdminLogIn = async (otp) => {
  try {
    const { data: res } = await axios.post(
      `http://localhost:8080/admin/verify-otp`,
      {
        otp,
        email: adminEmail,
      }
    );
    return res;
  } catch (e) {
    console.error(e);
    throw new Error("could not verify otp");
  }
};
