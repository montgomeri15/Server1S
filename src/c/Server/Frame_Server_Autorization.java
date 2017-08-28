package c.Server;


import c.DataBase.DB_Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Frame_Server_Autorization {

    DB_Helper db_helper = new DB_Helper();
    Frame_Server frame_server = new Frame_Server();

    JFrame frame = new JFrame("Мой 1С: Авторизация");

    JPanel panel = new JPanel(new GridBagLayout());
    JPanel panel_osn = new JPanel(new BorderLayout());

    JLabel l_name = new JLabel("Логин: ");
    JLabel l_password = new JLabel("Пароль: ");

    JLabel l_probel1 = new JLabel(" ");
    JLabel l_probel2 = new JLabel(" ");
    JLabel l_probel3 = new JLabel(" ");
    JLabel l_probel4 = new JLabel(" ");

    JTextField t_name = new JTextField(15);
    JPasswordField password = new JPasswordField(15);

    JButton button_Enter = new JButton("Вход");

    String getName;
    String getPassword;

    public void frame_Server_Autoriazation(){

        frame.setSize(300, 200);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /** Дизайн */
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0; //Размищение елемента по оси х (по горизонтали)
        c.gridy = 0; //Размищение елемента по оси у (по вертикали)
        c.gridwidth = 1; //Это эелемент занимает 1 блок по ширине
        c.gridheight = 1; //Это элемент занимает 1 блок по высоте
        c.anchor = GridBagConstraints.CENTER; //Прикрепляем елемент к северной части
        c.fill = GridBagConstraints.HORIZONTAL; //Элемент будет распологаться по горизонтали
        panel.add(l_probel1, c); //Разместили на панели наш первый елемент

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
        panel.add(password, c);
        c.gridx = 0;
        c.gridy = 6;
        panel.add(l_probel3, c);
        c.gridx = 0;
        c.gridy = 7;
        panel.add(button_Enter, c);
        c.gridx = 0;
        c.gridy = 8;
        panel.add(l_probel4, c);

        /** Работа с кнопками */

        password.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    buttonEnterAction();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
        button_Enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    buttonEnterAction();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
        panel_osn.add(panel);
        frame.add(panel_osn);
    }

    public void buttonEnterAction() throws SQLException, ClassNotFoundException {

        getName = t_name.getText();
        getPassword = password.getText();

        if (getName.equals("Admin") && getPassword.equals("Admin")){
            frame.dispose();
            frame_server.frame_Server();
        } else {
            password.setText("");
            l_probel1.setForeground(Color.RED);
            l_probel1.setText("Неверный логин или пароль");
        }

        /*if (getName.equals(db_helper.readTableUsers().get(0).getName()) && getPassword.equals(db_helper.readTableUsers().get(0).getPassword())){
            frame.dispose();
            frame_server.frame_Server();
        } else {
            password.setText("");
            l_probel1.setForeground(Color.RED);
            l_probel1.setText("Неверный логин или пароль");
        } */
    }
}