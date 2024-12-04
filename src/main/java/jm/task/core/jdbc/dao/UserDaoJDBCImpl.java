package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

            String sql = "CREATE TABLE IF NOT EXISTS USERS (`id` BIGINT AUTO_INCREMENT PRIMARY KEY, `name` VARCHAR(50), `lastName` VARCHAR(50), `age` TINYINT);";

            try (Statement statement = connection.createStatement()) {
                statement.execute(sql);
            } catch (Exception e) {
            System.out.println("Connection failed");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS USERS");
        } catch (SQLException e) {
            System.out.println("Connection failed");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO USERS (name, lastName, age) VALUES (?, ?, ?)")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Connection failed");
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USERS WHERE id = ?")) {
            preparedStatement.executeUpdate("DELETE FROM USERS WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Connection failed");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
            String sql = "SELECT * FROM USERS";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeQuery(sql);
                ResultSet resultSet = preparedStatement.getResultSet();

                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setAge(resultSet.getByte("age"));
                    users.add(user);
                }
            } catch (Exception e) {
                System.out.println("Connection failed");
            }
        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE TABLE USERS")) {
            preparedStatement.executeUpdate("TRUNCATE TABLE USERS");
        } catch (Exception e) {
            System.out.println("Connection failed");
        }
    }
}
