package ua.goit.kickstarter.dao;

import org.joda.time.DateTime;
import ua.goit.kickstarter.factory.ConnectionPool;
import ua.goit.kickstarter.factory.Factory;
import ua.goit.kickstarter.model.BlogPost;
import ua.goit.kickstarter.model.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlogPostDaoImpl extends AbstractDao<BlogPost>
        implements BlogPostDao {

  public BlogPostDaoImpl(Connection connection) {
    super(connection);
  }

  @Override
  public BlogPost getById(Integer id) {
    BlogPost blogPost;
    Project project;
    ProjectDao projectDao = new ProjectDaoImpl(connection);
    String sqlSelect = "SELECT * FROM blogs WHERE id = " + id + ";";
    ResultSet rs;
    try {
      rs = executeQuery(sqlSelect);
      if (rs.next()) {
        String title = rs.getString("title");
        String text = rs.getString("text");
        long date = rs.getLong("dateOfCreation");
        DateTime dateOfCreation = new DateTime(date);
        Integer id_project = rs.getInt("id_project");
        project = projectDao.getById(id_project);
        blogPost = new BlogPost(id, title, text, dateOfCreation,
                project);
      } else {
        blogPost = null;
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return blogPost;
  }

  @Override
  public List<BlogPost> getByProjectId(Integer projectID) {
    List<BlogPost> blogPostList = new ArrayList<>();
    BlogPost blogPost;
    Project project;
    ProjectDao projectDao = new ProjectDaoImpl(connection);
    String sqlSelect = "SELECT * FROM blogs WHERE id_project = " +
            projectID + ";";
    try {
      ResultSet rs = executeQuery(sqlSelect);
      while (rs.next()) {
        Integer id = rs.getInt("id");
        String title = rs.getString("title");
        String text = rs.getString("text");
        Long date = rs.getLong("dateOfCreation");
        DateTime dateOfCreation = new DateTime(date);
        Integer id_project = rs.getInt("id_project");
        project = projectDao.getById(id_project);
        blogPost = new BlogPost(id, title, text, dateOfCreation, project);
        blogPostList.add(blogPost);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return blogPostList;
  }

  @Override
  public List<BlogPost> getByProject(Project project) {
    List<BlogPost> blogPostList = new ArrayList<>();
    BlogPost blogPost;
    ProjectDao projectDao = new ProjectDaoImpl(connection);
    Integer projectID = project.getId();
    String sqlSelect = "SELECT * FROM blogs WHERE id_project = " +
            projectID + ";";
    Connection connection = ConnectionPool.getConnection();
    try {
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery(sqlSelect);
      while (rs.next()) {
        Integer id = rs.getInt("id");
        String title = rs.getString("title");
        String text = rs.getString("text");
        Long date = rs.getLong("dateOfCreation");
        DateTime dateOfCreation = new DateTime(date);
        Integer id_project = rs.getInt("id_project");
        project = projectDao.getById(id_project);
        blogPost = new BlogPost(id, title, text, dateOfCreation, project);
        blogPostList.add(blogPost);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return blogPostList;
  }

  @Override
  public List<BlogPost> getAll() {
    Project project;
    BlogPost blogPost;
    ProjectDao projectDao = new ProjectDaoImpl(connection);
    List<BlogPost> blogPostList = new ArrayList<>();
    String sqlQuery = "SELECT * FROM blogs;";
    ResultSet rs;
    try {
      rs = executeQuery(sqlQuery);
      while (rs.next()) {
        Integer id = rs.getInt("id");
        String title = rs.getString("title");
        String text = rs.getString("text");
        Long date = rs.getLong("dateOfCreation");
        DateTime dateOfCreation = new DateTime(date);
        Integer id_project = rs.getInt("id_project");
        project = projectDao.getById(id_project);
        blogPost = new BlogPost(id, title, text, dateOfCreation, project);
        blogPostList.add(blogPost);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return blogPostList;
  }

  @Override
  public BlogPost add(String title, String text, Integer projectID) {
    String sqlInsert = "INSERT INTO blogs (title, text, " +
            "dateOfCreation, id_project) VALUES ( ?,?,?,? );";
    Integer id;
    Project project;
    Connection con = ConnectionPool.getConnection();
    ProjectDao projectDao = Factory.getProjectDao(con);
    DateTime dateOfCreation = new DateTime();
    try {
      PreparedStatement statement = con.prepareStatement(sqlInsert);
      statement.setString(1, title);
      statement.setString(2, text);
      statement.setLong(3, dateOfCreation.getMillis());
      statement.setInt(4, projectID);
      int affectedRows = statement.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Creating user failed, no rows affected.");
      }
      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          id = generatedKeys.getInt(1);
        } else {
          throw new SQLException("Creating user failed, no ID obtained.");
        }
      }
      project = projectDao.getById(projectID);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return new BlogPost(id, title, text, dateOfCreation, project);
  }

  @Override
  public BlogPost add(BlogPost element) {
    return add(element.getTitle(), element.getText(),
            element.getProject().getId());
  }

  @Override
  public void deleteById(Integer id) {
    String query = "DELETE FROM blogs WHERE id = " + id + ";";
    executeUpdate(query);
  }

  @Override
  public BlogPost update(BlogPost element) {
    return null;
  }
}
