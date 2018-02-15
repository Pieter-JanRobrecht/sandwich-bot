package be.pj.sandwichbot.slack;

import be.pj.sandwichbot.model.SlackUser;
import be.pj.sandwichbot.services.SandwichService;
import be.pj.sandwichbot.services.SlackUserService;
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

import java.util.HashMap;
import java.util.Map;

@Component
public class SlackBot extends Bot {

  private static final Logger logger = LoggerFactory.getLogger(SlackBot.class);

  @Value("${slackApiKey}")
  private String slackToken;

  private String currentUserID;
  private String currentOrder;
  private Map<String, String> userMapping = new HashMap<>();

  @Autowired
  private SandwichService sandwichService;

  @Autowired
  private SlackUserService userService;

  @Override
  public String getSlackToken() {
    return slackToken;
  }

  @Override
  public Bot getSlackBot() {
    return this;
  }

  @Controller(events = EventType.MESSAGE, pattern = "SANDWICH SANDWICH SANDWICH")
  public void onReceiveMessage(WebSocketSession session, Event event) {
    reply(session, event, new Message("WHY DID YOU SUMMON ME MERE MORTAL????"));
  }

  @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE}, pattern = ".*list.*")
  public void onReceiveDM(WebSocketSession session, Event event) {
    logger.info("User is asking for list of sandwiches");

    String sandwiches = sandwichService.findAllSandwiches();
    reply(session, event, new Message(sandwiches));

    logger.info("Returned list in message:" + sandwiches);
  }

  @Controller(events = EventType.DIRECT_MENTION, pattern = ".*order.*", next = "confirmOrder")
  public void onWantingToOrder(WebSocketSession session, Event event) throws Exception {
    logger.info("User " + event.getUserId() + " wants to order. Starting conversation.");

    SlackUser model = userService.findUser(event.getUserId());

    if (!userMapping.containsKey(event.getUserId())) {
      userMapping.put(model.getUserId(), model.getName());
    }

    currentUserID = event.getUserId();

    startConversation(event, "confirmOrder");
    reply(session, event, new Message("Hey " + model.getName() + "! What would you like to order?"));

    logger.info("Asked user " + event.getUserId() + " for order.");
  }

  @Controller(next = "order")
  public void confirmOrder(WebSocketSession session, Event event) {
    if (currentUserID.equals(event.getUserId())) {
      logger.info("User " + event.getUserId() + " wants to order: " + event.getText());

      currentOrder = event.getText();
      reply(session, event, new Message("Okay no problem! I will order: " + event.getText() + " for you."));
      nextConversation(event);

      logger.info("Going to order " + event.getText() + " for user " + event.getUserId());
    }
  }

  @Controller
  public void order(WebSocketSession session, Event event) {
    if (currentUserID.equals(event.getUserId())) {
      logger.info("Ordering " + currentOrder + " for user " + currentUserID);

      reply(session, event, new Message("No problem"));
      stopConversation(event);

      logger.info("Thanked " + currentUserID + " and stopping conversation.");
    }
  }
}