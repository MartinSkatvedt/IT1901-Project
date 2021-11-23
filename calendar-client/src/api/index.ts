import axios from "axios";
import { User, EventType, CalendarType, RequestEventType } from "../types/user";
import { createCalendarResponseType } from "../types/api";

export const DEFAULT_URL = "http://www.localhost:8080/api";
export const USER_URI = (username: string): string =>
	`${DEFAULT_URL}/user/${username}`;
export const CALENDAR_URI = (username: string): string =>
	`${DEFAULT_URL}/calendar/${username}`;
export const CALENDAR_ID_URI = (username: string, id: number): string =>
	`${CALENDAR_URI(username)}/${id}`;

export const getUser = async (username: string): Promise<User | undefined> => {
	const response = await axios
		.get<User>(USER_URI(username))
		.catch(() => Promise.resolve(undefined));

	if (response && response.status === 200) {
		return response.data as User;
	} else return undefined;
};

export const getEvents = async (
	username: string
): Promise<CalendarType | undefined> => {
	const response = await axios
		.get<CalendarType>(CALENDAR_URI(username))
		.catch(() => Promise.resolve(undefined));

	if (response && response.status === 200) {
		return response.data as CalendarType;
	} else return undefined;
};

export const getEventByID = async (
	username: string,
	id: number
): Promise<EventType | undefined> => {
	const response = await axios
		.get<EventType>(CALENDAR_ID_URI(username, id))
		.catch(() => Promise.resolve(undefined));

	if (response && response.status === 200) {
		return response.data as EventType;
	} else return undefined;
};

export const createEvent = async (
	username: string,
	event: RequestEventType
): Promise<createCalendarResponseType | undefined> => {
	const response = await axios
		.post<createCalendarResponseType>(CALENDAR_URI(username), event)
		.catch(() => Promise.resolve(undefined));

	if (response && response.status === 201) {
		return response.data as createCalendarResponseType;
	} else return undefined;
};

export const updateEvent = async (
	username: string,
	id: number,
	event: RequestEventType
): Promise<boolean | undefined> => {
	const response = await axios
		.put<createCalendarResponseType>(CALENDAR_ID_URI(username, id), event)
		.catch(() => Promise.resolve(undefined));

	if (response && response.status === 200) {
		return true;
	} else return false;
};

export const deleteEvent = async (
	username: string,
	id: number
): Promise<boolean | undefined> => {
	const response = await axios
		.delete<createCalendarResponseType>(CALENDAR_ID_URI(username, id))
		.catch(() => Promise.resolve(undefined));

	if (response && response.status === 200) {
		return true;
	} else return false;
};
