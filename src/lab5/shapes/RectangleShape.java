package lab5.shapes;

import lab5.application.DrawingPanel;
import lab5.application.FigureObject;
import lab5.application.MyShape;
import lab5.application.ShapesListWindow;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

public class RectangleShape extends MouseAdapter implements MyShape {
    private DrawingPanel drawingPanel;
    private ShapesListWindow shapesListWindow;
    private Point startPoint;
    private Rectangle2D tempRectangle;
    private final String NAME_OF_FIGURE = "Rectangle";

    public RectangleShape(DrawingPanel panel) {
        this.drawingPanel = panel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (startPoint != null) {
           tempRectangle = new Rectangle2D.Double(Math.min(startPoint.x, e.getX()),
                    Math.min(startPoint.y, e.getY()),
                    Math.abs(startPoint.x - e.getX()),
                    Math.abs(startPoint.y - e.getY()));
           drawingPanel.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (startPoint != null) {
            Rectangle2D rectangle2D = new Rectangle2D.Float(
                    Math.min(startPoint.x, e.getX()),
                    Math.min(startPoint.y, e.getY()),
                    Math.abs(startPoint.x - e.getX()),
                    Math.abs(startPoint.y - e.getY()));
            FigureObject rectangleObject = new FigureObject(Arrays.<Shape>asList(rectangle2D, rectangle2D),
                    Arrays.<Boolean>asList(true, false), Arrays.<Color>asList(drawingPanel.getColorOfFigure(), Color.BLACK),
                    startPoint, e.getPoint(), NAME_OF_FIGURE);
            drawingPanel.addFigureObject(rectangleObject);
            tempRectangle = null;
            startPoint = null;
            drawingPanel.repaint();
            if(shapesListWindow != null) {
                shapesListWindow.refreshTheTable();
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if(tempRectangle != null){
            Stroke dashedStroke = new BasicStroke(2, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
            g2.setStroke(dashedStroke);
            g2.setColor(drawingPanel.getColorOfFigure());
            g2.fill(tempRectangle);
            g2.setColor(Color.BLACK);
            g2.draw(tempRectangle);
        }
    }
    public void setShapesListWindow(ShapesListWindow shapesListWindow) {
        this.shapesListWindow = shapesListWindow;
    }
}
