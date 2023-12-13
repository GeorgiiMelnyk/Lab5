package lab5.shapes;

import lab5.application.DrawingPanel;
import lab5.application.FigureObject;
import lab5.application.MyShape;
import lab5.application.ShapesListWindow;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.Arrays;

public class LineShape extends MouseAdapter implements MyShape {
    private DrawingPanel drawingPanel;
    private ShapesListWindow shapesListWindow;
    private Point startPoint;
    private Line2D tempLine;
    private final String NAME_OF_FIGURE = "Line";

    public LineShape(DrawingPanel drawingPanel){
        this.drawingPanel = drawingPanel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(startPoint != null){
            this.tempLine = new Line2D.Float(startPoint, e.getPoint());
            drawingPanel.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(startPoint != null){
            FigureObject lineObject = new FigureObject(Arrays.<Shape>asList(new Line2D.Float(startPoint, e.getPoint())),
                    Arrays.<Boolean>asList(false), Arrays.<Color>asList(drawingPanel.getColorOfFigure()),
                    startPoint, e.getPoint(), NAME_OF_FIGURE);
            drawingPanel.addFigureObject(lineObject);
        }
        tempLine = null;
        startPoint = null;
        drawingPanel.repaint();
        if(shapesListWindow != null) {
            shapesListWindow.refreshTheTable();
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (tempLine!= null) {
            g2.setColor(drawingPanel.getColorOfFigure());
            Stroke dashedStroke = new BasicStroke(2, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_BEVEL, 0, new float[]{2}, 0);
            g2.setStroke(dashedStroke);
            g2.draw(tempLine);
        }
    }
    public void setShapesListWindow(ShapesListWindow shapesListWindow) {
        this.shapesListWindow = shapesListWindow;
    }
}
