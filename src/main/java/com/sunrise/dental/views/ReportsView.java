package com.sunrise.dental.views;

import com.sunrise.dental.dao.ReportDAO;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Map;

@Route(value = "reports", layout = MainLayout.class)
@PageTitle("Reports — Sunrise Dental Clinic")
public class ReportsView extends VerticalLayout {

    private final ReportDAO reportDAO = new ReportDAO();

    private Grid<Map<String, Object>> todayGrid;
    private Grid<Map<String, Object>> revenueGrid;

    public ReportsView() {
        setPadding(true);
        setSpacing(true);

        H2 title = new H2("Reports");

        Button refreshButton = new Button("Refresh Reports", e -> loadData());
        ButtonStyler.outline(refreshButton, "#2C5364");

        H3 todayTitle = new H3("Today's Appointments");
        todayGrid = new Grid<>();
        todayGrid.addColumn(row -> row.get("apptNo")).setHeader("Appt No");
        todayGrid.addColumn(row -> row.get("patientName")).setHeader("Patient");
        todayGrid.addColumn(row -> row.get("dentistId")).setHeader("Dentist");
        todayGrid.addColumn(row -> row.get("treatment")).setHeader("Treatment");
        todayGrid.addColumn(row -> row.get("time")).setHeader("Time");
        todayGrid.addColumn(row -> row.get("status")).setHeader("Status");
        todayGrid.setWidthFull();
        todayGrid.getStyle()
                .set("border-radius", "12px")
                .set("box-shadow", "0 6px 18px rgba(0,0,0,0.08)")
                .set("padding", "4px");

        H3 revenueTitle = new H3("Revenue by Dentist");
        revenueGrid = new Grid<>();
        revenueGrid.addColumn(row -> row.get("dentistId")).setHeader("Dentist ID");
        revenueGrid.addColumn(row -> row.get("name")).setHeader("Name");
        revenueGrid.addColumn(row -> row.get("billCount")).setHeader("Bills Generated");
        revenueGrid.addColumn(row -> row.get("totalRevenue")).setHeader("Total Revenue (Rs.)");
        revenueGrid.setWidthFull();
        revenueGrid.getStyle()
                .set("border-radius", "12px")
                .set("box-shadow", "0 6px 18px rgba(0,0,0,0.08)")
                .set("padding", "4px");

        VerticalLayout todayCard = new VerticalLayout(todayTitle, todayGrid);
        todayCard.getStyle()
                .set("background", "white")
                .set("border-radius", "16px")
                .set("box-shadow", "0 6px 18px rgba(0,0,0,0.08)")
                .set("padding", "20px");
        todayCard.setWidthFull();

        VerticalLayout revenueCard = new VerticalLayout(revenueTitle, revenueGrid);
        revenueCard.getStyle()
                .set("background", "white")
                .set("border-radius", "16px")
                .set("box-shadow", "0 6px 18px rgba(0,0,0,0.08)")
                .set("padding", "20px");
        revenueCard.setWidthFull();

        add(title, refreshButton, todayCard, revenueCard);
        loadData();
    }

    private void loadData() {
        List<Map<String, Object>> today = reportDAO.getTodaysAppointments();
        todayGrid.setItems(today);

        List<Map<String, Object>> revenue = reportDAO.getRevenueByDentist();
        revenueGrid.setItems(revenue);
    }
}