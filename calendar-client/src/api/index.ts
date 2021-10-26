import axios from "axios";
import { User } from "../types/user";
export const DEFAULT_URL = "localhost:8000/api/";
export const USER_URL = DEFAULT_URL + "user";

export const getUser = async (): Promise<User | undefined> => {
  const response = await axios
    .get<User>(USER_URL)
    .catch(() => Promise.resolve(undefined));

  if (response && response.status === 200) {
    return response.data as User;
  }
  else return undefined;
};
