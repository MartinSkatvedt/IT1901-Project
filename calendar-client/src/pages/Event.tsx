import React, { FC, useState } from "react";
import { Box, 
	Heading, 
	Input, 
	Textarea, 
	FormControl,
	FormLabel,
	Divider,
	Center,
	HStack,
	NumberInput,
	NumberInputField,
	NumberInputStepper,
	NumberIncrementStepper,
	NumberDecrementStepper,
} from "@chakra-ui/react";
import DayPickerInput from "react-day-picker/DayPickerInput";
import "react-day-picker/lib/style.css";
import { EventType } from "../types/user";

const Event: FC = () => {
	const [currentHeader, setCurrentHeader] = useState("");
	const [currentDesc, setCurrentDesc] = useState("");
	const [currentDay, setCurrentDay] = useState(new Date());
	const [currentHour, setCurrentHour] = useState(12);
	const [currentMinute, setCurrentMinute] = useState(30);


	const parseTime = (): string => {
		const minute = currentMinute < 10 ?  "0" + currentMinute : currentMinute;
		const hour = currentHour < 10 ?  "0" + currentHour : currentHour;
		return hour + ":" + minute;
	};

	const onSubmit = (): EventType | undefined => {
		if (currentHeader == "" || currentDesc == "") return;
		const eventObj = {
			header: currentHeader,
			description: currentDesc,
			date: currentDay.toDateString(),
			timeString: parseTime(),
		};
		console.log(eventObj);
		return eventObj;
	};

	return (<Box w="60%" ml="auto" mr="auto" >
		<Heading textAlign="center">Opprett en ny hendelse</Heading>
		<form action="">
			<FormControl isRequired>
				<FormLabel>Overskrift</FormLabel>
				<Input type="text" variant="filled" onChange={(event) => setCurrentHeader(event.target.value)}/>
			</FormControl>

			<FormControl isRequired>
				<FormLabel>Beskrivelse</FormLabel>
				<Textarea variant="filled" onChange={(event) => setCurrentDesc(event.target.value)}/>
			</FormControl>
			<FormControl isRequired>
				<FormLabel>Dato</FormLabel>
				<DayPickerInput onDayChange={day => setCurrentDay(day)} />	
			</FormControl>
			<FormControl isRequired>
				<FormLabel>Tid</FormLabel>
				<HStack> 
					<NumberInput precision={0} defaultValue={12} min={0} max={24} w={20}variant="filled" >
						<NumberInputField onChange={(event) => setCurrentHour(Number(event.target.value))}/>
						<NumberInputStepper>
							<NumberIncrementStepper />
							<NumberDecrementStepper />
						</NumberInputStepper>
					</NumberInput>
					<NumberInput precision={0} defaultValue={30} min={0} max={60} w={20}variant="filled" >
						<NumberInputField onChange={(event) => setCurrentMinute(Number(event.target.value))} />
						<NumberInputStepper>
							<NumberIncrementStepper />
							<NumberDecrementStepper />
						</NumberInputStepper>
					</NumberInput>
				</HStack>
			
			</FormControl>
			<Divider w="90%" m={5} ml="auto" mr="auto"/>

			<Center>
				<Input type="submit"  onClick={() => onSubmit()}/>
			</Center>
		</form>
		
		

	</Box>);
};

export default Event;
