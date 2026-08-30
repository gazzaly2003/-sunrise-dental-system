package com.sunrise.dental.dao;

import com.sunrise.dental.model.Bill;
import com.sunrise.dental.util.DBConnectionManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BillDAO {

    public boolean saveBill(Bill bill) {
        String sql = "INSERT INTO bills (bill_id, appt_no, total_amount, payment_method) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = DBConnectionManager.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, bill.getBillId());
            ps.setString(2, bill.getAppointment().getApptNo());
            ps.setDouble(3, bill.getTotalAmount());
            ps.setString(4, bill.getPaymentMethod());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}