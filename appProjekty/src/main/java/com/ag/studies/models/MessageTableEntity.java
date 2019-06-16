package com.ag.studies.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"create_date"},
        allowGetters = true)
@Table(name = "message_table", schema = "projekty")
public class MessageTableEntity {
    private Long messageId;
    private Long creatorId;
    private Long parentMessageId;
    private String subject;
    private String messageBody;
    private Timestamp createDate;
    private Short isRead;
    private UserTableEntity userTableByCreatorId;
    private MessageTableEntity messageTableByParentMessageId;

    @Id
    @Column(name = "message_id", nullable = false)
    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    @Basic
    @Column(name = "creator_id", nullable = false)
    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    @Basic
    @Column(name = "parent_message_id", nullable = true)
    public Long getParentMessageId() {
        return parentMessageId;
    }

    public void setParentMessageId(Long parentMessageId) {
        this.parentMessageId = parentMessageId;
    }

    @Basic
    @Column(name = "subject", nullable = false, length = 200)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    @Column(name = "message_body", nullable = false, length = -1)
    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    @Basic
    @CreatedDate
    @Column(name = "create_date", nullable = false)
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "is_read", nullable = false)
    public Short getIsRead() {
        return isRead;
    }

    public void setIsRead(Short isRead) {
        this.isRead = isRead;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageTableEntity that = (MessageTableEntity) o;
        return Objects.equals(messageId, that.messageId) &&
                Objects.equals(creatorId, that.creatorId) &&
                Objects.equals(parentMessageId, that.parentMessageId) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(messageBody, that.messageBody) &&
                Objects.equals(createDate, that.createDate)&&
                Objects.equals(isRead, that.isRead);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, creatorId, parentMessageId, subject, messageBody, createDate, isRead);
    }

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "user_id", nullable = false, insertable=false, updatable=false)
    public UserTableEntity getUserTableByCreatorId() {
        return userTableByCreatorId;
    }

    public void setUserTableByCreatorId(UserTableEntity userTableByCreatorId) {
        this.userTableByCreatorId = userTableByCreatorId;
    }

    @OneToOne
    @JoinColumn(name = "parent_message_id", referencedColumnName = "message_id", insertable=false, updatable=false)
    public MessageTableEntity getMessageTableByParentMessageId() {
        return messageTableByParentMessageId;
    }

    public void setMessageTableByParentMessageId(MessageTableEntity messageTableByParentMessageId) {
        this.messageTableByParentMessageId = messageTableByParentMessageId;
    }
}
