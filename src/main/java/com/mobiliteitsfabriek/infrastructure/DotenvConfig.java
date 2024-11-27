package com.mobiliteitsfabriek.infrastructure;

import io.github.cdimascio.dotenv.Dotenv;

public class DotenvConfig {
    public static String NS_API_KEY;

    public DotenvConfig() {
        Dotenv dotenv = Dotenv.load();
        NS_API_KEY = dotenv.get("NS_API_KEY");
        if(NS_API_KEY == null){
            throw new RuntimeException("NS_API_KEY is not defined in .env");
        }
    }
}
