package com.sunrise.dental.service;

import com.sunrise.dental.dao.AppointmentDAO;
import com.sunrise.dental.dao.DentistDAO;
import com.sunrise.dental.dao.TreatmentTypeDAO;
import com.sunrise.dental.model.Appointment;
import com.sunrise.dental.model.Dentist;
import com.sunrise.dental.model.TreatmentType;

import java.time.LocalDate;
import java.util.List;

public class AppointmentService {

    private final AppointmentDAO appointmentDAO;
    private final DentistDAO dentistDAO;
    private final TreatmentTypeDAO treatmentDAO;

    // Default constructor for the actual application
    public AppointmentService() {
        this.appointmentDAO = new AppointmentDAO();
        this.dentistDAO = new DentistDAO();
        this.treatmentDAO = new TreatmentTypeDAO();
    }

    // Constructor used for unit testing
    public AppointmentService(
            AppointmentDAO appointmentDAO,
            DentistDAO dentistDAO,
            TreatmentTypeDAO treatmentDAO) {

        this.appointmentDAO = appointmentDAO;
        this.dentistDAO = dentistDAO;
        this.treatmentDAO = treatmentDAO;
    }

    public boolean registerAppointment(Appointment appt) {
        validate(appt);

        if (appointmentDAO.findByApptNo(appt.getApptNo()) != null) {
            throw new IllegalArgumentException(
                    "Appointment number already exists: " + appt.getApptNo());
        }

        Dentist dentist = dentistDAO.getDentistById(
                appt.getDentist().getDentistId());

        if (dentist == null) {
            throw new IllegalArgumentException(
                    "Dentist not found: " + appt.getDentist().getDentistId());
        }

        TreatmentType treatment = treatmentDAO.getTreatmentByName(
                appt.getTreatmentType().getName());

        if (treatment == null) {
            throw new IllegalArgumentException(
                    "Treatment type not found: " +
                            appt.getTreatmentType().getName());
        }

        return appointmentDAO.saveAppointment(appt);
    }

    public Appointment searchAppointment(String apptNo) {

        if (apptNo == null || apptNo.isBlank()) {
            throw new IllegalArgumentException(
                    "Appointment number is required");
        }

        Appointment appt = appointmentDAO.findByApptNo(apptNo);

        if (appt == null) {
            throw new IllegalArgumentException(
                    "No appointment found with number: " + apptNo);
        }

        return appt;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentDAO.getAllAppointments();
    }

    private void validate(Appointment appt) {

        if (appt == null) {
            throw new IllegalArgumentException(
                    "Appointment is required");
        }

        if (appt.getApptNo() == null || appt.getApptNo().isBlank()) {
            throw new IllegalArgumentException(
                    "Appointment number is required");
        }

        if (appt.getPatient() == null ||
                appt.getPatient().getName() == null ||
                appt.getPatient().getName().isBlank()) {

            throw new IllegalArgumentException(
                    "Patient name is required");
        }

        if (appt.getPatient().getContactNumber() == null ||
                !appt.getPatient().getContactNumber().matches("\\d{9,15}")) {

            throw new IllegalArgumentException(
                    "Contact number must be 9-15 digits");
        }

        if (appt.getAppointmentDate() == null ||
                appt.getAppointmentDate().isBefore(LocalDate.now())) {

            throw new IllegalArgumentException(
                    "Appointment date must be today or in the future");
        }

        if (appt.getAppointmentTime() == null) {
            throw new IllegalArgumentException(
                    "Appointment time is required");
        }
    }
}