package models.dao;

import models.Depnews;

import java.util.List;

public interface DepnewsDao {
  //  create
  void add(Depnews depnews);

  //  read
  List<Depnews> getAll();
  List<Depnews> getAllDepnewsByDepartment(int departmentId);
  List<Depnews> getAllDepnewsByDepartmentSortedNewestToOldest(int departmentId);

//  update
//  omit for now

  //  delete
  void deleteById(int id);
  void clearAll();

}
