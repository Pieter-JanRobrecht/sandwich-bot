package be.pj.sandwichbot.slack.test;

import be.pj.sandwichbot.slack.config.SlackBotConfiguration;
import me.ramswaroop.jbot.core.slack.SlackService;
import me.ramswaroop.jbot.core.slack.models.User;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.socket.WebSocketSession;

import java.util.Arrays;

import static org.mockito.Mockito.when;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {SlackBotConfiguration.class})
public class AbstractBotTest {

  private static String SLACK_BOT_NAME = "SlackBot";
  private static String SLACK_BOT_ID = "UEADGH12S";

  private static String SLACK_DM_CHANNEL1 = "D1E79BACV";
  private static String SLACK_DM_CHANNEL2 = "C0NDSV5B8";


  @Autowired
  private SlackService slackService;

  @Before
  public void init() {
    // set user
    User user = new User();
    user.setName(SLACK_BOT_NAME);
    user.setId(SLACK_BOT_ID);
    // set rtm
    when(slackService.getDmChannels()).thenReturn(Arrays.asList(SLACK_DM_CHANNEL1, SLACK_DM_CHANNEL2));
    when(slackService.getCurrentUser()).thenReturn(user);
    when(slackService.getWebSocketUrl()).thenReturn("");
  }

}
