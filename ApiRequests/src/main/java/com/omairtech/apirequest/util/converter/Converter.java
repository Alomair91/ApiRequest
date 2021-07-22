package com.omairtech.apirequest.util.converter;

import org.json.JSONObject;

public class Converter {

    private static volatile Converter instance;

    public static Converter getInstance() {
        if (instance == null)
            instance = new Converter();
        return instance;
    }

    public JSONObject stringJson(String response) {
        try {
            String data = response;
            //delete backslashes ( \ ) :
            data = data.replaceAll("[\\\\]{1}[\"]{1}", "\"");
            //delete first and last double quotation ( " ) :
            data = data.substring(data.indexOf("{"), data.lastIndexOf("}") + 1);
            if (response.lastIndexOf("}") != response.length())
                response = response + "}";
            return new JSONObject(data);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response = response.replace("\\\\", "");
                if (response.lastIndexOf("}") != response.length())
                    response = response + "}";
                return new JSONObject((response.substring(response.indexOf("{"))));
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
    }
}
