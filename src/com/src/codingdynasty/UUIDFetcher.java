package com.src.codingdynasty;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UUIDFetcher implements Callable<Map<String, UUID>> {
    private static final String PROFILE_URL = "https://api.mojang.com/users/profiles/minecraft/";
    private final JSONParser jsonParser = new JSONParser();
    private final String name;
    public UUIDFetcher(String name) {
        this.name = name;
    }

    @Override
	public Map<String, UUID> call() throws Exception {
        Map<String, UUID> uuidMap = new HashMap<String, UUID>();
        	HttpURLConnection connection = createConnection(name);

        	if(connection.getResponseCode() == 200){
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(connection.getInputStream()));
            connection.disconnect();
            String digits = (String) jsonObject.get("id");
            String uuidString = digits.replaceAll(                                            
            	    "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",                            
            	    "$1-$2-$3-$4-$5"); 
            UUID uuid = UUID.fromString(uuidString);
            System.out.println(uuid);
            jFrame.uuidRaw = digits;
            uuidMap.put(name, uuid);
        		
        	}
        	return uuidMap;
    }



    private static HttpURLConnection createConnection(String playerName) throws Exception {
        URL url = new URL(PROFILE_URL+playerName);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setUseCaches(true);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        return connection;
    }

}