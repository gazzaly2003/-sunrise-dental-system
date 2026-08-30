package com.sunrise.dental.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard — Sunrise Dental Clinic")
public class DashboardView extends VerticalLayout {

    public DashboardView() {
        setSizeFull();
        setPadding(true);

        HorizontalLayout heroRow = new HorizontalLayout();
        heroRow.setAlignItems(FlexComponent.Alignment.CENTER);
        heroRow.add(new LogoIcon(56, "#F2A88C"));

        H1 heading = new H1("Sunrise Dental Clinic");
        heading.getStyle().set("margin", "0 0 0 8px");
        heroRow.add(heading);

        Span sub = new Span("Your clinic's day, organized in one place.");
        sub.getStyle().set("color", "gray");

        HorizontalLayout quoteBar = new HorizontalLayout();
        quoteBar.setWidthFull();
        quoteBar.setMaxWidth("900px");
        quoteBar.getStyle()
                .set("background", "linear-gradient(135deg, #7EC8E3, #9AD8C2)")
                .set("border-radius", "14px")
                .set("padding", "16px 24px")
                .set("margin", "16px 0");

        LogoIcon quoteIcon = new LogoIcon(26, "white");
        quoteIcon.getStyle().set("margin-right", "12px");

        Span quoteText = new Span("Brush your teeth two times a day to have a healthy teeth.");
        quoteText.getStyle()
                .set("color", "white")
                .set("font-weight", "600")
                .set("font-style", "italic");

        quoteBar.add(quoteIcon, quoteText);
        quoteBar.setAlignItems(FlexComponent.Alignment.CENTER);

        com.vaadin.flow.component.html.Image heroImage = new com.vaadin.flow.component.html.Image("images/clinic-hero.jpg", "Sunrise Dental Clinic");
        heroImage.setWidthFull();
        heroImage.setMaxWidth("900px");
        heroImage.getStyle()
                .set("height", "280px")
                .set("object-fit", "cover")
                .set("border-radius", "20px")
                .set("box-shadow", "0 10px 30px rgba(0,0,0,0.15)")
                .set("margin", "20px 0");

        FlexLayout cardsRow = new FlexLayout();
        cardsRow.setWidthFull();
        cardsRow.getStyle().set("gap", "20px").set("flex-wrap", "wrap").set("margin-top", "30px");

        cardsRow.add(
                featureCard("Appointments", "Register and manage patient visits", "register", "#7EC8E3"),
                featureCard("Billing", "Search records and generate receipts", "search", "#F2A88C"),
                featureCard("Reports", "Daily activity and revenue insights", "reports", "#9AD8C2")
        );

        add(heroRow, sub, quoteBar, heroImage, cardsRow);
    }

    private VerticalLayout featureCard(String title, String description, String route, String accentColor) {
        VerticalLayout card = new VerticalLayout();
        card.setWidth("220px");
        card.getStyle()
                .set("background", "white")
                .set("border-radius", "16px")
                .set("box-shadow", "0 4px 14px rgba(0,0,0,0.08)")
                .set("padding", "22px")
                .set("cursor", "pointer")
                .set("transition", "transform 0.2s ease, box-shadow 0.2s ease");

        Span dot = new Span("●");
        dot.getStyle().set("color", accentColor).set("font-size", "22px");

        H3 titleLabel = new H3(title);
        titleLabel.getStyle().set("margin", "8px 0 4px 0");

        Span desc = new Span(description);
        desc.getStyle().set("color", "gray").set("font-size", "13px");

        card.add(dot, titleLabel, desc);
        card.getElement().addEventListener("click", e -> getUI().ifPresent(ui -> ui.navigate(route)));

        card.getElement().executeJs(
                "this.addEventListener('mouseenter', () => { " +
                        "  this.style.transform = 'translateY(-6px)'; " +
                        "  this.style.boxShadow = '0 12px 24px rgba(0,0,0,0.15)'; " +
                        "});" +
                        "this.addEventListener('mouseleave', () => { " +
                        "  this.style.transform = 'translateY(0)'; " +
                        "  this.style.boxShadow = '0 4px 14px rgba(0,0,0,0.08)'; " +
                        "});"
        );

        return card;
    }
}