/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgcatch.connector;

import connector.miniStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.util.List;
import org.apache.http.HttpResponse;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Kamil
 */
public class StreamController {

    private miniStream ministream;
    private catchStream catchstream;
    private catchConnector connector;
    // private List<miniObject> objects = new ArrayList<>();

//    public StreamController(miniStream stream) {
//        this.stream = stream;
//   }
    public StreamController(miniStream ministream) throws IOException, ParseException {
        connector = new catchConnector("kamildzi", "soos07");
        this.catchstream = getStreamFromMiniStream(ministream);

        this.ministream = ministream;
        catchstream.setContributorsMap(connector.getStreamContributors(ministream.getId()));

    }

    private catchStream getStreamFromMiniStream(miniStream ministream) throws IOException, ParseException {

        HttpResponse response = connector.getStream(ministream.getId());
        return new catchStream(response);
    }

    // private void setConnectionTODatabase()
    public void addStreamToDataBase() {

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

            //    statement.executeUpdate("drop table if exists person");
            //  statement.executeUpdate("create table person (id integer, name string)");
            //  statement.executeUpdate("insert into person values(1, 'leo')");
            //  statement.executeUpdate("insert into person values(2, 'yui')");
            //    statement.executeUpdate("insert into STREAMS values(" + "'ca'" + ", " + 1 + ", " + 1 + ", " + "'hehe'" + ", " + "'source'" + ", " + "'createdat'" + ", " + "'modifieddat'"  + ", " + 5 + ", " + "'usecolor'"  + ", " + "'olor'" + ", " + 5 + ", " + 5 + ", " + "'isuptodate'" +", " + "'deletedlocally'" + ", " + "'deletlly'" + ")");
            // statement.executeUpdate("insert into STREAMS (stream_id, contributor_count, name, server_modified_at, server_created_at) values(" + stream.getId() + ", " + stream.getContributor_count()+ ", " + stream.getName() + ", " + stream.getServer_modified_at() + ", " + stream.getServer_modified_at()  );

            // Set<String> keyset = ministream.getAnnotations().keySet();
            // System.out.println(""+keyset.toArray()[0]);
            statement.executeUpdate("insert into STREAMS values(" + "'" + catchstream.getId() + "'" + ", " + catchstream.getCount() + ", " + catchstream.getContributor_count() + ", " + "'" + catchstream.getName() + "'" + ", " + "'" + catchstream.getSource() + "'" + ", " + "'" + catchstream.getCreated_at() + "'" + ", " + "'" + catchstream.getModified_at() + "'" + ", " + ministream.getServer_modified_at() + ", " + "'" + ministream.getAnnotations().get("user:color") + "'" + ", " + "'" + ministream.getAnnotations().get("color") + "'" + ", " + ministream.getServer_created_at() + ", " + catchstream.getServer_deleted_at() + ", " + "'YESrecznie'" + ", " + "'NOrecznie'" + ", " + "'NOrecznie'" + ")");

            System.out.println("id obiektu " + catchstream.getObjects().size());


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
    }

    public void addContributorsToDatabase() throws IOException, ParseException {

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

            //catchstream.setContributorsMap(connector.getStreamContributors(ministream.getId()));
            List<String> userIdList = catchstream.getUser_idList();
            // List<String> userNameList = catchstream.getUser_nameList();
            for (int i = 0; i < userIdList.size(); i++) {
                statement.executeUpdate("insert into CONTRIBUTORS values(" + "'" + userIdList.get(i) + "'" + ", " + "'" + catchstream.getId() + "'" + ")");
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

    public void addUsersToDatabase() throws IOException, ParseException {
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

            //catchstream.setContributorsMap(connector.getStreamContributors(ministream.getId()));
            List<String> userIdList = catchstream.getUser_idList();
            List<String> userNameList = catchstream.getUser_nameList();
            for (int i = 0; i < userIdList.size(); i++) {
                // System.out.println("id usera w users to "+userIdList.get(i) + "a jego name to" + userNameList.get(i));
                statement.executeUpdate("insert into USERS(user_id, user_name) values(" + "'" + userIdList.get(i) + "'" + ", " + "'" + userNameList.get(i) + "'" + ")");
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

    // private String getObjectId(int i) {
    //      return catchstream.getObjects().get(i).getId(); 
    //  }
    public miniStream getMiniStream() {
        return ministream;
    }

    public catchStream getCatchStream() {
        return catchstream;
    }
}
