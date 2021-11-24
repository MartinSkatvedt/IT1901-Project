import {
	Box,
	IconButton,
	HStack,
	Popover,
	PopoverTrigger,
	PopoverContent,
	PopoverArrow,
	PopoverCloseButton,
	PopoverHeader,
	PopoverBody,
	Button,
	Center,
} from "@chakra-ui/react";
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
		history.push("/event");
	};

	if (!user) return <div>Loading...</div>;

	const onDelete = async () => {
		await deleteEvent(user.username, event.id);
		const reqUser = await getUser(user.username);
		if (reqUser) {
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
			<Popover id="pop-id">
				<PopoverTrigger>
					<IconButton
						aria-label="delete"
						icon={<DeleteIcon />}
						size="sm"
						colorScheme="tomato"
						_hover={{ color: "black" }}
					/>
				</PopoverTrigger>
				<PopoverContent>
					<PopoverArrow />
					<PopoverCloseButton />
					<PopoverHeader>Delete event</PopoverHeader>
					<PopoverBody>
						{"Are you sure you want to delete that event?"}
						<Center>
							<Button onClick={() => onDelete()}>Delete</Button>
						</Center>
					</PopoverBody>
				</PopoverContent>
			</Popover>
		</HStack>
	);
};

export default EventItem;
