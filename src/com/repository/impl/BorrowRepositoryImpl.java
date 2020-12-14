package com.repository.impl;

import com.entity.Book;
import com.entity.Borrow;
import com.entity.Reader;
import com.repository.BorrowRepository;
import com.utils.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowRepositoryImpl implements BorrowRepository {
    @Override
    public void insert(Integer bookid, Integer readerid, String borrowtime, String returntime, Integer adminid, Integer state) {
        Connection connection = JDBCTools.getConnection();
        String sql = "insert into borrow (bookid,readerid,borrowtime,returntime,state) VALUE (?,?,?,?,?)";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookid);
            preparedStatement.setInt(2, readerid);
            preparedStatement.setString(3, borrowtime);
            preparedStatement.setString(4, returntime);
            preparedStatement.setInt(5, 0);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(connection, preparedStatement, null);
        }
    }

    @Override
    public List<Borrow> findAllByReaderId(Integer readerId, Integer index, Integer limit) {
        Connection connection = JDBCTools.getConnection();
        String sql = "SELECT borrow.id,book.name,book.author,book.publish,borrow.borrowtime,borrow.returntime,reader.name,reader.tel,reader.cardid,borrow.state FROM borrow,book,reader " +
                " WHERE  borrow.bookid = book.id  AND borrow.readerid = reader.id AND reader.id=? limit ?,?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Borrow> borrows = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, readerId);
            preparedStatement.setInt(2, index);
            preparedStatement.setInt(3, limit);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                borrows.add(new Borrow(resultSet.getInt(1),
                        new Book(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)),
                        new Reader(resultSet.getString(7), resultSet.getString(8), resultSet.getString(9)),
                        resultSet.getString(5), resultSet.getString(6), resultSet.getInt(10)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(connection, preparedStatement, resultSet);
        }
        return borrows;
    }

    @Override
    public List<Borrow> findAllByState(Integer state, Integer index, Integer limit) {
        Connection connection = JDBCTools.getConnection();
        String sql = "SELECT borrow.id,book.name,book.author,book.publish,borrow.borrowtime,borrow.returntime,reader.name,reader.tel,reader.cardid,borrow.state FROM borrow,book,reader  WHERE  borrow.bookid = book.id  AND borrow.readerid = reader.id AND state=? limit ?,?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Borrow> borrows = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, state);
            preparedStatement.setInt(2, index);
            preparedStatement.setInt(3, limit);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                borrows.add(new Borrow(resultSet.getInt(1),
                        new Book(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)),
                        new Reader(resultSet.getString(7), resultSet.getString(8), resultSet.getString(9)),
                        resultSet.getString(5), resultSet.getString(6), resultSet.getInt(10)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(connection, preparedStatement, resultSet);
        }
        return borrows;
    }

    @Override
    public int count(Integer readerId) {
        Connection connection = JDBCTools.getConnection();
        String sql = "SELECT count(*) FROM  borrow  where readerid=?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int num = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, readerId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                num = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(connection, preparedStatement, resultSet);
        }
        return num;
    }

    @Override
    public int countByState(Integer state) {
        Connection connection = JDBCTools.getConnection();
        String sql = "SELECT count(*) FROM borrow,book,reader  WHERE  borrow.bookid = book.id  AND borrow.readerid = reader.id AND state=?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Borrow> borrows = new ArrayList<>();
        int count = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, state);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                count = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(connection, preparedStatement, resultSet);
        }
        return count;
    }

    @Override
    public void handle(Integer borrowId, Integer state, Integer adminId) {
        Connection connection = JDBCTools.getConnection();
        String sql = "UPDATE  borrow SET state=?,adminid=? WHERE id=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, state);
            preparedStatement.setInt(2, adminId);
            preparedStatement.setInt(3, borrowId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(connection, preparedStatement, null);
        }
    }
}