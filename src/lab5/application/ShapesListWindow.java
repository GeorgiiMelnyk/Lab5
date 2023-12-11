package lab5.application;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ShapesListWindow extends JDialog {
    private Image icon = new ImageIcon("resources/images/ShapesListWindowIcon.png").getImage();
    private static ShapesListWindow instance;
    private ShapeEditor parentFrame;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Dimension dimension = toolkit.getScreenSize();
    private JTable table;
    private final String[] columnNames = {"Фігура", "X1", "Y1", "X2", "Y2"};

    private ShapesListWindow(ShapeEditor parentFrame){
        super(parentFrame, "Фігури", false);
        this.parentFrame = parentFrame;
        this.setIconImage(icon);
        this.setBounds(dimension.width / 2 - 175, dimension.height / 2 - 100, 350, 200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Запретить редактирование всех ячеек
                return false;
            }
        };
        table = new JTable(model);

        table.getTableHeader().setReorderingAllowed(false);

        // Добавление таблицы в скролл-панель
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Добавление тестовых данных (можете изменить под свои нужды)
        Object[] data1 = {"Значение 1", "Значение 2", "Значение 3", "Значение 4", "Значение 5"};
        Object[] data2 = {"Значение A", "Значение B", "Значение C", "Значение D", "Значение E"};
        model.addRow(data1);
        model.addRow(data2);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e){
               ShapeEditor.getInstance().shapesListWindowClosed();
            }
        });
        this.setVisible(true);
    }

    public static ShapesListWindow getInstance(ShapeEditor parentFrame) {
        if (instance == null) {
            instance = new ShapesListWindow(parentFrame);
        }
        return instance;
    }

    public static void setInstance(ShapesListWindow instance) {
        ShapesListWindow.instance = instance;
    }
}
