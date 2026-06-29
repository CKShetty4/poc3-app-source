package com.poc3;

import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {
        // Starts an absolute basic microservice running on port 8080
        var app = Javalin.create().start(8080);
        
        app.get("/", ctx -> ctx.result("Hello from PoC 3! GitOps Pipeline Working Perfectly."));
    }
}
