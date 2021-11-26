import React from "react";
import CalendarItem from "../../../src/components/CalendarItem";
import renderer from "react-test-renderer";
import { StateContext } from "../../state/state";
import { mockUser } from "../../setupTests";

test("CalendarItem renders correctly", () => {
	const state = {
		user: mockUser,
	};
	const dispatch = jest.fn();
	const date = new Date(2021, 10, 23);
	const tree = renderer
		.create(
			<StateContext.Provider value={{ state, dispatch }}>
				<CalendarItem col={0} row={0} date={date} />
			</StateContext.Provider>
		)
		.toJSON();
	expect(tree).toMatchSnapshot();
});
