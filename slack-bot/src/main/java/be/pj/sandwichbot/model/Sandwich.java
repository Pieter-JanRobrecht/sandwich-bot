package be.pj.sandwichbot.model;

import javax.persistence.*;

@Entity
@Table(name = "sandwich")
public class Sandwich {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "sandwich_name")
  private String name;

  private String contents;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContents() {
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  @Override
  public String toString() {
    return "Sandwich{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", contents='" + contents + '\'' +
            '}';
  }
}
