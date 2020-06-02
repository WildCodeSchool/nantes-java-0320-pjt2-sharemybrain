package com.wildcodeschool.sharemybrain.repository;

import com.wildcodeschool.sharemybrain.entity.User;
import com.wildcodeschool.sharemybrain.util.JdbcSingleton;
import com.wildcodeschool.sharemybrain.util.JdbcUtils;

import java.sql.*;

public class UserRepository {

    public boolean findAnyUsername(String userName) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcSingleton.getInstance().getConnection();
            statement = connection.prepareStatement(
                    "SELECT * FROM user WHERE username = ?"
            );
            statement.setString(1, userName);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return false;
    }

    public boolean findUsernamePsw(String psw, String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcSingleton.getInstance().getConnection();
            statement = connection.prepareStatement(
                    "SELECT * FROM user WHERE  password = ? AND username = ?;"
            );
            statement.setString(1, psw);
            statement.setString(2, username);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return false;
    }

    public boolean findAnyEmail(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcSingleton.getInstance().getConnection();
            statement = connection.prepareStatement(
                    "SELECT * FROM user WHERE email = ?"
            );
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return false;
    }

    public void insertNewUser(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcSingleton.getInstance().getConnection();
            statement = connection.prepareStatement(
                    "INSERT INTO user (username, email, password, id_avatar, id_skill) VALUES (?, ?, ?, ?, ?);"
            );
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getMail());
            statement.setString(3, user.getPwd());
            statement.setInt(4, user.getIdAvatar());
            statement.setInt(5, user.getIdSkill());

            if (statement.executeUpdate() != 1) {
                throw new SQLException("failed to insert data");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
    }

    public int findSkill(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcSingleton.getInstance().getConnection();
            statement = connection.prepareStatement(
                    "SELECT id_skill FROM user WHERE username = ?;"
            );
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int idSkill = resultSet.getInt("id_skill");
                return idSkill;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return -1;
    }
    public int findUserId(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcSingleton.getInstance().getConnection();
            statement = connection.prepareStatement(
                    "SELECT id_user FROM user WHERE username = ?;"
            );
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int idUser = resultSet.getInt("id_user");
                return idUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return -1;
    }

    public int findAvatar(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcSingleton.getInstance().getConnection();
            statement = connection.prepareStatement(
                    "SELECT id_avatar FROM user WHERE username = ?;"
            );
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int idSkill = resultSet.getInt("id_avatar");
                return idSkill;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return -1;
    }

    public int findAvatarById(int idUser) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcSingleton.getInstance().getConnection();
            statement = connection.prepareStatement(
                    "SELECT id_avatar FROM user WHERE id_user = ?;"
            );
            statement.setInt(1, idUser);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int idSkill = resultSet.getInt("id_avatar");
                return idSkill;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return -1;
    }

    public int findUserName(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcSingleton.getInstance().getConnection();
            statement = connection.prepareStatement(
                    "SELECT username FROM user WHERE id_user = ?;"
            );
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int idUser = resultSet.getInt("id_user");
                return idUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return -1;
    }
    public String findUserNameWithIdAnswer(int answerId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcSingleton.getInstance().getConnection();
            statement = connection.prepareStatement(
                    "SELECT username FROM answer JOIN user ON answer.id_user = user.id_user WHERE id_answer = ?;"
            );
            statement.setInt(1, answerId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String answerUserName = resultSet.getString("username");
                return answerUserName;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }
    public String findUserNameWithIQuestion(int questionId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcSingleton.getInstance().getConnection();
            statement = connection.prepareStatement(
                    "SELECT username FROM question JOIN user ON question.id_user = user.id_user WHERE id_question = ?;"
            );
            statement.setInt(1, questionId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String questionUserName = resultSet.getString("username");
                return questionUserName;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

    public int updatePsw(String username, String newPsw) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcSingleton.getInstance().getConnection();
            statement = connection.prepareStatement(
                    "UPDATE user SET password = ? WHERE username = ?;"
            );
            statement.setString(1, newPsw);
            statement.setString(2, username);

            if (statement.executeUpdate() != 1) {
                throw new SQLException("failed to update data");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return -1;
    }
}
