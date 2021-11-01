package com.example.usermanagement.dao;

import com.example.usermanagement.model.User;
import com.example.usermanagement.utils.JDBCUtils;

import javax.swing.plaf.IconUIResource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String INSERT_USER_SQL = "INSERT INTO users " +
            "(name, email, country)" +
            "VALUES (?, ?, ?);";
    private static final String SELECT_USER_BY_ID = "SELECT id,name,email,country FROM users WHERE id =?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String DELETE_USERS_SQL = "DELETE FROM users WHERE id = ?;";
    private static final String UPDATE_USERS_SQL = "UPDATE users SET name = ?,email= ?, country =? WHERE id = ?;";

    public UserDAO(){}

    public void insertUser(User user) throws SQLException{
        System.out.println(INSERT_USER_SQL);
        try(Connection conn = JDBCUtils.getConnection();
            PreparedStatement prepStatement = conn.prepareStatement(INSERT_USER_SQL);){
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public User selectUser(int id){
        User user = null;

        // Establish a Connection
        try(Connection conn = JDBCUtils.getConnection();
            // Create a statement using connection object
            PreparedStatement prepStatement = conn.prepareStatement(SELECT_USER_BY_ID);){
            prepStatement.setInt(1, id);
            System.out.println(prepStatement);

            // Execute the query
            ResultSet resultSet = prepStatement.executeQuery();

            // Process the ResultSet object
            while(resultSet.next()){
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String country = resultSet.getString("country");
                user = new User(id, name, email, country);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    public List<User> selectAllUsers(){
        List<User> users = new ArrayList<>();
        try(Connection conn = JDBCUtils.getConnection();
            PreparedStatement prepStatement = conn.prepareStatement(SELECT_ALL_USERS);){
            System.out.println(prepStatement);
            ResultSet resultSet = prepStatement.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String country = resultSet.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch(SQLException e){
            JDBCUtils.printSQLException(e);
        }
        return users;
    }

    public boolean deleteUser(int id) throws SQLException{
        boolean rowDeleted;
        try(Connection conn = JDBCUtils.getConnection();
            PreparedStatement prepStatement = conn.prepareStatement(DELETE_USERS_SQL);
        ){
            prepStatement.setInt(1, id);
            rowDeleted = prepStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateUser(User user) throws SQLException{
        boolean rowUpdated;
        try(Connection conn = JDBCUtils.getConnection();
            PreparedStatement prepStatement = conn.prepareStatement(UPDATE_USERS_SQL);){
            prepStatement.setString(1, user.getName());
            prepStatement.setString(2, user.getEmail());
            prepStatement.setString(3, user.getCountry());
            prepStatement.setInt(4, user.getId());

            rowUpdated = prepStatement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

}
