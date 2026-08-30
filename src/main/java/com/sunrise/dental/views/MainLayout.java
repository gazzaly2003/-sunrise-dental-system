package com.sunrise.dental.views;

import com.sunrise.dental.model.User;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.VaadinSession;

public class MainLayout extends AppLayout implements RouterLayout {

    public MainLayout() {
        User currentUser = (User) VaadinSession.getCurrent().getAttribute("currentUser");
        if (currentUser == null) {
            getUI().ifPresent(ui -> ui.navigate("login"));
            return;
        }

        DrawerToggle toggle = new DrawerToggle();

        HorizontalLayout logoRow = new HorizontalLayout();
        logoRow.setAlignItems(FlexComponent.Alignment.CENTER);
        logoRow.add(new LogoIcon(28, "#2C5364"));

        H1 logo = new H1("Sunrise Dental");
        logo.getStyle().set("font-size", "1.3rem").set("margin", "0");
        logoRow.add(logo);

        Span welcome = new Span("Welcome back, " + currentUser.getUsername());
        welcome.getStyle().set("margin-left", "auto").set("margin-right", "10px").set("color", "gray");

        Button logout = new Button("Logout", e -> {
            VaadinSession.getCurrent().setAttribute("currentUser", null);
            getUI().ifPresent(ui -> ui.navigate("login"));
        });
        ButtonStyler.outline(logout, "#C0392B");

        HorizontalLayout header = new HorizontalLayout(toggle, logoRow, welcome, logout);
        header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.getStyle().set("padding", "0 20px");
        addToNavbar(header);

        SideNav nav = new SideNav();
        nav.addItem(new SideNavItem("Dashboard", DashboardView.class, VaadinIcon.HOME.create()));
        nav.addItem(new SideNavItem("New Appointment", RegisterAppointmentView.class, VaadinIcon.PLUS_CIRCLE.create()));
        nav.addItem(new SideNavItem("Search Appointment", SearchAppointmentView.class, VaadinIcon.SEARCH.create()));
        nav.addItem(new SideNavItem("Reports", ReportsView.class, VaadinIcon.CHART.create()));

        if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            nav.addItem(new SideNavItem("Manage Users", ManageUsersView.class, VaadinIcon.USERS.create()));
        }

        nav.addItem(new SideNavItem("Help", HelpView.class, VaadinIcon.QUESTION_CIRCLE.create()));

        addToDrawer(nav);
    }
}