package be.pj.sandwichbot.slack.builders;

import be.pj.sandwichbot.model.SlackUserModel;

public class SlackUserModelBuilder {

  private SlackUserModel model;

  public SlackUserModelBuilder(){
    model = new SlackUserModel();
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

  public SlackUserModel build() {
    return model;
  }
}
