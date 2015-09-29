package org.pelevin.web;

import org.pelevin.exceptions.EntityNotFoundException;
import org.pelevin.model.MessageVO;
import org.pelevin.model.UserVO;
import org.pelevin.services.MessageService;
import org.pelevin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by dmitry on 27.08.15.
 */
@RestController
@RequestMapping("/api/messages")
public class MessageRestController {

	private final MessageService service;

	@Autowired
	public MessageRestController(MessageService service) {
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.GET)
	Collection<MessageVO> getAll() {
		return this.service.findAll();
	}

}
