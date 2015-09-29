package org.pelevin.web;

import org.pelevin.model.MessageVO;
import org.pelevin.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.Collection;

/**
 * Created by dmitry on 27.08.15.
 */
@Controller
public class MessageSockJSController {

	private final MessageService service;

	@Autowired
	public MessageSockJSController(MessageService service) {
		this.service = service;
	}

	@MessageMapping("/message")
	@SendTo("/topic/messages")
	public MessageVO addMessage(String text) throws Exception {
		MessageVO message = new MessageVO(text);
		MessageVO result = service.save(message);
		return result;
	}

}
