import React from "react";
import EventItem from "../../../src/components/EventItem";
import renderer from "react-test-renderer";
import { StateContext } from "../../state/state";
import { mockUser, MockEvent } from "../../setupTests";

test("EventItem renders correctly", () => {
	const state = {
		user: mockUser,
	};
	const dispatch = jest.fn();

	const tree = renderer
		.create(
			<StateContext.Provider value={{ state, dispatch }}>
				<EventItem event={MockEvent} />
			</StateContext.Provider>
		)
		.toJSON();
	expect(tree).toMatchSnapshot();
});
