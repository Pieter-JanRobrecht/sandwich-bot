package be.pj.sandwichbot.slack.builders;

import be.pj.sandwichbot.slack.SlackMessage;

import java.time.Instant;

public class SlackMessageBuilder {

  private static String SLACK_BOT_ID = "UEADGH12S";
  private static String DEFAULT_USER = "UBADGH18S";
  private static String RANDOM_USER = "UEADGH18S";
  private static String DIRECT_MENTION_SLACK_BOT = "<@" + SLACK_BOT_ID + ">:";
  private static String SLACK_BOT_DM_CHANNEL = "D1E79BACV";
  private static String NORMAL_DM_CHANNEL = "C0NDSV5B8";
  private static String RANDOM_CHANNEL = "A1E78BACV";

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
    return this.type("message")
            .user(DEFAULT_USER)
            .channel(SLACK_BOT_DM_CHANNEL)
            .timestamp(Instant.now().toEpochMilli()+"");
  }

  public SlackMessageBuilder directMentionSlackBot(String payload) {
    return this.type("message")
            .user(DEFAULT_USER)
            .channel(RANDOM_CHANNEL)
            .timestamp(Instant.now().toEpochMilli() + "")
            .payload(DIRECT_MENTION_SLACK_BOT + payload);
  }

  public SlackMessageBuilder toRandomChannel() {
    return this.type("message")
            .channel(RANDOM_CHANNEL)
            .timestamp(Instant.now().toEpochMilli() + "");
  }

  public SlackMessageBuilder byDefaultUser() {
    return this.user(DEFAULT_USER);
  }

  public SlackMessageBuilder byRandomUser() {
    return this.user(RANDOM_USER);
  }
}
