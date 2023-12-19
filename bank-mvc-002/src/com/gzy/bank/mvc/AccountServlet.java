package com.gzy.bank.mvc;

import com.gzy.bank.exceptions.MoneyNotEnoughException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/transfer")
public class AccountServlet extends HttpServlet {

    private AccountService accountService = new AccountService();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //接受数据
        String fromActno = request.getParameter("fromActno");
        String toActno = request.getParameter("toActno");
        Double money = Double.parseDouble(request.getParameter("money"));
        //处理业务
        try {
            accountService.transfer(fromActno,toActno,money);
            response.sendRedirect(request.getContextPath()+"/success.jsp");
        } catch (MoneyNotEnoughException e){
            response.sendRedirect(request.getContextPath()+"/moneynotenough.jsp");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath()+"/error.jsp");
        }

        //展示处理结果
    }
}
