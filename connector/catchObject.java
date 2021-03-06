/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connector;

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

    public void setN_streams(Long n_streams) {
        this.n_streams = n_streams;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setModified_at(String modified_at) {
        this.modified_at = modified_at;
    }

    public void setLegacy_v2_share(boolean legacy_v2_share) {
        this.legacy_v2_share = legacy_v2_share;
    }

    public void setServer_modified_at(String server_modified_at) {
        this.server_modified_at = server_modified_at;
    }

    public void setStreams(List<String> streams) {
        this.streams = streams;
    }

    public void setUser(HashMap user) {
        this.user = user;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setServer_deleted_at(String server_deleted_at) {
        this.server_deleted_at = server_deleted_at;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setChild_of(String child_of) {
        this.child_of = child_of;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public void setCount(long count) {
        this.count = count;
    }
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
    private long count;
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
    private static final String TAG_TEXT = "text";

    public long getCount() {
        return count;
    }

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

    public catchObject(HttpResponse response) throws IOException, ParseException {
        initialResponse = response;
        setConfiguration();

    }

    public catchObject() {
    }

    public catchObject(Long n_streams, String text, String created_at, String modified_at, boolean legacy_v2_share, String server_modified_at, String type, String server_deleted_at, String id, boolean checked, String child_of, int size, String filename, String content_type, long count, HashMap user) {
        this.n_streams = n_streams;
        this.text = text;
        this.created_at = created_at;
        this.modified_at = modified_at;
        this.legacy_v2_share = legacy_v2_share;
        this.server_modified_at = server_modified_at;
        this.type = type;
        this.server_deleted_at = server_deleted_at;
        this.id = id;
        this.checked = checked;
        this.child_of = child_of;
        this.size = size;
        this.filename = filename;
        this.content_type = content_type;
        this.count = count;
        this.user = user;
        
    }

    public void setConfiguration() throws IOException, ParseException {
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

        if (jsonResult.get(TAG_N_STREAMS) != null) {
            n_streams = (Long) jsonResult.get(TAG_N_STREAMS);
        }
        if (jsonResult.get(TAG_TYPE) != null) {
            type = (String) jsonResult.get(TAG_TYPE);
        }
        if ("checkitem".equals(type)) {
            checked = false;
        }

        text = (String) jsonResult.get(TAG_TEXT);
       // if(text.contains("\\#")) {
         
         text = text.replaceAll("'","''");
      //  }
        count = (long) jsonResult.get(TAG_COUNT);
        created_at = (String) jsonResult.get(TAG_CREATED_AT);
        id = (String) jsonResult.get(TAG_ID);
        legacy_v2_share = (boolean) jsonResult.get(TAG_LEGACY_V2_SHARE);
        modified_at = (String) jsonResult.get(TAG_MODIFIED_AT);
        server_deleted_at = (String) jsonResult.get(TAG_SERVER_DELETED_AT);
        server_modified_at = (String) jsonResult.get(TAG_SERVER_MODIFIED_AT);

        JSONArray streamsJson = (JSONArray) jsonResult.get(TAG_STREAMS);
        String[] streamsString = streamsJson.toString().substring(1, streamsJson.toString().length() - 1).replaceAll("\"", "").split(",");
        streams.addAll(Arrays.asList(streamsString));

        JSONArray tagsJson = (JSONArray) jsonResult.get(TAG_TAGS);
        String[] tagsString = tagsJson.toString().substring(1, tagsJson.toString().length() - 1).replaceAll("\"", "").split(",");
        tags.addAll(Arrays.asList(tagsString));

        if (((JSONArray) jsonResult.get(TAG_CHILDREN)) != null) {
            JSONArray childrenJson = (JSONArray) jsonResult.get(TAG_CHILDREN);
            String[] childrenString = childrenJson.toString().substring(1, childrenJson.toString().length() - 1).replaceAll("\"", "").split(",");
            children.addAll(Arrays.asList(childrenString));
        }

        Object result2 = jsonResult.get(TAG_USER);
        JSONObject jsonResult2 = (JSONObject) result2;
        user.put(TAG_USER_NAME, jsonResult2.get(TAG_USER_NAME));
        user.put(TAG_ID, jsonResult2.get(TAG_ID));

        Object result3 = jsonResult.get(TAG_ANNOTATIONS);
        if (result3 != null) {
            JSONObject jsonResult3 = (JSONObject) result3;
            if (jsonResult3.get(TAG_CATCH_STARRED) != null) {
                annotations.put(TAG_CATCH_STARRED, jsonResult3.get(TAG_CATCH_STARRED));
            }

        }

        if (jsonResult.get(TAG_CHILD_OF) != null) {
            child_of = (String) jsonResult.get(TAG_CHILD_OF);
        }

        if (jsonResult.get(TAG_FILENAME) != null) {
            filename = (String) jsonResult.get(TAG_FILENAME);
        }

        if (jsonResult.get(TAG_CONTENT_TYPE) != null) {
            content_type = (String) jsonResult.get(TAG_CONTENT_TYPE);
        }

        if (jsonResult.get(TAG_SIZE) != null) {
            size = (int) jsonResult.get(TAG_SIZE);
        }
    }

    public void updateAfterEditing(HttpResponse response) throws IOException, ParseException {

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
        server_modified_at = (String) jsonResult.get(TAG_SERVER_MODIFIED_AT);
        type = (String) jsonResult.get(TAG_TYPE);
        id = (String) jsonResult.get(TAG_ID);

    }

    public void checkCheckitem() {
        checked = true;
    }

    public void uncheckCheckitem() {
        checked = false;
    }

    public void updateAfterGet(HttpResponse response) throws IOException, ParseException {

        initialResponse = response;
        setConfiguration();

    }

    public List<String> extractTagsFromText() {

        List<String> foundedTags = new ArrayList<>();
        String[] parts = text.split("#");
        for (int i = 1; i < parts.length; i++) {
            String[] parts2 = parts[i].split(" ");
            if (!foundedTags.contains(parts2[0])) {
                foundedTags.add(parts2[0]);
            }
        }

        return foundedTags;

    }
}
