package be.pj.sandwichbot.slack.builders;

import be.pj.sandwichbot.model.SlackUser;

public class SlackUserModelBuilder {

  private SlackUser model;

  public SlackUserModelBuilder(){
    model = new SlackUser();
  }

  public SlackUserModelBuilder userId(String userId) {
    model.setUserId(userId);

    return this;
  }

  public SlackUserModelBuilder name(String name) {
    model.setName(name);

    return this;
  }

  public SlackUserModelBuilder email(String email) {
    model.setEmail(email);

    return this;
  }

  public SlackUser build() {
    return model;
  }
}
