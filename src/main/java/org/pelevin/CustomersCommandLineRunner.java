package org.pelevin;

import org.apache.log4j.Logger;
import org.pelevin.model.CustomerVO;
import org.pelevin.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by dmitry on 27.08.15.
 */
@Component
public class CustomersCommandLineRunner implements CommandLineRunner{

	private static final Logger logger = Logger.getLogger(CustomersCommandLineRunner.class.getName());

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public void run(String... strings) throws Exception {
		for (CustomerVO cust: customerRepository.findAll()){
			logger.info("Customer added: " + cust.getName());
		}
	}
}
