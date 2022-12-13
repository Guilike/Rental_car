package com.entity;

/**
 * @User:桂炯
 * @Date:2022-11-23
 * @Time:17:31
 * @Description:
 */
public class Car {
    private int id;
    private String number;
    private String type;
    private String version;
    private String brand;
    private String color;
    private Double in_price;
    private Double out_price;
    private String address;
    private String description;
    private int status;
    private String image;

    public Car() {
    }

    public Car(int id, String number, String type, String version, String brand, String color, Double in_price, Double out_price, String address, String description, int status, String image) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.version = version;
        this.brand = brand;
        this.color = color;
        this.in_price = in_price;
        this.out_price = out_price;
        this.address = address;
        this.description = description;
        this.status = status;
        this.image = image;
    }

    public Car(String number, String type, String version, String brand, String color, Double in_price, Double out_price, String address, String description, int status, String image) {
        this.number = number;
        this.type = type;
        this.version = version;
        this.brand = brand;
        this.color = color;
        this.in_price = in_price;
        this.out_price = out_price;
        this.address = address;
        this.description = description;
        this.status = status;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getIn_price() {
        return in_price;
    }

    public void setIn_price(Double in_price) {
        this.in_price = in_price;
    }

    public Double getOut_price() {
        return out_price;
    }

    public void setOut_price(Double out_price) {
        this.out_price = out_price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", version='" + version + '\'' +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", in_price=" + in_price +
                ", out_price=" + out_price +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", image='" + image + '\'' +
                '}';
    }
}
