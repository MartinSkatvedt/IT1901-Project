import React, { FC, useState, useEffect, useContext } from "react";
import { StateContext } from "../state/state";
import { Grid, Box, Heading, Button, HStack, Center, Divider, SimpleGrid } from "@chakra-ui/react";
import {ArrowForwardIcon, ArrowBackIcon} from "@chakra-ui/icons";
import { BrowserRouter as Switch, Route, Link, Redirect } from "react-router-dom";
import Event from "./Event";
import CalendarItem from "../components/CalendarItem";

const Calendar: FC = () => {
	const [currentYear, setCurrentYear] = useState(2021);
	const [currentMonth, setCurrentMonth] = useState(10);
	const { state } = useContext(StateContext);
	const {user} = state;
	if (!user) return <Redirect to={"/"} />;

	console.log(user);
	useEffect(() => {
		const date = new Date();
		setCurrentMonth(date.getMonth());
		setCurrentYear(date.getFullYear());
	}, []);

	const thisDate = new Date();
	const currentDate = new Date(currentYear, currentMonth, 1);
	const firstDay = new Date(currentYear, currentMonth, 1).getDay();
	const lastDay = new Date(currentYear, currentMonth + 1, 0).getDate();
	const month = currentDate.toLocaleString("default", { month: "long" });
	const gridItems: JSX.Element[] = [];

	let currentIndex = 0;
	let dateIndex = 1;
	for (let i = 0; i < 7; i++) {
		for (let j = 0; j < 5; j++) {
			const isToday = dateIndex == thisDate.getDay() && thisDate.getMonth() == currentMonth? true : false;
			if (currentIndex + 1 >= firstDay && dateIndex <= lastDay) {
				gridItems.push(<CalendarItem 
					isCurrent={isToday} 
					key={currentIndex} 
					col={i} 
					row={j} 
					date={dateIndex}
				/>);
				dateIndex++;
			}
			else {
				gridItems.push(<CalendarItem key={currentIndex} isCurrent={isToday} col={i} row={j}/>);
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

	const goToToday = () => {
		const date = new Date();
		setCurrentMonth(date.getMonth());
		setCurrentYear(date.getFullYear());
	};

	return (
		<Box>
			<Heading textAlign="center">{month} {currentYear}</Heading>
			<Center>	
				<HStack spacing={200}> 
					<Button leftIcon={<ArrowBackIcon/>} onClick={prevMonth}>Forige måned</Button>
					<Button onClick={goToToday}>I dag</Button>
					<Button rightIcon={<ArrowForwardIcon/>} onClick={nextMonth}>Neste måned</Button>
				</HStack>
			</Center>
			<Divider w="90%" m={5} ml="auto" mr="auto"/>
			<SimpleGrid gap={2} 
				w="90%" 
				ml="auto" 
				mr ="auto"
				columns={7}>
				<Box>Mandag</Box>
				<Box>Tirsdag</Box>
				<Box>Onsdag</Box>
				<Box>Torsdag</Box>
				<Box>Fredag</Box>
				<Box>Lørdag</Box>
				<Box>Søndag</Box>
			</SimpleGrid>
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
