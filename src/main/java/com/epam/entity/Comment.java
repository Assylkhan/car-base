package com.epam.entity;

import java.sql.Timestamp;

public class Comment extends BaseEntity {
    Client client;
    String body;
    Timestamp leaveDate;

    public Timestamp getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Timestamp leaveDate) {
        this.leaveDate = leaveDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        if (!super.equals(o)) return false;

        Comment comment = (Comment) o;

        if (body != null ? !body.equals(comment.body) : comment.body != null) return false;
        if (client != null ? !client.equals(comment.client) : comment.client != null) return false;
        if (leaveDate != null ? !leaveDate.equals(comment.leaveDate) : comment.leaveDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (leaveDate != null ? leaveDate.hashCode() : 0);
        return result;
    }
}
