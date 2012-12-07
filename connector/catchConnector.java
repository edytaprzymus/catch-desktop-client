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
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;
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
    public String getMethod() { return METHOD_NAME; }

    public HttpDeleteWithBody(final String uri) {
        super();
        setURI(URI.create(uri));
    }
    public HttpDeleteWithBody(final URI uri) {
        super();
        setURI(uri);
    }
    public HttpDeleteWithBody() { super(); }
}

public class catchConnector {
    
    private String encodedLoginData;
    private static String basicUrl = "https://api.catch.com/v3/";
    private static HttpClient httpClient = new DefaultHttpClient();
    
    
    public catchConnector(String login, String password){
        
        String stringToConvert = login + ":" + password;
        byte[] theByteArray = stringToConvert.getBytes();
        Base64 base64 = new Base64();
        encodedLoginData = base64.encodeAsString(theByteArray);
        
    }
    
    public String getEncodedLoginData(){
        return encodedLoginData;
    }
    
    public String getStreamsUrl(){
    
        return basicUrl + "streams";
    
    }
    
    public String getInvitesUrl(){
    
        return basicUrl + "invites";
    
    }
    
    public String getObjectUrlInSync(String objectId){
    
        return basicUrl + "streams/sync" + objectId;
    
    }
    
    public String getObjectUrlInStream(String streamId, String objectId){
    
        return basicUrl + "streams/" + streamId + "/" + objectId;
    
    }
    
    public String getContributorsUrl(String streamId){
    
        return basicUrl + "streams/" + streamId + "/contributors";
    
    }
    
    public String getContributorUrl(String streamId, String contributorId){
    
        return basicUrl + "streams/" + streamId + "/contributors/" + contributorId;
    
    }
    
    
    public String getStreamUrl(String streamID){
    
        return basicUrl + "streams/" + streamID;
    
    }
    
    public HttpResponse createStream(String streamName) throws UnsupportedEncodingException, IOException{  
        
        HttpPost post = new HttpPost(basicUrl + "streams");
        post.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(1);
        nameValuePairs.add(new BasicNameValuePair("name", streamName));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpClient.execute(post);
        return response;
        
    }
    
    public HttpResponse getStreams() throws UnsupportedEncodingException, IOException{  
        
        HttpGet get = new HttpGet(getStreamsUrl());
        get.setHeader("Authorization", "Basic " + encodedLoginData);
        HttpResponse response = httpClient.execute(get);
        return response;
        
    }
    
    public HttpResponse getStream(String streamId) throws UnsupportedEncodingException, IOException{  
        
        HttpGet get = new HttpGet(getStreamUrl(streamId));
        get.setHeader("Authorization", "Basic " + encodedLoginData);
        HttpResponse response = httpClient.execute(get);
        return response;
        
    }
    
    public HttpResponse getObjectsInStream(String streamId, String objectId) throws UnsupportedEncodingException, IOException{  
        
        HttpGet get = new HttpGet(getObjectUrlInStream(streamId, objectId));
        get.setHeader("Authorization", "Basic " + encodedLoginData);
        HttpResponse response = httpClient.execute(get);
        return response;
        
    }
    
