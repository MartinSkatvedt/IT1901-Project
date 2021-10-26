export type User = {
    username: String;
    calendar: Calendar;
}

export type Calendar = {
    events: Event[] | undefined;
}

export type Event = {
    header: string;
    description: string;
    date: string;
    time: string;
}