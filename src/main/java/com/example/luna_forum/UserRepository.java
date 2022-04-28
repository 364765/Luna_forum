package com.example.luna_forum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate db;

    Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private String encryptPassword(String password) {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder(14);
        String hashedPassword = bCrypt.encode(password);
        return hashedPassword;
    }

    private boolean checkPassword(String password, String hashPassword) {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        if (bCrypt.matches(hashPassword, password)) {
            return true;
        }
        return false;
    }

    public boolean checkUsername(User user) {
        String sql = "SELECT username FROM User WHERE username =?";
        return db.query(sql, new BeanPropertyRowMapper(User.class), user.getUsername()).isEmpty();

    }

    public boolean saveUser(User user) {
        if (!checkUsername(user)) {
            return false;
        }
        String sql = "INSERT INTO User (username, password) VALUES(?,?)";
        try {
            db.update(sql, user.getUsername(), encryptPassword(user.getPassword()));
            return true;
        } catch (Exception e) {
            logger.error("Error in saveUser: " + e);
            return false;
        }
    }

    public boolean checkUsernameAndPassword(User user) {
        String sql = "SELECT password FROM User WHERE username = ?";
        try {
            String password = db.queryForObject(sql, String.class, user.getUsername());
            return checkPassword(password, user.getPassword());
        } catch (Exception e) {
            return false;
        }
    }


    public boolean encryptAllPasswords() {
        String sql = "SELECT * FROM User";
        String encryptedPassword;
        try {
            List<User> allUsers = db.query(sql, new BeanPropertyRowMapper(User.class));
            for (User b : allUsers) {

                encryptedPassword = encryptPassword(b.getPassword());

                sql = "UPDATE Bruker SET passord=? where id=?";
                db.update(sql, encryptedPassword, b.getId());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
