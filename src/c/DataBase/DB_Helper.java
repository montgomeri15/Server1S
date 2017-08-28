package c.DataBase;

import c.Server.Frame_Server;
import c.Server.Users_Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB_Helper {

    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultSet;

    List<Users_Server> listUsersServer = new ArrayList<>();
    List<Users_Server> listIpPort = new ArrayList<>();
    List<Users_Server> listItems = new ArrayList<>();
    List<Users_Server> listItemsNames = new ArrayList<>();

    /***Подключение к Отчет***/
    public static void connectDB() throws ClassNotFoundException, SQLException {

        connection = null;  //при старте нулл коннекшин
        Class.forName("org.sqlite.JDBC");  //sql-ная джава дата бэйз
        connection = DriverManager.getConnection("jdbc:sqlite:src/c/DataBase/Otchet.db");  //выбираем директорию, где лежит база данных. Если это расположено на каком-то диске, перед jdbc ввести адрес со слешами
        System.out.println("База Подключена!");
    }

    /***Создание таблицы Юзеры все! ***/
    public static void createTableUsers() throws ClassNotFoundException, SQLException  //обработка ошибок
    {
        statement = connection.createStatement();  //создает таблицу
        statement.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY NOT NULL, 'name' TEXT, 'password' TEXT, 'position' TEXT);");  //вводим параметры (поля, данные, свойства)
        System.out.println("Таблица юзеров создана или уже существует .");
    }

    /*** Создание таблицы IP и Порт*/
    public void createTableIpPort() throws SQLException {
        statement = connection.createStatement();
        statement.execute("CREATE TABLE if not exists 'ip/port' ('ip' INTEGER NOT NULL, 'port' TEXT NOT NULL)");
        System.out.println("Таблица портов и ip создана или уже существует. ");
    }

    /*** Создание таблицы товаров*/
    public void createTableTovary() throws ClassNotFoundException, SQLException {
        statement = connection.createStatement();
        statement.execute("CREATE TABLE if not exists 'items' ('name' TEXT NOT NULL, 'price' INTEGER NOT NULL, 'skolko' INTEGER NOT NULL, 'summa_za_den' INTEGER NOT NULL, 'data' TEXT NOT NULL)");
        System.out.println("Таблица товаров создана или уже существует. ");
    }

    /*** Создание таблицы наименований товаров*/
    public void createTableItemsNames() throws ClassNotFoundException, SQLException {
        statement = connection.createStatement();
        statement.execute("CREATE TABLE if not exists 'items_names' ('item_name' TEXT NOT NULL, 'price_name' INTEGER NOT NULL)");
        System.out.println("Таблица товаров создана или уже существует. ");
    }

    /***Заполнение таблицы users***/
    public static void writeTableUsers(List<Users_Server> list) throws SQLException {

        int i = 2; //Счетчик

        String name = null;
        String password = null;
        String position = null;

        Frame_Server frame_server = new Frame_Server();

        //int id = frame_server.getGetRowCountForJTable(); //Получаем id с класса фрейм_сервер
//        String name = frame_server.getNameFromJTable(); //Получаем имя с класса фрейм_сервер
//        String password = frame_server.getPasswordFromJTable(); //Получаем пароль с класса фрейм_сервер
//        String position = frame_server.getPositionFromJCheckBox();

        for (Users_Server el : list) {
            name = el.getName();
            password = el.getPassword();
            position = el.getPosition();

            PreparedStatement ps = connection.prepareStatement("insert into 'users' values (?, ?, ?, ?)");

            //ps.setInt(1, i);
            ps.setString(2, name);
            ps.setString(3, password);
            ps.setString(4, position);
            ps.executeUpdate();
        }

        System.out.println("Таблица заполнена");
        i++;
    }

    /*** Данный метод производит перезапись данных в таблице юзеров*/
    public static void reWriteTableUsers(int id, String name, String password, String position) throws SQLException {

        String sql = "UPDATE 'users' set id = ?, name = ?, password = ?, position = ? WHERE id = " + id;
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, password);
        ps.setString(4, position);
        ps.executeUpdate();
    }

    /*** Удаление из таблицы users*/
    public void removeTableUsers(int id) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("DELETE FROM 'users' WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();

    }

    /*** Заполнение таблицы ip/port*/
    public void writeTableIpPort(String ip, int port) throws SQLException {

        /** Делаем проверку, на то есть ли у нас в БД даные по серверу */
        if (readTableIpPort().size() > 0) {

            PreparedStatement psUpdate = connection.prepareStatement("UPDATE 'ip/port' SET ip = ?, port = ?");
            psUpdate.setString(1, ip);
            psUpdate.setInt(2, port);
            psUpdate.executeUpdate();
        } else {

            PreparedStatement psWrite = connection.prepareStatement("insert into 'ip/port' values (?, ?)");
            psWrite.setString(1, ip);
            psWrite.setInt(2, port);
            psWrite.executeUpdate();

        }
        System.out.println("Данные портов обновленны.");
    }

    /*** Чтение из таблицы ip/port*/
    public List<Users_Server> readTableIpPort() throws SQLException {

        listIpPort.clear(); //Чистим Коллекцию с портом и айпи, что бы она всегда перед заполнением была пустая

        resultSet = statement.executeQuery("SELECT * FROM 'ip/port' ");

        while (resultSet.next()) {
            String ip = resultSet.getString("ip");
            int port = resultSet.getInt("port");

            Users_Server users_server = new Users_Server();
            users_server.setIp(ip);
            users_server.setPort(port);

            listIpPort.add(users_server);
        }

        return listIpPort;
    }

    /*** Чтение из таблицы items_names и добавляем в Коллекцию*/
    public List<Users_Server> readTableItemsNames() throws SQLException {

            resultSet = statement.executeQuery("SELECT * FROM items_names");
            listItemsNames.clear();

            String item_name;
            int price_name;

            while (resultSet.next()) {

                item_name = resultSet.getString("item_name");
                price_name = resultSet.getInt("price_name");

                System.out.println("Name: " + item_name + " Price: " + price_name);

                Users_Server users_server = new Users_Server();
                users_server.setName_tov(item_name);
                users_server.setPrice_name(price_name);

                listItemsNames.add(users_server);
                System.out.println(listItemsNames + " \nЭто мы получили в классе ДБХелпер метод вытаскивание названий товаров");
            }
            return listItemsNames;
    }


    /*** Читаем таблицу паролей и добавляем в Коллекцию*/
    public List<Users_Server> readTableUsers() throws SQLException {

        resultSet = statement.executeQuery("SELECT * FROM users");
        listUsersServer.clear();

        String password;
        String name;

        while (resultSet.next()) {

            int id = resultSet.getInt("id");
            name = resultSet.getString("name");
            password = resultSet.getString("password");
            String position = resultSet.getString("position");
            System.out.println("Id: " + id);
            System.out.println("Name: " + name);
            System.out.println("Password: " + password);
            System.out.println("Position: " + position);

            Users_Server users_server = new Users_Server();
            users_server.setId(id);
            users_server.setName(name);
            users_server.setPassword(password);
            users_server.setPosition(position);

            listUsersServer.add(users_server);
        }
        return listUsersServer;
    }

    /** Заполнение таблицы items*/
    public static void writeTableItems(String name, int price, int skolko, int summa_za_den, String data) throws SQLException {

//        Table_Pri table_pri = new Table_Pri();

//        int id_tov = table_pri.getId_tov();  //Получаем id
//        String name = table_pri.getNameTovaraJTable();  //Получаем имя с класса таблицы товаров
//        int price = table_pri.getPriceTovaraJTable();
//        int skolko = table_pri.getSkolkoTovaraJTable();
//        int summa_za_den = table_pri.getSummaTovaraJTable();
//        String data = table_pri.getDataTovara();

        System.out.println(name + " " + price + " " + skolko + " " + summa_za_den);

        PreparedStatement ps = connection.prepareStatement("insert into 'items' values (?, ?, ?, ?, ?, ?)");

        //ps.setInt(1, id_tov);
        ps.setString(2, name);
        ps.setInt(3, price);
        ps.setInt(4, skolko);
        ps.setInt(5, summa_za_den);
        ps.setString(6, data);
        ps.executeUpdate();

        System.out.println("Таблица заполнена");
    }

    /*** Данный метод производит перезапись данных в таблице товаров*/
    public static void reWriteTableUsers(int id_tov, String name_tov, int price, int skolko, int summa_za_den, String data) throws SQLException {

        String sql = "UPDATE 'items' set id_tov = ?, name_tov = ?, price = ?, summa_za_den = ?, data = ?, WHERE id_tov = " + id_tov;
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id_tov);
        ps.setString(2, name_tov);
        ps.setInt(3, price);
        ps.setInt(4, skolko);
        ps.setInt(5, summa_za_den);
        ps.setString(6, data);
        ps.executeUpdate();
    }

    /*** Удаление из таблицы items*/

    public void removeTableItems(int id_tov) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("DELETE FROM 'items' WHERE id_tov = ?");
        ps.setInt(1, id_tov);
        ps.executeUpdate();

    }
    /*** Читаем таблицу товаров и добавляем в Коллекцию*/
    public List<Users_Server> readTableItems() throws SQLException {

        resultSet = statement.executeQuery("SELECT * FROM items");
        listItems.clear();

        String name_tov;
        int price;
        int skolko;
        int summa_za_den;
        String data;

        while (resultSet.next()) {

            int id_tov = resultSet.getInt("id_tov");
            name_tov = resultSet.getString("name_tov");
            price = resultSet.getInt("price");
            skolko = resultSet.getInt("skolko");
            summa_za_den = resultSet.getInt("summa_za_den");
            data = resultSet.getString("data");

            System.out.println("Id: " + id_tov + " Name: " + name_tov + " price: " + price + " skolko: " + skolko + " summa_za_den: " + summa_za_den + "data: " + data);

            Users_Server users_server = new Users_Server();
            users_server.setId_tov(id_tov);
            users_server.setName_tov(name_tov);
            users_server.setPrice(price);
            users_server.setSkolko(skolko);
            users_server.setSumma_za_den(summa_za_den);
            users_server.setData(data);

            listItems.add(users_server);
        }
        return listItems;
    }
}
