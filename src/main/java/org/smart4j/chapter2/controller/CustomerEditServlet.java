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


@WebServlet("/customer_edit")
public class CustomerEditServlet extends HttpServlet {

    /**
     * 编辑客户的界面，Get
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // todo

    }

    /**
     * 处理编辑客户的请求，Post
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // todo

    }

}
