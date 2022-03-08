import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Class DatabaseConnector performs connection to database
 * and all actions connected to operating on db
 */

public class DatabaseConnector {
    protected Connection connection;
    protected String lastTripName;

    public DatabaseConnector(){
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e) {
            System.err.println (e);
        }
        try {
            // open connection to database
            this.connection = DriverManager.getConnection(
                    "jdbc:postgresql://castor.db.elephantsql.com/ijwpwexe", "ijwpwexe", "8apajmCQ8lcLgOeZxlVL4A1vVMGEc1wI");
            Statement statement = connection.createStatement ();
            statement.executeUpdate("SET search_path to project");
        }
        catch (java.sql.SQLException e) {
        }
    }

    /**
     * SELECT name FROM trip_list
     */
    public String[] seeMyTrips() {
        List<String> list = new ArrayList<String>();
        try {
            String query = "SELECT name FROM trip_list";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next())
                list.add(rs.getString("name"));
        } catch (java.sql.SQLException e) {
        }

        String[] result = new String[list.size()];
        int idx = 0;
        for(String el: list){
            result[idx++]=el;
        }
        return result;
    }

    /**
     * SELECT start, finish, duration FROM plan_trip WHERE trip_no is chosen trip
     * @param name chosen trip name
     */
    public String[][] seeTripPoints(String name) {
        List<String[]> list = new ArrayList<String[]>();
        try {
            String query = "SELECT start, finish, duration FROM plan_trip WHERE trip_no IN (SELECT id FROM trip_list WHERE name = \'"+name+"\')";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                String[] row = new String[3];
                for(int i=1; i<=3; i++){
                    if(i<3){
                        query = "SELECT name FROM keypoints WHERE id =\'"+rs.getString(i)+"\'";
                        statement = connection.createStatement();
                        ResultSet r = statement.executeQuery(query);
                        r.next();
                        row[i-1] = r.getString("name");
                    }
                    else
                        row[i-1] = rs.getString("duration");
                }
                list.add(row);
            }
        } catch (java.sql.SQLException e) {
        }
        String[][] result = new String[list.size()][3];
        int idx = 0;
        for(String[] el: list){
            for(int i=0; i<3; i++){
                result[idx][i] = el[i];
            }
            idx++;
        }
        return result;
    }

    /**
     * DELETE FROM trip_list WHERE id is chosen planned trip
     * @param name chosen trip name
     */
    public void deleteMyTrip(String name) {
        try {
            String query = "DELETE FROM trip_list WHERE name = \'"+name+"\'";
            Statement statement = connection.createStatement();
            statement.executeQuery(query);

        } catch (java.sql.SQLException e) {}

    }

    /**
     * SELECT overall duration od the trip
     * @param name chosen trip name
     */
    public String showDur(String name){
        String result = new String();
        try {
            String query = "SELECT duration FROM trip_list WHERE name = \'" + name + "\'";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            result = rs.getString("duration");
        }catch(java.sql.SQLException e){}
        return result;
    }

    /**
     * INSERT INTO trip_list
     * @param name new name passed by user
     */
    public Boolean createTrip(String name){
        lastTripName = name;
        try {
            String query = "INSERT INTO trip_list(name, duration) VALUES (\'" + name + "\', '00:00:00')";
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
        }catch(java.sql.SQLException e) {
            if (e.toString().contains("ERROR")) {
                showMessageDialog(null, "Ta nazwa jest już zajęta!");
                return false;
            }
        }
        return true;
    }

    /**
     * SELECT all points that can be a start point (using views)
     */
    public String[] seeStartPoints() {
        List<String> list = new ArrayList<String>();
        try {
            String query = "SELECT name FROM start_points";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            String query2 = "SELECT name FROM shelters";
            Statement statement2 = connection.createStatement();
            ResultSet rs2 = statement2.executeQuery(query2);

            while (rs.next())
                list.add(rs.getString("name"));
            while (rs2.next())
                list.add(rs2.getString("name"));
        } catch (java.sql.SQLException e) {}

        String[] result = new String[list.size()];
        int idx = 0;
        for(String el: list){
            result[idx++]=el;
        }
        return result;
    }

    /**
     * SELECT all points that can be a next point for an already chosen point (using n:m relation table)
     * @param name is FROM point
     */
    public String[] availablePoints(String name) {
        List<String> list = new ArrayList<String>();
        try {
            String query = "SELECT name FROM keypoints WHERE id IN(" +
                    "SELECT id_keypoint FROM trail_has_keypoints WHERE id_trail IN (" +
                    "SELECT id_trail FROM trail_has_keypoints WHERE id_keypoint IN (" +
                    "SELECT id FROM keypoints WHERE name=\'"+name+"\') GROUP BY id_trail) GROUP BY id_keypoint)";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next())
                list.add(rs.getString("name"));
        } catch (java.sql.SQLException e) {}

        String[] result = new String[list.size()];
        int idx = 0;
        for(String el: list){
            result[idx++]=el;
        }
        return result;
    }

    /**
     * SELECT id FROM trip_list with given name
     */
    public int checkTripID() {
        int id = -1;
        try {
            String query = "SELECT id FROM trip_list WHERE name=\'"+lastTripName+"\'";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            id = rs.getInt("id");
        } catch (SQLException e) {}
        return id;
    }

    /**
     * SELECT id FROM keypoints with given name
     * @param name name of keypoint to search for id
     */
    public int checkPointID(String name) {
        int id = -1;
        try {
            String query = "SELECT id FROM keypoints WHERE name=\'"+name+"\'";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            id = rs.getInt("id");
        } catch (SQLException e) {}
        return id;
    }

    /**
     * SELECT flash FROM keypoints flag to check whether user need a flashlight during the trip
     * @param name name od keypoint
     */
    public boolean checkFlash(String name) {
        try {
            String query = "SELECT flash FROM keypoints WHERE id IN (SELECT finish FROM plan_trip WHERE trip_no=(SELECT id FROM trip_list WHERE name=\'"+name+"\'))";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                if(rs.getBoolean("flash")) return true;
            };

        } catch (SQLException e) {}
        return false;
    }

    /**
     * SELECT finish FROM plan_trip with the highest SERIAL number
     */
    public String checkLastFinish(){
        String finish = "";
        try {
            String query = "SELECT name FROM keypoints WHERE id=(SELECT finish FROM plan_trip WHERE id = (SELECT MAX(id) FROM plan_trip WHERE trip_no=\'"+checkTripID()+"\'))";
            Statement statement = connection.createStatement();
            ResultSet r = statement.executeQuery(query);
            r.next();
            finish = r.getString("name");
        } catch (java.sql.SQLException e) {}
        return finish;
    }

    /**
     * INSERT INTO plan_trip - first point (with start keypoint)
     * @param start start point of segment
     * @param finish finish point of segment
     */
    public boolean addPoint(String start, String finish){
        try {
            String query = "INSERT INTO plan_trip(trip_no, start, finish) VALUES ("+checkTripID()+","+checkPointID(start)+","+checkPointID(finish)+")";
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
        }catch (java.sql.SQLException e){}
        return false;
    }

    /**
     * INSERT INTO plan_trip - further points - automatic FROM deduction
     * @param finish finish point of the segment
     */
    public boolean addPoint(String finish) {
        try {
            String query = "INSERT INTO plan_trip(trip_no, finish) VALUES ("+checkTripID()+","+checkPointID(finish)+")";
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
        }catch (java.sql.SQLException e){
            if(e.toString().contains("ERR")) return true;
            System.out.println(e.toString());
        }
        return false;
    }

    /**
     * DELETE FROM plan_trip for the highest serial (last added point)
     */
    public void deleteLastPoint(){
        try {
            String query = "DELETE FROM plan_trip WHERE id = (SELECT MAX(id) FROM plan_trip WHERE trip_no=\'"+checkTripID()+"\')";
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
        } catch (java.sql.SQLException e) {}
    }

    /**
     * DELETE FROM trip_list
     */

    public void deleteAll() {
        try {
            String query = "DELETE FROM trip_list";
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e) {}
    }
    /**
     * closes database connection
     */
    public void close(){
        try {
            connection.close();
        } catch (java.sql.SQLException e){
            System.out.println("Connection error");
        }
    }

}
