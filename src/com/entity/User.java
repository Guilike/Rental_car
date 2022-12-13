package com.entity;

/**
 * @User:桂炯
 * @Date:2022-11-17
 * @Time:16:09
 * @Description:
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String sex;
    private String identity;
    private String icon;
    private int role;
    private String token;

    public User(String username, String password, String name, String phone, String sex, String identity, String icon, int role, String token) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.sex = sex;
        this.identity = identity;
        this.icon = icon;
        this.role = role;
        this.token = token;
    }

    public User(int id, String username, String password, String name, String phone,String sex, String identity, String icon, int role, String token) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.sex = sex;
        this.identity = identity;
        this.icon = icon;
        this.role = role;
        this.token = token;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", identity='" + identity + '\'' +
                ", icon='" + icon + '\'' +
                ", role=" + role +
                ", token='" + token + '\'' +
                '}';
    }
}
