package com.dao;

import com.entity.Indent;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @User:桂炯
 * @Date:2022-11-25
 * @Time:14:35
 * @Description:
 */
@Repository
public interface IndentDao {
    List<Indent> getIndentAll();
    List<Indent> getIndentUser(String identity);
    Indent getByOrderId(String id);
    int add(Indent indent);
    int update(Indent indent);
    int delete(String id);
    int delIds(List<String> ids);
    List<Indent> searchIndent(Map<String, Object> map);
}
