package com.controller;

import com.entity.Book;
import com.entity.Borrow;
import com.entity.Reader;
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

@WebServlet("/book")
public class BookServlet extends HttpServlet {
    private BookService bookService = new BookServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        String pageStr = req.getParameter("page");
        Integer page = Integer.parseInt(pageStr);
        HttpSession session = req.getSession();
        Reader reader = (Reader) session.getAttribute("reader");
        if (method == null)
            method = "findAll";
        switch (method) {
            case "findAll":
                List<Book> books = bookService.findAll(page);
                req.setAttribute("list", books);
                req.setAttribute("dataPrePage", 6);
                req.setAttribute("currentPage", page);
                req.setAttribute("pages", bookService.getPages());
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                break;
            case "addBorrow":
                String bookidStr = req.getParameter("bookid");
                Integer bookid = Integer.parseInt(bookidStr);
                //添加借书请求
                bookService.addBorrow(bookid, reader.getId());
                resp.sendRedirect("/book?method=findAllBorrow&page=1");
                break;
            case "findAllBorrow":
                //展示当前用户的所有借书记录
                List<Borrow> borrows = bookService.findAllBorrowByReaderId(reader.getId(), page);
                req.setAttribute("list", borrows);
                req.setAttribute("dataPrePage", 6);
                req.setAttribute("currentPage", page);
                req.setAttribute("pages", bookService.getBorrowedPages(reader.getId()));
                req.getRequestDispatcher("borrow.jsp").forward(req, resp);
                break;
//            case "handle":
//                //根据借书状态查询
//                break;

        }


    }
}
