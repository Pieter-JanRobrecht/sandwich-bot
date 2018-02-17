package be.pj.sandwichbot.slack.test;

import be.pj.sandwichbot.model.Sandwich;
import be.pj.sandwichbot.services.SandwichService;
import be.pj.sandwichbot.services.SlackUserService;
import be.pj.sandwichbot.slack.SlackBot;
import be.pj.sandwichbot.slack.SlackMessage;
import be.pj.sandwichbot.slack.builders.SandwichBuilder;
import be.pj.sandwichbot.slack.builders.SlackMessageBuilder;
import be.pj.sandwichbot.slack.builders.SlackUserModelBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.socket.WebSocketSession;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class SlackBotTest extends AbstractBotTest {

  private static final Sandwich SANDWICH = new SandwichBuilder().simple().build();
  private static String DEFAULT_USER = "UBADGH18S";
  private static String RANDOM_USER = "UEADGH18S";

  @Rule
  public OutputCapture capture = new OutputCapture();

  private SlackBot slackBot;

  @Autowired
  private SandwichService sandwichService;

  @Autowired
  private SlackUserService userService;

  @Autowired
  private WebSocketSession session;

  @Before
  public void setUp() {
    slackBot = new SlackBot();
    Mockito.spy(slackBot);
  }

  @Test
  public void ifAskingForListReturnEverything() throws Exception {
    when(sandwichService.findAllSandwiches()).thenReturn(SANDWICH.toPrettyFormat());

    SlackMessage slackMessage = new SlackMessageBuilder()
            .toSlackBot()
            .payload("list")
            .build();

    slackBot.handleTextMessage(session, slackMessage.convertToTextMessage());
    assertThat(capture.toString()).isNotNull().contains(SANDWICH.toPrettyFormat());
  }

  @Test
  public void simpleConversationTest() throws Exception {
    when(userService.findUser(anyString())).thenReturn(new SlackUserModelBuilder().userId("UBADGH18S").name("PJ").build());

    SlackMessage slackMessage = new SlackMessageBuilder()
            .directMentionSlackBot("order")
            .build();

    slackBot.handleTextMessage(session, slackMessage.convertToTextMessage());
    assertThat(capture.toString()).isNotNull().contains("Asked user " + DEFAULT_USER);

    SlackMessage slackMessage1 = new SlackMessageBuilder()
            .toRandomChannel()
            .byDefaultUser()
            .payload("sandwich")
            .build();

    slackBot.handleTextMessage(session, slackMessage1.convertToTextMessage());
    assertThat(capture.toString()).isNotNull().contains("Going to order sandwich");

    SlackMessage slackMessage2 = new SlackMessageBuilder()
            .toRandomChannel()
            .byDefaultUser()
            .payload("thanks")
            .build();

    slackBot.handleTextMessage(session, slackMessage2.convertToTextMessage());
    assertThat(capture.toString()).isNotNull().contains("Thanked " + DEFAULT_USER);
  }

  @Test
  public void conversationWithSomeoneInterrupting() throws Exception {
    SlackMessage slackMessage = new SlackMessageBuilder()
            .directMentionSlackBot("order")
            .build();

    slackBot.handleTextMessage(session, slackMessage.convertToTextMessage());
    assertThat(capture.toString()).isNotNull().contains("Asked user " + DEFAULT_USER);

    SlackMessage badMessage = new SlackMessageBuilder()
            .toRandomChannel()
            .byRandomUser()
            .payload("soup")
            .build();

    slackBot.handleTextMessage(session, badMessage.convertToTextMessage());
    assertThat(capture.toString()).isNotNull().doesNotContain("Going to order soup");

    SlackMessage slackMessage1 = new SlackMessageBuilder()
            .toRandomChannel()
            .byDefaultUser()
            .payload("sandwich")
            .build();

    slackBot.handleTextMessage(session, slackMessage1.convertToTextMessage());
    assertThat(capture.toString()).isNotNull().contains("Going to order sandwich");

    SlackMessage slackMessage2 = new SlackMessageBuilder()
            .toRandomChannel()
            .byDefaultUser()
            .payload("thanks")
            .build();

    slackBot.handleTextMessage(session, slackMessage2.convertToTextMessage());
    assertThat(capture.toString()).isNotNull().contains("Thanked " + DEFAULT_USER);
  }

  @Test
  public void conversationWithSomeoneStartingNewOrder() throws Exception {
    SlackMessage userOne = new SlackMessageBuilder()
            .directMentionSlackBot("order")
            .build();

    slackBot.handleTextMessage(session, userOne.convertToTextMessage());
    assertThat(capture.toString()).isNotNull().contains("Asked user " + DEFAULT_USER);

    SlackMessage userTwo = new SlackMessageBuilder()
            .directMentionSlackBot("order")
            .byRandomUser()
            .build();

    slackBot.handleTextMessage(session, userTwo.convertToTextMessage());
    assertThat(capture.toString()).isNotNull().contains("Asked user " + RANDOM_USER);

    SlackMessage orderMessageOne = new SlackMessageBuilder()
            .toRandomChannel()
            .byRandomUser()
            .payload("soup")
            .build();

    slackBot.handleTextMessage(session, orderMessageOne.convertToTextMessage());
    assertThat(capture.toString()).isNotNull().contains("Going to order soup");

    SlackMessage orderMessageTwo = new SlackMessageBuilder()
            .toRandomChannel()
            .byDefaultUser()
            .payload("sandwich")
            .build();

    slackBot.handleTextMessage(session, orderMessageTwo.convertToTextMessage());
    assertThat(capture.toString()).isNotNull().contains("Going to order sandwich");

    SlackMessage endMessageOne = new SlackMessageBuilder()
            .toRandomChannel()
            .byDefaultUser()
            .payload("thanks")
            .build();

    slackBot.handleTextMessage(session, endMessageOne.convertToTextMessage());
    assertThat(capture.toString()).isNotNull().contains("Thanked " + DEFAULT_USER);

    SlackMessage endMessageTwo = new SlackMessageBuilder()
            .toRandomChannel()
            .byDefaultUser()
            .payload("thanks")
            .build();

    slackBot.handleTextMessage(session, endMessageTwo.convertToTextMessage());
    assertThat(capture.toString()).isNotNull().contains("Thanked " + RANDOM_USER);
  }
}