    public HttpResponse inviteToStream(String email, String streamId) throws UnsupportedEncodingException, IOException{
        
        HttpPost post = new HttpPost(getInvitesUrl());
        post.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("type", "email"));
        nameValuePairs.add(new BasicNameValuePair("email", email));
        nameValuePairs.add(new BasicNameValuePair("stream", streamId));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpClient.execute(post);
        return response;
        
    }
    
    public HttpResponse getNotes(String streamId) throws IOException{
        
        HttpGet get = new HttpGet(getStreamUrl(streamId));
        get.setHeader("Authorization", "Basic " + encodedLoginData);
        HttpResponse response = httpClient.execute(get);
        return response;
        
    }
    //creates new note with the basic type
    //children such as checkitems and comments can be set  later
    public HttpResponse createNewNote(String streamId, String noteText, String[] tags) throws UnsupportedEncodingException, IOException{  
        
        HttpPost post = new HttpPost(getStreamUrl(streamId));
        post.setHeader("Authorization", "Basic " + encodedLoginData);
        int tagsNo = tags.length;
        List nameValuePairs = new ArrayList(1 + tagsNo);
        nameValuePairs.add(new BasicNameValuePair("text", noteText));
        for (int i = 0; i < tagsNo; i++){
            nameValuePairs.add(new BasicNameValuePair("tags", tags[i]));
        }
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpClient.execute(post);
        return response;
        
    }
    
    public HttpResponse addComment(String streamId, String noteIt, String commentText) throws UnsupportedEncodingException, IOException{  
        
        HttpPost post = new HttpPost(getStreamUrl(streamId));
        post.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("child_of", noteIt));
        nameValuePairs.add(new BasicNameValuePair("text", commentText));
        nameValuePairs.add(new BasicNameValuePair("type", "comment"));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpClient.execute(post);
        return response;
        
    }
    
    public HttpResponse addCheckitem(String streamId, String noteIt, String checkitemText) throws UnsupportedEncodingException, IOException{  
        
        HttpPost post = new HttpPost(getStreamUrl(streamId));
        post.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("child_of", noteIt));
        nameValuePairs.add(new BasicNameValuePair("text", checkitemText));
        nameValuePairs.add(new BasicNameValuePair("type", "checkitem"));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpClient.execute(post);
        return response;
        
    }
    
    public HttpResponse checkCheckitem(String checkitemId, String serverModifiedAt) throws UnsupportedEncodingException, IOException{  
        
        HttpPut put = new HttpPut(getObjectUrlInSync(checkitemId));
        put.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        nameValuePairs.add(new BasicNameValuePair("checked", "true"));
        nameValuePairs.add(new BasicNameValuePair("type", "checkitem"));
        put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpClient.execute(put);
        return response;
        
    }
    
    public HttpResponse uncheckCheckitem(String checkitemId, String serverModifiedAt) throws UnsupportedEncodingException, IOException{  
        
        HttpPut put = new HttpPut(getObjectUrlInSync(checkitemId));
        put.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        nameValuePairs.add(new BasicNameValuePair("checked", "false"));
        nameValuePairs.add(new BasicNameValuePair("type", "checkitem"));
        put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpClient.execute(put);
        return response;
        
    }
    
    public HttpResponse editNote(String noteId, String serverModifiedAt, String newText) throws UnsupportedEncodingException, IOException{  
        
        HttpPut put = new HttpPut(getObjectUrlInSync(noteId));
        put.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        nameValuePairs.add(new BasicNameValuePair("text", newText));
        nameValuePairs.add(new BasicNameValuePair("type", "note"));
        put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpClient.execute(put);
        return response;
        
    }
    
    public HttpResponse editComment(String commentId, String serverModifiedAt, String newText) throws UnsupportedEncodingException, IOException{  
        
        HttpPut put = new HttpPut(getObjectUrlInSync(commentId));
        put.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        nameValuePairs.add(new BasicNameValuePair("text", newText));
        nameValuePairs.add(new BasicNameValuePair("type", "comment"));
        put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpClient.execute(put);
        return response;
        
    }
    
    public HttpResponse editCheckitem(String checkitemId, String serverModifiedAt, String newText) throws UnsupportedEncodingException, IOException{  
        
        HttpPut put = new HttpPut(getObjectUrlInSync(newText));
        put.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        nameValuePairs.add(new BasicNameValuePair("text", newText));
        nameValuePairs.add(new BasicNameValuePair("type", "checkitem"));
        put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpClient.execute(put);
        return response;
        
    }
    
    public HttpResponse starNote(String noteId, String serverModifiedAt) throws UnsupportedEncodingException, IOException{  
        
        HttpPut put = new HttpPut(getObjectUrlInSync(noteId));
        put.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        nameValuePairs.add(new BasicNameValuePair("catch:starred", "true"));
        nameValuePairs.add(new BasicNameValuePair("type", "checkitem"));
        put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpClient.execute(put);
        return response;
        
    }
    
    public HttpResponse unstarNote(String noteId, String serverModifiedAt) throws UnsupportedEncodingException, IOException{  
        
        HttpPut put = new HttpPut(getObjectUrlInSync(noteId));
        put.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        nameValuePairs.add(new BasicNameValuePair("catch:starred", "false"));
        nameValuePairs.add(new BasicNameValuePair("type", "checkitem"));
        put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpClient.execute(put);
        return response;
        
    }
    
    public HttpResponse placeNoteInStream(String noteId, String serverModifiedAt, String streamId) throws UnsupportedEncodingException, IOException{  
        
        HttpPut put = new HttpPut(getObjectUrlInStream(streamId, noteId));
        put.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(1);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpClient.execute(put);
        return response;
        
    }
    
    public HttpResponse deleteNoteFromStream(String noteId, String serverModifiedAt, String streamId) throws UnsupportedEncodingException, IOException{  
        
        HttpDeleteWithBody delete = new HttpDeleteWithBody(getObjectUrlInStream(streamId, noteId) );
        delete.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(1);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        delete.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpClient.execute(delete);
        return response;
        
    }
    
    public HttpResponse deleteStream(String streamId, String serverModifiedAt) throws UnsupportedEncodingException, IOException{  
        
        HttpDeleteWithBody delete = new HttpDeleteWithBody(getStreamUrl(streamId) );
        delete.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(1);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        delete.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpClient.execute(delete);
        return response;
        
    }
    
    
    public HttpResponse deleteObjectFromWorkspace(String objectId, String serverModifiedAt) throws UnsupportedEncodingException, IOException{  
        
        HttpDeleteWithBody delete = new HttpDeleteWithBody(getObjectUrlInSync(objectId));
        delete.setHeader("Authorization", "Basic " + encodedLoginData);
        List nameValuePairs = new ArrayList(1);
        nameValuePairs.add(new BasicNameValuePair("server_modified_at", serverModifiedAt));
        delete.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpClient.execute(delete);
        return response;
        
    }
    
    public HttpResponse attachFile(String streamId, String noteId, String filePath) throws UnsupportedEncodingException, IOException{  
        
        HttpPost post = new HttpPost(basicUrl + "streams/" + streamId);
        post.setHeader("Authorization", "Basic " + encodedLoginData);
        StringBody noteIdStr = new StringBody(noteId);
        StringBody type = new StringBody("attachment");
        
        MultipartEntity entity = new MultipartEntity();
        entity.addPart(new FormBodyPart("file", new FileBody(new File(filePath))));
        entity.addPart("child_of", noteIdStr);
        entity.addPart("type", type);
        post.setEntity(entity);

        HttpResponse response = httpClient.execute(post);
        return response;
        
    }
    
    public HttpResponse getStreamContributors(String streamId) throws IOException{
        
        HttpGet get = new HttpGet(getContributorsUrl(streamId));
        get.setHeader("Authorization", "Basic " + encodedLoginData);
        HttpResponse response = httpClient.execute(get);
        return response;
        
    }
    
    public HttpResponse deleteStreamContributor(String streamId, String contributorId) throws IOException{
        
        HttpDelete delete = new HttpDelete(getContributorUrl(streamId, contributorId));
        delete.setHeader("Authorization", "Basic " + encodedLoginData);
        HttpResponse response = httpClient.execute(delete);
        return response;
        
    }
    
    public HttpResponse getRawFileData(String streamId, String objectId) throws IOException{
        
        HttpGet delete = new HttpGet(getObjectUrlInStream(streamId, objectId) + "/raw");
        delete.setHeader("Authorization", "Basic " + encodedLoginData);
        HttpResponse response = httpClient.execute(delete);
        return response;
        
    }
    
    public void saveImage(String path, HttpResponse responseWithRawData){
        
            BufferedImage image;
            try {
                InputStream input = responseWithRawData.getEntity().getContent();


                image = ImageIO.read(input);

                ImageIO.write(image, "jpg",new File(path));

            } catch (IOException e) {
            }
        
    }
    
    public int getResponseStatus(HttpResponse response) throws IOException, ParseException{
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        String str = "";
        while ((line = rd.readLine()) != null) {
            str += line;
        }
        str.replaceAll("\\s","");
        JSONParser parser=new JSONParser();
        Object obj=parser.parse(str);
        JSONObject jsonObject = (JSONObject) obj;
        String status = (String) jsonObject.get("status");
        if ("ok".equals(status)) {
            return 0;
        }
        else {
            return 1;
        }
    }
    
    //komentarz dodany celem sprawdzenia synchronizacji przez Kamila.
    public static void main(String[] args) throws ClientProtocolException, IOException, ParseException{
         catchConnector connector = new catchConnector("kamildzi", "soos07");
        HttpResponse response = connector.getStreams();


     
       // catchStream bigStream = new catchStream(response);
        
       // System.out.println("" + bigStream.getModified_at() + " " + bigStream.getName() + " " + bigStream.getSource());
        
         List<miniStream> streamList = new miniStreamList(response).getStreams();
         
       //  HttpResponse response1 = connector.getStream(streamList.get(1).getId());
         
    //     catchStream stream = new catchStream(response1);
        
         
         
         StreamController streamController = new StreamController(streamList.get(0));
       //  StreamController streamController = new StreamController(streamList.get(0));
         
         streamController.addStreamToDataBase();
         streamController.addContributorsToDatabase();
         streamController.addUsersToDatabase();
         //String objectId = streamController.getCatchStream().getObjects().get(0).getId();
        
        System.out.println(streamList.size());
        System.out.println("" + streamList.get(0).getId() + " " + streamList.get(0).getName() + " " + streamList.get(0).getServer_created_at() );
        ObjectController objectController = new ObjectController(streamController.getCatchStream());
        objectController.addObjectToDataBase();
        objectController.addObjectsInStreamsToDatabase();
        objectController.addTagsToDatabase();
        ObjectFromLocal objectFromLocal = new ObjectFromLocal();
        //objectFromLocal.deleteObject(objectController.getCatchObjectsList().get(5).getId());
        objectFromLocal.getObjects();
        objectFromLocal.updateObject(objectController.getCatchObjectsList().get(0));
       catchObject c =  objectFromLocal.getObject(objectController.getCatchObjectsList().get(0).getId());
       
       
       StreamFromLocal s = new StreamFromLocal();
      // s.deleteStream(streamList.get(0).getId());
      //System.out.println(" wybrany stream to "+ s.getStreams().get(0).getId() + " a jego kolor " + s.getStreams().get(0).getAnnotations().get("user_color"));
         s.updateStream(streamController.getCatchStream());
        catchStream streamcatch = s.getStream(streamController.getCatchStream().getId());
        
      //  System.out.println("scaigneity stream to   "+ streamcatch.getId() + " i jeszcze " + streamcatch.getName());
      //  StreamController streamController2 = new StreamController(streamList.get(1));
       // streamController2.addStreamToDataBase();
       //  streamController2.addContributorsToDatabase();
         
        //  ObjectController objectController2 = new ObjectController(streamController2.getCatchStream());
       // objectController2.addObjectToDataBase();
        
        
        //  catchConnector connector = new catchConnector("nastasja", "filipovna");
      //  HttpResponse response = connector.getStreams();
//        List<miniStream> streamList = new miniStreamList(response).getStreams();
//        System.out.print(streamList.size());
//        HttpResponse resp2 = connector.getStream(streamList.get(1).getId());
//        catchStream stream = new catchStream(resp2);
//        stream.setContributorsMap(connector.getStreamContributors(streamList.get(1).getId()));
//        HashMap contributors = stream.getContributors();
//        

//        List<miniStream> streamList2 = new miniStreamList(response).getStreams();
//        HttpResponse resp2 = connector.getStream(streamList2.get(1).getId());
//        catchStream stream = new catchStream(resp2);
//        stream.setContributorsMap(connector.getStreamContributors(streamList2.get(1).getId()));
//        HashMap contributors = stream.getContributors();
//        
//        HttpGet get = new HttpGet("https://api.catch.com/v3/streams/50a8af4c0731a317cfb0584c/50a8b05c70c8021a5b9815db");
//        get.setHeader("Authorization", "Basic " + connector.encodedLoginData);
//        HttpResponse response2 = httpClient.execute(get);
//        
//        catchObject obj = new catchObject((response2));
//        List tags = obj.getTags();
//        System.out.println(tags.size());
//        connector.deleteStream("50a79c3c0731a3179a37e651", "1353161789519");
        

        
        

        }
    }
