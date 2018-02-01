package be.pj.sandwichbot.slack.config;

import be.pj.sandwichbot.repositories.SlackUserRepository;
import be.pj.sandwichbot.slack.SlackBot;
import me.ramswaroop.jbot.core.slack.SlackDao;
import me.ramswaroop.jbot.core.slack.SlackService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class SlackBotConfiguration {

  @Bean
  public SlackBot slackBot() {
    return Mockito.spy(SlackBot.class);
  }

  @Bean
  public SlackUserRepository slackUserRepository() {
    return Mockito.mock(SlackUserRepository.class);
  }

  @Bean
  public SlackService slackService() {
    return Mockito.mock(SlackService.class);
  }

  @Bean
  public SlackDao slackDao() {
    return Mockito.mock(SlackDao.class);
  }
}
