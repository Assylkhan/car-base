package com.epam.entity;

import java.math.BigDecimal;

public class Client extends User {
    /**
     * order which did not complete currently for a given client. Basically Client can have many orders, but currently
     * only one. Order is considered as 'currently' when its status equals to 'not completed'
     */
    private Order currentOrder;
    /**
     * client has bill, which will increase per trip
     */
    private BigDecimal bill;

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public BigDecimal getBill() {
        return bill;
    }

    public void setBill(BigDecimal bill) {
        this.bill = bill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        if (!super.equals(o)) return false;

        Client client = (Client) o;

        if (bill != null ? !bill.equals(client.bill) : client.bill != null) return false;
        if (currentOrder != null ? !currentOrder.equals(client.currentOrder) : client.currentOrder != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (currentOrder != null ? currentOrder.hashCode() : 0);
        result = 31 * result + (bill != null ? bill.hashCode() : 0);
        return result;
    }
}
