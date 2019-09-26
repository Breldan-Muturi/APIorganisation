package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class DepnewsTest {

  public Depnews setupDepnews (){
    return new Depnews("Great service", "Kim", 4, 1);
  }
  private Depnews testDepnews = setupDepnews();


  @Test
  public void getcontent() {
    assertEquals("Great service", testDepnews.getContent());
  }

  @Test
  public void setcontent() {
    testDepnews.setContent("No free dessert :(");
    assertNotEquals("Great service", testDepnews.getContent());
  }

  @Test
  public void getWrittenBy() {
    assertEquals("Kim", testDepnews.getWrittenBy());
  }

  @Test
  public void setWrittenBy() {
    testDepnews.setWrittenBy("Mike");
    assertNotEquals("Kim", testDepnews.getWrittenBy());
  }

  @Test
  public void getRating() {
    assertEquals(4, testDepnews.getRating());
  }

  @Test
  public void setRating() {
    testDepnews.setRating(1);
    assertNotEquals(4, testDepnews.getRating());
  }

  @Test
  public void getId() {
  }

  @Test
  public void setId() {
    testDepnews.setId(5);
    assertEquals(5, testDepnews.getId());
  }

  @Test
  public void getDepartmentId() {
    assertEquals(1, testDepnews.getDepartmentId());
  }

  @Test
  public void setDepartmentId() {
    testDepnews.setDepartmentId(10);
    assertNotEquals(1, testDepnews.getDepartmentId());
  }
}
