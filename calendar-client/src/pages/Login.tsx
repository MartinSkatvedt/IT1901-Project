import React, { FC, useContext } from "react";
import { StateContext } from "../state/state";
import { setUser } from "../state/actions";
import { getUser } from "../api/index";
import { Box, Heading, Input, Button, Center} from "@chakra-ui/react";
const Login: FC = () => {
	const { dispatch } = useContext(StateContext);

	const onLogin = async () => {
		const user = await getUser();
		if (user) dispatch(setUser(user));
	};

	return (
		<Box w="100%" 
			h="100%"
			ml="auto"
			mr="auto"
			textAlign="center">
			<Center>
				<Box>	
					<Heading>Cool Calendar</Heading>
					<Input type="text" placeholder="username" />
					<Button onClick={() => onLogin}>Login</Button></Box>	
			</Center>
		
		</Box>
	);
};

export default Login;
