package com.service;

import com.entity.Book;
import com.entity.Borrow;

import java.util.List;

public interface BookService {
    public List<Book> findAll(int page);

    public int getPages();

    public int getBorrowedPages(Integer readid);

    public int getBorrowedPagesByState(Integer state);

    public void addBorrow(Integer bookid, Integer readid);

    public List<Borrow> findAllBorrowByReaderId(Integer id, Integer page);

    public List<Borrow> findAllBorrowWithoutAdmit(Integer state, Integer page);

    public void handleBorrow(Integer borrowId, Integer state, Integer adminId);

}
