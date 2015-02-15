package com.epam.entity;

public abstract class Employee extends User {
    /**
     * national id of citizen KZ
     */
    private String nationalId;

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        if (!super.equals(o)) return false;

        Employee employee = (Employee) o;

        if (nationalId != null ? !nationalId.equals(employee.nationalId) : employee.nationalId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (nationalId != null ? nationalId.hashCode() : 0);
        return result;
    }
}
