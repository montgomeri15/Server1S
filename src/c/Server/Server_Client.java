package c.Server;


import c.Server.ActionWithClients.Autorization;
import c.Server.ActionWithClients.DKBAction;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server_Client extends Thread {

    Socket socket;
    int clientNumber;
    boolean trueF = false;

    Server_Client(Socket socket, int clientNumber) { //Конструктор в который мы передаем значение
        this.socket = socket;
        this.clientNumber = clientNumber;
        server_client();
    }

    String clientNamePosition;
    String tabeleNameFromClient;
    String comand;

    transient ObjectInputStream obInputStream;
    transient ObjectOutputStream obOutStream;

    transient InputStream in;
    transient OutputStream out;

    List<Users_Server> listForReturn = new ArrayList<>();

    public void server_client() {

        Server_Listener server_listener = new Server_Listener();

        try {

            obInputStream = new ObjectInputStream(socket.getInputStream());
            obOutStream = new ObjectOutputStream(socket.getOutputStream());

            while (trueF == false){

                try {
                    while (!socket.isInputShutdown()){

                        listForReturn.clear();

                        String name = (String) obInputStream.readObject();
                        String password = (String) obInputStream.readObject();
                        String clientNamePosition = (String) obInputStream.readObject();
                        String tableName = (String) obInputStream.readObject();
                        String comand = (String) obInputStream.readObject();
                        List<Users_Server> list = (List<Users_Server>) obInputStream.readObject();
                        System.out.println("Name: " + name + "\npass: " + password + "\nposition: " + clientNamePosition + "\nTable name: " + tableName + "\ncomand: " + comand + "\nList: " + list);

                        /** Сначала происходит проверка, от какого клиента пришло сообщение, если это авторизация, то откроет один класс для
                         * обработки, если же это кто либо другой, то открывает второй класс это - DKBAction */
                        if (clientNamePosition.equals("autorization")) {
                            Autorization autorization = new Autorization();
                            String auto = String.valueOf(autorization.autorization(name, password));
                            String position = autorization.clientName();

                            obOutStream.writeObject(auto);
                            obOutStream.writeObject(position);
                            //obOutStream.writeObject(list);
                        }
                        else {
                            /** Вот здесь вот оно открывает класс DKBAction */
                            System.out.println("Теперь еще в следующий иф зашло");
                            DKBAction dkbAction = new DKBAction();
                            List<Users_Server> listToClient = dkbAction.dkbAction(tableName, comand, list);

                            System.out.println("Подготовка к отправке");
                            obOutStream.writeObject(listToClient);
                            System.out.println("Это то что мы только что отправили клиенту при любой другой команде кроме авторизации! В классе Сервер_Клиент\n" + listToClient);
                            System.out.println("По идее файлы должны быть отправлены");
                        }

                        obOutStream.flush();
                        break;

                    }
                    trueF = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Сокет закрыт");
            obOutStream.close();
            obInputStream.close();
            stop();
            System.out.println(this.isAlive());

            //socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

/*

1. Все вычисления происходят на сервере, клиент выполняет роль только отображения
2. Должно передаваться при Коннекте к серверу:
    При первом коннекте с авторизации передается логин и пароль
    Сделать отдельный класс на сервере авторизации, который принимает только логин и пароль, обрабаотывает их и возвращает тру или фолс

    Отдельный фрейм для Бухгалтера, Директора, Касира, что бы они выполняли определенные свои операции с БД

3. Передаются такие данные:
    Имя клиента.
    Имя таблицы (Передается сразу при коннекте к серверу и потом делается определение к какому классу делается перенаправление)
    Команда для БД (Передется при работе в отм или ином классе при работе с клиентом) не при подключении к серверу!


 */

/*

String name = (String) obInputStream.readObject();
                        String password = (String) obInputStream.readObject();
                        clientNamePosition = (String) obInputStream.readObject();
                        tabeleNameFromClient = (String) obInputStream.readObject();
                        comand = (String) obInputStream.readObject();
                        list = (List<Users_Server>) obInputStream.readObject();

                        System.out.println(name + " name");
                        System.out.println(password + " password");
                        System.out.println(clientNamePosition + " position");
                        System.out.println(tabeleNameFromClient + " table name");
                        System.out.println(comand + " comand");
                        System.out.println(list + " list");

                        obInputStream = new ObjectInputStream(socket.getInputStream());

 */