package com.sunrise.dental.views;

import com.sunrise.dental.dao.UserDAO;
import com.sunrise.dental.model.User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "users", layout = MainLayout.class)
@PageTitle("Manage Users — Sunrise Dental Clinic")
public class ManageUsersView extends VerticalLayout {

    private final UserDAO userDAO = new UserDAO();

    private Grid<User> userGrid;
    private TextField usernameField;
    private PasswordField passwordField;
    private ComboBox<String> roleCombo;

    public ManageUsersView() {
        setPadding(true);
        setSpacing(true);

        H2 title = new H2("Manage Users");

        userGrid = new Grid<>(User.class, false);
        userGrid.addColumn(User::getUsername).setHeader("Username");
        userGrid.addColumn(User::getRole).setHeader("Role");
        userGrid.setWidthFull();
        userGrid.getStyle()
                .set("border-radius", "12px")
                .set("box-shadow", "0 6px 18px rgba(0,0,0,0.08)")
                .set("padding", "4px");

        H3 addTitle = new H3("Add New User");

        usernameField = new TextField("Username");
        passwordField = new PasswordField("Password");
        roleCombo = new ComboBox<>("Role");
        roleCombo.setItems("STAFF", "ADMIN");
        roleCombo.setValue("STAFF");

        Button addButton = new Button("Add User", e -> addUser());
        ButtonStyler.outline(addButton, Theme.SUCCESS);
        HorizontalLayout form = new HorizontalLayout(usernameField, passwordField, roleCombo, addButton);
        form.setAlignItems(FlexComponent.Alignment.END);

        VerticalLayout gridCard = new VerticalLayout(userGrid);
        gridCard.getStyle()
                .set("background", "white")
                .set("border-radius", "16px")
                .set("box-shadow", "0 6px 18px rgba(0,0,0,0.08)")
                .set("padding", "20px");
        gridCard.setWidthFull();

        VerticalLayout formCard = new VerticalLayout(addTitle, form);
        formCard.getStyle()
                .set("background", "white")
                .set("border-radius", "16px")
                .set("box-shadow", "0 6px 18px rgba(0,0,0,0.08)")
                .set("padding", "20px");
        formCard.setWidthFull();

        add(title, gridCard, formCard);
        loadUsers();
    }

    private void loadUsers() {
        List<User> users = userDAO.getAllUsers();
        userGrid.setItems(users);
    }

    private void addUser() {
        String username = usernameField.getValue().trim();
        String password = passwordField.getValue().trim();
        String role = roleCombo.getValue();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Username and password are required.");
            return;
        }

        boolean created = userDAO.createUser(new User(username, password, role));

        if (created) {
            Notification success = Notification.show("User added successfully!", 3000, Notification.Position.TOP_CENTER);
            success.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            usernameField.clear();
            passwordField.clear();
            loadUsers();
        } else {
            showError("Failed to add user. Username may already exist.");
        }
    }

    private void showError(String message) {
        Notification error = Notification.show(message, 4000, Notification.Position.TOP_CENTER);
        error.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}