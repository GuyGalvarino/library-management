import axios from "axios";

export const getUser = async (email, token) => {
  try {
    const res = await axios.get(`http://localhost:8080/users/${email}`, {
      headers: {
        Authorization: token,
      },
    });
    return res.data;
  } catch (e) {
    console.error(e);
    throw new Error("Couldn't get user")
  }
};

export const getUsers = async () => {
  return (await axios.get("http://localhost:8080/users")).data;
};
