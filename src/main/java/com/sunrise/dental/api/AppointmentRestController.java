package com.sunrise.dental.api;

import com.sunrise.dental.model.*;
import com.sunrise.dental.service.AppointmentService;
import com.sunrise.dental.service.BillService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AppointmentRestController {

    private final AppointmentService appointmentService = new AppointmentService();
    private final BillService billService = new BillService();

    @GetMapping("/appointments")
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/appointments/{apptNo}")
    public Appointment getAppointment(@PathVariable String apptNo) {
        return appointmentService.searchAppointment(apptNo);
    }

    @PostMapping("/appointments")
    public Map<String, Object> registerAppointment(@RequestBody Map<String, Object> body) {
        Patient patient = new Patient((String) body.get("patientName"), (String) body.get("address"), (String) body.get("contactNumber"));
        Dentist dentist = new Dentist((String) body.get("dentistId"), null, null);
        TreatmentType treatment = new TreatmentType((String) body.get("treatmentType"), 0);

        Appointment appt = new Appointment(
                (String) body.get("apptNo"),
                patient,
                dentist,
                treatment,
                LocalDate.parse((String) body.get("appointmentDate")),
                LocalTime.parse((String) body.get("appointmentTime")),
                "CONFIRMED"
        );

        boolean saved = appointmentService.registerAppointment(appt);
        return Map.of("success", saved);
    }

    @PostMapping("/bills/{apptNo}")
    public Bill generateBill(@PathVariable String apptNo, @RequestBody Map<String, String> body) {
        Appointment appt = appointmentService.searchAppointment(apptNo);
        String paymentMethod = body.getOrDefault("paymentMethod", "CASH");
        return billService.generateBill(appt, paymentMethod);
    }
}