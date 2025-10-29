package com.example.helloworld;

import com.indvd00m.ascii.render.api.IRender;
import com.indvd00m.ascii.render.api.ICanvas;
import com.indvd00m.ascii.render.api.IContextBuilder;
import com.indvd00m.ascii.render.Render;
import com.indvd00m.ascii.render.elements.Rectangle;
import com.indvd00m.ascii.render.elements.PseudoText;


import java.util.Locale;
import java.util.Map;

public class Main {

    private static final Map<String, String> GREETINGS = Map.of(
            "hun", "Szia",
            "eng", "Hello",
            "hin", "Namaste"
    );

    public static void main(String[] args) {
        if (args.length == 0 || args[0].isBlank()) {
            System.err.println("Usage: java -jar hello-world.jar <name> [language]");
            System.err.println("Languages: hun (default), eng, hin");
            System.exit(1);
        }

        String name = args[0];
        String lang = (args.length >= 2) ? args[1].toLowerCase(Locale.ROOT) : "hun";
        String greet = GREETINGS.get(lang);
        if (greet == null) {
            System.err.println("Error: unknown language '" + lang + "'. Supported: hun, eng, hin");
            System.exit(2);
        }

        String message = greet + ", " + name + "!";

        IRender render = new Render();
        IContextBuilder builder = render.newBuilder();

        int width = Math.max(60, message.length() + 10);
        int height = 10;

        builder.width(width).height(height);
        builder.element(new Rectangle(0, 0, width - 1, height - 1));
        builder.element(new PseudoText(message)); // no antialiasing/font here

        ICanvas canvas = render.render(builder.build());
        System.out.println(canvas.getText());


    }
}
