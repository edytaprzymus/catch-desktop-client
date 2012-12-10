/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connector;
import gui.Controller;
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
    System.setProperty("java.awt.headless", "false");
    Controller guiController = new Controller();
  }
}
