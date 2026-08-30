package com.sunrise.dental.views;

import com.vaadin.flow.component.Html;

public class LogoIcon extends Html {

    public LogoIcon(int size, String hexColor) {
        super(buildSvg(size, hexColor));
    }

    private static String buildSvg(int size, String hexColor) {
        return "<span style='display:inline-block;width:" + size + "px;height:" + size + "px;'>"
                + "<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 100 100' width='" + size + "' height='" + size + "'>"
                + "<path fill='" + hexColor + "' stroke='#00000020' stroke-width='1.5' d='"
                + "M50,5 C75,5 85,30 78,50 C74,62 68,72 62,90 "
                + "C60,95 56,95 54,88 C52,80 52,65 50,60 "
                + "C48,65 48,80 46,88 C44,95 40,95 38,90 "
                + "C32,72 26,62 22,50 C15,30 25,5 50,5 Z'/>"
                + "</svg></span>";
    }
}