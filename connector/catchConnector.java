/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connector;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 *
 * @author edytka
 * 
 */
class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {

    public static final String METHOD_NAME = "DELETE";

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }

    public HttpDeleteWithBody(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    public HttpDeleteWithBody(final URI uri) {
        super();
        setURI(uri);
    }

    public HttpDeleteWithBody() {
        super();
    }
}

public class catchConnector {

    private String encodedLoginData;
    private String login;
    private static String basicUrl = "https://api.catch.com/v3/";
    private HttpClient httpClient = new DefaultHttpClient();
    private static HttpEntity entity  = null;

    public catchConnector(String login, String password) {

        this.login = login;
        String stringToConvert = login + ":" + password;
        byte[] theByteArray = stringToConvert.getBytes();
        Base64 base64 = new Base64();
        encodedLoginData = base64.encodeAsString(theByteArray);

    }
    
    /**
     * Metodka odpalana tylko przy uruchomieniu okna, zczytane z bazy dane pakuje do connectora zebym mogl synchronizowac
     * @param login
     * @param encodedLoginData
     * @param db 
     */
    public catchConnector(String login, String encodedLoginData,boolean db) {

        this.login = login;
        this.encodedLoginData = encodedLoginData;

    }

    public String getEncodedLoginData() {
        return encodedLoginData;
    }
    
    public String getLogin() {
        return login;
    }

    public String getStreamsUrl() {

        return basicUrl + "streams";

    }

    public String getInvitesUrl() {

        return basicUrl + "invites";

    }

    public String getObjectUrlInSync(String objectId) {

        return basicUrl + "streams/sync/" + objectId;

    }

    public String getObjectUrlInStream(String streamId, String objectId) {

        return basicUrl + "streams/" + streamId + "/" + objectId;

    }

    public String getContributorsUrl(String streamId) {

        return basicUrl + "streams/" + streamId + "/contributors";

    }

    public String getContributorUrl(String streamId, String contributorId) {

        return basicUrl + "streams/" + streamId + "/contributors/" + contributorId;

    }

    public String getStreamUrl(String streamID) {

        return basicUrl + "streams/" + streamID;

    }

