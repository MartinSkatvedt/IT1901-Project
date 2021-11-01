import React, { FC, useState, useEffect } from "react";
import { Grid, GridItem, Box, Heading, Button } from "@chakra-ui/react";
const Calendar: FC = () => {
	const [currentYear, setCurrentYear] = useState(2021);
	const [currentMonth, setCurrentMonth] = useState(10);

	useEffect(() => {
		const date = new Date(), y: number = date.getFullYear(), m: number = date.getMonth();
		setCurrentMonth(m);
		setCurrentYear(y);
	}, []);

	const month = new Date(currentYear, currentMonth, 1).toLocaleString("default", { month: "long" });
	const firstDay = new Date(currentYear, currentMonth, 1).getDay();
	const lastDay = new Date(currentYear, currentMonth + 1, 0).getDate();
	const gridItems: JSX.Element[] = [];

	let currentIndex = 0;
	let dateIndex = 1;
	for (let i = 0; i < 7; i++) {
		for (let j = 0; j < 5; j++) {
			if (currentIndex + 1 >= firstDay && dateIndex <= lastDay) {
				gridItems.push(<GridItem key={currentIndex} cold={i} row={j} h={100} w={150} bg="tomato"> {dateIndex + "."}</GridItem>);
				dateIndex++;
			}
			else {
				gridItems.push(<GridItem key={currentIndex} cold={i} row={j} h={100} w={150} bg="tomato"/>);
			}
			currentIndex++;
		}
	}

	const nextMonth = () => {
		if (currentMonth + 1 >= 12) {
			setCurrentMonth(0);
			setCurrentYear(currentYear + 1);
		}
		else setCurrentMonth(currentMonth + 1);
	};

	const prevMonth = () => {
		if (currentMonth - 1 < 0) {
			setCurrentMonth(11);
			setCurrentYear(currentYear - 1);
		}
		else setCurrentMonth(currentMonth - 1);
	};

	return (
		<Box>
			<Heading textAlign="center">{month} {currentYear}</Heading>
			<Button onClick={prevMonth}>{"<--"}</Button>
			<Button onClick={nextMonth}>{"-->"}</Button>
			<Grid templateColumns="repeat(7, 1fr)" 
				templateRows="repeat(5, 1fr)" 
				gap={2} 
				w="90%" 
				ml="auto" 
				mr ="auto">
				{gridItems}
			</Grid>
		</Box>

	);
};

export default Calendar;
