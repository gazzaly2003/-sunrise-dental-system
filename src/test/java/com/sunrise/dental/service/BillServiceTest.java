package com.sunrise.dental.service;

import com.sunrise.dental.dao.BillDAO;
import com.sunrise.dental.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class BillServiceTest {

    @Test
    void generateBill_withValidAppointment_calculatesCorrectTotal() {
        BillService service = new BillService();

        TreatmentType treatment = new TreatmentType("Filling", 6000);
        Appointment appt = new Appointment("A00001",
                new Patient("Test", "Addr", "0771234567"),
                new Dentist("D001", "Dr. Test", "General"),
                treatment,
                LocalDate.now(), LocalTime.of(10, 0), "CONFIRMED");

        Bill bill = service.generateBill(appt, "CASH");

        assertEquals(6000, bill.getTotalAmount());
        assertEquals("CASH", bill.getPaymentMethod());
        assertNotNull(bill.getBillId());
        assertTrue(bill.getBillId().startsWith("B"));
    }

    @Test
    void generateBill_withNullAppointment_throwsException() {
        BillService service = new BillService();
        assertThrows(IllegalArgumentException.class, () -> service.generateBill(null, "CASH"));
    }

    static class FakeBillDAO extends BillDAO {
        @Override
        public boolean saveBill(Bill bill) {
            return true;
        }
    }
}