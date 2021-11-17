import React, { FC, useContext } from "react";
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
} from "@chakra-ui/react";
import DayPickerInput from "react-day-picker/DayPickerInput";
import "react-day-picker/lib/style.css";
import { EventType } from "../types/user";
import { StateContext } from "../state/state";
import { FormikHelpers, useFormikContext, useField, Formik, Form, FormikProps } from "formik";


type Values = {
	header: string;
	description: string;
	minute: number;
	hour: number;
	date: Date;
};

export const DatePickerField = ({ ...props }: { [x: string]: any; name: string }): JSX.Element => {
	const { setFieldValue } = useFormikContext();
	const [field] = useField(props);
	return (
		<DayPickerInput
			{...field}
			{...props}
			value={(field.value && new Date(field.value)) || null}
			onDayChange={val => {
				setFieldValue(field.name, val);
			}}
		/>
	);
};


const Event: FC = () => {
	const {state} = useContext(StateContext);
	const {currentEvent} = state;

	
	const parseTime = (hours: number, minutes: number): string => {
		const minuteFixed = minutes < 10 ?  "0" + minutes : minutes;
		const hourFixed = hours < 10 ?  "0" + hours : hours;
		return hourFixed + ":" + minuteFixed;
	};

	const submitValues = (values: Values): EventType => {
		const eventObj = {
			header: values.header,
			description: values.description,
			date: values.date.toDateString(),
			timeString: parseTime(values.hour, values.minute),
		};
		console.log(eventObj);
		return eventObj;
	};

	return (<Box w="60%" ml="auto" mr="auto" >
		<Heading textAlign="center">Opprett en ny hendelse</Heading>
		<Formik 
			initialValues={{
				header: currentEvent ? currentEvent.header : "",
				description: currentEvent ? currentEvent.description : "",
				date: currentEvent ? new Date(currentEvent.date) : new Date(),
				hour: currentEvent ? Number(currentEvent.timeString.substr(0, 2)) : 12,
				minute: currentEvent ? Number(currentEvent.timeString.substr(3, 5)) : 30,
			}}
			onSubmit={(
				values: Values,
				{ setSubmitting }: FormikHelpers<Values>
			) => {
				setTimeout(() => {
					submitValues(values);
					setSubmitting(false);
				}, 500);
			}}
		>
			{(props: FormikProps<any>) => (
				<Form>
					<FormControl isRequired>
						<FormLabel>Overskrift</FormLabel>
						<Input value={props.values.header} onChange={props.handleChange} type="text" variant="filled" name="header"/>
					</FormControl>
	

					<FormControl isRequired>
						<FormLabel>Beskrivelse</FormLabel>
						<Textarea value={props.values.description} onChange={props.handleChange} variant="filled" name="description"/>
					</FormControl>

					<FormControl isRequired>
						<FormLabel>Dato</FormLabel>
						<DatePickerField value={props.values.date} onChange={props.handleChange} name="date"/>	
					</FormControl>

					<FormControl isRequired>
						<FormLabel>Tid</FormLabel>
						<HStack> 
							<NumberInput value={props.values.hour} precision={0} min={0} max={24} w={20} variant="filled">
								<NumberInputField name="hour" value={props.values.hour} onChange={props.handleChange}/>
							</NumberInput>
					
							<NumberInput value={props.values.minute} precision={0} min={0} max={60} w={20} variant="filled" >
								<NumberInputField name="minute" value={props.values.minute} onChange={props.handleChange}/>
							</NumberInput>
						</HStack>
			
					</FormControl>
					<Divider w="90%" m={5} ml="auto" mr="auto"/>

					<Center>
						<Input type="submit"/>
					</Center>
				</Form>
			)}
		</Formik>
	</Box>);
};

export default Event;
