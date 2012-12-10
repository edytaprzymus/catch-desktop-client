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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public ObjectController(catchStream catchstream, catchConnector connector) throws UnsupportedEncodingException, IOException, ParseException {
        this.connector = connector;
        this.catchstream = catchstream;
        getObjectsFromCatchStream(catchstream);
    }

    private void getObjectsFromCatchStream(catchStream catchstream) throws UnsupportedEncodingException, IOException, ParseException {

        for (miniObject object : catchstream.getObjects()) {
            String objectId = object.getId();
            catchObject catchobject = new catchObject(connector.getObjectsInStream(catchstream.getId(), objectId));
            if (!catchObjectsList.contains(catchobject)) {
                catchObjectsList.add(catchobject);
            }
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



                String modifiedLocally = "'modifiedLocaly dodane recznie'";
                //catchobject.getChild_of()
                //System.out.println(catchobject.getText());
                if ("*".equals(catchobject.getText())) {
                    modifiedLocally = "'nowa notatka'";
                }
                statement.executeUpdate("insert into OBJECTS values(" + "'" + catchobject.getId() + "'" + ", " + catchobject.getCount() + ", " + catchobject.getServer_deleted_at() + ", " + catchobject.getN_streams() + ", " + "'" + catchobject.getText() + "'" + ", " + "'" + catchobject.getCreated_at() + "'" + ", " + "'" + catchobject.getModified_at() + "'" + ", " + "'" + catchobject.isLegacy_v2_share() + "'" + ", " + catchobject.getServer_modified_at() + ", " + "'" + catchobject.getChild_of() + "'" + ", " + "'" + catchobject.getAnnotations().get("catch:starred") + "'" + ", " + "'" + catchobject.getType() + "'" + ", " + 000000 + ", " + 0000001 + ", " + "'" + catchobject.getContent_type() + "'" + ", " + catchobject.getSize() + ", " + "'" + catchobject.getFilename() + "'" + ", " + "'" + catchobject.isChecked() + "'" + ", " + modifiedLocally + ", " + "'deletedLocaly dodane recznie'" + ", " + "'isuptodate dodane recznie'" + ", '" + catchobject.getId() + "')");

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
            PreparedStatement pStmt = connection.prepareStatement("insert into OBJECT_IN_STREAM values(?,?)");

            for (int i = 0; i < catchObjectsList.size(); i++) {
                Statement statement = connection.createStatement();
                Statement statement2 = connection.createStatement();
                ResultSet rs;
                rs = statement2.executeQuery("select object_id, stream_id from OBJECT_IN_STREAM where object_id= '" + catchObjectsList.get(i).getId() + "' and stream_id = '" + catchstream.getId() + "'");
                if (!rs.next()) {
                    pStmt.setString(1, catchObjectsList.get(i).getId()); //This might be pStmt.SetInt(0, fileid) depending on teh type of fileid)
                    pStmt.setString(2, catchstream.getId());
                    try {
                        pStmt.executeUpdate();
                    } catch (SQLException e) {
                    }
                }
                //statement.setQueryTimeout(30);
                //statement.executeUpdate("insert into OBJECT_IN_STREAM values(" + "'" + catchObjectsList.get(i).getId() + "'" + ", " + "'" + catchstream.getId() + "'" + ")");
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

    public void getEditedObjects() throws SQLException, UnsupportedEncodingException, IOException {
        String sDriverName = "org.sqlite.JDBC";

        List<catchObject> returnList = new ArrayList<catchObject>();
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:catch.db");
        Statement statement = connection.createStatement();
        Statement statement2 = connection.createStatement();
        ResultSet rs;
        rs = statement2.executeQuery("select object_id, text from OBJECTS where is_up_to_date= 'modifiedLocaly dodane recznie'");
        while (rs.next()) {
            try {
                //System.out.println(rs.getString(1) + "!!!!!!!!!!!!!!!!!!!");
                for (int i = 0; i < catchObjectsList.size(); i++) {
                    if (catchObjectsList.get(i).getId().equals(rs.getString(1))) {
                        Statement statement3 = connection.createStatement();
                        Statement statement4 = connection.createStatement();
                        switch (catchObjectsList.get(i).getType()) {
                            case "note":
                                connector.editNote(catchObjectsList.get(i).getId(), catchObjectsList.get(i).getServer_modified_at(), rs.getString(2));
                                break;
                            case "comment":
                                connector.editComment(catchObjectsList.get(i).getId(), catchObjectsList.get(i).getServer_modified_at(), rs.getString(2));
                                break;
                            case "checkitem":
                                connector.editCheckitem(catchObjectsList.get(i).getId(), catchObjectsList.get(i).getServer_modified_at(), rs.getString(2));
                                break;
                        }
                        statement3.executeUpdate("delete from objects where object_id = '" + catchObjectsList.get(i).getId() + "'");
                        statement4.executeUpdate("delete from object_in_stream where object_id = '" + catchObjectsList.get(i).getId() + "' and stream_id = '" + catchstream.getId() + "'");
                        catchObjectsList.remove(i);

                    }
                }

            } catch (SQLException e) {
            }
        }
        ResultSet rs3;
        Statement statement5 = connection.createStatement();
        rs3 = statement5.executeQuery("select object_id, text,type from OBJECTS where is_up_to_date= 'newObject'");
        while (rs3.next()) {
            try {
                switch (rs3.getString(3)) {
                    case "note":
                        //System.out.println("NOWA NOTATKA!!!");
                        String[] strarray = new String[1];
                        connector.createNewNote(catchstream.getId(), rs3.getString(2), strarray);
                        Statement statement6 = connection.createStatement();
                        Statement statement7 = connection.createStatement();
                        statement6.executeUpdate("delete from objects where object_id = '" + rs3.getString(1) + "'");
                        statement7.executeUpdate("delete from object_in_stream where object_id = '" + rs3.getString(1) + "' and stream_id = '" + catchstream.getId() + "'");

                        break;
                }



            } catch (SQLException e) {
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
