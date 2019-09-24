package models.dao;

import models.Department;
import models.User;

import java.util.List;

public interface UserDao {
//  CREATE
  void add(User user);
  void addUserToDepartment(User user, Department department);

//  READ
  List<User> getAll();
  List<Department> getAllDepartmentsForAUser(int id);
  User findById(int id);

//  UPDATE
//  omit for now

//  DELETE
  void deleteById(int id);
  void clearAll();
}
