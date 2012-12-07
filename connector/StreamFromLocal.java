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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Kamil
 */
public class StreamFromLocal {

    public StreamFromLocal() {
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
            rs = statement.executeQuery("select stream_id, server_created_at, server_modified_at, count, name, user_color, color from streams");
            int i = 0;
            while (rs.next()) {
                ministream.add(new miniStream((String) rs.getString("stream_id"), (String) rs.getString("server_modified_at"), (long) rs.getLong("count"), (String) rs.getString("server_created_at"), (String) rs.getString("name"), getHashMap((String) rs.getString("user_color"), (String) rs.getString("color"))));
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
        String deletedLocaly = "nonotdeletedlocally";
        String modifiedLocally = "yesmodifiedlocally";




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

    public catchStream getStream(String stream_id) {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
        ResultSet rs = null;
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
