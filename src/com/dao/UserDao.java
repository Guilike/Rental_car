package com.dao;

import com.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @User:桂炯
 * @Date:2022-11-17
 * @Time:16:35
 * @Description:
 */
@Repository
public interface UserDao {
   /**
    * @登录处理
    */
   User loginCheck(User user);
   List<User> getUserKey(User user);
   User getByUsername(String username);
   int addUser(User user);
   int updateToken(User user);

   /**
    * @客户信息
    */
   List<User> getCustomerInfo();
   List<User> searchCustomer(Map<String, Object> map);
   User getCustomerById(int id);
   User getCustomerByIdentity(String identity);
   int addCustomer(User user);
   int updateCustomer(User user);

   /**
    * @账号管理
    */
   List<User> getAllList();
   int add(User user);
   int delById(int id);
   int delIds (List<String> ids);
   int update(User user);
   int resetPwd(Map<String, Object> map);
   List<User> search(Map<String, Object> map);

   /**
    * @个人信息
    */
   int updatePersonInfo(User user);
   int updatePersonPwd(Map<String, String> map);
}
