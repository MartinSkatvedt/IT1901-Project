import React, {FC} from "react";
import { GridItem } from "@chakra-ui/react";
import {EventType} from "../types/user";

type CalendarItemProps = {
    date?: number;
    col: number;
    row: number;
    events?: EventType[];
    isCurrent?: boolean;
}
const CalendarItem: FC<CalendarItemProps> = ({date, col, row, isCurrent}: CalendarItemProps) => {
	return <GridItem border={isCurrent ?"3px solid black" : "none"} 
		bg="tomato" 
		h={100} 
		w={150} 
		col={col} 
		row={row}>
		{date ? date + "." : null}
	</GridItem>;
};

export default CalendarItem;