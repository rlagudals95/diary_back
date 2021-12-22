package com.cos.jwtex01.service;

import java.io.*;
import java.net.*;
import com.google.gson.*;
import javax.net.ssl.HttpsURLConnection;

import org.springframework.stereotype.Service;

@Service
public class SpellService {
	
	static String host = "https://api.cognitive.microsoft.com";
	static String path = "/bing/v7.0/spellcheck";

	static String key = "23c42a93-5dfb-4223-baf5-2a99d7ea52f6";

	static String mkt = "ko";
	static String mode = "proof";
	//static String text = "Hollo, wrld!";
	
	public static void check (String text) throws Exception {
	    String params = "?mkt=" + mkt + "&mode=" + mode;
	    
	    URL url = new URL(host + path + params);
	    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
	    
	    connection.setRequestMethod("POST");
	    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    connection.setRequestProperty("Ocp-Apim-Subscription-Key", key);
	    connection.setDoOutput(true);
	    
	    DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
	    wr.writeBytes("text=" + text);
	    wr.flush();
	    wr.close();
	    
	    BufferedReader in = new BufferedReader(
		new InputStreamReader(connection.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
		    System.out.println(prettify(line));
		}
		in.close();
	}
	
	public static String prettify(String json_text) {
	    JsonParser parser = new JsonParser();
	    JsonElement json = parser.parse(json_text);
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    return gson.toJson(json);
	}
	
	
}
