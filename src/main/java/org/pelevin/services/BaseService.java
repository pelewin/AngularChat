package org.pelevin.services;

import org.pelevin.model.BaseVO;

import java.util.List;

/**
 * Created by dmitry on 23.09.15.
 */
public interface BaseService <T extends BaseVO> {

	T save(T entity);
	List<T> findAll();
	void delete(String id);

}
