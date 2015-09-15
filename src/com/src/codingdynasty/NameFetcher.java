package com.src.codingdynasty;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class NameFetcher implements Callable<String>{
    private static final String PROFILE_URL = "https://api.mojang.com/user/profiles/";
    private final JSONParser jsonParser = new JSONParser();
    private final String id;
    public NameFetcher(String id) {
        this.id = id;
    }

    @Override
	public String call() throws Exception {
        String nameFinal = "";
        	HttpURLConnection connection = createConnection(id);

        	if(connection.getResponseCode() == 200){
            JSONArray jsonObject = (JSONArray) jsonParser.parse(new InputStreamReader(connection.getInputStream()));

            JSONObject name = (JSONObject) jsonObject.get(jsonObject.size() - 1);
            
            nameFinal = (String) name.get("name");
        		
        	}
        	return nameFinal;
    }



    private static HttpURLConnection createConnection(String playerName) throws Exception {
        URL url = new URL(PROFILE_URL+playerName+"/names");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setUseCaches(true);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        return connection;
    }

}
