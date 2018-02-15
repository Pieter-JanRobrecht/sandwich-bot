package be.pj.sandwichbot.slack.test;

import be.pj.sandwichbot.model.Sandwich;
import be.pj.sandwichbot.repositories.SandwichRepository;
import be.pj.sandwichbot.slack.SlackBot;
import be.pj.sandwichbot.slack.SlackMessage;
import be.pj.sandwichbot.slack.builders.SandwichBuilder;
import be.pj.sandwichbot.slack.builders.SlackMessageBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class SlackBotTest extends AbstractBotTest {

  private static final Sandwich SANDWICH = new SandwichBuilder().simple().build();

  @Rule
  public OutputCapture capture = new OutputCapture();

  @Autowired
  private SlackBot slackBot;

  @Autowired
  private SandwichRepository sandwichRepository;

  @Autowired
  private WebSocketSession session;

  @Test
  public void simpleSlackBotTest() throws Exception {
    when(sandwichRepository.findAll()).thenReturn(Collections.singletonList(SANDWICH));

    SlackMessage slackMessage = new SlackMessageBuilder()
            .toSlackBot()
            .payload("list")
            .build();

    slackBot.handleTextMessage(session, slackMessage.convertToTextMessage());
    assertThat(capture.toString()).isNotNull().contains(SANDWICH.toString());
  }
}

