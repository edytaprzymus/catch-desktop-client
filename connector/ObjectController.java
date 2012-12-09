/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connector;

import connector.miniObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Kamil
 */
public class ObjectController {

    
    private catchConnector connector;
    //  private miniObject miniobject;
    private List<catchObject> catchObjectsList = new ArrayList<catchObject>();
    private catchStream catchstream;

    public ObjectController(catchStream catchstream,catchConnector connector) throws UnsupportedEncodingException, IOException, ParseException {
        this.connector = connector;
        this.catchstream = catchstream;
        getObjectsFromCatchStream(catchstream);
    }

    private void getObjectsFromCatchStream(catchStream catchstream) throws UnsupportedEncodingException, IOException, ParseException {

        for (miniObject object : catchstream.getObjects()) {
            String objectId = object.getId();
            catchObject catchobject = new catchObject(connector.getObjectsInStream(catchstream.getId(), objectId));
            catchObjectsList.add(catchobject);
        }

        //  return catchObjectsList;
    }

    public void addObjectToDataBase() {

        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
            
        }

        Connection connection = null;
         for (catchObject catchobject : catchObjectsList) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:catch.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            
           
                
                //catchobject.getChild_of()
                
                statement.executeUpdate("insert into OBJECTS values(" + "'" + catchobject.getId() + "'" + ", " + catchobject.getCount() + ", " + catchobject.getServer_deleted_at() + ", " + catchobject.getN_streams() + ", " + "'" + catchobject.getText() + "'" + ", " + "'" + catchobject.getCreated_at() + "'" + ", " + "'" + catchobject.getModified_at() + "'" + ", " + "'" + catchobject.isLegacy_v2_share() + "'" + ", " + catchobject.getServer_modified_at() + ", " + "'" + catchobject.getChild_of() + "'" + ", " + "'" + catchobject.getAnnotations().get("catch:starred") + "'" + ", " + "'" + catchobject.getType() + "'" + ", " + 000000 + ", " + 0000001 + ", " + "'" + catchobject.getContent_type() + "'" + ", " + catchobject.getSize() + ", " + "'" + catchobject.getFilename() + "'" + ", " + "'" + catchobject.isChecked() + "'" + ", " + "'modifiedLocaly dodane recznie'" + ", " + "'deletedLocaly dodane recznie'" + ", " + "'isuptodate dodane recznie'" + ", '" + catchobject.getId() + "')");
            
            // System.out.println("id obiektu "+catchstream.getObjects().size());

        } catch (SQLException e) {
            // if the error message is "out of memory", 
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
                
            }
        }
         }
    }

    public void addObjectsInStreamsToDatabase() throws IOException, ParseException {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:catch.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            for (int i = 0; i < catchObjectsList.size(); i++) {
                statement.executeUpdate("insert into OBJECT_IN_STREAM values(" + "'" + catchObjectsList.get(i).getId() + "'" + ", " + "'" + catchstream.getId() + "'" + ")");
                
            }


        } catch (SQLException e) {
            // if the error message is "out of memory", 
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
    }

    public void addTagsToDatabase() throws IOException, ParseException {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:catch.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            for (int i = 0; i < catchObjectsList.size(); i++) {
                // System.out.println("id usera w users to "+userIdList.get(i) + "a jego name to" + userNameList.get(i));
                if (catchObjectsList.get(i).getTags() != null) {
                    for (String tag : catchObjectsList.get(i).getTags()) {
                        if (!tag.trim().equals("") && catchObjectsList.get(i).getType().equals("note")) {
                            statement.executeUpdate("insert into TAGS(tag_text) values(" + "'" + tag + "'" + ")");
                            statement.executeUpdate("insert into TAGS_IN_OBJECTS values(" + "(select tag_id from TAGS where tag_text='" + tag + "'" + ")" + ", " + "'" + catchObjectsList.get(i).getId() + "'" + ")");
                        }

                    }
                }
            }


        } catch (SQLException e) {
            // if the error message is "out of memory", 
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
    }

    public List<catchObject> getCatchObjectsList() {
        return catchObjectsList;
    }
}
