package com.controller;

import com.dao.CarDao;
import com.entity.Car;
import com.entity.User;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @User:桂炯
 * @Date:2022-11-23
 * @Time:19:26
 * @Description:
 */
@Controller
@ResponseBody
@RequestMapping("/car")
public class CarController {
    @Autowired
    CarDao carDao;

    @GetMapping("/list")
    public Map<String, Object> getCarList() {
        return R.r(carDao.getCarAll(), "获取数据", 200);
    }

    @GetMapping("/outList")
    public Map<String, Object> getCarOutList() {
        return R.r(carDao.getCarOut(), "获取数据", 200);
    }

    @GetMapping("/type")
    public Map<String, Object> getCarType() {
        return R.r(carDao.getCarType(), "类型", 200);
    }

    @GetMapping("/brand")
    public Map<String, Object> getCarBrand() {
        return R.r(carDao.getCarBrand(), "品牌", 200);
    }

    @GetMapping("/color")
    public Map<String, Object> getCarColor() {
        return R.r(carDao.getCarColor(), "颜色", 200);
    }

    @GetMapping("/address")
    public Map<String, Object> getCarAddress() {
        return R.r(carDao.getCarAddress(), "城市", 200);
    }

    @GetMapping("/searchAddress")
    public Map<String, Object> searchAddress(@RequestParam("address") String address) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("address", address);
        return R.r(carDao.getCarByAddress(map), "部分城市数据", 200);
    }


    @GetMapping("/search")
    public Map<String, Object> search(@RequestParam("searchText") String searchText) {
        System.out.println(searchText);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("searchText", searchText);

        List<Car> list = carDao.getCarLike(hashMap);
        if (list.isEmpty() || searchText.isEmpty()) {
            return R.r(null, "未查询到数据", 400);
        }
        return R.r(list, "查找成功", 200);
    }

    @PostMapping("/add")
    public Map<String, Object> addCar(@RequestBody Car car) {
        Car carByNum = carDao.getCarByNum(car.getNumber());

        if (carByNum != null) {
            if (carByNum.getNumber().equals(car.getNumber())) {
                return R.r(null, "车牌号已存在", 400);
            }
        }
        car.setStatus(1);
        carDao.add(car);
        return R.r(null, "新增成功", 200);
    }


    @PostMapping("/update")
    public Map<String, Object> updateCar(@RequestBody Car car) {
        carDao.update(car);
        return R.r(null, "修改成功", 200);
    }

    @PostMapping("/delete")
    public Map<String, Object> delete(@RequestBody Car car) {
        if (carDao.delete(car.getId()) == 1) {
            return R.r(null, "删除成功", 200);
        }
        return R.r(null, "删除失败", 400);
    }

    @PostMapping("/deleteIds")
    public Map<String, Object> delIds(@RequestBody Map<String, String[]> ids) {
        List<String> o = Arrays.asList(ids.get("ids"));
        if (carDao.delIds(o) == 0) {
            return R.r(null, "删除失败", 400);
        }
        return R.r(null, "删除成功", 200);
    }

    @GetMapping("/listBynum")
    public Map<String, Object> listBynum(@RequestParam("number") String number) {
        return R.r(carDao.getCarByNum(number), "", 200);
    }
}
