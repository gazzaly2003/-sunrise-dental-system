package com.sunrise.dental.model;

public class TreatmentType {
    private String name;
    private double consultationFee;

    public TreatmentType() {}

    public TreatmentType(String name, double consultationFee) {
        this.name = name;
        this.consultationFee = consultationFee;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getConsultationFee() { return consultationFee; }
    public void setConsultationFee(double consultationFee) { this.consultationFee = consultationFee; }
}