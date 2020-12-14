package com.controller;

import com.entity.Admin;
import com.entity.Book;
import com.entity.Reader;
import com.service.BookService;
import com.service.LoginService;
import com.service.impl.BookServiceImpl;
import com.service.impl.LoginServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private LoginService loginService = new LoginServiceImpl();
    private BookService bookService = new BookServiceImpl();

    /**
     * 处理登录的业务逻辑
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String type = req.getParameter("type");
        Object object = loginService.login(username, password, type);
        if (object != null) {
            HttpSession session = req.getSession();
            switch (type) {
                case "reader":
                    Reader reader = (Reader) object;
                    session.setAttribute("reader", reader);
                    resp.sendRedirect("/book?page=1");
                    break;
                case "admin":
                    Admin admin = (Admin) object;
                    session.setAttribute("admin", admin);
                    //跳转 Admin 首页
                    break;
            }
        } else {
            resp.sendRedirect("login.jsp");
        }
    }
}
