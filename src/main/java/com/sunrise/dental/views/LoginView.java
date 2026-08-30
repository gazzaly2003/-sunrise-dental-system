package com.sunrise.dental.views;

import com.sunrise.dental.model.User;
import com.sunrise.dental.service.AuthService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.router.RouteAlias;

@Route("login")
@RouteAlias("")
@PageTitle("Sunrise Dental Clinic — Login")
public class LoginView extends VerticalLayout {

    private final AuthService authService = new AuthService();

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        getStyle().set("background", "linear-gradient(135deg, #0F2027, #203A43)");

        VerticalLayout card = new VerticalLayout();
        card.setWidth("360px");
        card.getStyle()
                .set("background", "rgba(255, 255, 255, 0.08)")
                .set("backdrop-filter", "blur(12px)")
                .set("border", "1px solid rgba(255,255,255,0.2)")
                .set("border-radius", "20px")
                .set("box-shadow", "0 10px 30px rgba(0,0,0,0.35)")
                .set("padding", "40px");
        card.setAlignItems(Alignment.CENTER);

        com.vaadin.flow.component.orderedlayout.HorizontalLayout titleRow = new com.vaadin.flow.component.orderedlayout.HorizontalLayout();
        titleRow.setAlignItems(Alignment.CENTER);
        titleRow.add(new LogoIcon(42, "#2C5364"));

        H1 title = new H1("Sunrise Dental");
        title.getStyle().set("margin", "0").set("color", "white");
        titleRow.add(title);

        Span subtitle = new Span("Staff Login");
        subtitle.getStyle().set("color", "rgba(255,255,255,0.7)");

        TextField username = new TextField("Username");
        username.setWidthFull();

        PasswordField password = new PasswordField("Password");
        password.setWidthFull();

        Span errorLabel = new Span();
        errorLabel.getStyle().set("color", "#FF8A75").set("font-size", "13px");

        Button loginButton = new Button("Login", e -> {
            errorLabel.setText("");
            User user = authService.login(username.getValue(), password.getValue());
            if (user != null) {
                VaadinSession.getCurrent().setAttribute("currentUser", user);
                getUI().ifPresent(ui -> ui.navigate("dashboard"));
            } else {
                errorLabel.setText("Invalid credentials");
            }
        });
        loginButton.setWidthFull();
        loginButton.getStyle()
                .set("background", "transparent")
                .set("border", "2px solid white")
                .set("color", "white")
                .set("border-radius", "24px")
                .set("font-weight", "600")
                .set("cursor", "pointer")
                .set("transition", "all 0.2s ease");
        loginButton.getElement().executeJs(
                "this.addEventListener('mouseenter', () => { this.style.background='white'; this.style.color='#0F2027'; });" +
                        "this.addEventListener('mouseleave', () => { this.style.background='transparent'; this.style.color='white'; });"
        );

        FormLayout form = new FormLayout();
        form.add(username, password);
        form.setWidthFull();
        card.add(titleRow, subtitle, form, errorLabel, loginButton);
        add(card);
    }
}