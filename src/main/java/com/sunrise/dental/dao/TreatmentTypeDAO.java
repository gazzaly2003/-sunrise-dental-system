package com.sunrise.dental.dao;

import com.sunrise.dental.model.TreatmentType;
import com.sunrise.dental.util.DBConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreatmentTypeDAO {

    public List<TreatmentType> getAllTreatments() {
        List<TreatmentType> treatments = new ArrayList<>();
        String sql = "SELECT * FROM treatment_types";

        try (Statement stmt = DBConnectionManager.getInstance().getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                treatments.add(new TreatmentType(rs.getString("treatment_name"), rs.getDouble("consultation_fee")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return treatments;
    }

    public TreatmentType getTreatmentByName(String name) {
        String sql = "SELECT * FROM treatment_types WHERE treatment_name = ?";

        try (PreparedStatement ps = DBConnectionManager.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new TreatmentType(rs.getString("treatment_name"), rs.getDouble("consultation_fee"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}