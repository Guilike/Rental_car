package com.controller;

import com.dao.CarDao;
import com.dao.CheckDao;
import com.dao.IndentDao;
import com.entity.Car;
import com.entity.Check;
import com.entity.Indent;
import com.utils.R;
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
 * @Date:2022-11-29
 * @Time:11:41
 * @Description:
 */
@Controller
@ResponseBody
@RequestMapping("/check")
public class CheckController {
    @Autowired
    CarDao carDao;
    @Autowired
    CheckDao checkDao;
    @Autowired
    IndentDao indentDao;

    @GetMapping("/list")
    public Map<String, Object> getCheckList() {
        return R.r(checkDao.getCheckAll(),"获取数据",200);
    }


    @PostMapping("/add")
    public Map<String, Object> addCheck(@RequestBody Check check) {
        //将订单信息状态更改成检查中
        Indent indent = indentDao.getByOrderId(check.getOrder_id());
        indent.setStatus(2);
        indentDao.update(indent);

        String str = "I" + LocalDateTime.now();
        String id = str.replace("-", "").replace(":", "").replace(".", "");
        check.setCheck_id(id);
        check.setTime(LocalDateTime.now());
//        System.out.println(check);
        checkDao.add(check);
        return R.r(null, "检查单创建成功", 200);
    }

    @PostMapping("/delete")
    public Map<String, Object> delete(@RequestBody Check check) {
        checkDao.delete(check.getCheck_id());
        return R.r(null, "删除成功", 200);
    }

    @PostMapping("/deleteIds")
    public Map<String, Object> delIds(@RequestBody Map<String, String[]> ids) {
        List<String> o = Arrays.asList(ids.get("ids"));
        for (String s : o) {
            System.out.println(s);
        }
        if (checkDao.delIds(o) == 0) {
            return R.r(null, "删除失败", 400);
        }
        return R.r(null, "删除成功", 200);
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody Check check) {

        Indent indent = indentDao.getByOrderId(check.getOrder_id());
        Car car = carDao.getCarByNum(indent.getNumber());
        if (indent.getStatus() == 2) {
            indent.setStatus(3);

            //计算还车差价以及赔付金额
            int days = IndentController.days(indent.getEnd_time(), check.getTime());
            System.out.println(days + "天");
            System.out.println(car.getOut_price() * days);
            double total = car.getOut_price() * days + check.getPay() + indent.getPrice();
            indent.setPrice(total);

            car.setStatus(1);
            carDao.update(car);
            indentDao.update(indent);
            checkDao.update(check);
            return R.r(null, "检查完成", 200);
        }
//        System.out.println(check.getPay());
//        System.out.println(checkDao.getCheckByOrderId(check.getOrder_id()).getPay());
        if (!check.getPay().equals(checkDao.getCheckByOrderId(check.getOrder_id()).getPay())) {
            indent.setPrice(indent.getPrice() + check.getPay() - checkDao.getCheckByOrderId(check.getOrder_id()).getPay());
            indentDao.update(indent);
        }
        checkDao.update(check);
        return R.r(null, "更新成功", 200);
    }

    @GetMapping("/search")
    public Map<String, Object> search(@RequestParam("type") String type, @RequestParam("searchText") String searchText) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("searchText", searchText);
        List<Check> list = checkDao.searchCheck(map);
        if (list.isEmpty() || searchText.isEmpty() || type.isEmpty()) {
            return R.r(null, "未查询到数据", 400);
        }
        return R.r(list, "查找成功", 200);
    }

    @GetMapping("/listByOrderId")
    public Map<String, Object> listByOrderId(@RequestParam("orderid") String orderid) {
        return R.r(checkDao.getCheckByOrderId(orderid), "", 200);
    }
}
