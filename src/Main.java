/**
 * @author Aleksandra Krzemińska
 */

public class Main {
    public static void main(String[] args){

        DatabaseConnector db = new DatabaseConnector();
        new GUI(db);
    }
}
