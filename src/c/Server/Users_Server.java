package c.Server;

import java.io.Serializable;

public class Users_Server implements Serializable {

    int id;
    String name;
    String password;
    String position;
    String chek;
    String d_otchet;
    String m_otchet;

    String ip;
    int port;

    int id_tov;
    String name_tov;
    int price;
    int skolko;
    int summa_za_den;
    String data;

    String item_name;
    int price_name;

    public Users_Server(int id, String name, String password, String position, String chek, String d_otchet, String m_otchet, String name_item, int price, String data, String item_name, int price_name) {

        this.id = id;
        this.name = name;
        this.password = password;
        this.position = position;
        this.chek = chek;
        this.d_otchet = d_otchet;
        this.m_otchet = m_otchet;
        this.name_tov = name_item;
        this.price = price;
        this.skolko = skolko;
        this.summa_za_den = summa_za_den;
        this.id_tov = id_tov;
        this.data = data;
        this.item_name = item_name;
        this.price_name = price_name;
    }

    public Users_Server(){
    }

    public String getM_otchet() {
        return m_otchet;
    }

    public void setM_otchet(String m_otchet) {
        this.m_otchet = m_otchet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getChek() {
        return chek;
    }

    public void setChek(String chek) {
        this.chek = chek;
    }

    public String getD_otchet() {
        return d_otchet;
    }

    public void setD_otchet(String d_otchet) {
        this.d_otchet = d_otchet;
    }

    /** Здесь я добавил отдльно Гетеры и Сетеры для вытаскивания IP и port с БД*/
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /** Здесь геттеры и сеттеры для товаров */

    public int getId_tov() {
        return id_tov;
    }

    public void setId_tov(int id_tov) {
        this.id_tov = id_tov;
    }

    public String getName_tov() {
        return name_tov;
    }

    public void setName_tov(String name_tov) {
        this.name_tov = name_tov;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSkolko() {
        return skolko;
    }

    public void setSkolko(int skolko) {
        this.skolko = skolko;
    }

    public int getSumma_za_den() {
        return summa_za_den;
    }

    public void setSumma_za_den(int summa_za_den) {
        this.summa_za_den = summa_za_den;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getPrice_name() {
        return price_name;
    }

    public void setPrice_name(int price_name) {
        this.price_name = price_name;
    }

    @Override
    public String toString() {
        return  "Id = " + id +
                ", Name = " + name + "\n" +
                ", Password = " + password + "\n" +
                ", Position = " + position + "\n" +
                ", d_otchet = " + d_otchet + "\n" +
                ", m_otchet = " + m_otchet + "\n" +
                ", ip = " + ip + "\n" +
                ", port = " + port +
                ", id_tov = " + id_tov +
                ", name_tov = " + name_tov + "\n" +
                ", price = " + price +
                ", skolko = " + skolko +
                ", summa_za_den = " + summa_za_den;
    }
}
