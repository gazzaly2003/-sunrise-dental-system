package com.sunrise.dental.views;

import com.sunrise.dental.model.Appointment;
import com.sunrise.dental.model.Bill;
import com.sunrise.dental.service.AppointmentService;
import com.sunrise.dental.service.BillService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "search", layout = MainLayout.class)
@PageTitle("Search Appointment — Sunrise Dental Clinic")
public class SearchAppointmentView extends VerticalLayout {

    private final AppointmentService appointmentService = new AppointmentService();
    private final BillService billService = new BillService();

    private TextField searchField;
    private VerticalLayout resultCard;
    private Appointment currentAppointment;

    public SearchAppointmentView() {
        setPadding(true);
        setSpacing(true);

        H2 title = new H2("Search Appointment");

        searchField = new TextField();
        searchField.setPlaceholder("Enter appointment number, e.g. A00001");
        searchField.setWidth("300px");

        Button searchButton = new Button("Search", e -> search());
        ButtonStyler.outline(searchButton, "#2C5364");

        HorizontalLayout searchBar = new HorizontalLayout(searchField, searchButton);
        searchBar.setAlignItems(Alignment.END);

        resultCard = new VerticalLayout();
        resultCard.setVisible(false);
        resultCard.getStyle()
                .set("background", "white")
                .set("border-radius", "14px")
                .set("box-shadow", "0 4px 14px rgba(0,0,0,0.08)")
                .set("padding", "24px")
                .set("margin-top", "20px");

        add(title, searchBar, resultCard);
    }

    private void search() {
        String apptNo = searchField.getValue().trim();
        if (apptNo.isEmpty()) {
            showError("Please enter an appointment number.");
            return;
        }

        try {
            currentAppointment = appointmentService.searchAppointment(apptNo);
            displayAppointment(currentAppointment);
        } catch (IllegalArgumentException ex) {
            resultCard.setVisible(false);
            showError(ex.getMessage());
        }
    }

    private void displayAppointment(Appointment appt) {
        resultCard.removeAll();
        resultCard.setVisible(true);

        H3 heading = new H3("Appointment " + appt.getApptNo());

        FormLayout details = new FormLayout();
        details.addFormItem(new Span(appt.getPatient().getName()), "Patient Name");
        details.addFormItem(new Span(appt.getPatient().getAddress()), "Address");
        details.addFormItem(new Span(appt.getPatient().getContactNumber()), "Contact");
        details.addFormItem(new Span(appt.getDentist() != null ? appt.getDentist().getName() : "N/A"), "Dentist");
        details.addFormItem(new Span(appt.getTreatmentType() != null ? appt.getTreatmentType().getName() : "N/A"), "Treatment");
        details.addFormItem(new Span(appt.getAppointmentDate().toString()), "Date");
        details.addFormItem(new Span(appt.getAppointmentTime().toString()), "Time");
        details.addFormItem(new Span(appt.getStatus()), "Status");
        details.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );

        ComboBox<String> paymentCombo = new ComboBox<>("Payment Method");
        paymentCombo.setItems("CASH", "CARD", "INSURANCE");
        paymentCombo.setValue("CASH");
        paymentCombo.setWidth("200px");

        Button billButton = new Button("Generate & Print Bill", e -> confirmAndGenerateBill(paymentCombo.getValue()));
        ButtonStyler.outline(billButton, "#27AE60");

        HorizontalLayout billRow = new HorizontalLayout(paymentCombo, billButton);
        billRow.setAlignItems(Alignment.END);
        billRow.getStyle().set("margin-top", "16px");

        resultCard.add(heading, details, billRow);
    }
    private void confirmAndGenerateBill(String paymentMethod) {
        com.vaadin.flow.component.confirmdialog.ConfirmDialog confirm = new com.vaadin.flow.component.confirmdialog.ConfirmDialog();
        confirm.setHeader("Confirm Billing");
        confirm.setText("Generate a bill for appointment " + currentAppointment.getApptNo()
                + " with payment method " + paymentMethod + "? This cannot be undone.");
        confirm.setCancelable(true);
        confirm.setConfirmText("Generate Bill");
        confirm.setConfirmButtonTheme("success primary");
        confirm.addConfirmListener(e -> generateBill(paymentMethod));
        confirm.open();
    }
    private void generateBill(String paymentMethod) {
        try {
            Bill bill = billService.generateBill(currentAppointment, paymentMethod);
            showReceiptDialog(bill);
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void showReceiptDialog(Bill bill) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Receipt");

        VerticalLayout content = new VerticalLayout();
        content.add(new Span("Bill ID: " + bill.getBillId()));
        content.add(new Span("Appointment No: " + bill.getAppointment().getApptNo()));
        content.add(new Span("Patient: " + bill.getAppointment().getPatient().getName()));
        content.add(new Span("Treatment: " + bill.getAppointment().getTreatmentType().getName()));
        content.add(new Span("Payment Method: " + bill.getPaymentMethod()));
        H3 total = new H3("Total: Rs. " + bill.getTotalAmount());
        content.add(total);

        Button closeButton = new Button("Close", e -> dialog.close());
        ButtonStyler.outline(closeButton, "#2C5364");

        dialog.add(content);
        dialog.getFooter().add(closeButton);
        dialog.open();
    }

    private void showError(String message) {
        Notification error = Notification.show(message, 4000, Notification.Position.TOP_CENTER);
        error.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}