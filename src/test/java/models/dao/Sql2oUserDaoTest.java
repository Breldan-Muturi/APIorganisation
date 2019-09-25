package models.dao;

import models.Department;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertEquals;

public class Sql2oUserDaoTest {
  private Connection conn;
  private Sql2oUserDao userDao;
  public Sql2oDepartmentDao departmentDao;

  private User setUpUser() {
    return new User("Turi", "Lead Tech", "Manage Teams");
  }

  private Department setupDepartment() {
    return new Department("Testing", "Ensure there are no crashes", "9-11");
  }

  private Department setupAltDepartment() {
    return new Department("DevOps", "Promote rapid deployment over several iterations", "10-15");
  }

  private Department testDepartment = setupDepartment();
  private Department altDepartment = setupAltDepartment();
  private User testUser = setUpUser();
  private User otherUser = new User("Tyra", "Lead UI", "Optimise User Experience");

  @Before
  public void setUp() throws Exception {
    String connectionString = "jdbc:h2:mem:testing;INIT = RUNSCRIPT from 'classpath:db/create.sql'";
    Sql2o sql2o = new Sql2o(connectionString, "", "");
    userDao = new Sql2oUserDao(sql2o);
    conn = sql2o.open();
  }

  @After
  public void tearDown() throws Exception {
    conn.close();
  }

  @Test
  public void add() {
    assertEquals(1, testUser.getId());
  }

  @Test
  public void getAll() {
    User testUser1 = setUpUser();
    User testUser2 = setUpUser();
    assertEquals(2, userDao.getAll().size());
  }

  @Test
  public void deleteById() {
    User testUser1 = setUpUser();
    User testUser2 = setUpUser();
    assertEquals(2, userDao.getAll().size());
    userDao.deleteById(testUser1.getId());
    assertEquals(1, userDao.getAll().size());
  }

  @Test
  public void clearAll() {
    User testUser1 = setUpUser();
    User testUser2 = setUpUser();
    userDao.clearAll();
    assertEquals(0, userDao.getAll().size());
  }

  @Test
  public void addUserToDepartmentsCorrectly() throws Exception {
    departmentDao.add(testDepartment);
    departmentDao.add(altDepartment);
    userDao.add(testUser);
    userDao.addUserToDepartment(testUser, testDepartment);
    userDao.addUserToDepartment(testUser, altDepartment);
    assertEquals(2, userDao.getAllDepartmentsForAUser(testUser.getId()).size());
  }

  @Test
  public void deletingDepartmentUpdatesJoinsTable() throws Exception {
    userDao.add(otherUser);
    departmentDao.add(setupDepartment());
    departmentDao.add(altDepartment);
    departmentDao.addDepartmentToUser(testDepartment, otherUser);
    departmentDao.addDepartmentToUser(altDepartment, otherUser);
    departmentDao.deleteById(testDepartment.getId());
    assertEquals(0, departmentDao.getAllUsersByDepartment(testDepartment.getId()).size());
  }

  @Test
  public void deletingUserUpdatesJoinsTable() throws Exception {
    departmentDao.add(testDepartment);
    userDao.add(testUser);
    userDao.add(otherUser);
    userDao.addUserToDepartment(testUser, testDepartment);
    userDao.addUserToDepartment(otherUser, testDepartment);
    userDao.deleteById(testDepartment.getId());
    assertEquals(0, userDao.getAllDepartmentsForAUser(testUser.getId()).size());
  }
}
