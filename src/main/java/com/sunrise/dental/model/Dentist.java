package com.sunrise.dental.model;

public class Dentist {
    private String dentistId;
    private String name;
    private String specialization;

    public Dentist() {}

    public Dentist(String dentistId, String name, String specialization) {
        this.dentistId = dentistId;
        this.name = name;
        this.specialization = specialization;
    }

    public String getDentistId() { return dentistId; }
    public void setDentistId(String dentistId) { this.dentistId = dentistId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
}