package com.gzy.bank.mvc;

import com.gzy.bank.bean.Account;
import com.gzy.bank.utils.DBUtils;

import java.sql.*;


/**
 * DAO 是 Data Access Object 数据库访问对象
 * 只负责数据库的CRUD
 */
public class AccountDao {
    public int insert(Account acc){
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = DBUtils.getConnection();
            String sql = "insert into t_act (actno,balance) values (?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,acc.getActno());
            ps.setDouble(2,acc.getBalance());
            count = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBUtils.close(conn,ps,null);
        }
        return count;
    }

    public int deleteById(Long id){
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = DBUtils.getConnection();
            String sql = "Delete from t_act where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1,id);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBUtils.close(conn,ps,null);
        }
        return count;
    }

    public int update(Account acc){
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = DBUtils.getConnection();
            String sql = "UPDATE t_act set balance = ?,actno = ? where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1,acc.getBalance());
            ps.setString(2,acc.getActno());
            ps.setLong(3,acc.getId());
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBUtils.close(conn,ps,null);
        }
        return count;
    }

    public Account selectByActno(String actno){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Account acc = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "select id,balance from t_act where actno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,actno);
            rs = ps.executeQuery();
            if (rs.next()){
                Long id = rs.getLong("id");
                Double balance = rs.getDouble("balance");
                acc = new Account(id,actno,balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(conn,ps,rs);
        }
        return acc;
    }
}
