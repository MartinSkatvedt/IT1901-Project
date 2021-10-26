import React, {FC} from "react";
import {
	BrowserRouter as Router,
	Switch,
	Route,
} from "react-router-dom";
import Login from "./pages/Login";

const App:FC = () => {
	return (
		<Router>
			<Switch>
				<Route path="/newEvent" />
				<Route path="/calendar" />
				<Route path="/">
					<Login />
				</Route>
			</Switch>
		</Router>
	);
};

export default App;
