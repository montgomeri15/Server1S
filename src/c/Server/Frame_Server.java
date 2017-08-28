package c.Server;

import c.DataBase.DB_Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class Frame_Server  implements   Runnable{

    DB_Helper db_helper = new DB_Helper();

    Server_Listener server_listener = new Server_Listener();
    Thread thread_server_listener = new Thread((Runnable) server_listener,"t2");

    JFrame frame = new JFrame("Сервер");
    JPanel panel = new JPanel(new GridBagLayout());
    JPanel panel_table = new JPanel(new BorderLayout());
    JPanel panel_osn = new JPanel(new GridBagLayout());

    JButton save_Button = new JButton(new ImageIcon("images/save.png"));
    JButton plus_Button = new JButton(new ImageIcon("images/plus.png"));
    JButton minus_Button = new JButton(new ImageIcon("images/mines.png"));
    JButton refresh_Button = new JButton(new ImageIcon("images/refresh.png"));
    JButton settings_Button = new JButton(new ImageIcon("images/settings.png"));
    JButton startServer_Button = new JButton(new ImageIcon("images/start.png"));
    JButton stopServer_Button = new JButton(new ImageIcon("images/stop.png"));

    JLabel l_probel0 = new JLabel("    ");
    JLabel l_probel1 = new JLabel(" ");
    JLabel l_probel2 = new JLabel("    ");
    JLabel l_probel3 = new JLabel("    ");
    JLabel l_probel4 = new JLabel("    ");
    JLabel l_probel5 = new JLabel("    ");
    JLabel l_probel6 = new JLabel("    ");
    JLabel l_probel7 = new JLabel(" ");

    DefaultTableModel model = new DefaultTableModel(0, 4);  //Позволит нам изменять таблицу
    JTable table = new JTable(model);  //Приписываем сюда модель
    JScrollPane tableContainer = new JScrollPane(table);  //Скролл

    private static String nameFromJTable; // Имя Нового пользователя с таблицы
    private static String passwordFromJTable; //Пароль нового пользователя с талицы
    private static String positionFromJCheckBox;

    public static String getNameFromJTable() {
        return nameFromJTable;
    }

    public static void setNameFromJTable(String nameFromJTable) {
        Frame_Server.nameFromJTable = nameFromJTable;
    }

    public static String getPasswordFromJTable() {
        return passwordFromJTable;
    }

    public static void setPasswordFromJTable(String passwordFromJTable) {
        Frame_Server.passwordFromJTable = passwordFromJTable;
    }

    public static String getPositionFromJCheckBox() {
        return positionFromJCheckBox;
    }

    public static void setPositionFromJCheckBox(String positionFromJCheckBox) {
        Frame_Server.positionFromJCheckBox = positionFromJCheckBox;
    }

    /*Делаем Гетеры и Сетеры что бы к этим переменным был доступ в базе данных*/

    public void frame_Server(){

        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getContentPane().setLayout(new FlowLayout());  //Для таблицы

        table.setPreferredScrollableViewportSize(new Dimension(485, 350));

        /** Дизайн */
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(l_probel1, c);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(save_Button, c);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(l_probel0, c);
        c.gridx = 2;
        c.gridy = 1;
        panel.add(plus_Button, c);
        c.gridx = 3;
        c.gridy = 1;
        panel.add(l_probel2, c);
        c.gridx = 4;
        c.gridy = 1;
        panel.add(minus_Button, c);
        c.gridx = 5;
        c.gridy = 1;
        panel.add(l_probel3, c);
        c.gridx = 6;
        c.gridy = 1;
        panel.add(refresh_Button, c);
        c.gridx = 7;
        c.gridy = 1;
        panel.add(l_probel4, c);
        c.gridx = 8;
        c.gridy = 1;
        panel.add(settings_Button, c);
        c.gridx = 9;
        c.gridy = 1;
        panel.add(l_probel5, c);
        c.gridx = 10;
        c.gridy = 1;
        panel.add(startServer_Button, c);
        c.gridx = 11;
        c.gridy = 1;
        panel.add(l_probel6, c);
        c.gridx = 12;
        c.gridy = 1;
        panel.add(stopServer_Button, c);
        c.gridx = 0;
        c.gridy = 2;
        panel.add(l_probel7, c);

        panel_table.add(tableContainer, BorderLayout.CENTER);  //Добавляем таблицу на панель таблицы

        c.gridx = 0;
        c.gridy = 0;
        panel_osn.add(panel, c);
        c.gridx = 0;
        c.gridy = 1;
        panel_osn.add(panel_table, c);

        frame.add(panel_osn);

        save_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveButton();
            }
        });

        plus_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cleanTable();
                Plus();  //Вызываем метод кнопки "+"
            }
        });

        minus_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int idFromCount = table.getSelectedRow();
                int tableValue = Integer.parseInt(String.valueOf(table.getValueAt(idFromCount, 0)));
                System.out.println(table.getSelectedRow() + " Это первое");
                System.out.println(table.getValueAt(idFromCount, 1));

                try {
                    db_helper.removeTableUsers(tableValue);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                model.removeRow(table.getSelectedRow());  //Удаляет выбранную строку
            }
        });

        refresh_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    cleanTable(); //Метод котроый очищает данные по таблице
                    actionButtonPlus_Refresh(); //Показывает всех узеров

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        settings_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Server_Configuration server_configuration = new Server_Configuration();
                server_configuration.server_configuration();
            }
        });

        startServer_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                thread_server_listener.start();
            //    server_listener.start();
            //    System.out.print("sadasbdjsagdhdl fmnszdbhlf  "+server_listener.isAlive());
             //   System.out.println(server_listener.getName());
            }
        });

        stopServer_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Server_Listener server_listener = new Server_Listener();

                server_listener.Stop();
                thread_server_listener.getName();

                System.out.println("ok");
                System.out.println(thread_server_listener.getName());
                thread_server_listener.stop();
             //   server_listener.isDaemon();
             //   server_listener.isInterrupted();
                //server_listener.
            //    System.out.println(server_listener.isAlive());
                System.out.println(thread_server_listener.isDaemon());
                System.out.println(thread_server_listener.getState());
            }
        });
    }

    public void Plus() {  //Задаем функционал кнопке "+"

        JFrame frame_wwod = new JFrame("Новый пользователь");  //Мини-фрейм для ввода пользователя

        JPanel panel_Check_Box = new JPanel(new GridBagLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel panel_osn = new JPanel(new BorderLayout());

        JLabel l_name = new JLabel("Логин:");
        JLabel l_password = new JLabel("Пароль:");

        JLabel l_probelF = new JLabel(" ");
        JLabel l_probel2 = new JLabel(" ");
        JLabel l_probel3 = new JLabel(" ");
        JLabel l_probel4 = new JLabel(" ");

        JLabel l_probel_ch0 = new JLabel(" ");
        JLabel l_probel_ch1 = new JLabel("Выберите функционал для клиента: ");
        JLabel l_probel_ch2 = new JLabel(" ");

        JTextField t_name = new JTextField(15);
        JTextField t_password = new JTextField(15);

        JCheckBox ch_director = new JCheckBox("Директор");
        JCheckBox ch_d_kassa = new JCheckBox("Касир");
        JCheckBox ch_buhgalter = new JCheckBox("Бухгалтер");

        JButton button_add = new JButton("Добавить");

        List<JCheckBox> list_CH = new ArrayList<>();

        frame_wwod.setSize(300, 330);
        frame_wwod.setLocationRelativeTo(null);
        frame_wwod.setVisible(true);

        /***Дизайн***/
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel_Check_Box.add(l_probel_ch0, c);

        c.gridx = 0;
        c.gridy = 1;
        panel_Check_Box.add(l_probel_ch1, c);

        c.gridx = 0;
        c.gridy = 2;
        panel_Check_Box.add(l_probel_ch2, c);

        c.gridx = 0;
        c.gridy = 3;
        panel_Check_Box.add(ch_director, c);

        c.gridx = 0;
        c.gridy = 4;
        panel_Check_Box.add(ch_d_kassa, c);

        c.gridx = 0;
        c.gridy = 5;
        panel_Check_Box.add(ch_buhgalter, c);

        list_CH.add(ch_director);
        list_CH.add(ch_d_kassa);
        list_CH.add(ch_buhgalter);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1; //элемент занимает 1 блок по ширине
        c.gridheight = 1; //и 1 блок по высоте
        c.anchor = GridBagConstraints.NORTH; //приклеиваем его к северной части
        c.fill = GridBagConstraints.HORIZONTAL; //элемент будет располагаться по горизонтали
        panel.add(l_probelF, c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(l_name, c);
        c.gridx = 0;
        c.gridy = 2;
        panel.add(t_name, c);
        c.gridx = 0;
        c.gridy = 3;
        panel.add(l_probel2, c);
        c.gridx = 0;
        c.gridy = 4;
        panel.add(l_password, c);
        c.gridx = 0;
        c.gridy = 5;
        panel.add(t_password, c);
        c.gridx = 0;
        c.gridy = 6;
        panel.add(l_probel3, c);
        c.gridx = 0;
        c.gridy = 7;
        panel.add(button_add, c);
        c.gridx = 0;
        c.gridy = 8;
        panel.add(l_probel4, c);

        panel_osn.add(panel_Check_Box, BorderLayout.NORTH);
        panel_osn.add(panel, BorderLayout.CENTER);
        frame_wwod.add(panel_osn);

        button_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String getPosition = null;
                String getName = t_name.getText().toString();
                String getPassword = t_password.getText().toString();

                /**  Присваивам переменным значения полученые с текст филдов и Чек боксов */
                setNameFromJTable(t_name.getText());
                setPasswordFromJTable(t_password.getText());

                /** Если какой то чек бокс выбран, то оно выгрибает из него значение и присвает переменно, а в классе
                 * БД мы получаем значение через гетеры и сетеры и делаем запись в БД */
                for (JCheckBox el : list_CH){
                    if (el.isSelected()){
                        setPositionFromJCheckBox(el.getText());
                        getPosition = el.getText();
                        System.out.println(el.getText() + " Это Чек Бокc");
                    }
                }

//                if (ch_chek.isSelected()){
//                    setChekFromCheckBox(ch_chek.getText().toString());
//                }
//                if (ch_d_Otchet.isSelected()){
//                    setD_OtchetFromCheckBox(ch_d_Otchet.getText().toString());
//                }
//                if (ch_m_Otchet.isSelected()){
//                    setM_OtchetFromCheckBox(ch_m_Otchet.getText().toString());
//                }

                  try {

                      List<Users_Server> list = db_helper.readTableUsers();
                      System.out.println(list.size());
                      boolean new_login = false;

                                for (int xw=0;xw<list.size();xw++){
                                    String x = list.get(xw).getName();

                                    if (x.equals(getName)){

                                        new_login =true;

                                        l_probelF.setText("Такое имя уже есть");
                                        l_probelF.setForeground(Color.RED);
                                        break;

                                    }if(x!=getName){
                                        new_login = false;
                                    }
                                }

                               if(new_login==false){

                                   List<Users_Server> list1 = new ArrayList<>(); //Колекция для того, что бы отправить ее в клсс БД, метод writeTableUsers

                                   Users_Server us = new Users_Server();
                                    us.setName(getName);
                                    us.setPassword(getPassword);
                                    us.setPosition(getPosition);

                                    list1.add(us);

                                    db_helper.writeTableUsers(list1); //Записываем данные полученные с фрейма
                                    actionButtonPlus_Refresh(); //Переходим в метод чтобы отобразить даные обо всех пользователях
                                    frame_wwod.dispose();

                                }

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

            }
        });
        cleanTable();//Метод котроый очищает данные по таблице
    }

    public void actionButtonPlus_Refresh() throws SQLException {

        /**  Создаем коллекцию, которая принимает в себя значение после чтения с базы данных
         * Она создается потому что если без нее то процес добавления, а потом вытаскивания с БД зацыкливается! */
        List<Users_Server> list = new ArrayList<>();

        list = db_helper.readTableUsers();

        for (int i = 0; i < list.size(); i++){
            model.addRow(new Object[]{list.get(i).getId(), list.get(i).getName(), list.get(i).getPassword(), list.get(i).getPosition()});  // Добавляем новую строку
        }
        list.clear();

    }

    public void cleanTable(){ //Метод котроый очищает данные по таблице

        /** Чистим масив который для JTable, потому что он хранит в себе совершенно все значения и
         после второго добавления дублирует их */

       while (model.getRowCount() > 0){
           model.removeRow(0);
       }
    }

    /** Я решил немного почудить и сделал кнопку сохранения внесеных данных */
    public void saveButton(){

        List<Users_Server> list = new ArrayList<>();
        try {
            list = db_helper.readTableUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < table.getRowCount(); i++ ) {
            /* Вытаскивам данные из таблицы из каждой ячейки */
            int idValue = Integer.parseInt(String.valueOf(table.getValueAt(i, 0)));
            String nameValue = String.valueOf(table.getValueAt(i, 1));
            String passwordValue = String.valueOf(table.getValueAt(i, 2));
            String positionValue = String.valueOf(table.getValueAt(i, 3));

            System.out.println(table.getRowCount());

            /* Проверяем, совпадают с теми что уже есть в БД, если нет, то делаем перезапись строки данных по ID */
            for (int z = 0; z < list.size(); z++){
                if (list.get(z).getId() == idValue && list.get(z).getName().equals(nameValue) && list.get(z).getPassword().equals(passwordValue) && list.get(z).getPosition().equals(positionValue)){
                    System.out.println("Nothing was changed");
                }else {
                    try {

                        db_helper.reWriteTableUsers(idValue, nameValue, passwordValue, positionValue); //Производим изменения в БД

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        try {

            cleanTable(); //Очищаем таблицу
            actionButtonPlus_Refresh(); //И сразу же запускаем обновление таблицы с выводом на экран

        } catch (SQLException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "База данных обновленна!", "Users Update", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void run() {

    }
}