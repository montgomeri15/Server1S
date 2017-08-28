package c.Server;


import c.DataBase.DB_Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Server_Configuration {

    DB_Helper db_helper = new DB_Helper();

    JFrame frame_Configuration = new JFrame();

    JPanel panel_osn = new JPanel(new BorderLayout());
    JPanel panelIP = new JPanel(new GridBagLayout());
    JPanel panelPort = new JPanel(new GridBagLayout());
    JPanel panelButtonSave = new JPanel(new GridBagLayout());

    JTextField t_ip1 = new JTextField(3);
    JTextField t_ip2 = new JTextField(3);
    JTextField t_ip3 = new JTextField(3);
    JTextField t_ip4 = new JTextField(3);

    JLabel l_ip = new JLabel("IP");
    JLabel l_port = new JLabel("port");
    JLabel l_error = new JLabel(" ");

    JLabel l_probel1 = new JLabel(" ");
    JLabel l_probel2 = new JLabel(" ");
    JLabel l_probel3 = new JLabel(" ");

    JLabel l_point1 = new JLabel(". ");
    JLabel l_point2 = new JLabel(". ");
    JLabel l_point3 = new JLabel(". ");

    JButton buttonSave = new JButton("Сохранить");
    JButton buttonDatails = new JButton("");

    String[] portMass = {"Выберите порт", "7546", "5421", "3287", "1875", "1245", "6987", "8259", "3576", "1946", "9998"};

    JComboBox comboBox = new JComboBox(portMass);

    int port; //Переменная в которую мы забиваем значения порта
    String ip;// Переменная в которую мы бросаем значения IP

    public void server_configuration(){

        /** Дизайн */
        frame_Configuration.setTitle("Мой 1С: Настройки");
        frame_Configuration.setSize(300, 200);
        frame_Configuration.setVisible(true);
        frame_Configuration.setLocationRelativeTo(null);

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        panelIP.add(l_ip, c);
        c.gridx = 0;
        c.gridy = 2;
        panelIP.add(t_ip1, c);
        c.gridx = 1;
        c.gridy = 2;
        panelIP.add(l_point1, c);
        c.gridx = 2;
        c.gridy = 2;
        panelIP.add(t_ip2, c);
        c.gridx = 3;
        c.gridy = 2;
        panelIP.add(l_point2, c);
        c.gridx = 4;
        c.gridy = 2;
        panelIP.add(t_ip3, c);
        c.gridx = 5;
        c.gridy = 2;
        panelIP.add(l_point3, c);
        c.gridx = 6;
        c.gridy = 2;
        panelIP.add(t_ip4, c);

        c.gridx = 0;
        c.gridy = 0;
        panelPort.add(l_error, c);
        c.gridx = 0;
        c.gridy = 1;
        panelPort.add(l_port, c);
        c.gridx = 0;
        c.gridy = 2;
        panelPort.add(comboBox, c);

        c.gridx = 0;
        c.gridy = 0;
        panelButtonSave.add(buttonSave, c);
        c.gridx = 0;
        c.gridy = 1;
        panelButtonSave.add(l_probel2, c);

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonSaveConfiguration(); //Слушатель кнопки сохранения
            }
        });

        /** Добавляем все елементы на панели и на фрейм */
        panel_osn.add(panelIP, BorderLayout.NORTH);
        panel_osn.add(panelPort, BorderLayout.CENTER);
        panel_osn.add(panelButtonSave, BorderLayout.SOUTH);
        frame_Configuration.add(panel_osn);

    }

    public void buttonSaveConfiguration(){

        /** Получаем IP адресс с текст филдов и делаем его одним Стринговым полем*/
        ip = t_ip1.getText() + "." + t_ip2.getText() + "." + t_ip3.getText() + "." + t_ip4.getText();

        /** Получаем выбранное значения с выпадающего списка портов */
        if (comboBox.getSelectedItem() != "Выберите порт"){
            port = Integer.parseInt(String.valueOf(comboBox.getSelectedItem()));

            try {
                /***  Конектимся к таблице ip и port */
                db_helper.createTableIpPort();

                /** Производим запись полученых данных по порту и IP в БД */
                db_helper.writeTableIpPort(ip, port);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            JOptionPane.showMessageDialog(null, "Данные обновленны!", "Отчет по настройке", JOptionPane.OK_OPTION);

        }else {
            l_error.setText("Выберите правильный порт!");
            l_error.setForeground(Color.RED);
        }
    }
}
