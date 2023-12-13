package lab5.shapes;

import lab5.application.DrawingPanel;
import lab5.application.FigureObject;
import lab5.application.MyShape;
import lab5.application.ShapesListWindow;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PencilShape extends MouseAdapter implements MyShape {
    private DrawingPanel drawingPanel;
    private ShapesListWindow shapesListWindow;
    private Point2D startPoint;
    private List<Shape> tempPencilLine = new ArrayList<>();
    private final String NAME_OF_FIGURE = "Pencil Line";
    private Boolean paintStatus = false;

    public PencilShape(DrawingPanel drawingPanel){
        this.drawingPanel = drawingPanel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.paintStatus = true;
        startPoint = e.getPoint();
        tempPencilLine.add(new Ellipse2D.Double(e.getX() - 2, e.getY() - 2, 4, 4));
        drawingPanel.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(paintStatus){
            tempPencilLine.add(new Ellipse2D.Double(e.getX() - 2, e.getY() - 2, 4, 4));
            drawingPanel.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(paintStatus){
            tempPencilLine.add(new Ellipse2D.Double(e.getX() - 2, e.getY() - 2, 4, 4));
            List<Boolean> fillStyles = new ArrayList<>();
            List<Color> colorsOfShapes = new ArrayList<>();
            for (int i = 0; i < tempPencilLine.size(); i++){
                fillStyles.add(true);
                colorsOfShapes.add(drawingPanel.getColorOfFigure());
            }
            FigureObject pencilLineObject = new FigureObject(tempPencilLine, fillStyles, colorsOfShapes, startPoint, e.getPoint(), NAME_OF_FIGURE);
            drawingPanel.addFigureObject(pencilLineObject);
            tempPencilLine.clear();
            startPoint = null;
            this.paintStatus = false;
            drawingPanel.repaint();
            if(shapesListWindow != null) {
                shapesListWindow.refreshTheTable();
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (tempPencilLine != null) {
            Stroke thickerStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
            g2.setStroke(thickerStroke);
            g2.setColor(drawingPanel.getColorOfFigure());
            for (Shape point : tempPencilLine) {
                g2.fill(point);
            }
        }
    }
    public void setShapesListWindow(ShapesListWindow shapesListWindow) {
        this.shapesListWindow = shapesListWindow;
    }
}