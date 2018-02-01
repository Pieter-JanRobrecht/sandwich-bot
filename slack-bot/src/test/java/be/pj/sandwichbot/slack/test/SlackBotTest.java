package be.pj.sandwichbot.slack.test;

import be.pj.sandwichbot.slack.SlackBot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
public class SlackBotTest extends AbstractBotTest {

  @Autowired
  private SlackBot slackBot;

  @Test
  public void simpleSlackBotTest() {
    doReturn("TOKEN").when(slackBot).getSlackToken();

    System.out.println(slackBot.getSlackToken());
  }
}

