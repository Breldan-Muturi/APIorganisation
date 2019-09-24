package models;

import java.util.Objects;

public class Department {
  private int id;
  private String name;
  private String description;
  private String size;

  public Department(String name, String description, String size) {
    this.name = name;
    this.description = description;
    this.size = size;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Department that = (Department) o;
    return id == that.id &&
      Objects.equals(name, that.name) &&
      Objects.equals(description, that.description) &&
      Objects.equals(size, that.size);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, size);
  }
}
