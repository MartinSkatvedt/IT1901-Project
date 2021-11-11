import { Box } from "@chakra-ui/react";
import React, {FC} from "react";
import {EventType} from "../types/user";

type EventItemProps = {
    event: EventType;
}
const EventItem: FC<EventItemProps> = ({event} : EventItemProps) => {
	return <Box w="100%" _hover={{background: "black"}} transition="0.2s">{event.timeString} {event.header}</Box>;
};  


export default EventItem;