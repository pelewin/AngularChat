package org.pelevin;

import org.apache.log4j.Logger;
import org.pelevin.model.UserVO;
import org.pelevin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by dmitry on 27.08.15.
 */
@Component
public class ChatCommandLineRunner implements CommandLineRunner {

	private static final Logger logger = Logger.getLogger(ChatCommandLineRunner.class.getName());

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... strings) throws Exception {
		for (UserVO cust : userRepository.findAll()) {
			logger.info("User added: " + cust.getName());
		}
	}
}
