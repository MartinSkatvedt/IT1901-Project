export type User = {
    username: string;
    calendar: CalendarType;
}

export type CalendarType = {
    events: Event[] | undefined;
}

export type EventType = {
    header: string;
    description: string;
    date: string;
    time: string;
}