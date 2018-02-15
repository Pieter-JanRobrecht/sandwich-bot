package be.pj.sandwichbot.slack;

import be.pj.sandwichbot.model.Sandwich;
import be.pj.sandwichbot.repositories.SandwichRepository;
import be.pj.sandwichbot.repositories.SlackUserRepository;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsCreateRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersIdentityRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersInfoRequest;
import com.github.seratch.jslack.api.methods.response.users.UsersIdentityResponse;
import com.github.seratch.jslack.api.methods.response.users.UsersInfoResponse;
import javassist.tools.web.Webserver;
import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.Controller;
import me.ramswaroop.jbot.core.slack.EventType;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.regex.Matcher;

@Component
public class SlackBot extends Bot {

  private static final Logger logger = LoggerFactory.getLogger(SlackBot.class);

  @Value("${slackApiKey}")
  private String slackToken;

  @Autowired
  private SlackUserRepository slackUserRepository;

  @Autowired
  private SandwichRepository sandwichRepository;

  @Override
  public String getSlackToken() {
    return slackToken;
  }

  @Override
  public Bot getSlackBot() {
    return this;
  }

  @Controller(events = EventType.MESSAGE, pattern = "SANDWICH SANDWICH SANDWICH")
  public void onReceiveMessage(WebSocketSession session, Event event, Matcher matcher) {
    reply(session, event, new Message("WHY DID YOU SUMMON ME MERE MORTAL????"));
  }

  @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE}, pattern = ".*list.*")
  public void onReceiveDM(WebSocketSession session, Event event) throws Exception{
    logger.info("User is asking for list of sandwiches");

    Iterable<Sandwich> sandwiches = sandwichRepository.findAll();
    StringBuilder message = new StringBuilder("");

    for (Sandwich sandwich : sandwiches) {
      message.append(sandwich.toPrettyFormat());
      message.append("\n");
    }

    reply(session, event, new Message(message.toString()));
    logger.info("Returned list in message:" + message);
  }

  @Controller(events = EventType.DIRECT_MENTION, pattern = ".*order.*", next = "confirmOrder")
  public void onWantingToOrder(WebSocketSession session, Event event) {
    logger.info("User want to order something. Starting conversation");

    startConversation(event, "confirmOrder");
    reply(session, event, new Message("What would you like to order?"));

    logger.info("Going to next step in conversation: confirmOrder");
  }

  @Controller(next = "order")
  public void confirmOrder(WebSocketSession session, Event event) {
    logger.info("Asking what user wants to order " + event.getText());

    reply(session, event, new Message("Okay no problem! I will order a " + event.getText() + " for you."));
    nextConversation(event);

    logger.info("Asked what the user wants to order. Going to order " + event.getText());
  }

  @Controller
  public void order(WebSocketSession session, Event event) {
    logger.info("Ordered" + event.getText());

    reply(session, event, new Message("No problem"));
    stopConversation(event);

    logger.info("Thanked the user and stopping conversation");
  }
}