package c.Server;


import c.DataBase.DB_Helper;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Этот класс отвечает за слушателя сервера, его действия:
 * 1. Слушает кто подключился, как только появился какой то коннект, он должен перенаправить на класс обработки данных */

public class Server_Listener extends Thread {

    int clientNumber = 0;//Счетчик клиентов
    public static boolean check = true;

    DB_Helper db_helper = new DB_Helper();

    Socket socket;
    List<Server_Client> server_clients_list = new ArrayList();

    public static List<ServerSocket> list = new ArrayList<>();

    int port;
    String ip;
    ServerSocket ss;

    public static Server_Listener server_listener;

    Server_Listener(){
        server_listener = this;
    }

    public void run(){

        Frame_Server frame_server = new Frame_Server();

        try {

            /** Сначала делаем проверку, на то ли есть вообще сохраненные данные в БД по серверу, если ничего нету
             то должно выйти сообщение что там пусто и перекинуть на фрейм настроек! */
            db_helper.createTableIpPort(); //Проверяем на подключение таблицы портов
            if (db_helper.readTableIpPort().size() > 0) {

                System.out.println("Сервер запущен");

                /** Здесь происходят действия если БД настрек сервера заполнена */
                port = db_helper.readTableIpPort().get(0).getPort(); //Получаем конфигурации порта
                ip = db_helper.readTableIpPort().get(0).getIp(); //Это получаем конфигурации IP

                ss = new ServerSocket(port); //Указиваем серверному сокету какой порт он должен слушать

                while (check == true) {

                    if (check == false) {
                        System.out.println("Thread stop");
                    }
                    System.out.println(check + " Thread");
                    socket = ss.accept(); //Слушаем подключенный порт

                    System.out.println("Client number: " + clientNumber + " connected");
                    /** Создаем новый объект класса обработки данных вошедших данных, и в его конструктор ссылку на сокет
                     и номер в коллекции и общий номер клиента */
                    Server_Client server_client = new Server_Client(socket, clientNumber);
                    server_client.setName(String.valueOf(clientNumber));
                    server_clients_list.add(server_client);
                    server_client.start(); //Запусаем класс обработки вошедших данных в новом потоке для каждого пользователя
                    clientNumber++; //Делаем счетчик клиентов на +1

                }

                socket.close();

            } else {
                int i = JOptionPane.showConfirmDialog(null, "Сначала настройте сервер!", "Server Error", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    Server_Configuration server_configuration = new Server_Configuration();
                    server_configuration.server_configuration();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void threaClientListenerStop(String clientName){

        for (Thread el : server_clients_list){
            if (el.getName().equals(clientName)){
                el.stop();
            }
        }
    }

    public void Stop() {

        check = false;

    }
}
