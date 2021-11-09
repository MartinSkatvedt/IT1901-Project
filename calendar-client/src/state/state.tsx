import React, { createContext, Dispatch, ReactNode, useReducer } from "react";
import { User } from "../types/user";
import { Action, ActionTypes } from "./actions";

type State = {
  user?: User;
};

const initialState: State = {
	user: undefined,
};

const StateReducer = (state: State, action: Action): State => {
	switch (action.type) {
	case ActionTypes.SET_USER:
		return { ...state, user: action.payload };
	case ActionTypes.LOGOUT_USER:
		return {
			...state,
			user: undefined,
		};
	default:
		return { ...state };
	}
};

type StateContextProps = {
  state: State;
  dispatch: Dispatch<Action>;
};

const initialContext: StateContextProps = {
	state: initialState,
	dispatch: () => null,
};

export const StateContext = createContext<StateContextProps>(initialContext);

type StateProviderProps = {
  children: ReactNode;
};

export const StateProvider = (props: StateProviderProps): JSX.Element => {
	const [state, dispatch] = useReducer(StateReducer, initialState);
	const { children } = props;

	return (
		<StateContext.Provider value={{ state, dispatch }}>
			{children}
		</StateContext.Provider>
	);
};