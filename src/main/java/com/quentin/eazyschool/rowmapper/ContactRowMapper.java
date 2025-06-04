package com.quentin.eazyschool.rowmapper;

import com.quentin.eazyschool.model.Contact;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ContactRowMapper implements RowMapper<Contact> {
    @Override
    public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contact contact = new Contact();
        contact.setContactId(rs.getInt("contact_id"));
        contact.setName(rs.getString("name"));
        contact.setEmail(rs.getString("email"));
        contact.setMobileNum(rs.getString("mobile_num"));
        contact.setMessage(rs.getString("message"));
        contact.setSubject(rs.getString("subject"));
        contact.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        contact.setCreatedBy(rs.getString("created_by"));

        if (null != rs.getTimestamp("updated_at")) {
            contact.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }
        contact.setUpdatedBy(rs.getString("updated_by"));

        return contact;
    }
}
