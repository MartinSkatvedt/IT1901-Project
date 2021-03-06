import React, { FC, useState, useEffect, useContext } from "react";
import { StateContext } from "../state/state";
import {
	Grid,
	Box,
	Heading,
	Button,
	HStack,
	Center,
	Divider,
	SimpleGrid,
} from "@chakra-ui/react";
import { ArrowForwardIcon, ArrowBackIcon } from "@chakra-ui/icons";
import { useHistory, Redirect } from "react-router-dom";
import CalendarItem from "../components/CalendarItem";
import { clearEvent } from "../state/actions";

const Calendar: FC = () => {
	const [currentYear, setCurrentYear] = useState(2021);
	const [currentMonth, setCurrentMonth] = useState(10);
	const [gridItems, setGridItems] = useState<(JSX.Element | undefined)[]>();
	const history = useHistory();
	const { state, dispatch } = useContext(StateContext);
	const { user } = state;

	const currentDate = new Date(currentYear, currentMonth, 1);
	const month = currentDate.toLocaleString("default", {
		month: "long",
	});

	if (!user) return <Redirect to={"/"} />;
	useEffect(() => {
		const date = new Date();
		setCurrentMonth(date.getMonth());
		setCurrentYear(date.getFullYear());
	}, []);

	useEffect(() => {
		const thisDate = new Date();
		const firstDay = new Date(currentYear, currentMonth, 1).getDay();
		const lastDay = new Date(currentYear, currentMonth + 1, 0).getDate();
		const items: JSX.Element[] = [];

		let currentIndex = 0;
		let dateIndex = 1;
		for (let i = 0; i < 7; i++) {
			for (let j = 0; j < 5; j++) {
				const isToday =
					dateIndex == thisDate.getDay() && thisDate.getMonth() == currentMonth
						? true
						: false;
				items.push(
					<CalendarItem
						isCurrent={isToday}
						key={currentIndex}
						col={i}
						row={j}
						date={
							currentIndex + 1 >= firstDay && dateIndex <= lastDay
								? new Date(currentYear, currentMonth, dateIndex)
								: undefined
						}
					/>
				);
				if (currentIndex + 1 >= firstDay && dateIndex <= lastDay) dateIndex++;
				currentIndex++;
			}
		}
		setGridItems(items);
	}, [currentMonth]);

	const nextMonth = () => {
		if (currentMonth + 1 >= 12) {
			setCurrentMonth(0);
			setCurrentYear(currentYear + 1);
		} else setCurrentMonth(currentMonth + 1);
	};

	const prevMonth = () => {
		if (currentMonth - 1 < 0) {
			setCurrentMonth(11);
			setCurrentYear(currentYear - 1);
		} else setCurrentMonth(currentMonth - 1);
	};

	const goToToday = () => {
		const date = new Date();
		setCurrentMonth(date.getMonth());
		setCurrentYear(date.getFullYear());
	};

	return (
		<Box>
			<Heading textAlign="center">
				{month} {currentYear}
			</Heading>
			<Center>
				<HStack spacing={200}>
					<Button leftIcon={<ArrowBackIcon />} onClick={prevMonth}>
						Forige m??ned
					</Button>
					<Button onClick={goToToday}>I dag</Button>
					<Button rightIcon={<ArrowForwardIcon />} onClick={nextMonth}>
						Neste m??ned
					</Button>
				</HStack>
			</Center>
			<Divider w="90%" m={5} ml="auto" mr="auto" />
			<SimpleGrid gap={2} w="90%" ml="auto" mr="auto" columns={7}>
				<Box>Mandag</Box>
				<Box>Tirsdag</Box>
				<Box>Onsdag</Box>
				<Box>Torsdag</Box>
				<Box>Fredag</Box>
				<Box>L??rdag</Box>
				<Box>S??ndag</Box>
			</SimpleGrid>
			<Grid
				templateColumns="repeat(7, 1fr)"
				templateRows="repeat(5, 1fr)"
				gap={2}
				w="90%"
				ml="auto"
				mr="auto"
			>
				{gridItems}
			</Grid>
			<Divider w="90%" m={5} ml="auto" mr="auto" />

			<Center>
				<Button
					onClick={() => {
						dispatch(clearEvent());
						history.push("/event");
					}}
				>
					Ny hendelse
				</Button>
			</Center>
		</Box>
	);
};

export default Calendar;
