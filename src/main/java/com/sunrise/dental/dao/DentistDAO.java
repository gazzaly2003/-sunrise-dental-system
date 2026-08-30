package com.sunrise.dental.dao;

import com.sunrise.dental.model.Dentist;
import com.sunrise.dental.util.DBConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DentistDAO {

    public List<Dentist> getAllDentists() {
        List<Dentist> dentists = new ArrayList<>();
        String sql = "SELECT * FROM dentists";

        try (Statement stmt = DBConnectionManager.getInstance().getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                dentists.add(new Dentist(
                        rs.getString("dentist_id"),
                        rs.getString("name"),
                        rs.getString("specialization")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dentists;
    }

    public Dentist getDentistById(String dentistId) {
        String sql = "SELECT * FROM dentists WHERE dentist_id = ?";

        try (PreparedStatement ps = DBConnectionManager.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, dentistId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Dentist(rs.getString("dentist_id"), rs.getString("name"), rs.getString("specialization"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}