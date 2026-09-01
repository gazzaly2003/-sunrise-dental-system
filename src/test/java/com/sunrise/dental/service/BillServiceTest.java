package com.sunrise.dental.service;

import com.sunrise.dental.dao.BillDAO;
import com.sunrise.dental.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class BillServiceTest {

    private Appointment validAppointment() {
        TreatmentType treatment = new TreatmentType("Filling", 6000);
        return new Appointment("A00001",
                new Patient("Test Patient", "123 Main St", "0771234567"),
                new Dentist("D001", "Dr. Test", "General"),
                treatment,
                LocalDate.now(), LocalTime.of(10, 0), "CONFIRMED");
    }

    // BILL-01: Generate bill from valid appointment
    @Test
    void generateBill_withValidAppointment_returnsBillObject() {
        FakeBillDAO fakeDAO = new FakeBillDAO();
        BillService service = new BillService(fakeDAO);

        Bill bill = service.generateBill(validAppointment(), "CASH");

        assertNotNull(bill);
    }

    // BILL-02: Verify calculated bill amount
    @Test
    void generateBill_calculatesCorrectTotal() {
        BillService service = new BillService(new FakeBillDAO());
        Appointment appt = validAppointment();

        Bill bill = service.generateBill(appt, "CASH");

        assertEquals(appt.calculateBill(), bill.getTotalAmount());
    }

    // BILL-03: Generate bill identifier
    @Test
    void generateBill_producesNonBlankBillId() {
        BillService service = new BillService(new FakeBillDAO());

        Bill bill = service.generateBill(validAppointment(), "CASH");

        assertNotNull(bill.getBillId());
        assertFalse(bill.getBillId().isBlank());
    }

    // BILL-04: Store payment method
    @Test
    void generateBill_withCardPayment_storesCardAsPaymentMethod() {
        BillService service = new BillService(new FakeBillDAO());

        Bill bill = service.generateBill(validAppointment(), "CARD");

        assertEquals("CARD", bill.getPaymentMethod());
    }

    // BILL-05: Save generated bill (verify DAO receives it)
    @Test
    void generateBill_passesBillToDAO() {
        FakeBillDAO fakeDAO = new FakeBillDAO();
        BillService service = new BillService(fakeDAO);

        Bill bill = service.generateBill(validAppointment(), "CASH");

        assertTrue(fakeDAO.saveWasCalled);
        assertEquals(bill.getBillId(), fakeDAO.lastSavedBill.getBillId());
    }

    // BILL-06: Null appointment
    @Test
    void generateBill_withNullAppointment_throwsException() {
        BillService service = new BillService(new FakeBillDAO());

        assertThrows(IllegalArgumentException.class, () -> service.generateBill(null, "CASH"));
    }

    static class FakeBillDAO extends BillDAO {
        boolean saveWasCalled = false;
        Bill lastSavedBill = null;

        @Override
        public boolean saveBill(Bill bill) {
            saveWasCalled = true;
            lastSavedBill = bill;
            return true;
        }
    }
}