import { Box } from "@chakra-ui/react";
import React, {FC, useContext} from "react";
import {EventType} from "../types/user";
import { useHistory  } from "react-router-dom";
import { StateContext } from "../state/state";
import {setEvent} from "../state/actions";

type EventItemProps = {
    event: EventType;
}
const EventItem: FC<EventItemProps> = ({event} : EventItemProps) => {
	const history = useHistory();
	const {dispatch} = useContext(StateContext);
	const editEvent = () => {
		dispatch(setEvent(event));
		history.push("/event");
	};
	return <Box as="button" onClick={() => editEvent()}textAlign="left" w="100%" _hover={{background: "yellow.500"}} transition="0.2s">{event.timeString} {event.header}</Box>;
};

export default EventItem;