/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgcatch.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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

public final class catchObject {
    
    private HttpResponse initialResponse;
    private Long n_streams;
    private List<String> tags = new ArrayList<>();
    private String text;
    private String created_at;
    private String modified_at;
    private boolean legacy_v2_share;
    private String server_modified_at;
    private List<String> streams = new ArrayList<>();
    private HashMap user = new HashMap();
    private String type;
    private String server_deleted_at;
    private String id;
    private boolean checked;
    private String child_of;
    private List<String> children = new ArrayList<>();
    private HashMap annotations = new HashMap();
    private int size;
    private String filename;
    private String content_type;
    
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
    private static final String TAG_N_STREAMS = "n_streams";
    private static final String TAG_STREAMS = "streams";
    private static final String TAG_TAGS = "tags";
    private static final String TAG_CHILDREN = "children";
    private static final String TAG_CATCH_STARRED = "catch:starred";
    private static final String TAG_CHILD_OF = "child_of";
    private static final String TAG_LEGACY_V2_SHARE = "legacy_v2_share";
    private static final String TAG_USER = "user";
    private static final String TAG_SIZE = "size";
    private static final String TAG_USER_NAME = "user_name";
    private static final String TAG_CONTENT_TYPE = "content_type";
    private static final String TAG_FILENAME = "filename";

    public int getSize() {
        return size;
    }

    public String getFilename() {
        return filename;
    }

    public String getContent_type() {
        return content_type;
    }
    

    public HttpResponse getInitialResponse() {
        return initialResponse;
    }

    public HashMap getAnnotations() {
        return annotations;
    }

    public Long getN_streams() {
        return n_streams;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getText() {
        return text;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getModified_at() {
        return modified_at;
    }

    public boolean isLegacy_v2_share() {
        return legacy_v2_share;
    }

    public String getServer_modified_at() {
        return server_modified_at;
    }

    public List<String> getStreams() {
        return streams;
    }

    public HashMap getUser() {
        return user;
    }

    public String getType() {
        return type;
    }

    public String getServer_deleted_at() {
        return server_deleted_at;
    }

    public String getId() {
        return id;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getChild_of() {
        return child_of;
    }

    public List<String> getChildren() {
        return children;
    }

    public catchObject (HttpResponse response) throws IOException, ParseException{
        initialResponse = response;
        setConfiguration();
    
    }
    
    public void setConfiguration() throws IOException, ParseException{
        BufferedReader rd = new BufferedReader(new InputStreamReader(initialResponse.getEntity().getContent()));
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
        n_streams = (Long) jsonResult.get(TAG_N_STREAMS);
        type = (String) jsonResult.get(TAG_TYPE);
        
        if("checkitem".equals(type)){
            checked =  false;
        }
        
        created_at = (String) jsonResult.get(TAG_CREATED_AT);
        id = (String) jsonResult.get(TAG_ID);
        legacy_v2_share = (boolean) jsonResult.get(TAG_LEGACY_V2_SHARE);
        modified_at = (String) jsonResult.get(TAG_MODIFIED_AT);
        server_deleted_at = (String) jsonResult.get(TAG_SERVER_DELETED_AT);
        server_modified_at = (String) jsonResult.get(TAG_SERVER_MODIFIED_AT);
        
        JSONArray streamsJson = (JSONArray) jsonResult.get(TAG_STREAMS);
        String[] streamsString = streamsJson.toString().substring(1,streamsJson.toString().length()-1).replaceAll("\"","").split(",");
        streams.addAll(Arrays.asList(streamsString));
        
        JSONArray tagsJson = (JSONArray) jsonResult.get(TAG_TAGS);
        String[] tagsString = tagsJson.toString().substring(1,tagsJson.toString().length()-1).replaceAll("\"","").split(",");
        tags.addAll(Arrays.asList(tagsString));
        
        if(((JSONArray) jsonResult.get(TAG_CHILDREN)) != null){
            JSONArray childrenJson = (JSONArray) jsonResult.get(TAG_CHILDREN);
            String[] childrenString = childrenJson.toString().substring(1,childrenJson.toString().length()-1).replaceAll("\"","").split(",");
            tags.addAll(Arrays.asList(childrenString));
        }
        
        Object result2 = jsonResult.get(TAG_USER);
        JSONObject jsonResult2 = (JSONObject) result2;        
        user.put(TAG_USER_NAME, jsonResult2.get(TAG_USER_NAME));
        user.put(TAG_ID, jsonResult2.get(TAG_ID));
        
        Object result3 = jsonResult.get(TAG_ANNOTATIONS);
        if (result3 != null){
            JSONObject jsonResult3 = (JSONObject) result3;        
            if(jsonResult3.get(TAG_CATCH_STARRED) != null){
                annotations.put(TAG_CATCH_STARRED, jsonResult3.get(TAG_CATCH_STARRED));
            }
            
        }

        if(jsonResult.get(TAG_CHILD_OF) != null){
            child_of = (String) jsonResult.get(TAG_CHILD_OF);
        }
        
        if(jsonResult.get(TAG_FILENAME) != null){
            filename = (String) jsonResult.get(TAG_FILENAME);
        }
        
        if(jsonResult.get(TAG_CONTENT_TYPE) != null){
            content_type = (String) jsonResult.get(TAG_CONTENT_TYPE);
        }
        
        if(jsonResult.get(TAG_SIZE) != null){
            size = (int) jsonResult.get(TAG_SIZE);
        }
    }
    
    public void updateAfterEditing(HttpResponse response) throws IOException, ParseException{
        
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
        server_modified_at = (String) jsonResult.get(TAG_SERVER_MODIFIED_AT);
        type = (String) jsonResult.get(TAG_TYPE);
        id = (String) jsonResult.get(TAG_ID);
    
    }
    
    public void checkCheckitem(){
        checked = true;
    }
    
    public void uncheckCheckitem(){
        checked = false;
    }
    
    public void updateAfterGet(HttpResponse response) throws IOException, ParseException{
        
        initialResponse = response;
        setConfiguration();
        
    }
    
    
    
    
    
}
