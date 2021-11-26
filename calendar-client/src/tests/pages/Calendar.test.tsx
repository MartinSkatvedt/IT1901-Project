import React from "react";
import App from "../../App";
import renderer from "react-test-renderer";
import { StateContext } from "../../state/state";
import { mockUser } from "../../setupTests";
import { Router } from "react-router";
import { createMemoryHistory } from "history";

test("Calendar renders correctly", () => {
	const state = {
		user: mockUser,
	};
	const dispatch = jest.fn();
	const history = createMemoryHistory();
	history.push("/calendar");

	const tree = renderer
		.create(
			<StateContext.Provider value={{ state, dispatch }}>
				<Router history={history}>
					<App />
				</Router>
			</StateContext.Provider>
		)
		.toJSON();
	expect(tree).toMatchSnapshot();
});
