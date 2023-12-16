package lab5.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends JPanel {

    private static DrawingPanel instance;
    private ShapesListWindow shapesListWindow;
    private MyShape currentShape;
    private List<FigureObject> figureObjectsMainList = new ArrayList<>();
    private Color colorOfFigure = Color.BLACK;

    private DrawingPanel(){}

    public static DrawingPanel getInstance() {
        if (instance == null) {
            instance = new DrawingPanel();
        }
        return instance;
    }

    public void setCurrentShape(MyShape currentShape, MouseAdapter adapter){
        for(MouseListener mouseListener : getMouseListeners()){
            removeMouseListener(mouseListener);
        }
        for(MouseMotionListener motionListener : getMouseMotionListeners()){
            removeMouseMotionListener(motionListener);
        }

        addMouseListener(adapter);
        addMouseMotionListener(adapter);
        this.currentShape = currentShape;
    }

    public void addFigureObject(FigureObject figureObject){
        figureObjectsMainList.add(figureObject);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        Stroke thickerStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
        g2.setStroke(thickerStroke);

        for(int i = 0; i < figureObjectsMainList.size(); i++){

            for (int j = 0; j < figureObjectsMainList.get(i).getShapes().size(); j++){

                g2.setColor(figureObjectsMainList.get(i).getColors().get(j));

                if(figureObjectsMainList.get(i).getFillStyle().get(j)){
                    g2.fill(figureObjectsMainList.get(i).getShapes().get(j));
                } else {
                    g2.draw(figureObjectsMainList.get(i).getShapes().get(j));
                }
            }

        }

        if(currentShape != null){
            currentShape.draw(g2);
        }

    }

    public void removeLastShape(){
        if (!figureObjectsMainList.isEmpty()){
            figureObjectsMainList.remove(figureObjectsMainList.size() - 1);
            repaint();
            if(shapesListWindow != null) {
                shapesListWindow.refreshTheTable();
            }
        }
    }

    public void setColorOfFigure(Color color){
        this.colorOfFigure = color;
    }

    public Color getColorOfFigure(){
        return this.colorOfFigure;
    }

    public List<FigureObject> getFigureObjectsMainList() {
        return figureObjectsMainList;
    }

    public void setShapesListWindow(ShapesListWindow shapesListWindow) {
        this.shapesListWindow = shapesListWindow;
    }
}
