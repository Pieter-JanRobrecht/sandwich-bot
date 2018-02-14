package be.pj.sandwichbot.slack;

import be.pj.sandwichbot.model.SandwichModel;
import be.pj.sandwichbot.repositories.SandwichRepository;
import be.pj.sandwichbot.repositories.SlackUserRepository;
import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.Controller;
import me.ramswaroop.jbot.core.slack.EventType;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.regex.Matcher;

@Component
public class SlackBot extends Bot {

  private static final Logger logger = LoggerFactory.getLogger(SlackBot.class);

  private String slackToken = System.getenv("SLACK_API_KEY");

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
  public void onReceiveDM(WebSocketSession session, Event event) {
    logger.info("User is asking for list of sandwiches");

    Iterable<SandwichModel> sandwiches = sandwichRepository.findAll();
    StringBuilder message = new StringBuilder("");

    for (SandwichModel sandwich : sandwiches) {
      message.append(sandwich.toString());
      message.append("\n");
    }

    reply(session, event, new Message(message.toString()));
    logger.info("Returned list of sandwiches to user");
  }
}