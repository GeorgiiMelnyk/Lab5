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
    private List<FigureObject> mainList = new ArrayList<>();
    private int indexOfIlluminatedFigure = -1;
    private Color colorOfFigure = Color.BLACK;
    private final Color ILLUMINATION_COLOR = new Color(255, 0, 0, 128);
    private final Stroke BASE_STROKE = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
    private final Stroke ILLUMINATION_STROKE = new BasicStroke(4.0f);

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
        mainList.add(figureObject);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(BASE_STROKE);

        for (FigureObject figureObject : mainList) {
            for (int j = 0; j < figureObject.getShapes().size(); j++) {
                drawCurrentShape(g2, figureObject, j);
            }
        }

        if(indexOfIlluminatedFigure > -1) {

            g2.setColor(ILLUMINATION_COLOR);
            g2.setStroke(ILLUMINATION_STROKE);

            for (int j = 0; j < mainList.get(indexOfIlluminatedFigure).getShapes().size(); j++) {
                g2.draw(mainList.get(indexOfIlluminatedFigure).getShapes().get(j));
            }

            g2.setStroke(BASE_STROKE);

            for (int j = 0; j < mainList.get(indexOfIlluminatedFigure).getShapes().size(); j++) {
            drawCurrentShape(g2, mainList.get(indexOfIlluminatedFigure), j);
            }
        }

        if(currentShape != null){
            currentShape.draw(g2);
        }

    }

    private void drawCurrentShape(Graphics2D g2, FigureObject figureObject, int shapeIndex){

        g2.setColor(figureObject.getColors().get(shapeIndex));

        if(figureObject.getFillStyle().get(shapeIndex)){
            g2.fill(figureObject.getShapes().get(shapeIndex));
        } else {
            g2.draw(figureObject.getShapes().get(shapeIndex));
        }
    }

    public void removeLastShape(){
        if (!mainList.isEmpty()){
            mainList.remove(mainList.size() - 1);
            repaint();
            if(shapesListWindow != null) {
                shapesListWindow.refreshTheTable();
            }
        }
    }

    public int getIndexOfIlluminatedFigure() {
        return indexOfIlluminatedFigure;
    }

    public void setIndexOfIlluminatedFigure(int indexOfIlluminatedFigure) {
        this.indexOfIlluminatedFigure = indexOfIlluminatedFigure;
    }
    public void setColorOfFigure(Color color){
        this.colorOfFigure = color;
    }

    public Color getColorOfFigure(){
        return this.colorOfFigure;
    }

    public List<FigureObject> getMainList() {
        return mainList;
    }

    public void setShapesListWindow(ShapesListWindow shapesListWindow) {
        this.shapesListWindow = shapesListWindow;
    }
}
