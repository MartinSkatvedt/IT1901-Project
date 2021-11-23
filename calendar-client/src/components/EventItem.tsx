import { Box, IconButton, HStack } from "@chakra-ui/react";
import React, { FC, useContext } from "react";
import { EventType } from "../types/user";
import { useHistory } from "react-router-dom";
import { StateContext } from "../state/state";
import { setEvent, setUser } from "../state/actions";
import { DeleteIcon } from "@chakra-ui/icons";
import { deleteEvent, getUser } from "../api";
type EventItemProps = {
	event: EventType;
};
const EventItem: FC<EventItemProps> = ({ event }: EventItemProps) => {
	const history = useHistory();
	const { state, dispatch } = useContext(StateContext);
	const { user } = state;
	const editEvent = () => {
		dispatch(setEvent(event));
		console.log(event);
		history.push("/event");
	};

	if (!user) return <div>Loading...</div>;

	const onDelete = async () => {
		await deleteEvent(user.username, event.id);
		const reqUser = await getUser(user.username);
		if (reqUser) {
			console.log(reqUser);
			dispatch(setUser(reqUser));
		}
	};

	return (
		<HStack
			as="button"
			textAlign="left"
			w="100%"
			_hover={{ background: "yellow.500" }}
			transition="0.2s"
		>
			<Box onClick={() => editEvent()}>
				{event.timeString} {event.header}
			</Box>
			<IconButton
				aria-label="delete"
				icon={<DeleteIcon />}
				size="sm"
				colorScheme="tomato"
				_hover={{ color: "black" }}
				onClick={() => onDelete()}
			/>
		</HStack>
	);
};

export default EventItem;
