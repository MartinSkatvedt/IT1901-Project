import React, { FC, useContext, useState } from "react";
import { StateContext } from "../state/state";
import { setUser } from "../state/actions";
import { getUser } from "../api/index";
import { Box, Heading, Input, Button, Center} from "@chakra-ui/react";
import { Redirect } from "react-router-dom";

const Login: FC = () => {
	const [currentUsername, setCurrentUsername] = useState("");
	const { state, dispatch } = useContext(StateContext);
	const {user} = state;
	if (user) return <Redirect to="/calendar"/>;

	const onLogin = async () => {
		const reqUser = await getUser(currentUsername);
		if (reqUser) {
			dispatch(setUser(reqUser));
		}
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
					<Input type="text" placeholder="username" onChange={(e: React.ChangeEvent<HTMLInputElement>) => setCurrentUsername(e.target.value)}/>
					<Button onClick={onLogin} >Login</Button></Box>	
			</Center>
		
		</Box>
	);
};

export default Login;
