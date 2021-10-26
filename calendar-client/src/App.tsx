import React from "react";
import {
	BrowserRouter as Router,
	Switch,
	Route,
} from "react-router-dom";
import Login from "./pages/Login";

function App() {
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
}

export default App;
