package io.github.hisenz.mysqlview.mysqlview.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class DataSourceInfo implements Serializable {
    private static final long serialVersionUID = 7758214534259611362L;

    private Integer id;

    private String name;

    private String url;

    private String driver;

    private String username;

    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataSourceInfo that = (DataSourceInfo) o;
        return url.equals(that.getUrl()) && driver.equals(that.getDriver()) && username.equals(that.getUsername()) && password.equals(that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, driver, username, password);
    }
}