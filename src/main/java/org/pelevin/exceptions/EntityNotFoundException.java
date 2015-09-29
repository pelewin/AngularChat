package org.pelevin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by dmitry on 27.08.15.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

	public EntityNotFoundException(Class vo, String id) {
		super("could not find " + vo.getSimpleName() + " id:" + id + "'.");
	}

}
