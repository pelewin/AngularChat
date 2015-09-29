package org.pelevin.services.impl;

import org.pelevin.model.CustomerVO;
import org.pelevin.repositories.CustomerRepository;
import org.pelevin.services.CustomerService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by dmitry on 23.09.15.
 */
@Service
public class CustomerServiceImpl extends BaseServiceImpl<CustomerVO, CustomerRepository> implements CustomerService {

	@Override
	public Optional<CustomerVO> findById(String id) {
		return getRepository().findById(id);
	}
}
