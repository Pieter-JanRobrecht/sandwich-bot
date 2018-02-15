package be.pj.sandwichbot.services;

import be.pj.sandwichbot.model.SlackUser;
import be.pj.sandwichbot.repositories.SlackUserRepository;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.request.users.UsersInfoRequest;
import com.github.seratch.jslack.api.methods.response.users.UsersInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SlackUserService {

  @Value("${SLACK_API_KEY}")
  private String slackToken;

  @Autowired
  private SlackUserRepository userRepository;

  private Slack slack = Slack.getInstance();

  public SlackUser findUser(String userId) throws Exception{
    SlackUser model = new SlackUser();

    if (!userRepository.exists(userId)) {
      UsersInfoRequest request = UsersInfoRequest.builder().token(slackToken).user(userId).build();
      UsersInfoResponse response = slack.methods().usersInfo(request);
      model.setUserId(userId);
      model.setName(response.getUser().getProfile().getRealName());
      model.setEmail(response.getUser().getProfile().getEmail());
      userRepository.save(model);
    } else {
      model = userRepository.findOne(userId);
    }

    return model;
  }
}
