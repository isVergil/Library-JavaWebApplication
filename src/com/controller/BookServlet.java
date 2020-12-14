package com.controller;

import com.entity.Book;
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
        if (method == null)
            method = "findAll";
        switch (method) {
            case "findAll":
                String pageStr = req.getParameter("page");
                Integer page = Integer.parseInt(pageStr);
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
                HttpSession session = req.getSession();
                Reader reader = (Reader) session.getAttribute("reader");
                bookService.addBorrow(bookid, reader.getId());
                break;

        }


    }
}
