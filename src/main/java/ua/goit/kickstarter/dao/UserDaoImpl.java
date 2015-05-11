package ua.goit.kickstarter.dao;

import ua.goit.kickstarter.factory.ConnectionPool;
import ua.goit.kickstarter.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

  public UserDaoImpl(Connection connection) {
    super(connection);
  }

  @Override
  public User getById(Integer id) {
    User user;
    String sqlSelect = "SELECT * FROM users WHERE id = " + id + ";";
    try {
      ResultSet rs = executeQuery(sqlSelect);
      if (rs.next()) {

        String login = rs.getString("login");
        String password = rs.getString("password");
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String email = rs.getString("email");
        user = new User(id, login, password, firstName, lastName, email);
      } else {
        user = null;
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return user;
  }

  @Override
  public List<User> getAll() {
    return null;
  }

  @Override
  public User add(User element) {

    int userId;
    User newUser = null;
    String sqlInsert = "INSERT INTO users (login,password,firstName, lastName, email ) VALUES ( ?,?,?,?,? )";
    Connection con = ConnectionPool.getConnection();
    try {
      PreparedStatement statement = con.prepareStatement(sqlInsert);
      statement.setString(1, element.getLogin());
      statement.setString(2, element.getPassword());
      statement.setString(3, element.getFirstName());
      statement.setString(4, element.getLastName());
      statement.setString(5, element.getEmail());

      int affectedRows = statement.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Creating user failed, no rows affected.");
      }
      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          userId = generatedKeys.getInt(1);
          newUser = getById(userId);
        } else {
          throw new SQLException("Creating user failed, no ID obtained.");
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return newUser;
  }


  @Override
  public void deleteById(Integer id) {

  }

  @Override
  public User update(User element) {
    return null;
  }
}
