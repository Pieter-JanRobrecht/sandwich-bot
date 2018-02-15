package be.pj.sandwichbot.slack.builders;

import be.pj.sandwichbot.slack.SlackMessage;

import java.time.Instant;

public class SlackMessageBuilder {

  private static String SLACK_BOT_ID = "UEADGH12S";
  private static String SLACK_BOT_DM_CHANNEL = "D1E79BACV";

  private SlackMessage message;

  public SlackMessageBuilder() {
    message = new SlackMessage();
  }

  public SlackMessageBuilder channel(String channel){
    message.setChannel(channel);

    return this;
  }

  public SlackMessageBuilder type(String type) {
    message.setType(type);

    return this;
  }

  public SlackMessageBuilder user(String user) {
    message.setUser(user);

    return this;
  }

  public SlackMessageBuilder payload(String payload) {
    message.setPayload(payload);

    return this;
  }

  public SlackMessageBuilder timestamp(String timestamp) {
    message.setTimestamp(timestamp);

    return this;
  }

  public SlackMessage build() {
    return message;
  }

  public SlackMessageBuilder toSlackBot() {
    return new SlackMessageBuilder()
            .type("message")
            .user(SLACK_BOT_ID)
            .channel(SLACK_BOT_DM_CHANNEL)
            .timestamp(Instant.now().toEpochMilli()+"");
  }
}
