import React, { FC } from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import { ChakraProvider, extendTheme } from "@chakra-ui/react";
import Login from "./pages/Login";
import Calendar from "./pages/Calendar";
import Event from "./pages/Event";

const theme = extendTheme({
	styles: {
		global: {
			body: {
				bg: "yellow.100",
				color: "black",
			},
		},
	},
	initialColorMode: "dark",
	useSystemColorMode: false,
});

const App: FC = () => {
	return (
		<ChakraProvider theme={theme}>
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
		</ChakraProvider>
	);
};

export default App;
