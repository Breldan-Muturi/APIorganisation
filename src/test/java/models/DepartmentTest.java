package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class DepartmentTest {
  private Department testDepartment = setupDepartment();
//  private Department otherDepartment =setupAltDepartment();
  public Department setupDepartment (){
    return new Department("Ai", "214 NE Broadway", "97232");
  }

//  public Department setupAltDepartment (){
//    return new Department("Fish Witch", "214 NE Broadway", "97232", "503-402-9874");
//  }


  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void getName() {
    assertEquals("Fish Witch", testDepartment.getName());
  }

  @Test
  public void setName() {
    testDepartment.setName("UI/UX");
    assertEquals("UI/UX",testDepartment.getName());
  }

  @Test
  public void getDescription() { assertEquals("214 NE Broadway", testDepartment.getDescription());
  }

  @Test
  public void setDescription() {
    testDepartment.setDescription("Optimise user Experience");
    assertEquals("Optimise user Experience", testDepartment.getDescription());
  }

  @Test
  public void getSize() { assertEquals("97232", testDepartment.getSize());
  }

  @Test
  public void setSize() {
    testDepartment.setSize("20-25");
    assertEquals("20-25",testDepartment.getSize());
  }

  @Test
  public void setId() {
    testDepartment.setId(7);
    assertEquals(7,testDepartment.getId());
  }
}
