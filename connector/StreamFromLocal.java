/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connector;

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
public class StreamFromLocal {

    public StreamFromLocal() {
    }
    
    public catchStream createStream(String userId) {
         String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
        catchStream stream = new catchStream();
        Connection connection = null;
        Random random = new Random();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:catch.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            int randomId = random.nextInt();
            statement.executeUpdate("insert into STREAMS values(" + "'" + randomId + "'" + ", " + 0 + ", " + 0 + ", " + "'" + "*" + "'" + ", " + "'" + "*" + "'" + ", " + "'" + new Date() + "'" + ", " + "'" + new Date() + "'" + ", " + 0 + ", " + "'" + "*" + "'" + ", " + "'" + "*" + "'" + ", " + 0 + ", " + 0 + ", " + "'YESrecznie'" + ", " + "'NOrecznie'" + ", " + "'NOrecznie'" + ")");
            statement.executeUpdate("insert into CONTRIBUTORS values(" + "'" + userId + "'" + ", " + "'" + randomId + "'" + ")");
            stream = this.getStream(Integer.toString(randomId));


            ResultSet rs = statement.executeQuery("select * from STREAMS");
            while (rs.next()) {
                // read the result set
                System.out.println("stream_id = " + rs.getString("stream_id"));
                System.out.println("name = " + rs.getInt("name"));
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
        
        return stream;
    }
    
    public void updateStreamName(String newName, catchStream stream) {
        stream.setName(newName);
        this.updateStream(stream);
    }

    public void deleteStream(String streamId) {
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
            statement.executeUpdate("delete from STREAMS where stream_id='" + streamId + "'");
            statement.executeUpdate("delete from USERS where user_id=" + "(select user_id from CONTRIBUTORS where stream_id='" + streamId + "'" + ")");
            statement.executeUpdate("delete from CONTRIBUTORS where stream_id='" + streamId + "'");

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

    public List<miniStream> getStreams() {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
        ResultSet rs = null;
        Connection connection = null;
        List<miniStream> ministream = new ArrayList<miniStream>();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:catch.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            rs = statement.executeQuery("select stream_id, server_created_at, server_modified_at, count, name, user_color, color, deleted_locally, modified_locally from streams");
            int i = 0;
            while (rs.next()) {
                miniStream mS = new miniStream((String) rs.getString("stream_id"), (String) rs.getString("server_modified_at"), (long) rs.getLong("count"), (String) rs.getString("server_created_at"), (String) rs.getString("name"), getHashMap((String) rs.getString("user_color"), (String) rs.getString("color")));
                mS.setDeleted_locally(rs.getString("deleted_locally"));        
                mS.setModified_locally(rs.getString("modified_locally"));        
                ministream.add(mS);
                
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

        return ministream;

    }

    private HashMap getHashMap(String user_color, String color) {
        HashMap annotations = new HashMap();
        annotations.put("user_color", user_color);
        annotations.put("color", color);
        return annotations;

    }

    public void updateStream(catchStream stream) {

        String isUptoDate = "yesisuptodate";
        String deletedLocaly = stream.getDeletedLocally();
        String modifiedLocally = stream.getModifiedLocally();




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
            String id = stream.getId();

            if (id != null) {
                statement.executeUpdate("update streams set " + "count=" + stream.getCount() + ", " + "server_deleted_at=" + stream.getServer_deleted_at() + ", " + "contributor_count=" + stream.getContributor_count() + ", " + "name='" + stream.getName() + "'" + ", " + "created_at='" + stream.getCreated_at() + "'" + ", " + "modified_at='" + stream.getModified_at() + "'" + ", " + "source='" + stream.getSource() + "'" + ", " + "server_modified_at=" + stream.getServerModifiedAt() + ", " + "user_color='" + stream.getAnnotations().get("user_color") + "'" + ", " + "color='" + stream.getAnnotations().get("color") + "'" + ", " + "is_up_to_date='" + isUptoDate + "'" + ", " + "deleted_locally='" + deletedLocaly + "'" + ", " + "modified_locally='" + modifiedLocally + "'" + " where stream_id='" + stream.getId() + "'");
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
    
    public List<catchObject> getObjectsFromSteam(String stream_id) {
        
        List<catchObject> objects = new ArrayList<catchObject>();
        catchStream catchstream = this.getStream(stream_id);
        List<miniObject> miniobjects = catchstream.getObjectsFromStreamId(stream_id);
        System.out.println("Na tym etapie mamy z kolei " + miniobjects.size());
        ObjectFromLocal o= new ObjectFromLocal();
        
        for(miniObject mini : miniobjects){
            objects.add(o.getObject(mini.getId()));
        }
        
        
        return objects;
    }

    public catchStream getStream(String stream_id) {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
        ResultSet rs = null;
        ResultSet rs2 = null;
        Connection connection = null;
        catchStream catchstream1 = new catchStream();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:catch.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            rs = statement.executeQuery("select * from streams where stream_id='" + stream_id + "'");
            int i = 0;
            while (rs.next()) {
                catchStream catchstream = new catchStream((int) rs.getInt("count"), (int) rs.getInt("contributor_count"), (String) rs.getString("name"), (String) rs.getString("source"), (String) rs.getString("created_at"), (String) rs.getString("modified_at"), (String) rs.getString("server_deleted_at"), (String) rs.getString("stream_id"), (String) rs.getString("server_created_at"), (String) rs.getString("server_modified_at"));
                catchstream.setModifiedLocally(rs.getString("modified_locally"));
                catchstream.setDeletedLocally(rs.getString("deleted_locally"));
                catchstream1 = catchstream;
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


        return catchstream1;
    }
}
