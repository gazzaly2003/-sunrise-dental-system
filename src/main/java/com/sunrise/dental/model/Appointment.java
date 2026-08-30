package com.sunrise.dental.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private String apptNo;
    private Patient patient;
    private Dentist dentist;
    private TreatmentType treatmentType;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;

    public Appointment() {}

    public Appointment(String apptNo, Patient patient, Dentist dentist, TreatmentType treatmentType,
                       LocalDate appointmentDate, LocalTime appointmentTime, String status) {
        this.apptNo = apptNo;
        this.patient = patient;
        this.dentist = dentist;
        this.treatmentType = treatmentType;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    public String getApptNo() { return apptNo; }
    public void setApptNo(String apptNo) { this.apptNo = apptNo; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Dentist getDentist() { return dentist; }
    public void setDentist(Dentist dentist) { this.dentist = dentist; }

    public TreatmentType getTreatmentType() { return treatmentType; }
    public void setTreatmentType(TreatmentType treatmentType) { this.treatmentType = treatmentType; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double calculateBill() {
        return treatmentType != null ? treatmentType.getConsultationFee() : 0.0;
    }
}