import axios from "axios";
import { User, EventType, CalendarType } from "../types/user";
import {createCalendarResponseType} from "../types/api";

export const DEFAULT_URL = "localhost:8080/api/";
export const USER_URI = (username: string) => `${DEFAULT_URL}/user/${username}`;
export const CALENDAR_URI = (username: string) => `${DEFAULT_URL}/calendar/${username}`;
export const CALENDAR_ID_URI = (username: string, id: number) => `${CALENDAR_URI(username)}/${id}`;


export const getUser = async (username: string): Promise<User | undefined> => {
	const response = await axios
		.get<User>(USER_URI(username))
		.catch(() => Promise.resolve(undefined));

	if (response && response.status === 200) {
		return response.data as User;
	}
	else return undefined;
};

export const getEvents = async (username: string): Promise<CalendarType | undefined> => {
	const response = await axios
		.get<CalendarType>(CALENDAR_URI(username))
		.catch(() => Promise.resolve(undefined));

	if (response && response.status === 200) {
		return response.data as CalendarType;
	} else return undefined;
};

export const getEventByID = async (username: string, id: number): Promise<EventType | undefined> => {
	const response = await axios
		.get<EventType>(CALENDAR_ID_URI(username, id))
		.catch(() => Promise.resolve(undefined));

	if (response && response.status === 200) {
		return response.data as EventType;
	} else return undefined;
};

export const createEvent = async (username: string, event: EventType): Promise<createCalendarResponseType | undefined> => {
	const response = await axios
		.post<createCalendarResponseType>(CALENDAR_URI(username), event)
		.catch(() => Promise.resolve(undefined));

	if (response && response.status === 200) {
		return response.data as createCalendarResponseType;
	} else return undefined;
};

export const updateEvent = async (username: string, id: number): Promise<createCalendarResponseType | undefined> => {
	const response = await axios
		.put<createCalendarResponseType>(CALENDAR_ID_URI(username, id))
		.catch(() => Promise.resolve(undefined));

	if (response && response.status === 200) {
		return response.data as createCalendarResponseType;
	} else return undefined;
};


export const deleteEvent = async (username: string, id: number): Promise<createCalendarResponseType | undefined> => {
	const response = await axios
		.delete<createCalendarResponseType>(CALENDAR_ID_URI(username, id))
		.catch(() => Promise.resolve(undefined));

	if (response && response.status === 200) {
		return response.data as createCalendarResponseType;
	} else return undefined;
};