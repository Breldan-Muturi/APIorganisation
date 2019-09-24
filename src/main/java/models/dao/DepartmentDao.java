package models.dao;

import models.Department;
import models.User;

import java.util.List;

public interface DepartmentDao {
  //create
  void add(Department department);
  void addDepartmentToUser(Department department, User user);

  //read
  List<Department> getAll();
  Department findById(int id);
  List <User> getAllUsersByDepartment(int departmentId);

  //update
  void update(int id, String name, String description, String size);

  //delete
  void deleteById(int id);
  void clearAll();
}
