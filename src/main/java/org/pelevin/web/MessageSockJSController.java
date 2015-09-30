package org.pelevin.web;

import org.pelevin.model.CurrentUserVO;
import org.pelevin.model.MessageVO;
import org.pelevin.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

/**
 * Created by dmitry on 27.08.15.
 */
@Controller
public class MessageSockJSController {

	@Autowired
	private MessageService service;


	@MessageMapping("/message")
	@SendTo("/topic/messages")
	public MessageVO addMessage(Authentication authentication, String text) throws Exception {
		CurrentUserVO currentUserVO = (CurrentUserVO) authentication.getPrincipal();
		MessageVO message = new MessageVO(currentUserVO.getUser(), text);
		MessageVO result = service.save(message);
		return result;
	}

}
