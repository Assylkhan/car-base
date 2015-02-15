package com.epam.entity;

public class Driver extends Employee {
    /**
     * order which did not complete currently for a given client. Basically Driver can have many orders, but currently
     *  only one. Order is considered as 'currently' when its status equals to 'not completed'
     */
    private Order currentOrder;
    private boolean available;
    private String currentLocation;
    private String govNumber;
    private String carModel;
    private CarClass carClass;
    private String carState;
    private String carImage;

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public String getGovNumber() {
        return govNumber;
    }

    public void setGovNumber(String govNumber) {
        this.govNumber = govNumber;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public CarClass getCarClass() {
        return carClass;
    }

    public void setCarClass(CarClass carClass) {
        this.carClass = carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = CarClass.valueOf(carClass);
    }

    public String getCarState() {
        return carState;
    }

    public void setCarState(String carState) {
        this.carState = carState;
    }

    public String getCarImage() {
        return carImage;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Driver driver = (Driver) o;

        if (available != driver.available) return false;
        if (carClass != driver.carClass) return false;
        if (carImage != null ? !carImage.equals(driver.carImage) : driver.carImage != null) return false;
        if (carModel != null ? !carModel.equals(driver.carModel) : driver.carModel != null) return false;
        if (carState != null ? !carState.equals(driver.carState) : driver.carState != null) return false;
        if (currentLocation != null ? !currentLocation.equals(driver.currentLocation) : driver.currentLocation != null)
            return false;
        if (currentOrder != null ? !currentOrder.equals(driver.currentOrder) : driver.currentOrder != null)
            return false;
        if (govNumber != null ? !govNumber.equals(driver.govNumber) : driver.govNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = currentOrder != null ? currentOrder.hashCode() : 0;
        result = 31 * result + (available ? 1 : 0);
        result = 31 * result + (currentLocation != null ? currentLocation.hashCode() : 0);
        result = 31 * result + (govNumber != null ? govNumber.hashCode() : 0);
        result = 31 * result + (carModel != null ? carModel.hashCode() : 0);
        result = 31 * result + (carClass != null ? carClass.hashCode() : 0);
        result = 31 * result + (carState != null ? carState.hashCode() : 0);
        result = 31 * result + (carImage != null ? carImage.hashCode() : 0);
        return result;
    }
}
