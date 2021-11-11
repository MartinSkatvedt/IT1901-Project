import React, {FC, useContext, useEffect, useState} from "react";
import { Box, GridItem } from "@chakra-ui/react";
import { StateContext } from "../state/state";

type CalendarItemProps = {
    col: number;
    row: number;
    isCurrent?: boolean;
	date?: Date;
}
const CalendarItem: FC<CalendarItemProps> = ({col, row, isCurrent, date}: CalendarItemProps) => {
	const {state} = useContext(StateContext);
	const {user} = state;

	let eventItems: (JSX.Element | undefined)[] = [];

	if (user && user.calendar.events && date != undefined) {
		const events = user.calendar.events;
		eventItems = Object.keys(events).map((key: string) => {
			const currentEvent = events[Number(key)];
			const currentDate = new Date(currentEvent.date);

			if (currentDate.getDate() == date.getDate() && currentDate.getMonth() == date.getMonth()) {
				return <Box key={key}>{currentEvent.timeString} {currentEvent.header}</Box>;
			}
		});
	}

	// &&  &&  currentDate.getFullYear() == date.getFullYear()
	return <GridItem border={isCurrent ?"3px solid black" : "none"} 
		bg="tomato" 
		h={100} 
		w={150} 
		col={col} 
		row={row}>
		{date ? date.getDate() + "." : null}
		{eventItems}
	</GridItem>;
};

export default CalendarItem;