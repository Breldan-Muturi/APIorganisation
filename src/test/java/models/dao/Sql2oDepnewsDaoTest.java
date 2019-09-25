package models.dao;

import models.Department;
import models.Depnews;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertEquals;

public class Sql2oDepnewsDaoTest {
  private Connection conn;
  private Sql2oDepnewsDao depnewsDao;
  private Sql2oDepartmentDao departmentDao;

  public Depnews setupDepnews() {
    Depnews depnews = new Depnews("great", "Kim", 4, 555);
    depnewsDao.add(depnews);
    return depnews;
  }

  public Department setupDepartment() {
    Department department = new Department("UI/UX", "Optimize User Experience", "10-15");
    departmentDao.add(department);
    return department;
  }

  public Depnews setupDepnewsForDepartment(Department department) {
    Depnews depnews = new Depnews("great", "Kim", 4, department.getId());
    depnewsDao.add(depnews);
    return depnews;
  }

  Depnews testDepnews = setupDepnews();
  Depnews otherDepnews = setupDepnews();
  Department otherDepartment = setupDepartment();
  Department testDepartment = setupDepartment();

  @Before
  public void setUp() throws Exception {
    String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
    Sql2o sql2o = new Sql2o(connectionString, "", "");
    depnewsDao = new Sql2oDepnewsDao(sql2o);
//    departmentDao = new Sql2oDepartmentDao(sql2o);
    conn = sql2o.open();
  }

  @After
  public void tearDown() throws Exception {
    conn.close();
  }

  @Test
  public void add() {
    Depnews testDepnews = setupDepnews();
    assertEquals(1, testDepnews.getId());
  }

  @Test
  public void getAll() {
    Depnews depnews1 = setupDepnews();
    Depnews depnews2 = setupDepnews();
    assertEquals(1, depnewsDao.getAll().size());
  }

  @Test
  public void getAllDepnewsByDepartment() {
    Depnews depnews1 = setupDepnewsForDepartment(testDepartment);
    Depnews depnews2 = setupDepnewsForDepartment(testDepartment);
    Depnews depnewsForOtherDepartment = setupDepnewsForDepartment(otherDepartment);
    assertEquals(2, depnewsDao.getAllDepnewsByDepartment(testDepartment.getId()).size());
  }


  @Test
  public void deleteById() {
    Depnews testDepnews = setupDepnews();
    Depnews otherDepnews = setupDepnews();
    assertEquals(2, depnewsDao.getAll().size());
    depnewsDao.deleteById(testDepnews.getId());
    assertEquals(1, depnewsDao.getAll().size());
  }

  @Test
  public void clearAll() {
    Depnews testDepnews = setupDepnews();
    Depnews otherDepnews = setupDepnews();
    depnewsDao.clearAll();
    assertEquals(0, depnewsDao.getAll().size());
  }

  @Test
  public void timeStampIsReturnedCorrectly() throws Exception {
    departmentDao.add(testDepartment);
    Depnews testDepnews = new Depnews("foodcoma!", "Captain Kirk", 3, testDepartment.getId());
    depnewsDao.add(testDepnews);
    long creationTime = testDepnews.getCreatedat();
    long savedTime = depnewsDao.getAll().get(0).getCreatedat();
    String formattedCreationTime = testDepnews.getFormattedCreatedAt();
    String formattedSaveTime = depnewsDao.getAll().get(0).getFormattedCreatedAt();
    assertEquals(formattedCreationTime, formattedSaveTime);
    assertEquals(creationTime, savedTime);
  }

  @Test
  public void depnewsAreReturnedInCorrectOrder() throws Exception {
    departmentDao.add(testDepartment);
    Depnews testDepnews = new Depnews("foodcoma!", "Captain Kirk", 3, testDepartment.getId());
    depnewsDao.add(testDepnews);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
    Depnews testSecondDepnews = new Depnews("passable", "Mr. Spock", 1, testDepartment.getId());
    depnewsDao.add(testSecondDepnews);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
    Depnews testThirdDepnews = new Depnews("Bloody good grub", "Scotty", 10, testDepartment.getId());
    depnewsDao.add(testThirdDepnews);
    assertEquals(3, depnewsDao.getAllDepnewsByDepartment(testDepartment.getId()).size());
    assertEquals("Bloody good grub", depnewsDao.getAllDepnewsByDepartmentSortedNewestToOldest(testDepartment.getId()).get(0).getContent());
  }
}
