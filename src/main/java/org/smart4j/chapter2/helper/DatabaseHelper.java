package org.smart4j.chapter2.helper;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.utils.CollectionUtil;
import org.smart4j.chapter2.utils.PropsUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * Created by sally on 2017/2/11.
 */
public class DatabaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
    private static final ThreadLocal<Connection> CONNECTION_HOLDER=new ThreadLocal<Connection>();
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    //查询实体，是一个list
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object ... params) {
        List<T> entityList;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return entityList;
    }
    //查询单个实体
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object ... params) {
        T entity;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return entity;
    }
    //执行查询语句（多张表查询，返回map）
    public static List<Map<String, Object>> executeQuery(String sql, Object ... params) {
        List<Map<String, Object>> result;
        try {
            Connection conn = getConnection();
            result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return result;
    }
    //执行更新语句
    public static int executeupdate(String sql, Object ... params) {
        int rows = 0;
        try {
            Connection conn = getConnection();
            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return rows;
    }
    //根据id删除实体
    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "delete from " +getTableName(entityClass) + "where id = ?";
        return executeupdate(sql,id) == 1;
    }
    //更新实体
    public static <T> boolean updateEntity(Class<T> entityClass,long id,Map<String, Object> fieldMap) {
        if(CollectionUtil.isEmpty(fieldMap.keySet())) {
            LOGGER.error("can not update entity:fieldMap is empty");
            return  false;
        }
        String sql = " update "+getTableName(entityClass) + " set ";
        StringBuilder columns = new StringBuilder();
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append("=?, ");
        }
        sql += columns.substring(0, columns.lastIndexOf(", "))+ " where id=?";
        List<Object> paramList = new ArrayList<Object>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();
        return executeupdate(sql,params) == 1;
    }
    //插入实体
    public static <T> boolean insertEntity(Class<T> entityClass,Map<String, Object> fieldMap) {
        if(CollectionUtil.isEmpty(fieldMap.keySet())) {
            LOGGER.error("can not update entity:fieldMap is empty");
            return  false;
        }
        String sql = " insert into "+getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "),values.length(),")");
        sql += columns+ " values" +values;
        Object[] params = fieldMap.values().toArray();
        return executeupdate(sql,params) == 1;

    }
    //拿到表名
    private static String getTableName(Class<?> entityClass) {
        return entityClass.getSimpleName();
    }
    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties conf = PropsUtil.loadProps("jdbc.properties");
        DRIVER=conf.getProperty("jdbc.driver");
        URL=conf.getProperty("jdbc.url");
        USERNAME=conf.getProperty("jdbc.username");
        PASSWORD=conf.getProperty("jdbc.password");
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e){
            LOGGER.error("can not load jdbc driver",e);
        }
    }
    /**
     * 获取数据库连接
     */
   public static Connection getConnection() {
       Connection conn = CONNECTION_HOLDER.get();
       if(conn==null){
           try {
               DriverManager.getConnection(URL,USERNAME,PASSWORD);
           } catch (SQLException e) {
               LOGGER.error("get connection failure",e);
               throw new RuntimeException(e);
           } finally {
               CONNECTION_HOLDER.set(conn);
           }
       }
       return conn;
   }
    /**
     * 关闭数据库连接
     */
    public static void closeConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure",e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }
}
