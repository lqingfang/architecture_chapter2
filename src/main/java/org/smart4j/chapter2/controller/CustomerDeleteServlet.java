package org.smart4j.chapter2.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by huxiaoxiang on 2016/11/13.
 */

@WebServlet("/customer_delete")
public class CustomerDeleteServlet extends HttpServlet {

    /**
     * 删除客户界面，Get
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // todo

    }

    /**
     * 处理删除客户请求，Post
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // todo

    }

}
