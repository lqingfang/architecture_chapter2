package org.smart4j.chapter2.service;


import org.smart4j.chapter2.model.Customer;

import java.util.List;
import java.util.Map;

/**
 * Created by huxiaoxiang on 2016/11/13.
 */

/**
 * 接口：客户服务
 */
public interface CustomerService {

    /**
     * 获取客户列表
     * @param keyword
     * @return
     */
    public List<Customer> getCustomerList();

    /**
     * 根据id获取客户
     * @param id
     * @return
     */
    public Customer getCustomer(long id);

    /**
     * 创建客户
     * @param fieldMap
     * @return
     */
    public boolean createCustomer(Map<String, Object> fieldMap);

    /**
     * 更新客户
     * @param id
     * @param fieldMap
     * @return
     */
    public boolean updateCustomer(long id, Map<String, Object> fieldMap);

    /**
     * 删除客户
     * @param id
     * @return
     */
    public boolean deleteCustomer(long id);


}
