package com.sunrise.dental.service;

import com.sunrise.dental.dao.BillDAO;
import com.sunrise.dental.model.Appointment;
import com.sunrise.dental.model.Bill;

public class BillService {
    private final BillDAO billDAO = new BillDAO();

    public BillService() {
    }

    public Bill generateBill(Appointment appt, String paymentMethod) {
        if (appt == null) {
            throw new IllegalArgumentException("Appointment is required to generate a bill");
        }
        double total = appt.calculateBill();
        String billId = IdGeneratorFactory.generate(IdGeneratorFactory.IdType.BILL);
        Bill bill = new Bill(billId, appt, total, paymentMethod);
        billDAO.saveBill(bill);
        return bill;
    }
}