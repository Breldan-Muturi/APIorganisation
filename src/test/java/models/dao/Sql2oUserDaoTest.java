package models.dao;

import org.junit.After;
import org.junit.Before;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oUserDaoTest {
  private Connection conn;
  private Sql2oUserDao userDao;


  @Before
  public void setUp() throws Exception {
    String connectionString = "jdbc:h2:mem:testing;INIT = RUNSCRIPT from 'classpath:db/create.sql'";
    Sql2o sql2o = new Sql2o(connectionString, "","");
    userDao = new Sql2oUserDao(sql2o);
    conn = sql2o.open();
  }

  @After
  public void tearDown() throws Exception { conn.close(); }
}
