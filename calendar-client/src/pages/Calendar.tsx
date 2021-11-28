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
import { clearEvent, logoutUser } from "../state/actions";

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
					dateIndex == thisDate.getUTCDate() &&
					thisDate.getMonth() == currentMonth
						? true
						: false;
				console.log(dateIndex + "/" + thisDate.getDay());
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
		<Box w="100%">
			<Button
				aria-label="logout button"
				onClick={() => {
					dispatch(logoutUser());
					history.push("/");
				}}
				w="10%"
				marginTop="10px"
				marginLeft="85%"
			>
				Log out
			</Button>
			<Heading textAlign="center">
				{month.charAt(0).toUpperCase() + month.slice(1)} {currentYear}
			</Heading>
			<Center>
				<HStack spacing={200}>
					<Button leftIcon={<ArrowBackIcon />} onClick={prevMonth}>
						Forige måned
					</Button>
					<Button onClick={goToToday}>I dag</Button>
					<Button rightIcon={<ArrowForwardIcon />} onClick={nextMonth}>
						Neste måned
					</Button>
				</HStack>
			</Center>
			<Divider w="90%" m={5} ml="auto" mr="auto" />
			<SimpleGrid gap={2} w="95%" ml="auto" mr="auto" columns={7}>
				<Box>Mandag</Box>
				<Box>Tirsdag</Box>
				<Box>Onsdag</Box>
				<Box>Torsdag</Box>
				<Box>Fredag</Box>
				<Box>Lørdag</Box>
				<Box>Søndag</Box>
			</SimpleGrid>
			<Center>
				<Grid
					templateColumns="repeat(7, 1fr)"
					templateRows="repeat(5, 1fr)"
					gap={2}
					w="95%"
				>
					{gridItems}
				</Grid>
			</Center>
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
