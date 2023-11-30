import axios from "axios";

export const loginRequest = async (email, password) => {
  try {
    const res = await axios.post("http://localhost:8080/users/login", {
      email,
      password,
    });
    return res.data;
  }
  catch(e) {
    console.error(e)
    throw new Error("Couldn't login")
  }
};
