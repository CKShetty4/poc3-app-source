package com.poc3;

import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {
        var app = Javalin.create().start(8080);
        
        app.get("/", ctx -> ctx.result("Hello Team, This is Demo."));
    }
}
