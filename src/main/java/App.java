import static spark.Spark.*;

import com.google.gson.Gson;
import exceptions.ApiException;
import models.Department;
import models.Depnews;
import models.User;
import models.dao.Sql2oDepartmentDao;
import models.dao.Sql2oDepnewsDao;
import models.dao.Sql2oUserDao;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.staticFileLocation;

public class App {
  public static void main(String[] args) {
    Sql2oDepartmentDao departmentDao;
    Sql2oUserDao userDao;
    Sql2oDepnewsDao depnewsDao;
    Connection conn;
    Gson gson = new Gson();

    staticFileLocation("/public");
    String connectionString ="jdbc:h2:~/APIorganisation.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
    Sql2o sql2o = new Sql2o(connectionString ,"","");

    departmentDao = new Sql2oDepartmentDao(sql2o);
    userDao = new Sql2oUserDao(sql2o);
    depnewsDao = new Sql2oDepnewsDao(sql2o);
    conn = sql2o.open();

    //CREATE
    post("/departments/:departmentId/user/:userId","application/json", (req,res) -> {
      int departmentId = Integer.parseInt(req.params("departmentId"));
      int userId = Integer.parseInt(req.params("userId"));
      Department department = departmentDao.findById(departmentId);
      User user = userDao.findById(userId);

      if(department != null && user != null){
        //both exist and can be associated
        userDao.addUserToDepartment(user,department);
        res.status(201);
        return gson.toJson(String.format("Department '%s' and User '%s' have been associated", user.getName(),department.getName()));
      } else {
        throw new ApiException(404,String.format("Department or User does not exist"));
      }
    });
    get("/users/:id/departments", "application/json",(req,res)-> {
      int userId = Integer.parseInt(req.params("id"));
      User userToFind = userDao.findById(userId);
      if(userToFind == null){
        throw new ApiException(404,String.format("No user with the id: \"%s\" exists", req.params("id")));
      } else if (userDao.getAllDepartmentsForAUser(userId).size()==0){
        return "{\"message\":\"I'm sorry, but no departments are listed for this user.\"}";
      } else {
        return gson.toJson(userDao.getAllDepartmentsForAUser(userId));
      }
    });

    post("/departments/:departmentId/depnews/new","application/json", (req,res) ->{
      int departmentId = Integer.parseInt(req.params("departmentId"));
      Depnews depnews = gson.fromJson(req.body(),Depnews.class);
      depnews.setCreatedat();
      depnews.setFormattedCreatedAt();
      depnews.setDepartmentId(departmentId);
      depnewsDao.add(depnews);
      res.status(201);
      return gson.toJson(depnews);
    });

    post("/users/new", "application/json",(req,res) -> {
      User user = gson.fromJson(req.body(),User.class);
      userDao.add(user);
      res.status(201);
      return gson.toJson(user);
    });

    //READ
    get("/departments","application/json", (req,res) ->{
      System.out.println(departmentDao.getAll());

      if(departmentDao.getAll().size()> 0){
        return gson.toJson(departmentDao.getAll());
      } else {
        return "{\"message\":\"I'm sorry , but no departments are currently listed in the database.\"}";
      }
    });

    get("/departments/:id","application/json",(req,res) -> {
      int departmentId = Integer.parseInt(req.params("id"));
      Department departmentToFind = departmentDao.findById(departmentId);
      if(departmentToFind == null){
        throw new ApiException(404, String.format("No department with the id:\"%s\" exists",req.params("id")));
      }
      return gson.toJson(departmentToFind);
    });

    get("/departments/:id/depnews","application/json", (req,res) -> {
      int departmentId = Integer.parseInt(req.params("id"));
      Department departmentToFind = departmentDao.findById(departmentId);
      List<Depnews> allDepnews;
      if(departmentToFind == null){
        throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
      }
      allDepnews = depnewsDao.getAllDepnewsByDepartment(departmentId);
      return gson.toJson(allDepnews);
    });

    get("/users", "application/json", (req,res) -> {
      return gson.toJson(userDao.getAll());
    });

    //CREATE
    post("/departments/new", "application/json", (req, res) -> {
      Department department = gson.fromJson(req.body(),Department.class);
      departmentDao.add(department);
      res.status(201);
      return gson.toJson(department);
    });

    //FILTERS
    exception(ApiException.class, (exception, req, res) -> {
      ApiException err = exception;
      Map<String, Object> jsonMap = new HashMap<>();
      jsonMap.put("status",err.getStatusCode());
      jsonMap.put("errorMessage",err.getMessage());
      res.type("application/json");
      res.status(err.getStatusCode());
      res.body(gson.toJson(jsonMap));
    });

    after((req,res) -> {
      res.type("application/json");
    });

  }
}
