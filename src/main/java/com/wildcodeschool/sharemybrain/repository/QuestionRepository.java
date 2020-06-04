package com.wildcodeschool.sharemybrain.repository;

import com.wildcodeschool.sharemybrain.entity.Question;
import com.wildcodeschool.sharemybrain.util.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class QuestionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Question> findWithLimit(int limit, int offset, Boolean newest) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            if (newest) {
                statement = connection.prepareStatement(
                        "SELECT * FROM question ORDER BY `date` DESC LIMIT ?,?;"
                );
            } else {
                statement = connection.prepareStatement(
                        "SELECT * FROM question ORDER BY `date` ASC LIMIT ?,?;"
                );
            }

            statement.setInt(2, limit);
            statement.setInt(1, offset);
            resultSet = statement.executeQuery();

            List<Question> questions = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id_question");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Date date = resultSet.getDate("date");
                int idUser = resultSet.getInt("id_user");
                int idSkill = resultSet.getInt("id_skill");

                questions.add(new Question(id, title, description, idUser, idSkill, date));
            }
            return questions;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

    public int totalLines(int idSkill) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            if (idSkill == -1) {
                statement = connection.prepareStatement(
                        "SELECT COUNT(*) as count FROM question;"
                );
            } else {
                statement = connection.prepareStatement(
                        "SELECT COUNT(*) as count FROM question where id_skill = ?;"
                );
                statement.setInt(1, idSkill);
            }
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return 0;
    }

    public List<Question> findWithSkill(int limit, int offset, int idSkill, Boolean newest) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            if (newest) {
                statement = connection.prepareStatement(
                        "SELECT * FROM question  WHERE id_skill = ? ORDER BY `date` DESC LIMIT ?,?;"
                );
            } else {
                statement = connection.prepareStatement(
                        "SELECT * FROM question  WHERE id_skill = ? ORDER BY `date` ASC LIMIT ?,?;"
                );
            }

            statement.setInt(3, limit);
            statement.setInt(2, offset);
            statement.setInt(1, idSkill);
            resultSet = statement.executeQuery();

            List<Question> questions = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id_question");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Date date = resultSet.getDate("date");
                int idUser = resultSet.getInt("id_user");
                questions.add(new Question(id, title, description, idUser, idSkill, date));
            }
            return questions;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

    public Question findQuestion(int idQuestion) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            statement = connection.prepareStatement(
                    "SELECT * FROM question  WHERE id_question = ?;"
            );
            statement.setInt(1, idQuestion);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Date date = resultSet.getDate("date");
                int idUser = resultSet.getInt("id_user");
                int idSkill = resultSet.getInt("id_skill");

                Question question = new Question(idQuestion, title, description, idUser, idSkill, date);

                return question;
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

    public void askQuestion(String question_title, String question, String date, int idUser, int idSkill) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            statement = connection.prepareStatement(
                    "INSERT INTO question (title, description, `date`, id_user, id_skill) VALUES (?, ?, ?, ?, ?);"
            );
            statement.setString(1, question_title);
            statement.setString(2, question);
            statement.setString(3, date);
            statement.setInt(4, idUser);
            statement.setInt(5, idSkill);

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


    public List<Question> findWithUserId(int idUser) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            statement = connection.prepareStatement(
                    "SELECT * FROM question WHERE id_user = ?;"
            );
            statement.setInt(1, idUser);
            resultSet = statement.executeQuery();

            List<Question> questions = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id_question");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Date date = resultSet.getDate("date");
                int idSkill = resultSet.getInt("id_skill");
                questions.add(new Question(id, title, description, idUser, idSkill, date));
            }
            return questions;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

    public List<Question> findQuestionsAnsweredByUserId(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            statement = connection.prepareStatement(
                    "SELECT DISTINCT question.id_question as question, question.title as title, question.description as descript, question.id_user as user FROM answer " +
                            "JOIN question ON answer.id_question = question.id_question " +
                            "WHERE answer.id_user = ?;"
            );
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            List<Question> questions = new ArrayList<>();
            while (resultSet.next()) {
                int idQuestion = resultSet.getInt("question");
                String title = resultSet.getString("title");
                String description = resultSet.getString("descript");
                int idUser = resultSet.getInt("user");
                questions.add(new Question(idQuestion, title, description, idUser));
            }
            return questions;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

    public List<Question> search(int limit, int offset, String word, boolean newest) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            if (newest) {
                statement = connection.prepareStatement(
                        "SELECT * FROM question " +
                                "JOIN skill ON question.id_skill = skill.id_skill " +
                                "WHERE title LIKE ? OR description LIKE ? OR skill.name LIKE ? " +
                                "ORDER BY `date` DESC " +
                                "LIMIT ?,?;"
                );
            } else {
                statement = connection.prepareStatement(
                        "SELECT * FROM question " +
                                "JOIN skill ON question.id_skill = skill.id_skill " +
                                "WHERE title LIKE ? OR description LIKE ? OR skill.name LIKE ? " +
                                "ORDER BY `date` ASC " +
                                "LIMIT ?,?;"
                );
            }

            word = "%" + word + "%";
            statement.setString(1, word);
            statement.setString(2, word);
            statement.setString(3, word);
            statement.setInt(5, limit);
            statement.setInt(4, offset);
            resultSet = statement.executeQuery();

            List<Question> questions = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id_question");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Date date = resultSet.getDate("date");
                int idUser = resultSet.getInt("id_user");
                int idSkill = resultSet.getInt("id_skill");

                questions.add(new Question(id, title, description, idUser, idSkill, date));
            }
            return questions;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

    public int totalSearch(String word) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            statement = connection.prepareStatement(
                    "SELECT COUNT(*) as count FROM question WHERE title LIKE ? OR description LIKE ?;"
            );
            word = "%" + word + "%";
            statement.setString(1, word);
            statement.setString(2, word);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return 0;
    }
}