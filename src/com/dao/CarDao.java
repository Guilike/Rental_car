package com.dao;

import com.entity.Car;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @User:桂炯
 * @Date:2022-11-23
 * @Time:19:06
 * @Description:
 */
@Repository
public interface CarDao {
    List<Car> getCarAll();
    List<Car> getCarOut();
    List<Car> getCarLike(Map<String, Object> map);
    List<Car> getCarByAddress(Map<String, Object> address);
    Car getCarByNum(String number);
    List<String> getCarType();
    List<String> getCarBrand();
    List<String> getCarColor();
    List<String> getCarAddress();
    int add(Car car);
    int update(Car car);
    int delete(int id);
    int delIds(List<String> ids);
}
