package org.pelevin;

import org.apache.log4j.Logger;
import org.pelevin.model.UserVO;
import org.pelevin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by dmitry on 27.08.15.
 */
@Component
public class ChatCommandLineRunner implements CommandLineRunner {

	private static final Logger logger = Logger.getLogger(ChatCommandLineRunner.class.getName());

	@Autowired
	private UserService userService;

	@Override
	public void run(String... strings) throws Exception {

		addUser("Dmitry", "Dd123456");
		addUser("Gennady", "Gg123456");
		addUser("Maxim", "Mm123456");
		addUser("Ivan", "Ii123456");

		for (UserVO cust : userService.findAll()) {
			logger.info("User added: " + cust.getName());
		}
	}

	private void addUser(String name, String password){

		UserVO user = userService.findByName(name)
				.orElse(new UserVO(name, new BCryptPasswordEncoder().encode(password)));
		userService.save(user);
	}

}
