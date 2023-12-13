package lab5.shapes;

import lab5.application.DrawingPanel;
import lab5.application.FigureObject;
import lab5.application.MyShape;
import lab5.application.ShapesListWindow;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LineWithCircles extends MouseAdapter implements MyShape {

    private DrawingPanel drawingPanel;
    private ShapesListWindow shapesListWindow;
    private Point startPoint;
    private final int MAXRADIUSOFCIRCLES = 8;
    private final String NAME_OF_FIGURE = "Line With Circles";
    private List<Shape> tempLineWithCircles = new ArrayList<>();

    public LineWithCircles(DrawingPanel drawingPanel){
        this.drawingPanel = drawingPanel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(startPoint != null){
            tempLineWithCircles.clear();
            tempLineWithCircles.addAll(getCurrentLWithCircles(e.getPoint()));
            drawingPanel.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(startPoint != null) {
            List<Shape> finalLWithEllipses = getCurrentLWithCircles(e.getPoint());
            FigureObject lineWithCirclesObject = new FigureObject(finalLWithEllipses, Arrays.<Boolean>asList(false, false, false),
                    Arrays.asList(drawingPanel.getColorOfFigure(), drawingPanel.getColorOfFigure(), drawingPanel.getColorOfFigure()),
                    startPoint, e.getPoint(), NAME_OF_FIGURE);
            drawingPanel.addFigureObject(lineWithCirclesObject);
        }
        tempLineWithCircles.clear();
        startPoint = null;
        drawingPanel.repaint();
        if(shapesListWindow != null) {
            shapesListWindow.refreshTheTable();
        }
    }

    private List<Shape> getCurrentLWithCircles(Point currentP){
        List<Shape> lineWEllipsesComponents = new ArrayList<>();

        Ellipse2D firstCircle = new Ellipse2D.Double();
        Line2D finalLine = new Line2D.Double();
        Ellipse2D secondCircle = new Ellipse2D.Double();

        double distance = startPoint.distance(currentP);
        double currentRadius = distance / 4;

        double limitedRadius = Math.min(currentRadius, MAXRADIUSOFCIRCLES);

        firstCircle.setFrame(startPoint.x - limitedRadius, startPoint.y - limitedRadius, 2 * limitedRadius, 2 * limitedRadius);

        secondCircle.setFrame(currentP.x - limitedRadius, currentP.y - limitedRadius, 2 * limitedRadius, 2 * limitedRadius);

        Line2D line = new Line2D.Double(startPoint.x, startPoint.y, currentP.x, currentP.y);
        // Дізнаємося координати першої точкидля лінії
        double deltaX = (line.getX2() - line.getX1()) / distance;
        double deltaY = (line.getY2() - line.getY1()) / distance;

        double firstPointX = line.getX1() + limitedRadius * deltaX;
        double firstPointY = line.getY1() + limitedRadius * deltaY;

        // Дізнаємося координати другої точкидля лінії
        double deltaX2 = (line.getX1() - line.getX2()) / distance;
        double deltaY2 = (line.getY1() - line.getY2()) / distance;

        double secondPointX = line.getX2() + limitedRadius * deltaX2;
        double secondPointY = line.getY2() + limitedRadius * deltaY2;

        finalLine.setLine(firstPointX, firstPointY, secondPointX, secondPointY);

        lineWEllipsesComponents.add(firstCircle);
        lineWEllipsesComponents.add(finalLine);
        lineWEllipsesComponents.add(secondCircle);

        return lineWEllipsesComponents;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if(tempLineWithCircles != null) {
            g2.setColor(drawingPanel.getColorOfFigure());
            Stroke dashedStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[]{2}, 0);
            g2.setStroke(dashedStroke);
            for (Shape element : tempLineWithCircles) {
                g2.draw(element);
            }
        }
    }
    public void setShapesListWindow(ShapesListWindow shapesListWindow) {
        this.shapesListWindow = shapesListWindow;
    }
}
