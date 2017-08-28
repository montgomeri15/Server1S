package c.Server.ActionWithClients;


import c.DataBase.DB_Helper;
import c.Server.Users_Server;

import java.sql.SQLException;
import java.util.List;


/** Этот класс отвечает за обработку данных пришедших с фрейма авторизации
 * он считывает данные с БД, сверяет пароль и логин которые прислал клиент
 * и если данные совпадают, то он возвращает true в класс Server_Client (который
 * отвечает за прием и отправку данных клиенту), или false отправляет в тот же клас.*/
public class Autorization {

    int i = 0; //Переменая что бы проверять в классе clientName вытащил ли метод autorization данные из БД

    DB_Helper db_helper = new DB_Helper();

    List<Users_Server> list;

    String position = null;

    public boolean autorization(String name, String password){

        boolean checkAutorization = false; //Переменная котороя возвращается результат в класс приема и отправку данных клиенту

        try {

            position = null;
            list = db_helper.readTableUsers(); //Считываем с БД данные по клиентам
            for (Users_Server el : list){
                if (el.getName().equals(name) && el.getPassword().equals(password)){

                    /** Если присланные данные от клиента логина и пароля совпадают,
                     * то данной переменной присваиватся значение тру */
                    checkAutorization = true;
                    position = el.getPosition();
                    i++;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return checkAutorization;
    }

    public String clientName(){

        boolean truef = false;
        while(truef != true) {
            if (i != 0) {
                if (position.equals("Директор")) {
                    System.out.println(position + " Проверка должности, в классе Autorization");
                    truef = true;
                }
                if (position.equals("Касир")) {
                    System.out.println(position + " Проверка должности, в классе Autorization");
                    truef = true;
                }
                if (position.equals("Бухгалтер")) {
                    System.out.println(position + " Проверка должности, в классе Autorization");
                    truef = true;
                } else {
                    if (position == null){
                        truef = true;
                    }
                }
            }
        }
        i = 0;
        return position;
    }
}
