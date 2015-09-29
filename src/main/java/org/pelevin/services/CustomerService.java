package org.pelevin.services;

import org.pelevin.model.CustomerVO;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by dmitry on 27.08.15.
 */
@Service
public interface CustomerService extends BaseService<CustomerVO>{

	Optional<CustomerVO> findById(String id);

}
