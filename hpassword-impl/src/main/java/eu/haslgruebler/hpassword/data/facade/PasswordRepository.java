package eu.haslgruebler.hpassword.data.facade;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import eu.haslgruebler.hpassword.data.UserDAO;

@Repository
@Transactional
public class PasswordRepository {

    private DataSource dataSource;

    public PasswordRepository(DataSource dataSource, String passwordGetQuery, String passwordUpdateQuery) {
        this.dataSource = dataSource;
    }

    public UserDAO get(String email) {
        try {
            PreparedStatement ps = dataSource.getConnection().prepareStatement("SELECT email, password FROM users WHERE email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UserDAO(rs.getString("email"), rs.getString("password"), true);
            }
            return null;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public void save(final UserDAO user) {
        if (!user.isCrypted()) {
            throw new IllegalArgumentException("password of user is not crypted");
        }
        try {
            PreparedStatement ps = dataSource.getConnection().prepareStatement("UPDATE users SET password=? WHERE email=?");
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getEmail());
            ps.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
