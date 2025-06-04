package com.quentin.eazyschool.repository;

import com.quentin.eazyschool.model.Contact;
import com.quentin.eazyschool.rowmapper.ContactRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ContactRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ContactRowMapper contactRowMapper;

    @Autowired
    public ContactRepository(JdbcTemplate jdbcTemplate, ContactRowMapper contactRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.contactRowMapper = contactRowMapper;
    }

    public int save(Contact contact) {
        String sql = "insert into contact_msg (name, mobile_num, email, message, subject, status," +
         " created_at, created_by) values (?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, contact.getName(), contact.getMobileNum(), contact.getEmail(), contact.getMessage(),
                contact.getSubject(), contact.getStatus(), contact.getCreatedAt(), contact.getCreatedBy());
    }


    public List<Contact> findAllWithStatusOpen() {
        String sql = "select * from contact_msg where status = 'OPEN'";
        return jdbcTemplate.query(sql, contactRowMapper);
    }

    public int updateStatus(Integer contactId, String updatedBy, String status) {
        String sql = "update contact_msg set status = ?, updated_at = ?, updated_by = ? where contact_id = ?";
        return jdbcTemplate.update(sql, ps -> {
            ps.setString(1, status);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(3, updatedBy);
            ps.setInt(4, contactId);
        });
    }
}
