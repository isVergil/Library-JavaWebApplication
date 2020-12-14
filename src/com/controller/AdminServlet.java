package com.controller;

import com.entity.Admin;
import com.entity.Borrow;
import com.service.BookService;
import com.service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private BookService bookService = new BookServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method == null)
            method = "findAllBorrow";
        HttpSession session = req.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        switch (method) {
            case "findAllBorrow":
                String page = req.getParameter("page");
                List<Borrow> borrowList = bookService.findAllBorrowWithoutAdmit(0, Integer.parseInt(page));
                req.setAttribute("list", borrowList);
                req.setAttribute("dataPrePage", 6);
                req.setAttribute("currentPage", page);
                req.setAttribute("pages", bookService.getBorrowedPagesByState(0));
                req.getRequestDispatcher("admin.jsp").forward(req, resp);
                break;
            case "handle":
                Integer borrowid = Integer.parseInt(req.getParameter("id"));
                Integer state = Integer.parseInt(req.getParameter("state"));
                bookService.handleBorrow(borrowid, state, admin.getId());
                if (state == 1 || state == 2)
                    resp.sendRedirect("/admin?page=1");   //借书
                if (state == 3)
                    resp.sendRedirect("/admin?method=getBorrowed&page=1");    //还书
                break;
            case "getBorrowed":
                page = req.getParameter("page");
                borrowList = bookService.findAllBorrowWithoutAdmit(1, Integer.parseInt(page));
                req.setAttribute("list", borrowList);
                req.setAttribute("dataPrePage", 6);
                req.setAttribute("currentPage", page);
                req.setAttribute("pages", bookService.getBorrowedPagesByState(1));
                req.getRequestDispatcher("return.jsp").forward(req, resp);
                break;
        }

    }
}
