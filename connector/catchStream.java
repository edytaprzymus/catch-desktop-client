/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgcatch.connector;

import connector.miniObject;
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
public final class catchStream {

    private HttpResponse initialResponse;
    private int count;
    private int contributor_count;
    private String name;
    private String source;
    private String created_at;
    private String modified_at;
    private HashMap annotations = new HashMap();
    private List<miniObject> objects = new ArrayList<>();
    private String server_deleted_at;
    private String id;
    private String server_created_at;
    private String server_modified_at;
    private HashMap contributors = new HashMap();
    private List<String> user_idList = new ArrayList<>();
    private List<String> user_nameList = new ArrayList<>();
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
    private static final String TAG_CONTRIBUTORS = "contributors";

    //dodac konstruktor i funkcje "update afer get on this id"
    public int getCount() {
        return count;
    }

    public int getContributor_count() {
        return contributor_count;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getModified_at() {
        return modified_at;
    }

    public HashMap getAnnotations() {
        return annotations;
    }

    public List<miniObject> getObjects() {
        return objects;
    }

    public String getServer_deleted_at() {
        return server_deleted_at;
    }

    public String getId() {
        return id;
    }

    public String getServer_created_at() {
        return server_created_at;
    }

    public HashMap getContributors() {
        return contributors;
    }

    public List<String> getUser_idList() {
        return user_idList;
    }

    public List<String> getUser_nameList() {
        return user_nameList;
    }

    public String getServerModifiedAt() {
        return server_modified_at;
    }

    catchStream(HttpResponse response) throws IOException, ParseException {

        initialResponse = response;
        SetConfiguration(false);


    }

    public catchStream() {
    }

    public catchStream(int count, int contributor_count, String name, String source, String created_at, String modified_at, String server_deleted_at, String id, String server_created_at, String server_modified_at) {
        this.count = count;
        this.contributor_count = contributor_count;
        this.name = name;
        this.source = source;
        this.created_at = created_at;
        this.modified_at = modified_at;
        this.server_deleted_at = server_deleted_at;
        this.id = id;
        this.server_created_at = server_created_at;
        this.server_modified_at = server_modified_at;
    }

    public void SetConfiguration(boolean get) throws IOException, ParseException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(initialResponse.getEntity().getContent()));
        String line;
        String str = "";
        while ((line = rd.readLine()) != null) {
            str += line;
        }
        String replaceAll = str.replaceAll("\\s", "");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(str);
        JSONObject jsonObject = (JSONObject) obj;
        Object result = jsonObject.get(TAG_RESULT);
        JSONObject jsonResult = (JSONObject) result;
        name = (String) jsonResult.get(TAG_NAME);
        source = (String) jsonResult.get(TAG_SOURCE);
        created_at = (String) jsonResult.get(TAG_CREATED_AT);
        id = (String) jsonResult.get(TAG_ID);
        modified_at = (String) jsonResult.get(TAG_MODIFIED_AT);
        server_deleted_at = (String) jsonResult.get(TAG_SERVER_DELETED_AT);
        server_modified_at = (String) jsonResult.get(TAG_SERVER_MODIFIED_AT);
        server_created_at = (String) jsonResult.get(TAG_SERVER_CREATED_AT);

        //   if (get){

        Object jsonAnnotations = jsonResult.get(TAG_ANNOTATIONS);
        if (jsonAnnotations != null) {
            JSONObject jsonAnnotationsObject = (JSONObject) jsonAnnotations;
            if (jsonAnnotationsObject.get(TAG_COLOR) != null) {
                annotations.put(TAG_COLOR, jsonAnnotationsObject.get(TAG_COLOR));
            }
            if (jsonAnnotationsObject.get(TAG_USER_COLOR) != null) {
                annotations.put(TAG_USER_COLOR, jsonAnnotationsObject.get(TAG_USER_COLOR));
            }

        }

        JSONArray objectsJson = (JSONArray) jsonResult.get(TAG_OBJECTS);
        for (int i = 0; i < objectsJson.size(); i++) {
            JSONObject objectJson = (JSONObject) objectsJson.get(i);
            miniObject mini = new miniObject((String) objectJson.get(TAG_ID), (String) objectJson.get(TAG_SERVER_MODIFIED_AT), (String) objectJson.get(TAG_TYPE));
            //  System.out.println("dodalem obiekt mini o id" + mini.getId());
            objects.add(mini);
        }
        // }

    }

    public void updateStreamAfterGet(HttpResponse getResponse) throws IOException, ParseException {

        initialResponse = getResponse;

        SetConfiguration(true);



    }

    public void setContributorsMap(HttpResponse response) throws IOException, ParseException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line;
        String str = "";
        while ((line = rd.readLine()) != null) {
            str += line;
        }
        String replaceAll = str.replaceAll("\\s", "");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(str);
        JSONObject jsonObject = (JSONObject) obj;
        Object result = jsonObject.get(TAG_RESULT);
        JSONObject jsonResult = (JSONObject) result;
        Object contribs = jsonResult.get(TAG_CONTRIBUTORS);
        JSONArray jsonContributors = (JSONArray) contribs;
        for (int i = 0; i < jsonContributors.size(); i++) {
            JSONObject rec = (JSONObject) jsonContributors.get(i);
            String uname = (String) rec.get("user_name");
            System.out.println("uname " + uname + i);
            String uid = (String) rec.get("id");
            user_idList.add(uid);
            user_nameList.add(uname);
            System.out.println("id usera w users todwed " + user_idList.get(i) + "a jego name todewdew " + user_nameList.get(i) + "  " + i);
            Object put = contributors.put(uid, uname);
        }
    }
}