    public HttpResponse createStream(String streamName) throws UnsupportedEncodingException, IOException {

        HttpPost post = new HttpPost(basicUrl + "streams");
        post.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(1);
        nameValuePairs.add(new BasicNameValuePair("name", streamName));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(post);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse getStreams() throws UnsupportedEncodingException, IOException {

        HttpGet get = new HttpGet(getStreamsUrl());
        get.setHeader("Authorization", "Basic " + encodedLoginData);
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(get);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse getStream(String streamId) throws UnsupportedEncodingException, IOException {

        HttpGet get = new HttpGet(getStreamUrl(streamId));
        get.setHeader("Authorization", "Basic " + encodedLoginData);
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(get);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse getObjectsInStream(String streamId, String objectId) throws UnsupportedEncodingException, IOException {

        HttpGet get = new HttpGet(getObjectUrlInStream(streamId, objectId));
        get.setHeader("Authorization", "Basic " + encodedLoginData);
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(get);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse inviteToStream(String email, String streamId) throws UnsupportedEncodingException, IOException {

        HttpPost post = new HttpPost(getInvitesUrl());
        post.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("type", "email"));
        nameValuePairs.add(new BasicNameValuePair("email", email));
        nameValuePairs.add(new BasicNameValuePair("stream", streamId));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(post);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse getNotes(String streamId) throws IOException {

        HttpGet get = new HttpGet(getStreamUrl(streamId));
        get.setHeader("Authorization", "Basic " + encodedLoginData);
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(get);
        entity = response.getEntity();
        return response;

    }
    //creates new note with the basic type
    //children such as checkitems and comments can be set  later

    public HttpResponse createNewNote(String streamId, String noteText, String[] tags) throws UnsupportedEncodingException, IOException {

        HttpPost post = new HttpPost(getStreamUrl(streamId));
        post.setHeader("Authorization", "Basic " + encodedLoginData);
        int tagsNo = tags.length;
        List nameValuePairs = new ArrayList(1 + tagsNo);
        nameValuePairs.add(new BasicNameValuePair("text", noteText));
        for (int i = 0; i < tagsNo; i++) {
            nameValuePairs.add(new BasicNameValuePair("tags", tags[i]));
        }
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(post);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse addComment(String streamId, String noteIt, String commentText) throws UnsupportedEncodingException, IOException {

        HttpPost post = new HttpPost(getStreamUrl(streamId));
        post.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("child_of", noteIt));
        nameValuePairs.add(new BasicNameValuePair("text", commentText));
        nameValuePairs.add(new BasicNameValuePair("type", "comment"));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(post);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse addCheckitem(String streamId, String noteIt, String checkitemText) throws UnsupportedEncodingException, IOException {

        HttpPost post = new HttpPost(getStreamUrl(streamId));
        post.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("child_of", noteIt));
        nameValuePairs.add(new BasicNameValuePair("text", checkitemText));
        nameValuePairs.add(new BasicNameValuePair("type", "checkitem"));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(post);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse checkCheckitem(String checkitemId, String serverModifiedAt) throws UnsupportedEncodingException, IOException {

        HttpPut put = new HttpPut(getObjectUrlInSync(checkitemId));
        put.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        nameValuePairs.add(new BasicNameValuePair("checked", "true"));
        nameValuePairs.add(new BasicNameValuePair("type", "checkitem"));
        put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(put);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse uncheckCheckitem(String checkitemId, String serverModifiedAt) throws UnsupportedEncodingException, IOException {

        HttpPut put = new HttpPut(getObjectUrlInSync(checkitemId));
        put.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        nameValuePairs.add(new BasicNameValuePair("checked", "false"));
        nameValuePairs.add(new BasicNameValuePair("type", "checkitem"));
        put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(put);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse editNote(String noteId, String serverModifiedAt, String newText) throws UnsupportedEncodingException, IOException {

        HttpPut put = new HttpPut(getObjectUrlInSync(noteId));
        put.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        nameValuePairs.add(new BasicNameValuePair("text", newText));
        nameValuePairs.add(new BasicNameValuePair("type", "note"));
        put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(put);
        entity = response.getEntity();
        //System.out.println(EntityUtils.getContentMimeType(entity));
        //System.out.println(encodedLoginData);
        return response;

    }
    
    public HttpResponse editComment(String commentId, String serverModifiedAt, String newText) throws UnsupportedEncodingException, IOException {

        HttpPut put = new HttpPut(getObjectUrlInSync(commentId));
        put.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        nameValuePairs.add(new BasicNameValuePair("text", newText));
        nameValuePairs.add(new BasicNameValuePair("type", "comment"));
        put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(put);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse editStream(String streamId, String serverModifiedAt, String name, String colorName) throws UnsupportedEncodingException, IOException {

        HttpPut put = new HttpPut(getStreamUrl(streamId));
        put.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        nameValuePairs.add(new BasicNameValuePair("name", name));
        if (!colorName.isEmpty()){
            nameValuePairs.add(new BasicNameValuePair("user:color", "base_1"));
        }
        put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(put);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse editCheckitem(String checkitemId, String serverModifiedAt, String newText) throws UnsupportedEncodingException, IOException {

        HttpPut put = new HttpPut(getObjectUrlInSync(newText));
        put.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        nameValuePairs.add(new BasicNameValuePair("text", newText));
        nameValuePairs.add(new BasicNameValuePair("type", "checkitem"));
        put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(put);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse starNote(String noteId, String serverModifiedAt) throws UnsupportedEncodingException, IOException {

        HttpPut put = new HttpPut(getObjectUrlInSync(noteId));
        put.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        nameValuePairs.add(new BasicNameValuePair("catch:starred", "true"));
        nameValuePairs.add(new BasicNameValuePair("type", "checkitem"));
        put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(put);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse unstarNote(String noteId, String serverModifiedAt) throws UnsupportedEncodingException, IOException {

        HttpPut put = new HttpPut(getObjectUrlInSync(noteId));
        put.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        nameValuePairs.add(new BasicNameValuePair("catch:starred", "false"));
        nameValuePairs.add(new BasicNameValuePair("type", "checkitem"));
        put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(put);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse placeNoteInStream(String noteId, String serverModifiedAt, String streamId) throws UnsupportedEncodingException, IOException {

        HttpPut put = new HttpPut(getObjectUrlInStream(streamId, noteId));
        put.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(1);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(put);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse deleteNoteFromStream(String noteId, String serverModifiedAt, String streamId) throws UnsupportedEncodingException, IOException {

        HttpDeleteWithBody delete = new HttpDeleteWithBody(getObjectUrlInStream(streamId, noteId));
        delete.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(1);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        delete.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(delete);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse deleteStream(String streamId, String serverModifiedAt) throws UnsupportedEncodingException, IOException {

        HttpDeleteWithBody delete = new HttpDeleteWithBody(getStreamUrl(streamId));
        delete.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(1);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        delete.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(delete);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse deleteObjectFromWorkspace(String objectId, String serverModifiedAt) throws UnsupportedEncodingException, IOException {

        HttpDeleteWithBody delete = new HttpDeleteWithBody(getObjectUrlInSync(objectId));
        delete.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(1);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        delete.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(delete);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse attachFile(String streamId, String noteId, String filePath) throws UnsupportedEncodingException, IOException {

        HttpPost post = new HttpPost(basicUrl + "streams/" + streamId);
        post.setHeader("Authorization", "Basic " + encodedLoginData);
        StringBody noteIdStr = new StringBody(noteId);
        StringBody type = new StringBody("attachment");

        MultipartEntity entity2 = new MultipartEntity();
        entity2.addPart(new FormBodyPart("file", new FileBody(new File(filePath))));
        entity2.addPart("child_of", noteIdStr);
        entity2.addPart("type", type);
        post.setEntity(entity2);
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(post);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse getStreamContributors(String streamId) throws IOException {

        HttpGet get = new HttpGet(getContributorsUrl(streamId));
        get.setHeader("Authorization", "Basic " + encodedLoginData);
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(get);
        
        entity = response.getEntity();
        return response;

    }

    public HttpResponse deleteStreamContributor(String streamId, String contributorId) throws IOException {

        HttpDelete delete = new HttpDelete(getContributorUrl(streamId, contributorId));
        delete.setHeader("Authorization", "Basic " + encodedLoginData);
        EntityUtils.consume(entity);
        HttpResponse response = httpClient.execute(delete);
        entity = response.getEntity();
        return response;

    }

    public HttpResponse getRawFileData(String streamId, String objectId) throws IOException {

        HttpGet delete = new HttpGet(getObjectUrlInStream(streamId, objectId) + "/raw");
        delete.setHeader("Authorization", "Basic " + encodedLoginData);
        HttpResponse response = httpClient.execute(delete);
        return response;

    }

    public void saveImage(String path, HttpResponse responseWithRawData) {

        BufferedImage image;
        try {
            InputStream input = responseWithRawData.getEntity().getContent();


            image = ImageIO.read(input);

            ImageIO.write(image, "jpg", new File(path));

        } catch (IOException e) {
        }

    }

    public int getResponseStatus(HttpResponse response) throws IOException, ParseException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        String str = "";
        while ((line = rd.readLine()) != null) {
            str += line;
        }
        str.replaceAll("\\s", "");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(str);
        JSONObject jsonObject = (JSONObject) obj;
        String status = (String) jsonObject.get("status");
        if ("ok".equals(status)) {
            return 0;
        } else {
            return 1;
        }
    }

}
