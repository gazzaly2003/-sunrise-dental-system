package com.sunrise.dental.views;

import com.sunrise.dental.dao.AppointmentDAO;
import com.sunrise.dental.dao.DentistDAO;
import com.sunrise.dental.dao.TreatmentTypeDAO;
import com.sunrise.dental.model.*;
import com.sunrise.dental.service.AppointmentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Route(value = "register", layout = MainLayout.class)
@PageTitle("New Appointment — Sunrise Dental Clinic")
public class RegisterAppointmentView extends VerticalLayout {

    private final AppointmentService appointmentService = new AppointmentService();
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();
    private final DentistDAO dentistDAO = new DentistDAO();
    private final TreatmentTypeDAO treatmentDAO = new TreatmentTypeDAO();

    private TextField apptNoField;
    private TextField patientNameField;
    private TextField addressField;
    private TextField contactField;
    private ComboBox<Dentist> dentistCombo;
    private ComboBox<TreatmentType> treatmentCombo;
    private DatePicker datePicker;
    private TimePicker timePicker;

    public RegisterAppointmentView() {
        setPadding(true);
        setSpacing(true);

        H2 title = new H2("Register New Appointment");

        apptNoField = new TextField("Appointment No.");
        apptNoField.setValue(appointmentDAO.getNextAppointmentNumber());
        apptNoField.setReadOnly(true);

        patientNameField = new TextField("Patient Name");
        addressField = new TextField("Address");
        contactField = new TextField("Contact Number");
        contactField.setPlaceholder("e.g. 0771234567");

        dentistCombo = new ComboBox<>("Dentist");
        List<Dentist> dentists = dentistDAO.getAllDentists();
        dentistCombo.setItems(dentists);
        dentistCombo.setItemLabelGenerator(d -> d.getDentistId() + " — " + d.getName());

        treatmentCombo = new ComboBox<>("Treatment Type");
        List<TreatmentType> treatments = treatmentDAO.getAllTreatments();
        treatmentCombo.setItems(treatments);
        treatmentCombo.setItemLabelGenerator(TreatmentType::getName);

        datePicker = new DatePicker("Appointment Date");
        datePicker.setMin(LocalDate.now());
        datePicker.setValue(LocalDate.now());

        timePicker = new TimePicker("Appointment Time");
        timePicker.setValue(LocalTime.of(10, 0));

        FormLayout form = new FormLayout();
        form.add(apptNoField, patientNameField, addressField, contactField, dentistCombo, treatmentCombo, datePicker, timePicker);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );

        Button submitButton = new Button("Register Appointment", e -> submit());
        ButtonStyler.outline(submitButton, "#27AE60");

        add(title, form, submitButton);
    }

    private void submit() {
        try {
            if (patientNameField.getValue().isBlank()) throw new IllegalArgumentException("Patient name is required.");
            if (addressField.getValue().isBlank()) throw new IllegalArgumentException("Address is required.");
            if (!contactField.getValue().matches("\\d{9,15}")) throw new IllegalArgumentException("Contact number must be 9-15 digits.");
            if (dentistCombo.getValue() == null) throw new IllegalArgumentException("Please select a dentist.");
            if (treatmentCombo.getValue() == null) throw new IllegalArgumentException("Please select a treatment type.");
            if (datePicker.getValue() == null) throw new IllegalArgumentException("Please select a date.");
            if (timePicker.getValue() == null) throw new IllegalArgumentException("Please select a time.");

            Patient patient = new Patient(patientNameField.getValue(), addressField.getValue(), contactField.getValue());

            Appointment appt = new Appointment(
                    apptNoField.getValue(),
                    patient,
                    dentistCombo.getValue(),
                    treatmentCombo.getValue(),
                    datePicker.getValue(),
                    timePicker.getValue(),
                    "CONFIRMED"
            );

            boolean saved = appointmentService.registerAppointment(appt);

            if (saved) {
                Notification success = Notification.show("Appointment registered successfully!", 3000, Notification.Position.TOP_CENTER);
                success.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                clearForm();
            } else {
                showError("Failed to register appointment. Please try again.");
            }

        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void showError(String message) {
        Notification error = Notification.show(message, 4000, Notification.Position.TOP_CENTER);
        error.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    private void clearForm() {
        patientNameField.clear();
        addressField.clear();
        contactField.clear();
        dentistCombo.clear();
        treatmentCombo.clear();
        apptNoField.setValue(appointmentDAO.getNextAppointmentNumber());
    }
}