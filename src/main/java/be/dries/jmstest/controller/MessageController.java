package be.dries.jmstest.controller;

import be.dries.jmstest.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/message")
public class MessageController {
  @Autowired
  private MessageService messageService;

  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  public String sendMessage() {
    messageService.sendMessage("some strange message");

    return "success";
  }

  @RequestMapping(value = "/error", method = RequestMethod.GET)
  @ResponseBody
  public String sendErrorCausingMessage() {
    messageService.sendErrorMessage("causes error");

    return "failed";
  }
}
