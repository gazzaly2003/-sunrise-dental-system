package com.sunrise.dental.dao;

import com.sunrise.dental.model.*;
import com.sunrise.dental.util.DBConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {


    private final DentistDAO dentistDAO = new DentistDAO();
    private final TreatmentTypeDAO treatmentDAO = new TreatmentTypeDAO();

    public boolean saveAppointment(Appointment appt) {
        String sql = "INSERT INTO appointments (appt_no, patient_name, address, contact_number, dentist_id, treatment_name, appt_date, appt_time, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = DBConnectionManager.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, appt.getApptNo());
            ps.setString(2, appt.getPatient().getName());
            ps.setString(3, appt.getPatient().getAddress());
            ps.setString(4, appt.getPatient().getContactNumber());
            ps.setString(5, appt.getDentist().getDentistId());
            ps.setString(6, appt.getTreatmentType().getName());
            ps.setDate(7, Date.valueOf(appt.getAppointmentDate()));
            ps.setTime(8, Time.valueOf(appt.getAppointmentTime()));
            ps.setString(9, appt.getStatus());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Appointment findByApptNo(String apptNo) {
        String sql = "SELECT * FROM appointments WHERE appt_no = ?";

        try (PreparedStatement ps = DBConnectionManager.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, apptNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToAppointment(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments";

        try (Statement stmt = DBConnectionManager.getInstance().getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRowToAppointment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Appointment mapRowToAppointment(ResultSet rs) throws SQLException {
        Patient patient = new Patient(rs.getString("patient_name"), rs.getString("address"), rs.getString("contact_number"));
        Dentist dentist = dentistDAO.getDentistById(rs.getString("dentist_id"));
        TreatmentType treatment = treatmentDAO.getTreatmentByName(rs.getString("treatment_name"));

        return new Appointment(
                rs.getString("appt_no"),
                patient,
                dentist,
                treatment,
                rs.getDate("appt_date").toLocalDate(),
                rs.getTime("appt_time").toLocalTime(),
                rs.getString("status")
        );
    }
    public String getNextAppointmentNumber() {
        String sql = "{CALL GetNextAppointmentNumber(?)}";
        try (CallableStatement cs = DBConnectionManager.getInstance().getConnection().prepareCall(sql)) {
            cs.registerOutParameter(1, Types.VARCHAR);
            cs.execute();
            return cs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}