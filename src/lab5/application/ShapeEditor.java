package lab5.application;

import lab5.shapes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class ShapeEditor extends JFrame{
    private static ShapeEditor instance;
    private Image icon = new ImageIcon("resources/images/Lab5Icon.png").getImage();
    private ImageIcon backArrowIcon = new ImageIcon(new ImageIcon("resources/images/BackArrowImage.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
    private ImageIcon pointIcon = new ImageIcon(new ImageIcon("resources/images/PointImage.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
    private ImageIcon lineIcon = new ImageIcon(new ImageIcon("resources/images/LineImage.png").getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH));
    private ImageIcon lineWithCirclesIcon = new ImageIcon(new ImageIcon("resources/images/LineWithCirclesIcon.png").getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH));
    private ImageIcon rectangleIcon = new ImageIcon(new ImageIcon("resources/images/RectangleImage.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private ImageIcon ellipseIcon = new ImageIcon(new ImageIcon("resources/images/EllipseImage.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private ImageIcon pencilIcon = new ImageIcon(new ImageIcon("resources/images/PencilImage.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private ImageIcon cubeIcon = new ImageIcon(new ImageIcon("resources/images/CubeImage.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Dimension dimension = toolkit.getScreenSize();
    private JMenuBar menuBar = new JMenuBar();
    private JToolBar toolbar = new JToolBar();
    private DrawingPanel drawingPanel = DrawingPanel.getInstance();
    private CircleComponent circleComponent = new CircleComponent(drawingPanel);
    private ShapesListWindow shapesListWindow;
    private SaveChooserWindow saveChooserWindow;
    private PointShape pointShape = new PointShape(drawingPanel);
    private LineShape lineShape = new LineShape(drawingPanel);
    private LineWithCircles lineWCirclesShape = new LineWithCircles(drawingPanel);
    private RectangleShape rectangleShape = new RectangleShape(drawingPanel);
    private EllipseShape ellipseShape =  new EllipseShape(drawingPanel);
    private PencilShape pencilShape = new PencilShape(drawingPanel);
    private CubeShape cubeShape = new CubeShape(drawingPanel);

    private ShapeEditor(){
        super("Lab 5");
        createFrame();
        createMenuBar();
        createToolBar();
        this.setVisible(true);
    }
    public static ShapeEditor getInstance() {
        if (instance == null) {
            instance = new ShapeEditor();
        }
        return instance;
    }

    private void createFrame(){
        this.setBounds(dimension.width / 2 - 325, dimension.height / 2 - 275, 650, 550);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(icon);
        this.add(drawingPanel);
    }

    private  void createMenuBar(){
        JMenu fileMenu = new JMenu("Файл");
        JMenu colorsMenu = new JMenu("Кольори");
        JMenu infoMenu = new JMenu("Довідка");


        JMenuItem black = new JMenuItem("Чорний");
        JMenuItem empty = new JMenuItem("Порожній");
        JMenuItem white = new JMenuItem("Білий");
        JMenuItem red = new JMenuItem("Червоний");
        JMenuItem blue = new JMenuItem("Синій");
        JMenuItem green = new JMenuItem("Зелений");
        JMenuItem purple = new JMenuItem("Фіолетовий");
        JMenuItem pink = new JMenuItem("Рожевий");
        JMenuItem yellow = new JMenuItem("Жовтий");
        JMenuItem orange = new JMenuItem("Помаранчевий");

        JMenuItem createShapesListWindow = new JMenuItem("Список фігур");

        JMenuItem createSaveChooserWindow = new JMenuItem("Зберегти");

        black.addActionListener(e -> { drawingPanel.setColorOfFigure(Color.BLACK); });
        empty.addActionListener(e -> { drawingPanel.setColorOfFigure(new Color(0,0,0,0)); });
        white.addActionListener(e -> { drawingPanel.setColorOfFigure(Color.WHITE); });
        red.addActionListener(e -> { drawingPanel.setColorOfFigure(Color.RED); });
        blue.addActionListener(e -> { drawingPanel.setColorOfFigure(Color.BLUE); });
        green.addActionListener(e -> { drawingPanel.setColorOfFigure(new Color(0, 255, 127)); });
        purple.addActionListener(e -> { drawingPanel.setColorOfFigure(new Color(153, 17, 153)); });
        pink.addActionListener(e -> { drawingPanel.setColorOfFigure(new Color(240, 118,139)); });
        yellow.addActionListener(e -> { drawingPanel.setColorOfFigure(new Color(255, 255, 0)); });
        orange.addActionListener(e -> { drawingPanel.setColorOfFigure(new Color(237, 145, 33)); });

        createShapesListWindow.addActionListener(e -> { if(shapesListWindow == null){
            shapesListWindow = ShapesListWindow.getInstance(this, this.drawingPanel);
            drawingPanel.setShapesListWindow(shapesListWindow);
            pointShape.setShapesListWindow(shapesListWindow);
            lineShape.setShapesListWindow(shapesListWindow);
            lineWCirclesShape.setShapesListWindow(shapesListWindow);
            pencilShape.setShapesListWindow(shapesListWindow);
            rectangleShape.setShapesListWindow(shapesListWindow);
            ellipseShape.setShapesListWindow(shapesListWindow);
            cubeShape.setShapesListWindow(shapesListWindow);

        } });

        createSaveChooserWindow.addActionListener(e -> {
            saveChooserWindow = new SaveChooserWindow(this, this.drawingPanel);});

        colorsMenu.add(black);
        colorsMenu.add(empty);
        colorsMenu.add(white);
        colorsMenu.add(red);
        colorsMenu.add(blue);
        colorsMenu.add(green);
        colorsMenu.add(purple);
        colorsMenu.add(pink);
        colorsMenu.add(yellow);
        colorsMenu.add(orange);

        infoMenu.add(createShapesListWindow);

        fileMenu.add(createSaveChooserWindow);

        menuBar.add(fileMenu);
        menuBar.add(colorsMenu);
        menuBar.add(circleComponent);
        menuBar.add(infoMenu);

        this.setJMenuBar(menuBar);
    }

    private void createToolBar(){

        JButton backArrowButton = new JButton(backArrowIcon);
        backArrowButton.setToolTipText("Прибрати останню фігуру");
        JButton pointButton = new JButton(pointIcon);
        pointButton.setToolTipText("Крапка");
        JButton lineButton = new JButton(lineIcon);
        lineButton.setToolTipText("Лінія");
        JButton lineWCirclesButton = new JButton(lineWithCirclesIcon);
        lineWCirclesButton.setToolTipText("Лінія з кругами");
        JButton rectangleButton = new JButton(rectangleIcon);
        rectangleButton.setToolTipText("Прямокутник");
        JButton ellipseButton = new JButton(ellipseIcon);
        ellipseButton.setToolTipText("Еліпс");
        JButton pencilButton = new JButton(pencilIcon);
        pencilButton.setToolTipText("Пензлик");
        JButton cubeButton = new JButton(cubeIcon);
        cubeButton.setToolTipText("Куб");

        JLabel label = new JLabel("Фігуру не обрано");

        backArrowButton.addActionListener(e -> drawingPanel.removeLastShape());
        pointButton.addActionListener(e -> { drawingPanel.setCurrentShape(pointShape, pointShape); label.setText("Крапка"); });
        lineButton.addActionListener(e -> { drawingPanel.setCurrentShape(lineShape, lineShape); label.setText("Лінія"); });
        lineWCirclesButton.addActionListener(e -> { drawingPanel.setCurrentShape(lineWCirclesShape, lineWCirclesShape); label.setText("Лінія з кругами"); });
        rectangleButton.addActionListener(e -> { drawingPanel.setCurrentShape(rectangleShape, rectangleShape); label.setText("Прямокутник"); });
        ellipseButton.addActionListener(e -> { drawingPanel.setCurrentShape(ellipseShape, ellipseShape); label.setText("Еліпс"); });
        pencilButton.addActionListener(e -> { drawingPanel.setCurrentShape(pencilShape, pencilShape); label.setText("Пензлик"); });
        cubeButton.addActionListener(e -> { drawingPanel.setCurrentShape(cubeShape, cubeShape); label.setText("Куб"); });

        backArrowButton.setFocusPainted(false);
        pointButton.setFocusPainted(false);
        lineButton.setFocusPainted(false);
        lineWCirclesButton.setFocusPainted(false);
        rectangleButton.setFocusPainted(false);
        ellipseButton.setFocusPainted(false);
        pencilButton.setFocusPainted(false);
        cubeButton.setFocusPainted(false);

        toolbar.add(backArrowButton);
        toolbar.add(pointButton);
        toolbar.add(lineButton);
        toolbar.add(lineWCirclesButton);
        toolbar.add(rectangleButton);
        toolbar.add(ellipseButton);
        toolbar.add(pencilButton);
        toolbar.add(cubeButton);
        toolbar.addSeparator(new Dimension(15, 0));
        toolbar.add(label);

        toolbar.setFloatable(false);
        this.add(toolbar, BorderLayout.NORTH);
    }

    static class CircleComponent extends JPanel {
        private DrawingPanel drawingPanel;
        public CircleComponent(DrawingPanel drawingPanel){
           this.drawingPanel = drawingPanel;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            double diameter = (Math.min(getWidth(), getHeight())) * (9/10.0);
            Point center = new Point(getWidth() / 2, getHeight() / 2);

            Ellipse2D circle = new Ellipse2D.Double(center.x - diameter / 2,
                    center.y - diameter / 2, diameter, diameter);

            if(!(drawingPanel.getColorOfFigure().equals(new Color(0,0,0,0)))){
                g2d.setColor(drawingPanel.getColorOfFigure());
                g2d.fill(circle);
            } else {
                Stroke thickerStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
                g2d.setStroke(thickerStroke);
                g2d.setColor(Color.black);
                g2d.draw(circle);
            }

        }
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(20, 20);
        }
        @Override
        public Dimension getMaximumSize() {
            return getPreferredSize();
        }
    }
    public void shapesListWindowClosed(){
        this.shapesListWindow = null;
        ShapesListWindow.setInstance(null);
    }

}
