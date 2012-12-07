/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgcatch.connector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author edytka
 */
public class Catch {

    /**
     * @param args the command line arguments
     */
  public static void main(String[] args) throws ClassNotFoundException, IOException, ParseException
  {
    // load the sqlite-JDBC driver using the current class loader
      
    /*
     * Prosty przyklad bazy SQL
       * Komentarz specjalnie dla Kamilka D
       * A tu komentarz dla Przemka
       * drugi komentarz dla Przemka
     */
      
    String sDriverName = "org.sqlite.JDBC";
    Class.forName(sDriverName);
    
    Connection connection = null;
    try
    {
      // create a database connection
      connection = DriverManager.getConnection("jdbc:sqlite:catch.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.
      
      statement.executeUpdate("drop table if exists person");
      statement.executeUpdate("create table person (id integer, name string)");
      statement.executeUpdate("insert into person values(1, 'leo')");
      statement.executeUpdate("insert into person values(2, 'yui')");
      ResultSet rs = statement.executeQuery("select * from person");
      while(rs.next())
      {
        // read the result set
        System.out.println("name = " + rs.getString("name"));
        System.out.println("id = " + rs.getInt("id"));
      }
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory", 
      // it probably means no database file is found
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        System.err.println(e);
      }
    }
    
    /*
     * Ponizej komunikacja z Catch
     * jeden GET (lista streamow) i jeden POST (stworzenie streama)
     */
    
    HttpClient client = new DefaultHttpClient();
    HttpGet request = new HttpGet("https://api.catch.com/v3/streams/508eb9df0731a35ac5b91a27");
    request.setHeader("Authorization", "Basic a2FtaWxkemk6c29vczA3");
    HttpResponse response = client.execute(request);
    BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
    String line = "";
    while ((line = rd.readLine()) != null) {
      System.out.println(line);
    }
  
    HttpPost post = new HttpPost("https://api.catch.com/v3/streams");
    /*
     * UWAGA! Zmiencie w tej linijce setHeader 
     * bmFzdGFzamE6ZmlsaXBvdm5h na Wasze zaszywrowane login:haslo
     */
    post.setHeader("Authorization", "Basic a2FtaWxkemk6c29vczA3");
    List nameValuePairs = new ArrayList(1);
    nameValuePairs.add(new BasicNameValuePair("name", "fromNetbeans")); //you can as many name value pair as you want in the list.
    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    response = client.execute(post);
    rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    line = "";
    String str = "";
    while ((line = rd.readLine()) != null) {
        str += line;
        System.out.println(line);
    }
    
    /*
     * Parsownie JSONa, ktorzy przyszedl w odpowiedzi na POSTA
     */

    str.replaceAll("\\s","");
    JSONParser parser=new JSONParser();
    Object obj=parser.parse(str);
    JSONObject jsonObject = (JSONObject) obj;
    String status = (String) jsonObject.get("status");
    System.out.println(status);
    /*
     * "zagniezdzony JSON
     */
    
    Object result = jsonObject.get("result");
    JSONObject jsonResult = (JSONObject) result;
    String name = (String) jsonResult.get("name");
    System.out.println(name);
    
  }
}
