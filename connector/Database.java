package connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static String sDriverName = "org.sqlite.JDBC";
    private static String sDbName = "jdbc:sqlite:catch.db";

    public static void dropDb() {

        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(sDbName);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("delete from OBJECTS ");
            statement.executeUpdate("delete from TAGS ");
            statement.executeUpdate("delete from TAGS_IN_OBJECTS");
            statement.executeUpdate("delete from OBJECT_IN_STREAM");
            statement.executeUpdate("delete from STREAMS");
            statement.executeUpdate("delete from USERS");
            statement.executeUpdate("delete from CONTRIBUTORS");
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
                System.err.println(e);
            }
        }
    }

    public static String getObjectStream(String objectId) {

        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(sDbName);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs;
            rs = statement.executeQuery("SELECT stream_id FROM OBJECT_IN_STREAM WHERE object_id='" + objectId + "'");
            if (rs.next()) {
                return rs.getString(0);
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
                System.err.println(e);
            }
        }
        return "";
    }

    public static List<String> getStreamObject(String streamId) {

        List<String> objectIdList = new ArrayList<String>();

        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(sDbName);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs;
            rs = statement.executeQuery("SELECT object_id FROM OBJECT_IN_STREAM WHERE stream_id='" + streamId + "'");
            while (rs.next()) {
                objectIdList.add(rs.getString(0));
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
                System.err.println(e);
            }
        }
        return objectIdList;
    }

    public static boolean checkUser(String userpass) {
        String u = "";
        //System.out.println("Sprawdzam usera w bazie");
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
        ResultSet rs = null;
        Connection connection = null;
        try {

            connection = DriverManager.getConnection(sDbName);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            rs = statement.executeQuery("SELECT count(*) FROM USERS WHERE encoded_login_data='" + userpass + "'");

            while (rs.next()) {
                u = rs.getString(1);

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
                System.err.println(e);
            }
        }
        return (u.equals("1") ? false : true);
    }

    public static void createNewUser(catchConnector conn) {

        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(sDbName);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("INSERT INTO USERS VALUES ('45345','" + conn.getLogin() + "','" + conn.getLogin() + "','" + conn.getEncodedLoginData() + "')");

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
                System.err.println(e);
            }
        }
    }

    public static catchConnector getUser() {

        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
        ResultSet rs = null;
        Connection connection = null;
        catchConnector conn = null;
        try {
            connection = DriverManager.getConnection(sDbName);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            rs = statement.executeQuery("SELECT * FROM USERS WHERE encoded_login_data is not null");

            while (rs.next()) {
                conn = new catchConnector(rs.getString("user_name"), rs.getString("encoded_login_data"), true);
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
                System.err.println(e);
            }
        }

        return conn;
    }

    public static String getColorForStream(String streamId) {

        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
        ResultSet rs = null;
        Connection connection = null;
        String color = null;
        try {
            connection = DriverManager.getConnection(sDbName);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            rs = statement.executeQuery("SELECT user_color FROM STREAMS WHERE stream_id ='" + streamId + "'");

            while (rs.next()) {
                color = rs.getString("user_color");
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
                System.err.println(e);
            }
        }

        return color;
    }
}
