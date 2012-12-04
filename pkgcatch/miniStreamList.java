/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgcatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author edytka
 */
public class miniStreamList {
    
    private List<miniStream> streams = new ArrayList<>();
    private static final String TAG_COUNT = "count";
    private static final String TAG_RESULT = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_TYPE = "type";
    private static final String TAG_SOURCE = "source";
    private static final String TAG_NAME = "name";
    private static final String TAG_CREATED_AT = "created_at";
    private static final String TAG_MODIFIED_AT = "modified_at";
    private static final String TAG_ANNOTATIONS = "annotations";
    private static final String TAG_COLOR = "color";
    private static final String TAG_USER_COLOR = "user:color";
    private static final String TAG_SERVER_MODIFIED_AT = "server_modified_at";
    private static final String TAG_OBJECTS = "objects";
    private static final String TAG_SERVER_DELETED_AT = "server_deleted_at";
    private static final String TAG_SERVER_CREATED_AT = "server_created_at";
    private static final String TAG_STREAMS = "streams";
    private static final String TAG_CONTRIBUTOR_COUNT = "contributor_count";

    public List<miniStream> getStreams() {
        return streams;
    }
    
    
    
    public miniStreamList(HttpResponse response) throws IOException, ParseException{
        
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line;
        String str = "";
        while ((line = rd.readLine()) != null) {
            str += line;
        }
        String replaceAll = str.replaceAll("\\s","");
        JSONParser parser=new JSONParser();
        Object obj=parser.parse(str);
        JSONObject jsonObject = (JSONObject) obj;
        Object result = jsonObject.get(TAG_RESULT);
        JSONObject jsonResult = (JSONObject) result;
        JSONArray objectsJson = (JSONArray) jsonResult.get(TAG_STREAMS);
        for(int i = 0; i< objectsJson.size(); i++){
            JSONObject objectJson = (JSONObject) objectsJson.get(i);
            HashMap hm = new HashMap();
            Object annotation = objectJson.get(TAG_ANNOTATIONS);
            JSONObject annotationJson = (JSONObject) annotation;
            if(annotationJson != null) {
                hm.put(TAG_USER_COLOR, annotationJson.get(TAG_USER_COLOR));
            }
            miniStream mini = new miniStream((String) objectJson.get(TAG_ID), (String) objectJson.get(TAG_SERVER_MODIFIED_AT),(Long) objectJson.get(TAG_CONTRIBUTOR_COUNT), (String) objectJson.get(TAG_SERVER_CREATED_AT),(String) objectJson.get(TAG_NAME), hm );
            streams.add(mini);
        }
        
    }
    
}
