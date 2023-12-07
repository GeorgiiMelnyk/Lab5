package lab4.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends JPanel {

    private MyShape currentShape;
    private List<Boolean> newShapeObjects = new ArrayList<>();
    private List<Shape> shapes = new ArrayList<>();
    private List<Boolean> fillStyles = new ArrayList<>();
    private List<Color> colors = new ArrayList<>();
    private Color colorOfFigure = Color.BLACK;

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


    public void addShape(boolean newShapeObject, Shape shape, boolean fillStyle, Color color){
        newShapeObjects.add(newShapeObject);
        shapes.add(shape);
        fillStyles.add(fillStyle);
        colors.add(color);
    }

    public void setColorOfFigure(Color color){
        this.colorOfFigure = color;
    }

    public Color getColorOfFigure(){
        return this.colorOfFigure;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Stroke thickerStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
        g2.setStroke(thickerStroke);

        for (int i = 0; i < shapes.size(); i++){
            g2.setColor(colors.get(i));
            if(fillStyles.get(i)){
                g2.fill(shapes.get(i));
            } else {
                g2.draw(shapes.get(i));
            }
        }

        if(currentShape != null){
            currentShape.draw(g2);
        }

    }

    public void removeLastShape(){
        int shapesSize = shapes.size();
        for (int i = (shapesSize - 1); i >= 0; i--){
            if(newShapeObjects.get(i)){
                for(int j = i; i < shapesSize; i++) {
                    colors.remove(j);
                    fillStyles.remove(j);
                    newShapeObjects.remove(j);
                    shapes.remove(j);
                }
                repaint();
                break;
            }
        }
    }

}
