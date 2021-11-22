export type User = {
	username: string;
	calendar: CalendarType;
};

export type CalendarType = {
	events: EventType[] | undefined;
};

export type EventType = {
	header: string;
	description: string;
	date: string;
	timeString: string;
	id: number;
	timeHour: number;
	timeMinute: number;
};

export type RequestEventType = {
	header: string;
	description: string;
	date: string;
	time: string;
};
