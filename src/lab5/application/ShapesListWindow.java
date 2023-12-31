package lab5.application;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ShapesListWindow extends JDialog {
    private Image icon = new ImageIcon("resources/images/ShapesListWindowIcon.png").getImage();
    private static ShapesListWindow instance;
    private DrawingPanel drawingPanel;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Dimension dimension = toolkit.getScreenSize();
    private DefaultTableModel model;
    private JTable table;
    private JPanel jPanel = new JPanel();
    private JButton deleteButton = new JButton("Видалити");
    private final String[] columnNames = {"Фігура", "X1", "Y1", "X2", "Y2"};
    private  final int NUMBER_OF_COLUMNS = 5;
    private static final int NAME_COLUMN_WIDTH = 80;
    private static final int DEFAULT_COLUMN_WIDTH = 55;

    private ShapesListWindow(ShapeEditor parentFrame, DrawingPanel drawingPanel){
        super(parentFrame, "Фігури", false);
        this.drawingPanel = drawingPanel;
        createDialog();
        createTable();
        this.setVisible(true);
    }

    public static ShapesListWindow getInstance(ShapeEditor parentFrame, DrawingPanel drawingPanel) {
        if (instance == null) {
            instance = new ShapesListWindow(parentFrame, drawingPanel);
        }
        return instance;
    }

    private void createDialog(){
        this.setIconImage(icon);
        this.setBounds(dimension.width / 2 - 200, dimension.height / 2 - 100, 400, 200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e){
                drawingPanel.setIndexOfIlluminatedFigure(-1);
                drawingPanel.repaint();
                ShapeEditor.getInstance().shapesListWindowClosed();
            }
        });

        deleteButton.addActionListener(e -> {deleteSelectRow(); drawingPanel.setIndexOfIlluminatedFigure(-1); drawingPanel.repaint();});
        deleteButton.setFocusPainted(false);
        jPanel.add(deleteButton);

        this.add(jPanel, BorderLayout.NORTH);
    }

    private void createTable(){
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                    drawingPanel.setIndexOfIlluminatedFigure(table.getSelectedRow());
                    if (drawingPanel.getIndexOfIlluminatedFigure() != -1) {
                        drawingPanel.repaint();
                    }
            }   else {
                drawingPanel.setIndexOfIlluminatedFigure(-1);
            }
        });

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(0).setPreferredWidth(NAME_COLUMN_WIDTH);
        for (int i = 1; i < NUMBER_OF_COLUMNS; i++){
            table.getColumnModel().getColumn(i).setPreferredWidth(DEFAULT_COLUMN_WIDTH);
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        refreshTheTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshTheTable(){

        model.setRowCount(0);

        for(int i = 0; i < drawingPanel.getMainList().size(); i++){
            String[] data = {drawingPanel.getMainList().get(i).getNameOfFigure(),
                    Integer.toString((int) drawingPanel.getMainList().get(i).getPoint1().getX()),
                    Integer.toString((int) drawingPanel.getMainList().get(i).getPoint1().getY()),
                    Integer.toString((int) drawingPanel.getMainList().get(i).getPoint2().getX()),
                    Integer.toString((int) drawingPanel.getMainList().get(i).getPoint2().getY())};
            model.addRow(data);
        }
    }

    public static void setInstance(ShapesListWindow instance) {
        ShapesListWindow.instance = instance;
    }

    private void deleteSelectRow(){
        int selectRow = table.getSelectedRow();
        if(selectRow != -1){
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(selectRow);
            drawingPanel.getMainList().remove(selectRow);
            drawingPanel.repaint();
        }
    }

}
