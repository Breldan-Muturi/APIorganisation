package models.dao;

import models.Department;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oUserDao implements UserDao {
  private final Sql2o sql2o;
  private Sql2oUserDao (Sql2o sql2o) { this.sql2o = sql2o; }

  @Override
  public void add(User user){
    String sql = "INSERT INTO users (name, title, role) VALUES (:name, :title, :role)";
    try (Connection con = sql2o.open()){
      int id = (int)con.createQuery(sql,true)
        .bind(user)
        .executeUpdate()
        .getKey();
      user.setId(id);
    } catch (Sql2oException ex){
      System.out.println(ex);
    }
  }

  @Override
  public List<User> getAll() {
    try (Connection con = sql2o.open()){
      return con.createQuery("SELECT * FROM users")
        .executeAndFetch(User.class);
    }
  }

  @Override
  public void deleteById(int id){
    String sql = "DELETE from users WHERE id = :id";
    try (Connection con = sql2o.open()){
      con.createQuery(sql)
        .addParameter("id",id)
        .executeUpdate();
    } catch (Sql2oException ex){
      System.out.println(ex);
    }
  }

  @Override
  public void clearAll() {
    String sql = "DELETE from users";
    try (Connection con = sql2o.open()) {
    con.createQuery(sql)
      .executeUpdate();
    } catch (Sql2oException ex){
      System.out.println(ex);
    }
  }

  @Override
  public void addUserToDepartment(User user, Department department) {
    String sql = "INSERT INTO departments_users(departmentid ,userid) VALUES (:departmentid,:userid)";
    try (Connection con = sql2o.open()) {
      con.createQuery(sql)
        .addParameter("departmentId",department.getId())
        .addParameter("userId", user.getId())
        .executeUpdate();
    } catch (Sql2oException ex){
      System.out.println(ex);
    }
  }

  @Override
  public List<Department> getAllDepartmentsForAUser(int userId) {
    List<Department> departments = new ArrayList();
    String joinQuery="SELECT departmentid FROM departments_users WHERE userid = :userid";
    try (Connection con = sql2o.open()) {
      List<Integer> allDepartmentsIds = con.createQuery(joinQuery)
        .addParameter("userId",userId)
        .executeAndFetch(Integer.class);
      for(Integer departmentId:allDepartmentsIds){
        String departmentQuery = "SELECT * FROM departments WHERE id = departmentId";
        departments.add(
          con.createQuery(departmentQuery)
          .addParameter("departmentId", departmentId)
          .executeAndFetchFirst(Department.class));
      }
    }catch (Sql2oException ex){
      System.out.println(ex);
    }
    return departments;
  }


}
