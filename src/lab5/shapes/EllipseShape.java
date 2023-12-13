package lab5.shapes;

import lab5.application.DrawingPanel;
import lab5.application.FigureObject;
import lab5.application.MyShape;
import lab5.application.ShapesListWindow;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;

public class EllipseShape extends MouseAdapter implements MyShape {
    private DrawingPanel drawingPanel;
    private ShapesListWindow shapesListWindow;
    private Point startPoint;
    private Ellipse2D tempEllipse;
    private final String NAME_OF_FIGURE = "Ellipse";

    public EllipseShape(DrawingPanel drawingPanel){
        this.drawingPanel = drawingPanel;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(startPoint != null){
            tempEllipse = new Ellipse2D.Double(
                    Math.min(startPoint.x, e.getX()),
                    Math.min(startPoint.y, e.getY()),
                    Math.abs(startPoint.x - e.getX()),
                    Math.abs(startPoint.y - e.getY()
                    ));
            drawingPanel.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(startPoint != null){
            Ellipse2D ellipse2D = new Ellipse2D.Double(Math.min(startPoint.x, e.getX()),
                    Math.min(startPoint.y, e.getY()),
                    Math.abs(startPoint.x - e.getX()),
                    Math.abs(startPoint.y - e.getY()));

            FigureObject ellipseObject = new FigureObject(Arrays.<Shape>asList(ellipse2D, ellipse2D), Arrays.<Boolean>asList(true, false),
                    Arrays.<Color>asList(drawingPanel.getColorOfFigure(), Color.BLACK), startPoint, e.getPoint(), NAME_OF_FIGURE);

            drawingPanel.addFigureObject(ellipseObject);
            tempEllipse = null;
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
        if (tempEllipse != null){
            Stroke dashedStroke = new BasicStroke(2, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
            g2.setStroke(dashedStroke);
            g2.setColor(drawingPanel.getColorOfFigure());
            g2.fill(tempEllipse);
            g2.setColor(Color.BLACK);
            g2.draw(tempEllipse);
        }
    }
    public void setShapesListWindow(ShapesListWindow shapesListWindow) {
        this.shapesListWindow = shapesListWindow;
    }
}
