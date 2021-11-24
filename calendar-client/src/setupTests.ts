// jest-dom adds custom jest matchers for asserting on DOM nodes.
// allows you to do things like:
// expect(element).toHaveTextContent(/react/i)
// learn more: https://github.com/testing-library/jest-dom
import "@testing-library/jest-dom";
import { User } from "./types/user";

export const mockUser: User = {
	username: "testUser",
	calendar: {
		events: [
			{
				header: "testEvent1",
				description: "testDescription1",
				date: "2021-11-23",
				timeString: "12:30",
				timeHour: 12,
				timeMinute: 30,
				id: 1,
			},
			{
				header: "testEvent2",
				description: "testDescription1",
				date: "2021-11-11",
				timeString: "15:00",
				timeHour: 15,
				timeMinute: 0,
				id: 2,
			},
		],
	},
};
