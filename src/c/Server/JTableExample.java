package c.Server;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class JTableExample {

    DefaultTableModel model = new DefaultTableModel(1, 3);
    JTable jTabPeople = new JTable(model);
    JButton button = new JButton("+");

    public JTableExample() {

        JFrame jfrm = new JFrame("JTableExample");
        jfrm.getContentPane().setLayout(new FlowLayout());
        jfrm.setSize(400, 300);
        jfrm.setLocationRelativeTo(null);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setVisible(true);

        //jTabPeople = new JTable(data, headers);  //Создаем новую таблицу на основе двумерного массива данных и заголовков
        JScrollPane jscrlp = new JScrollPane(jTabPeople);  //Создаем панель прокрутки и включаем в ее состав нашу таблицу
        jTabPeople.setPreferredScrollableViewportSize(new Dimension(350, 250));  //Устаналиваем размеры прокручиваемой области
        jfrm.getContentPane().add(jscrlp);  //Добавляем в контейнер нашу панель прокрути и таблицу вместе с ней
        jfrm.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                model.addRow(new Object[]{"a", "b", "c"});
            }
        });

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {  //Создаем фрейм в потоке обработки событий
            @Override
            public void run() {
                new JTableExample();
            }
        });
    }
}