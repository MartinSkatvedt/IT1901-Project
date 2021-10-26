import React, { FC, useContext } from "react";
import { StateContext } from "../state/state";
import { setUser } from "../state/actions";
import { getUser } from "../api/index";
import "./styles/login.css";

const Login: FC = () => {
  const { dispatch } = useContext(StateContext);

  const onLogin = async () => {
    const user = await getUser();
    if (user) dispatch(setUser(user));
  };

  return (
    <div className="inputContainer">
      <h1>Cool Calendar</h1>
      <input type="text" placeholder="username" />
      <button onClick={() => onLogin}>Login</button>
    </div>
  );
};

export default Login;
