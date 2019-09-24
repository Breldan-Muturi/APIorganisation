package models.dao;

import models.Depnews;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oDepnewsDao implements DepnewsDao{
  private final Sql2o sql2o;
  public Sql2oDepnewsDao(Sql2o sql2o){ this.sql2o = sql2o; }

  @Override
  public void add(Depnews depnews) {
    String sql = "INSERT INTO depnews(content, writtenBy, rating, departmentId) VALUES (:content, :writtenBy, :rating, :departmentId)";
    try (Connection con = sql2o.open()){
      int id = (int) con.createQuery(sql, true)
        .bind(depnews)
        .executeUpdate()
        .getKey();
      depnews.setId(id);
    } catch (Sql2oException ex){
      System.out.println(ex);
    }

  }

  @Override
  public List<Depnews> getAll() {
    try(Connection con = sql2o.open()){
      return con.createQuery("SELECT * FROM depnews")
        .executeAndFetch(Depnews.class);
    }
  }

  @Override
  public List<Depnews> getAllDepnewsByDepartment(int departmentId) {
    try (Connection con = sql2o.open()) {
      return con.createQuery("SELECT * FROM depnews WHERE departmentId = :departmentId")
        .addParameter("departmentId", departmentId)
        .executeAndFetch(Depnews.class);
    }
  }

  @Override
  public List<Depnews> getAllDepnewsByDepartmentSortedNewestToOldest(int departmentId) {
    List<Depnews> unsortedDepnews = getAllDepnewsByDepartment(departmentId);
    List<Depnews> sortedDepnews = new ArrayList<>();
    int i = 1;
    for(Depnews depnews : unsortedDepnews){
      int comparisonResult;
      if( i == unsortedDepnews.size()){
        if(depnews.compareTo(unsortedDepnews.get(i-1))== -1){
          sortedDepnews.add(0, unsortedDepnews.get(i-1));
        }
        break;
      }
      else {
        if(depnews.compareTo(unsortedDepnews.get(i)) == -1){
          sortedDepnews.add(0, unsortedDepnews.get(i));
          i++;
        } else if(depnews.compareTo(unsortedDepnews.get(i))==0){
          sortedDepnews.add(0,unsortedDepnews.get(i));
          i++;
        } else {
          sortedDepnews.add(0,unsortedDepnews.get(i));
          i++;
        }
      }
    }
    return sortedDepnews;
  }

  @Override
  public void deleteById(int id) {
      String sql = "DELETE from depnews WHERE id=:id";
      try (Connection con = sql2o.open()) {
        con.createQuery(sql)
          .addParameter("id", id)
          .executeUpdate();
      } catch (Sql2oException ex) {
        System.out.println(ex);
      }

  }

  @Override
  public void clearAll() {
      String sql = "DELETE from depnews";
      try (Connection con = sql2o.open()) {
        con.createQuery(sql).executeUpdate();
      } catch (Sql2oException ex) {
        System.out.println(ex);
      }

  }
}
