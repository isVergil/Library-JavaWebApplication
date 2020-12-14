package com.service.impl;

import com.entity.Book;
import com.entity.Borrow;
import com.repository.BookRepository;
import com.repository.BorrowRepository;
import com.repository.impl.BookRepositoryImpl;
import com.repository.impl.BorrowRepositoryImpl;
import com.service.BookService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookServiceImpl implements BookService {

    private BookRepository bookRepository = new BookRepositoryImpl();
    private BorrowRepository borrowRepository = new BorrowRepositoryImpl();
    private final int LIMIT = 6;

    @Override
    public List<Book> findAll(int page) {
        return bookRepository.findAll((page - 1) * LIMIT, LIMIT);
    }

    @Override
    public int getPages() {
        int count = bookRepository.count();
        int page = 0;
        if (count % LIMIT == 0)
            page = count / LIMIT;
        else
            page = count / LIMIT + 1;
        return page;
    }

    @Override
    public int getBorrowedPages(Integer readid) {
        int count = borrowRepository.count(readid);
        int page = 0;
        if (count % LIMIT == 0)
            page = count / LIMIT;
        else
            page = count / LIMIT + 1;
        return page;
    }

    @Override
    public int getBorrowedPagesByState(Integer state) {
        int count = borrowRepository.countByState(state);
        int page = 0;
        if (count % LIMIT == 0)
            page = count / LIMIT;
        else
            page = count / LIMIT + 1;
        return page;
    }

    @Override
    public void addBorrow(Integer bookid, Integer readid) {
        //借书时间
        Date data1 = new Date();
        //格式化借书时间字符串
        //
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //还书时间 = 借书时间 + 14天
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 14);
        Date data2 = calendar.getTime();
        String borrowDate = simpleDateFormat.format(data1);
        String returnDate = simpleDateFormat.format(data2);

        //插入记录
        borrowRepository.insert(bookid, readid, borrowDate, returnDate, null, 0);
    }

    @Override
    public List<Borrow> findAllBorrowByReaderId(Integer id, Integer page) {
        return borrowRepository.findAllByReaderId(id, (page - 1) * LIMIT, LIMIT);
    }

    @Override
    public List<Borrow> findAllBorrowWithoutAdmit(Integer state, Integer page) {
        return borrowRepository.findAllByState(state, (page - 1) * LIMIT, LIMIT);
    }

    @Override
    public void handleBorrow(Integer borrowId, Integer state, Integer adminId) {
        borrowRepository.handle(borrowId, state, adminId);
    }

    //    public static void main(String[] args) {
//        Date data1 = new Date();
//        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
//        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:SS");
//        SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(simpleDateFormat1.format(data1));
//        System.out.println(simpleDateFormat2.format(data1));
//        System.out.println(simpleDateFormat3.format(data1));
//        System.out.println(simpleDateFormat4.format(data1));
//    }
}
