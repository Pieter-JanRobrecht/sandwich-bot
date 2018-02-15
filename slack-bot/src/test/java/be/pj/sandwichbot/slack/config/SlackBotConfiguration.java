package be.pj.sandwichbot.slack.config;

import be.pj.sandwichbot.repositories.SandwichRepository;
import be.pj.sandwichbot.repositories.SlackUserRepository;
import be.pj.sandwichbot.services.SandwichService;
import be.pj.sandwichbot.services.SlackUserService;
import be.pj.sandwichbot.slack.SlackBot;
import me.ramswaroop.jbot.core.slack.SlackDao;
import me.ramswaroop.jbot.core.slack.SlackService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketSession;

@SpringBootConfiguration
public class SlackBotConfiguration {

  @Bean
  public WebSocketSession webSocketSession(){
    return Mockito.mock(WebSocketSession.class);
  }

  @Bean
  public SlackBot slackBot() {
    SlackBot bot = new SlackBot();
    return Mockito.spy(bot);
  }

  @Bean
  public SandwichService sandwichService(){
    return Mockito.mock(SandwichService.class);
  }

  @Bean
  public SlackUserService slackUserService(){
    return Mockito.mock(SlackUserService.class);
  }

  @Bean
  public SlackUserRepository slackUserRepository() {
    return Mockito.mock(SlackUserRepository.class);
  }

  @Bean
  public SandwichRepository sandwichRepository(){
    return Mockito.mock(SandwichRepository.class);
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
