package be.pj.sandwichbot.slack.test;

import be.pj.sandwichbot.slack.config.SlackBotConfiguration;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {SlackBotConfiguration.class})
public class AbstractBotTest {
}
