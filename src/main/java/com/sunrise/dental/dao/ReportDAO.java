package com.sunrise.dental.dao;

import com.sunrise.dental.util.DBConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReportDAO {

    public List<Map<String, Object>> getTodaysAppointments() {
        List<Map<String, Object>> results = new ArrayList<>();
        String sql = "SELECT appt_no, patient_name, dentist_id, treatment_name, appt_time, status " +
                "FROM appointments WHERE appt_date = CURDATE() ORDER BY appt_time";

        try (Statement stmt = DBConnectionManager.getInstance().getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("apptNo", rs.getString("appt_no"));
                row.put("patientName", rs.getString("patient_name"));
                row.put("dentistId", rs.getString("dentist_id"));
                row.put("treatment", rs.getString("treatment_name"));
                row.put("time", rs.getString("appt_time"));
                row.put("status", rs.getString("status"));
                results.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<Map<String, Object>> getRevenueByDentist() {
        List<Map<String, Object>> results = new ArrayList<>();
        String sql = "SELECT d.dentist_id, d.name, COUNT(b.bill_id) AS bill_count, " +
                "IFNULL(SUM(b.total_amount), 0) AS total_revenue " +
                "FROM dentists d " +
                "LEFT JOIN appointments a ON a.dentist_id = d.dentist_id " +
                "LEFT JOIN bills b ON b.appt_no = a.appt_no " +
                "GROUP BY d.dentist_id, d.name " +
                "ORDER BY total_revenue DESC";

        try (Statement stmt = DBConnectionManager.getInstance().getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("dentistId", rs.getString("dentist_id"));
                row.put("name", rs.getString("name"));
                row.put("billCount", rs.getInt("bill_count"));
                row.put("totalRevenue", rs.getDouble("total_revenue"));
                results.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
    public int getTotalAppointmentCount() {
        String sql = "SELECT COUNT(*) AS total FROM appointments";
        try (Statement stmt = DBConnectionManager.getInstance().getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public java.util.Map<String, Integer> getAppointmentsPerDayThisWeek() {
        java.util.Map<String, Integer> result = new java.util.LinkedHashMap<>();
        String sql = "SELECT DAYNAME(appt_date) AS day_name, COUNT(*) AS cnt " +
                "FROM appointments " +
                "WHERE appt_date BETWEEN DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY) AND CURDATE() " +
                "GROUP BY DAYNAME(appt_date), DAYOFWEEK(appt_date) " +
                "ORDER BY DAYOFWEEK(appt_date)";

        try (Statement stmt = DBConnectionManager.getInstance().getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.put(rs.getString("day_name"), rs.getInt("cnt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}