import { EventType, User } from "../types/user";

export enum ActionTypes {
	SET_USER = "SET_USER",
	LOGOUT_USER = "LOGOUT_USER",
	SET_EVENT = "SET_EVENT",
	CLEAR_EVENT = "CLEAR_EVENT",
}

type SetUserAction = { type: ActionTypes.SET_USER; payload: User };
type LogoutUserAction = { type: ActionTypes.LOGOUT_USER };
type SetEventAction = { type: ActionTypes.SET_EVENT; payload: EventType };
type ClearEventAction = { type: ActionTypes.CLEAR_EVENT };

export type Action =
	| SetUserAction
	| LogoutUserAction
	| SetEventAction
	| ClearEventAction;

export const setUser = (user: User): SetUserAction => ({
	type: ActionTypes.SET_USER,
	payload: user,
});

export const logoutUser = (): LogoutUserAction => ({
	type: ActionTypes.LOGOUT_USER,
});

export const setEvent = (event: EventType): SetEventAction => ({
	type: ActionTypes.SET_EVENT,
	payload: event,
});

export const clearEvent = (): ClearEventAction => ({
	type: ActionTypes.CLEAR_EVENT,
});
