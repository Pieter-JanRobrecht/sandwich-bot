package be.pj.sandwichbot.slack.builders;

import be.pj.sandwichbot.model.Sandwich;

public class SandwichBuilder {
  private Sandwich model;

  public SandwichBuilder() {
    model = new Sandwich();
  }

  public SandwichBuilder name(String name){
    model.setName(name);

    return this;
  }

  public SandwichBuilder contents(String contents) {
    model.setContents(contents);

    return this;
  }

  public Sandwich build(){
    return model;
  }

  public SandwichBuilder simple() {
    model.setName("Simple sandwich");
    model.setContents("Ham, Kaas");
    return this;
  }
}
