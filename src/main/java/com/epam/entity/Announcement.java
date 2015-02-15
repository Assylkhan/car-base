package com.epam.entity;

public class Announcement extends BaseEntity {
    private String body;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Announcement)) return false;
        if (!super.equals(o)) return false;

        Announcement announcement = (Announcement) o;

        if (body != null ? !body.equals(announcement.body) : announcement.body != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
