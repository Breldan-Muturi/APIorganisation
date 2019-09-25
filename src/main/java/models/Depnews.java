package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Depnews implements Comparable<Depnews>{
  private int id;
  private String content;
  private String writtenBy;
  private int rating;
  private int departmentId;
  private long createdat;
  private String formattedCreatedAt;

  public Depnews(String content, String writtenBy, int rating, int departmentId) {
    this.content = content;
    this.writtenBy = writtenBy;
    this.rating = rating;
    this.departmentId = departmentId;
    this.createdat = System.currentTimeMillis();
    setFormattedCreatedAt();
  }

  @Override
  public int compareTo(Depnews depnewsObject){
    if(this.createdat < depnewsObject.createdat){
      return -1;
    }
    else if(this.createdat > depnewsObject.createdat){
      return 1;
    }
    else{
      return 0;
    }
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getWrittenBy() {
    return writtenBy;
  }

  public void setWrittenBy(String writtenBy) {
    this.writtenBy = writtenBy;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public int getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(int departmentId) {
    this.departmentId = departmentId;
  }

  public long getCreatedat() {
    return createdat;
  }

  public void setCreatedat() {
    this.createdat = System.currentTimeMillis();
  }

  public String getFormattedCreatedAt() {
    Date date = new Date(createdat);
    String datePatternToUse = "MM/dd/yyyy @ K:mm a";
    SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
    return sdf.format(date);
  }

  public void setFormattedCreatedAt() {
    Date date = new Date (this.createdat);
    String datePatterToUse = "MM/dd/yyyy @ k:mm a";
    SimpleDateFormat sdf = new SimpleDateFormat(datePatterToUse);
    this.formattedCreatedAt = sdf.format(date);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Depnews depnews = (Depnews) o;
    return id == depnews.id &&
      rating == depnews.rating &&
      departmentId == depnews.departmentId &&
      createdat == depnews.createdat &&
      Objects.equals(content, depnews.content) &&
      Objects.equals(writtenBy, depnews.writtenBy) &&
      Objects.equals(formattedCreatedAt, depnews.formattedCreatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, content, writtenBy, rating, departmentId, createdat, formattedCreatedAt);
  }
}
