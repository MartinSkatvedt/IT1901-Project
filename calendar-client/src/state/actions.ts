import { User } from "../types/user";

export enum ActionTypes {
  SET_USER = "SET_USER",
  LOGOUT_USER = "LOGOUT_USER",
}

type SetUserAction = { type: ActionTypes.SET_USER; payload: User };
type LogoutUserAction = { type: ActionTypes.LOGOUT_USER };


export type Action =
  | SetUserAction
  | LogoutUserAction

export const setUser = (user: User): SetUserAction => ({
	type: ActionTypes.SET_USER,
	payload: user,
});

export const logoutUser = (): LogoutUserAction => ({
	type: ActionTypes.LOGOUT_USER,
});

