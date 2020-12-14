package com.repository.impl;

import com.entity.Book;
import com.entity.BookCase;
import com.repository.BookRepository;
import com.utils.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    @Override
    public List<Book> findAll(int index, int limit) {
        Connection connection = JDBCTools.getConnection();
        String sql = "SELECT *  FROM  book LEFT JOIN bookcase ON book.bookcaseid=bookcase.id LIMIT ?,?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Book> books = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, index);
            preparedStatement.setInt(2, limit);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
//                BookCase bookCase = new BookCase(resultSet.getInt(9), resultSet.getString(10));
//                Book book = new Book(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)
//                        , resultSet.getString(4), resultSet.getInt(5), resultSet.getDouble(6), bookCase);
//                books.add(book);
                books.add(new Book(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5), resultSet.getDouble(6),
                        new BookCase(resultSet.getInt(9), resultSet.getString(10))));
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(connection, preparedStatement, resultSet);
        }
        return books;
    }

    @Override
    public int count() {
        Connection connection = JDBCTools.getConnection();
        String sql = "SELECT count(*) FROM  book LEFT JOIN bookcase ON book.bookcaseid=bookcase.id ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                count = resultSet.getInt(1);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(connection, preparedStatement, resultSet);
        }
        return count;
    }
}
