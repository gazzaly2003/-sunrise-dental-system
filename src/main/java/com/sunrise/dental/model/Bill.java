package com.sunrise.dental.model;

public class Bill {
    private String billId;
    private Appointment appointment;
    private double totalAmount;
    private String paymentMethod;

    public Bill() {}

    public Bill(String billId, Appointment appointment, double totalAmount, String paymentMethod) {
        this.billId = billId;
        this.appointment = appointment;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
    }

    public String getBillId() { return billId; }
    public void setBillId(String billId) { this.billId = billId; }

    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String generateReceipt() {
        return "Receipt\n--------\nBill ID: " + billId +
                "\nAppointment No: " + appointment.getApptNo() +
                "\nPatient: " + appointment.getPatient().getName() +
                "\nTreatment: " + appointment.getTreatmentType().getName() +
                "\nPayment Method: " + paymentMethod +
                "\nTotal: Rs. " + totalAmount;
    }
}