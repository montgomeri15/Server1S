package c.Server.ActionWithClients;


import c.DataBase.DB_Helper;
import c.Server.Users_Server;

import java.sql.SQLException;
import java.util.List;


/** Этот класс, отвечает за обработку данных, которые приходят от клиентов, а точнее
 * (от Касира, Бухгалтера, Директора)  */
public class DKBAction {

    DB_Helper db_helper = new DB_Helper();

    List<Users_Server> list = null;

    public List<Users_Server> dkbAction(String tableName, String comand, List<Users_Server> listFromClient){

        System.out.println(tableName + " \n" + comand + " \n" + listFromClient);

        try {

            /** Здесь происходят действия с таблицей Юзеров, чтение
             * запись, удаление, перезапись */
            if (tableName.equals("users")) {
                if (comand.equals("read")) {
                    list = db_helper.readTableUsers();
                }
                if (comand.equals("write")){
                    db_helper.writeTableUsers(list);
                }
                if (comand.equals("remove")){
                    for (Users_Server el : listFromClient){
                        int id = el.getId();
                        db_helper.removeTableUsers(id);
                    }
                }
                if (comand.equals("update")){
                    /** Для того, что бы перезаписать Юзеров, в тот метод необходимо отправить
                     * его обнавленные данные, которые мы вытаскиваем из коллекции в цыкле */
                    for(Users_Server el : listFromClient){
                        int id = el.getId();
                        String name = el.getName();
                        String password = el.getPassword();
                        String position = el.getPosition();

                        db_helper.reWriteTableUsers(id, name, password, position);
                    }
                }
            }

            if (tableName.equals("items")){
                if (comand.equals("read")){
                    list = db_helper.readTableItems();
                }
                if (comand.equals("write")){
                    /** Здесь нужно будет переделать метод записи в таблицу товаров, потому что
                     * он настроен на то, что запись будет происходить только с одного места,
                     * нужно будет сделать так, что бы при обращении к тому методу, я уже при вызове отправлял туда данные,
                     * что то типо как в конструктор передавал. Так же как это сделано в методе reWriteTableUsers */

                    for (int i = 0; i < listFromClient.size(); i++) {

                        String nameTov = listFromClient.get(i).getName_tov();
                        int price = listFromClient.get(i).getPrice_name();
                        int skolko = listFromClient.get(i).getSkolko();
                        int summa_za_den = listFromClient.get(i).getSumma_za_den();
                        String data = listFromClient.get(i).getData();

                        db_helper.writeTableItems(nameTov, price, skolko, summa_za_den, data);
                    }
                }
                if (comand.equals("remove")){
                    /** Важный момент!!!!
                     * Для того что бы удалить елемент Товара, необходимо при запросе отправлять
                     * коллекцию, вместе с информацией о данном товаре, но самое главное это ID, потому что
                     * по нему будет происходить удаление! **/

                    for (Users_Server el : listFromClient){
                        int id = el.getId();
                        db_helper.removeTableItems(id);
                        System.out.println(id + " Это то что мы получаем Айди для удаления с БД");
                    }
                }
            }

            if (tableName.equals("itemsName")){
                if (comand.equals("read")){
                    list = db_helper.readTableItemsNames();

                    System.out.println("Эту колекцию получили при вытаскивании с БД Названий товаров в классе ДКБЕкшн \n" + list);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
