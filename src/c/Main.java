package c;

import c.DataBase.DB_Helper;
import c.Server.Frame_Server;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

//        Server_Configuration server_configuration = new Server_Configuration();
//        server_configuration.server_configuration();

        DB_Helper db_helper = new DB_Helper();

        try {

            db_helper.connectDB();
            db_helper.createTableUsers();
            db_helper.createTableTovary();
            //db_helper.writeTableUsers();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        Frame_Server frame_server = new Frame_Server();
        frame_server.frame_Server();
    }
}

//Изменения внесены в Table_Pri, Users_Server, DB_Helper, Frame_Client