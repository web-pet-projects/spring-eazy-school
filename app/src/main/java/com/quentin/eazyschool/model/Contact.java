package com.quentin.eazyschool.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "contact_msg")
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//
//@SqlResultSetMappings({
//        @SqlResultSetMapping(name = "SqlResultSetMapping.count", columns = @ColumnResult(name = "cnt"))
//})
//@NamedQueries({
//        @NamedQuery(name = "Contact.findOpenMsgs",
//                query = "SELECT c FROM Contact c WHERE c.status = :status"),
//        @NamedQuery(name = "Contact.updateMsgStatus",
//                query = "UPDATE Contact c SET c.status = ?1 WHERE c.contactId = ?2")
//})
//@NamedNativeQueries({
//        @NamedNativeQuery(name = "Contact.findOpenMsgsNative",
//                query = "SELECT * FROM contact_msg c WHERE c.status = :status"
//                ,resultClass = Contact.class),
//        @NamedNativeQuery(name = "Contact.findOpenMsgsNative.count",
//                query = "select count(*) as cnt from contact_msg c where c.status = :status",
//                resultSetMapping = "SqlResultSetMapping.count"),
//        /*Spring Data JPA doesn’t support dynamic sorting for native queries.
//        Doing that would require Spring Data to analyze the provided statement and generate
//        the ORDER BY clause in the database-specific dialect. This would be a very complex operation
//        and is currently not supported by Spring Data JPA.*/
//        @NamedNativeQuery(name = "Contact.updateMsgStatusNative",
//                query = "UPDATE contact_msg c SET c.status = ?1 WHERE c.contact_id = ?2")
//})
public class Contact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Integer id;
    private String name;
    private String mobileNum;
    private String email;
    private String message;
    private String subject;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        OPEN, CLOSE
    }
}
