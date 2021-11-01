import React, { FC, useState, useEffect } from "react";
import { Grid, GridItem, Box, Heading, Button, HStack, Center, Divider  } from "@chakra-ui/react";
import {ArrowForwardIcon, ArrowBackIcon} from "@chakra-ui/icons";
import { BrowserRouter as Router, Switch, Route, Link, useRouteMatch } from "react-router-dom";
import Event from "./Event";
const Calendar: FC = () => {
	const match = useRouteMatch();
	const [currentYear, setCurrentYear] = useState(2021);
	const [currentMonth, setCurrentMonth] = useState(10);

	useEffect(() => {
		const date = new Date();
		setCurrentMonth(date.getMonth());
		setCurrentYear(date.getFullYear());
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
			<Center>	
				<HStack spacing={500}> 
					<Button leftIcon={<ArrowBackIcon/>} onClick={prevMonth}>Forige måned</Button>
					<Button rightIcon={<ArrowForwardIcon/>} onClick={nextMonth}>Neste måned</Button>
				</HStack>
			</Center>
			<Divider w="90%" m={5} ml="auto" mr="auto"/>
			
			<Grid templateColumns="repeat(7, 1fr)" 
				templateRows="repeat(5, 1fr)" 
				gap={2} 
				w="90%" 
				ml="auto" 
				mr ="auto">
				{gridItems}
			</Grid>
			<Divider w="90%" m={5} ml="auto" mr="auto"/>
			<Center>
				<Button>
					<Link to="/event">Ny hendelse</Link>
				</Button>
			</Center>

			<Switch>
				<Route path="/event">
					<Event />
				</Route>
			</Switch>
		</Box>
	);
};

export default Calendar;
