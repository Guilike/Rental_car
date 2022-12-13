package com.dao;

import com.entity.Check;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @User:桂炯
 * @Date:2022-11-29
 * @Time:11:41
 * @Description:
 */
@Repository
public interface CheckDao {
    List<Check> getCheckAll();
    Check getCheckByOrderId(String id);
    int add(Check check);
    int delete(String id);
    int delIds(List<String> ids);
    int update(Check check);
    List<Check> searchCheck(Map<String, Object> map);
}
