package com.controller;

import com.dao.CarDao;
import com.dao.IndentDao;
import com.dao.UserDao;
import com.entity.Car;
import com.entity.Indent;
import com.entity.User;
import com.utils.R;
import net.sf.cglib.core.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @User:桂炯
 * @Date:2022-11-25
 * @Time:15:04
 * @Description:
 */
@Controller
@ResponseBody
@RequestMapping("/indent")
public class IndentController {
    @Autowired
    IndentDao indentDao;
    @Autowired
    CarDao carDao;


    @GetMapping("/list")
    public Map<String, Object> getIndentList() {
        List<Indent> list = indentDao.getIndentAll();
        return R.r(list, "获取数据", 200);
    }

    @GetMapping("/userList")
    public Map<String, Object> getIndentUser(@RequestParam("identity") String identity) {
        return R.r(indentDao.getIndentUser(identity), "", 200);
    }

    @PostMapping("/price")
    public Map<String, Object> price(@RequestBody Indent indent) {
        Car car = carDao.getCarByNum(indent.getNumber());
        indent.setPrice(days(indent.getStart_time(), indent.getEnd_time()) * car.getOut_price());
        HashMap<String, Object> map = new HashMap<>();
        map.put("days", days(indent.getStart_time(), indent.getEnd_time()));
        map.put("price", indent.getPrice());
        return R.r(map, "确认价格", 200);
    }

    @PostMapping("/add")
    public Map<String, Object> addIndent(@RequestBody Indent indent) {
        List<Indent> user = indentDao.getIndentUser(indent.getIdentity());
        for (Indent i : user) {
//            System.out.println(i);
            if (i.getStatus() == 1) {
                return R.r(null, "已有车辆租赁中", 400);
            }
        }
        Car car = carDao.getCarByNum(indent.getNumber());
        if (car.getStatus() == 2) {
            return R.r(null, "该车辆已出租", 400);
        }
        car.setStatus(2);
        carDao.update(car);

//        days(indent.getStart_time(), indent.getEnd_time())
        indent.setPrice(days(indent.getStart_time(), indent.getEnd_time()) * car.getOut_price());

        String str = "I" + LocalDateTime.now();
        String id = str.replace("-", "").replace(":", "").replace(".", "");
        indent.setOrder_id(id);
        indent.setStatus(1);
        indent.setTime(LocalDateTime.now());
        System.out.println(indent);
        indentDao.add(indent);
        return R.r(null, "租赁成功", 200);
    }

    @PostMapping("/delete")
    public Map<String, Object> delete(@RequestBody Indent indent) {
        indentDao.delete(indent.getOrder_id());
        return R.r(null, "删除成功", 200);
    }

    @PostMapping("/deleteIds")
    public Map<String, Object> delIds(@RequestBody Map<String, String[]> ids) {
        List<String> o = Arrays.asList(ids.get("ids"));
        for (String s : o) {
            System.out.println(s);
        }
        if (indentDao.delIds(o) == 0) {
            return R.r(null, "删除失败", 400);
        }
        return R.r(null, "删除成功", 200);
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody Indent indent) {
        Car car = carDao.getCarByNum(indent.getNumber());
        if (indent.getStatus() == 1) {
            System.out.println("1");
            indent.setPrice(days(indent.getStart_time(), indent.getEnd_time()) * car.getOut_price());
            indentDao.update(indent);
            return R.r(null, "已续约", 200);
        }
        if (indent.getStatus() == 3) {
//            System.out.println("33");
            car.setStatus(1);
            carDao.update(car);
            indentDao.update(indent);
            return R.r(null, "订单完成", 200);
        }
//        System.out.println("777");
        indentDao.update(indent);
        return R.r(null, "修改成功", 200);
    }

    @PostMapping("/cancel")
    public Map<String, Object> canel(@RequestBody Indent indent) {
        Car car = carDao.getCarByNum(indent.getNumber());
        long minu = indent.getTime().until(LocalDateTime.now(), ChronoUnit.MINUTES);
        System.out.println(minu);
        if (minu > 20) {
            //20分钟内可以还车
            return R.r(null, "已超过取消订单时间", 400);
        }
        car.setStatus(1);
        carDao.update(car);
        indent.setStatus(4);
        indentDao.update(indent);
        return R.r(null, "已取消", 200);
    }

    @GetMapping("/search")
    public Map<String, Object> search(@RequestParam("type") String type, @RequestParam("searchText") String searchText, @RequestParam("timevalue") String timevalue) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("searchText", searchText);
        map.put("timevalue", timevalue);
        List<Indent> list = indentDao.searchIndent(map);
        if (list.isEmpty() || searchText.isEmpty()) {
            return R.r(null, "未查询到数据", 400);
        }
        return R.r(list, "查找成功", 200);
    }

    public static int days(LocalDateTime start, LocalDateTime end) {
        long minutes = start.until(end, ChronoUnit.MINUTES);
        int day = 0;
        if (minutes / 1440 == 0) {
            day += 1;
        } else {
            if (minutes % 1440 == 0) {
                day += minutes / 1440;
            } else {
                day += minutes / 1440 + 1;
            }
        }
        return day;
    }

}
