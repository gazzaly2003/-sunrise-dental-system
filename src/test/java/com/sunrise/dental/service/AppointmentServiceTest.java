package com.sunrise.dental.service;

import com.sunrise.dental.dao.AppointmentDAO;
import com.sunrise.dental.dao.DentistDAO;
import com.sunrise.dental.dao.TreatmentTypeDAO;
import com.sunrise.dental.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceTest {

    private AppointmentService service;
    private FakeAppointmentDAO fakeAppointmentDAO;

    @BeforeEach
    void setUp() {
        fakeAppointmentDAO = new FakeAppointmentDAO();
        service = new AppointmentService(fakeAppointmentDAO, new FakeDentistDAO(), new FakeTreatmentTypeDAO());
    }

    private Appointment validAppointment() {
        Patient patient = new Patient("Test Patient", "123 Main St", "0771234567");
        Dentist dentist = new Dentist("D001", "Dr. Test", "General");
        TreatmentType treatment = new TreatmentType("Cleaning", 3000);
        return new Appointment("A00001", patient, dentist, treatment, LocalDate.now().plusDays(1), LocalTime.of(10, 0), "CONFIRMED");
    }

    @Test
    void registerAppointment_withValidData_succeeds() {
        boolean result = service.registerAppointment(validAppointment());
        assertTrue(result);
    }

    @Test
    void registerAppointment_withBlankPatientName_throwsException() {
        Appointment appt = validAppointment();
        appt.getPatient().setName("");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.registerAppointment(appt));
        assertTrue(ex.getMessage().contains("Patient name"));
    }

    @Test
    void registerAppointment_withInvalidContactNumber_throwsException() {
        Appointment appt = validAppointment();
        appt.getPatient().setContactNumber("abc");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.registerAppointment(appt));
        assertTrue(ex.getMessage().contains("Contact number"));
    }

    @Test
    void registerAppointment_withPastDate_throwsException() {
        Appointment appt = validAppointment();
        appt.setAppointmentDate(LocalDate.now().minusDays(1));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.registerAppointment(appt));
        assertTrue(ex.getMessage().contains("future"));
    }

    @Test
    void registerAppointment_withDuplicateApptNo_throwsException() {
        fakeAppointmentDAO.existingAppointment = validAppointment();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.registerAppointment(validAppointment()));
        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void searchAppointment_withNonExistentNumber_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.searchAppointment("A99999"));
    }

    static class FakeAppointmentDAO extends AppointmentDAO {
        Appointment existingAppointment = null;

        @Override
        public Appointment findByApptNo(String apptNo) {
            return existingAppointment;
        }

        @Override
        public boolean saveAppointment(Appointment appt) {
            return true;
        }
    }

    static class FakeDentistDAO extends DentistDAO {
        @Override
        public Dentist getDentistById(String id) {
            return new Dentist(id, "Dr. Test", "General");
        }
    }

    static class FakeTreatmentTypeDAO extends TreatmentTypeDAO {
        @Override
        public TreatmentType getTreatmentByName(String name) {
            return new TreatmentType(name, 3000);
        }
    }
}