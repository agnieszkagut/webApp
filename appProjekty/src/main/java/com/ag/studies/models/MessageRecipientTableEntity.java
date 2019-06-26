package com.ag.studies.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "message_recipient_table", schema = "projekty")
public class MessageRecipientTableEntity {
    private Long messageRecipientId;
    private Long recipientId;
    private Long userGroupId;
    private Long messageId;
    private UserTableEntity userTableByRecipientId;
    private MessageTableEntity messageTableByMessageId;

    @Id
    @GeneratedValue
    @Column(name = "message_recipient_id", nullable = false)
    public Long getMessageRecipientId() {
        return messageRecipientId;
    }

    public void setMessageRecipientId(Long messageRecipientId) {
        this.messageRecipientId = messageRecipientId;
    }

    @Basic
    @Column(name = "recipient_id", nullable = false)
    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    @Basic
    @Column(name = "user_group_id", nullable = true)
    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    @Basic
    @Column(name = "message_id", nullable = false)
    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageRecipientTableEntity that = (MessageRecipientTableEntity) o;
        return Objects.equals(messageRecipientId, that.messageRecipientId) &&
                Objects.equals(recipientId, that.recipientId) &&
                Objects.equals(userGroupId, that.userGroupId) &&
                Objects.equals(messageId, that.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageRecipientId, recipientId, userGroupId, messageId);
    }

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "user_id", nullable = false, insertable=false, updatable=false)
    public UserTableEntity getUserTableByRecipientId() {
        return userTableByRecipientId;
    }

    public void setUserTableByRecipientId(UserTableEntity userTableByRecipientId) {
        this.userTableByRecipientId = userTableByRecipientId;
    }

    @ManyToOne
    @JoinColumn(name = "message_id", referencedColumnName = "message_id", nullable = false, insertable=false, updatable=false)
    public MessageTableEntity getMessageTableByMessageId() {
        return messageTableByMessageId;
    }

    public void setMessageTableByMessageId(MessageTableEntity messageTableByMessageId) {
        this.messageTableByMessageId = messageTableByMessageId;
    }
}
