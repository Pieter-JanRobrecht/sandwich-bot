package be.pj.sandwichbot.slack;

import org.springframework.web.socket.TextMessage;

public class SlackMessage {
  private String type;
  private String timestamp;
  private String channel;
  private String user;
  private String payload;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }

  public TextMessage convertToTextMessage() {
    return new TextMessage(formatMessage());
  }

  @Override
  public String toString() {
    return formatMessage();
  }

  private String formatMessage() {
    return "{\"type\": \"" + getType() + "\"," +
            "\"ts\": \"" + getTimestamp() + "\"," +
            "\"channel\": \"" + getChannel() + "\"," +
            "\"user\": \"" + getUser() + "\"," +
            "\"text\": \"" + getPayload() + "\"}";
  }
}
