package com.gzy.bank.web.servlet;

import com.gzy.bank.exceptions.AppException;
import com.gzy.bank.exceptions.MoneyNotEnoughException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/transfer")
public class AccountTransferServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //响应流
        response.setContentType("text/html;charset = UTF-8");
        PrintWriter out = response.getWriter();
        //获取转账
        String fromActno = request.getParameter("fromActno");
        String toActno = request.getParameter("toActno");
        double money = Double.parseDouble(request.getParameter("money"));

        //转账的业务逻辑，连接数据库
        //1、判断余额
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;

        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/mvc?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
            String user = "root";
            String password = "000000";
            conn = DriverManager.getConnection(url,user,password);
            String sql = "select balance from t_act where actno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,fromActno);
            rs = ps.executeQuery();
            if (rs.next()){
                double balance = rs.getDouble("balance");
                if(balance<money){
                    //余额不足
                    throw new MoneyNotEnoughException("对不起，余额不足");
                }
                //余额充足
                //开始转账

                //手动提交
                conn.setAutoCommit(false);

                String sql2 = "update t_act set balance = balance - ? where actno = ?";
                ps2 = conn.prepareStatement(sql2);
                ps2.setDouble(1,money);
                ps2.setString(2,fromActno);
                int count = ps2.executeUpdate();


                String sql3 = "update t_act set balance = balance + ? where actno = ?";
                ps3 = conn.prepareStatement(sql3);
                ps3.setDouble(1,money);
                ps3.setString(2,toActno);
                count+=ps3.executeUpdate();

                if(count!=2){
                    throw new AppException("App异常，联系管理员");
                }

                conn.commit();
                out.print("转账成功");

            }
        } catch (Exception e) {
            try{
                if(conn!=null){
                    conn.rollback();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
            out.print(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps2 != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps3 != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
