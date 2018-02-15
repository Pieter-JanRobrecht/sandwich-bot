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
  public void ifAskingForListReturnEverything() throws Exception {
    when(sandwichRepository.findAll()).thenReturn(Collections.singletonList(SANDWICH));

    SlackMessage slackMessage = new SlackMessageBuilder()
            .toSlackBot()
            .payload("list")
            .build();

    slackBot.handleTextMessage(session, slackMessage.convertToTextMessage());
    assertThat(capture.toString()).isNotNull().contains(SANDWICH.toPrettyFormat());
  }

  @Test
  public void simpleConversationTest() throws Exception {
    SlackMessage slackMessage = new SlackMessageBuilder()
            .directMentionSlackBot("order")
            .build();

    slackBot.handleTextMessage(session, slackMessage.convertToTextMessage());
    assertThat(capture.toString()).isNotNull().contains("Going to next step in conversation");

    SlackMessage badMessage = new SlackMessageBuilder()
            .toRandomChannel()
            .byRandomUser()
            .payload("soup")
            .build();

//    slackBot.handleTextMessage(session, badMessage.convertToTextMessage());
//    assertThat(capture.toString()).isNotNull().doesNotContain("soup");

    SlackMessage slackMessage1 = new SlackMessageBuilder()
            .toRandomChannel()
            .byDefaultUser()
            .payload("sandwich")
            .build();

    slackBot.handleTextMessage(session, slackMessage1.convertToTextMessage());
    assertThat(capture.toString()).isNotNull().contains("Going to order sandwich");

    SlackMessage slackMessage2 = new SlackMessageBuilder()
            .toRandomChannel()
            .payload("thanks")
            .build();

    slackBot.handleTextMessage(session, slackMessage2.convertToTextMessage());
    assertThat(capture.toString()).isNotNull().contains("stopping conversation");
  }
}

