/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connector;

import connector.miniObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Kamil
 */
public class ObjectFromLocal {

    public ObjectFromLocal() {
    }
    
    public void updateObjectText(String text, catchObject object) { //tutaj zmieniasz text dowolnego obiektu takze notakti komentarza itp itd
        object.setText(text);
        this.updateObject(object);
    }
    
    public boolean isStarred(catchObject object) {
        String starred = (String) object.getAnnotations().get("catch:starred");
        if(starred.equals("true")){
            return true;
        }
        else {
            return false;
        }
        
    }
    
    public void updateTask(String taskText, boolean checked, catchObject object) {
        object.setText(taskText);
        object.setChecked(checked);
        this.updateObject(object);
    }
    
    public catchObject createObject(String streamId, String type) {
        Random random = new Random();
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }

        Connection connection = null;
        catchObject catchobject1 = new catchObject();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:catch.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            int randomId = random.nextInt();
            int randomPath = random.nextInt();
                statement.executeUpdate("insert into OBJECTS values(" + "'" + randomId + "'" + ", " + 0 + ", " + 0 + ", " + 0 + ", " + "'" + "*" + "'" + ", " + "'" + new Date() + "'" + ", " + "'" + new Date() + "'" + ", " + "'" + "*" + "'" + ", " + 0 + ", " + "'" + "*" + "'" + ", " + "'" + "false" + "'" + ", " + "'" + type + "'" + ", " + 000000 + ", " + 0000001 + ", " + "'" + "*" + "'" + ", " + 0 + ", " + "'" + "*" + "'" + ", " + "'" + 0 + "'" + ", " + "'modifiedLocaly dodane recznie'" + ", " + "'deletedLocaly dodane recznie'" + ", " + "'newObject'" + ", " + "'filepath dodane recznie" + randomPath+ "'" + ")");
                statement.executeUpdate("insert into OBJECT_IN_STREAM values(" + "'" + randomId + "'" + ", " + "'" + streamId + "'" + ")");
               catchobject1 = this.getObject(Integer.toString(randomId));

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
        return catchobject1;
    }

    public void deleteObject(String objectId) {
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
            statement.executeUpdate("delete from OBJECTS where object_id='" + objectId + "'");
            statement.executeUpdate("delete from TAGS where tag_id=" + "(select tag_id from TAGS_IN_OBJECTS where object_id='" + objectId + "'" + ")");
            statement.executeUpdate("delete from TAGS_IN_OBJECTS where object_id='" + objectId + "'");
            statement.executeUpdate("delete from OBJECT_IN_STREAM where object_id='" + objectId + "'");

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

    public List<miniObject> getObjects() {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
        ResultSet rs = null;
        Connection connection = null;
        List<miniObject> miniobject = new ArrayList<>();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:catch.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            rs = statement.executeQuery("select object_id, type, server_modified_at from objects");
            int i = 0;
            while (rs.next()) {
                miniobject.add(new miniObject((String) rs.getString("object_id"), (String) rs.getString("server_modified_at"), (String) rs.getString("type")));
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

        return miniobject;

    }

    public void updateObject(catchObject object) {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
        ResultSet rs = null;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:catch.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String id = object.getId();

            if (id != null) {
                statement.executeUpdate("update objects set " + "count='" + object.getCount() + "'" + ", " + "server_deleted_at='" + object.getServer_deleted_at() + "'" + ", " + "n_streams='" + object.getN_streams() + "'" + ", " + "text='" + object.getText() + "'" + ", " + "created_at='" + object.getCreated_at() + "'" + ", " + "modified_at='" + object.getModified_at() + "'" + ", " + "legacy_v2_share='" + object.isLegacy_v2_share() + "'" + ", " + "server_modified_at='" + object.getServer_modified_at() + "'" + ", " + "child_of='" + object.getChild_of() + "'" + ", " + "type='" + object.getType() + "'" + ", " + "location_id=" + 1111 + ", " + "user_id=" + 2222 + ", " + "content_type='" + object.getContent_type() + "'" + ", " + "size='" + object.getSize() + "'" + ", " + "filename='" + object.getFilename() + "'" + ", " + "checked='" + object.isChecked() + "'" + ", " + "modified_locally='modifiedLocaly dodane recznie'" + ", " + "deleted_locally='modifiedLocaly dodane recznie'" + ", " + "is_up_to_date='modifiedLocaly dodane recznie'" + ", " + "file_path='modifiedLocaly dodane recznie'" + "where object_id ='" + id + "'");
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

    public catchObject getObject(String object_id) {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
        ResultSet rs = null;
        Connection connection = null;
        catchObject catchobject1 = new catchObject();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:catch.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            rs = statement.executeQuery("select * from objects where object_id='" + object_id + "'");
            int i = 0;
            while (rs.next()) {
                catchObject catchobject = new catchObject((long) rs.getLong("n_streams"), (String) rs.getString("text"), (String) rs.getString("created_at"), (String) rs.getString("modified_at"), (boolean) rs.getBoolean("legacy_v2_share"), (String) rs.getString("server_modified_at"), (String) rs.getString("type"), (String) rs.getString("server_deleted_at"), (String) rs.getString("object_id"), (boolean) rs.getBoolean("checked"), (String) rs.getString("child_of"), (int) rs.getInt("size"), (String) rs.getString("filename"), (String) rs.getString("content_type"), (long) rs.getLong("count"), getUserFromObjectId(object_id));
                catchobject1 = catchobject;
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


        return catchobject1;
    }
    
    private HashMap getUserFromObjectId (String id) {
        HashMap user = new HashMap();
         String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
        ResultSet rs = null;
        Connection connection = null;
        
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:catch.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            rs = statement.executeQuery("select user_id, user_name from USERS where user_id ='" + id + "'");
            int i = 0;
            while (rs.next()) {
               user.put("user_name", rs.getString("user_name"));
               user.put("id", rs.getString("user_id"));
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
        return user;
        
    }
}
