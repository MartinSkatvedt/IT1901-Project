import React, { FC } from "react";
import { useRouteMatch } from "react-router-dom";

const Event: FC = () => {
	const match = useRouteMatch();

	return <div>Event</div>;
};

export default Event;
