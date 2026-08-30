package com.sunrise.dental.views;

import com.vaadin.flow.component.button.Button;

public class ButtonStyler {

    public static void outline(Button button, String colorHex) {
        button.getStyle()
                .set("background", "transparent")
                .set("border", "2px solid " + colorHex)
                .set("color", colorHex)
                .set("border-radius", "24px")
                .set("font-weight", "600")
                .set("cursor", "pointer")
                .set("transition", "all 0.2s ease");

        button.getElement().executeJs(
                "this.addEventListener('mouseenter', () => { this.style.background='" + colorHex + "'; this.style.color='white'; });" +
                        "this.addEventListener('mouseleave', () => { this.style.background='transparent'; this.style.color='" + colorHex + "'; });"
        );
    }

    public static void outlineWhite(Button button) {
        button.getStyle()
                .set("background", "transparent")
                .set("border", "2px solid white")
                .set("color", "white")
                .set("border-radius", "24px")
                .set("font-weight", "600")
                .set("cursor", "pointer")
                .set("transition", "all 0.2s ease");

        button.getElement().executeJs(
                "this.addEventListener('mouseenter', () => { this.style.background='white'; this.style.color='#0F2027'; });" +
                        "this.addEventListener('mouseleave', () => { this.style.background='transparent'; this.style.color='white'; });"
        );
    }
}