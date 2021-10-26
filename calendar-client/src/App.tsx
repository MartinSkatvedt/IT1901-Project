import React, { FC } from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Login from "./pages/Login";
import Calendar from "./pages/Calendar";
import Event from "./pages/Event";
const App: FC = () => {
	return (
		<Router>
			<Switch>
				<Route path="/event">
					<Event />
				</Route>
				<Route path="/calendar">
					<Calendar />
				</Route>
				<Route path="/">
					<Login />
				</Route>
			</Switch>
		</Router>
	);
};

export default App;
