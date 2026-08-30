package com.sunrise.dental.views;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "help", layout = MainLayout.class)
@PageTitle("Help — Sunrise Dental Clinic")
public class HelpView extends VerticalLayout {

    public HelpView() {
        setPadding(true);
        setSpacing(true);

        H2 title = new H2("Help — How to use this system");

        VerticalLayout card = new VerticalLayout();
        card.getStyle()
                .set("background", "white")
                .set("border-radius", "16px")
                .set("box-shadow", "0 6px 18px rgba(0,0,0,0.08)")
                .set("padding", "30px");
        card.setMaxWidth("700px");

        card.add(
                step("1. New Appointment", "Click 'New Appointment' in the sidebar to register a patient visit. Fill in patient details, choose a dentist and treatment type, then pick a date and time."),
                step("2. Search Appointment", "Click 'Search Appointment' and enter an appointment number to view its full details."),
                step("3. Generate a Bill", "From the search results, choose a payment method (Cash, Card, or Insurance) and click 'Generate & Print Bill' to see the receipt."),
                step("4. Reports", "Click 'Reports' to see today's scheduled appointments and revenue totals by dentist. Use the Refresh button to pull the latest data."),
                step("5. Manage Users", "Admin accounts only: add new staff or admin logins from the 'Manage Users' page."),
                step("6. Quick Access", "Use the floating '+' button in the bottom-right corner from any page to jump straight to New Appointment."),
                step("7. Logging Out", "Click 'Logout' in the top header when you're done, to safely close your session.")
        );

        add(title, card);
    }

    private VerticalLayout step(String heading, String description) {
        VerticalLayout box = new VerticalLayout();
        box.setPadding(false);
        box.setSpacing(false);
        box.getStyle().set("margin-bottom", "18px");

        H3 h = new H3(heading);
        h.getStyle().set("margin", "0 0 4px 0").set("color", "#2C5364");

        Span d = new Span(description);
        d.getStyle().set("color", "#555");

        box.add(h, d);
        return box;
    }
}