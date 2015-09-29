package org.pelevin.services.impl;

import org.pelevin.model.BaseVO;
import org.pelevin.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by dmitry on 23.09.15.
 */
public abstract class BaseServiceImpl<T extends BaseVO, R extends MongoRepository> implements BaseService<T> {

	@Autowired
	private R repository;

	public R getRepository() {
		return repository;
	}

	@Override
	public T save(T entity) {
		entity.setUpdatedAt(new Date());
		return (T) repository.save(entity);
	}

	@Override
	public List<T> findAll() {
		return repository.findAll();
	}

	@Override
	public void delete(String id) {
		repository.delete(id);
	}
}
