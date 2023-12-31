package lab5.shapes;

import lab5.application.DrawingPanel;
import lab5.application.FigureObject;
import lab5.application.MyShape;
import lab5.application.ShapesListWindow;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class CubeShape extends MouseAdapter implements MyShape {
    private DrawingPanel drawingPanel;
    private ShapesListWindow shapesListWindow;
    private Point startPoint;
    private List<Shape> tempCube = new ArrayList<>();
    private final String NAME_OF_FIGURE = "Cube";
    public CubeShape(DrawingPanel drawingPanel){
        this.drawingPanel = drawingPanel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (startPoint != null){
            tempCube.clear();
            tempCube.addAll(calculateAndGetCube(this.startPoint, e.getPoint()));
            drawingPanel.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(startPoint != null){
            List<Shape> finalCube = calculateAndGetCube(this.startPoint, e.getPoint());
            List<Boolean> fillStyles = new ArrayList<>();
            List<Color> colors = new ArrayList<>();
            for (int i = 0; i < finalCube.size(); i++){
                fillStyles.add(false);
                colors.add(drawingPanel.getColorOfFigure());
            }
            FigureObject cubeObject = new FigureObject(finalCube, fillStyles, colors, startPoint, e.getPoint(), NAME_OF_FIGURE);
            drawingPanel.addFigureObject(cubeObject);
            finalCube.clear();
            tempCube.clear();
            startPoint = null;
            drawingPanel.repaint();
            if(shapesListWindow != null) {
                shapesListWindow.refreshTheTable();
            }
        }
    }

    public List<Shape> calculateAndGetCube(Point startPoint, Point currentP) {
         List<Shape> cubeComponents = new ArrayList<>();

         Rectangle2D frontRectangle = new Rectangle2D.Double();
         Rectangle2D backRectangle = new Rectangle2D.Double();
         Line2D topLeftLine = new Line2D.Double();
         Line2D bottomLeftLine = new Line2D.Double();
         Line2D topRightLine = new Line2D.Double();
         Line2D bottomRightLine = new Line2D.Double();

         double width = (Math.abs(startPoint.x - currentP.getX())) * 0.7;
         double height = (Math.abs(startPoint.y - currentP.getY())) * 0.7;

         if (startPoint.x < currentP.getX() && startPoint.y < currentP.getY()) {
            backRectangle.setRect(startPoint.x, startPoint.y, width, height);
            frontRectangle.setRect((currentP.getX() - width), (currentP.getY() - height), width, height);
            topLeftLine.setLine(startPoint.x, startPoint.y, (currentP.getX() - width), (currentP.getY() - height));
            topRightLine.setLine(startPoint.x + width, startPoint.y, currentP.getX(), (currentP.getY() - height));
            bottomLeftLine.setLine(startPoint.x, startPoint.y + height, (currentP.getX() - width), currentP.getY());
            bottomRightLine.setLine(startPoint.x + width, startPoint.y + height, currentP.getX(), currentP.getY());
        }

         if (startPoint.x > currentP.getX() && startPoint.y < currentP.getY()) {
            backRectangle.setRect(startPoint.x - width, startPoint.y, width, height);
            frontRectangle.setRect(currentP.getX(), (currentP.getY() - height), width, height);
            topLeftLine.setLine(startPoint.x - width, startPoint.y, currentP.getX(), (currentP.getY() - height));
            topRightLine.setLine(startPoint.x, startPoint.y, (currentP.getX() + width), (currentP.getY() - height));
            bottomLeftLine.setLine((startPoint.x - width), (startPoint.y + height), currentP.getX(), currentP.getY());
            bottomRightLine.setLine(startPoint.x, startPoint.y + height, (currentP.getX() + width), currentP.getY());
         }

         if (startPoint.x > currentP.getX() && startPoint.y > currentP.getY()) {
            backRectangle.setRect(startPoint.x - width, startPoint.y - height, width, height);
            frontRectangle.setRect(currentP.getX(), currentP.getY(), width, height);
            topLeftLine.setLine((startPoint.x - width), (startPoint.y - height), currentP.getX(), currentP.getY());
            topRightLine.setLine(startPoint.x, startPoint.y - height, (currentP.getX() + width), currentP.getY());
            bottomLeftLine.setLine((startPoint.x - width), startPoint.y, currentP.getX(), currentP.getY() + height);
            bottomRightLine.setLine(startPoint.x, startPoint.y, (currentP.getX() + width), (currentP.getY() + height));
         }

         if (startPoint.x < currentP.getX() && startPoint.y > currentP.getY()) {
            backRectangle.setRect(startPoint.x, startPoint.y - height, width, height);
            frontRectangle.setRect(currentP.getX() - width, currentP.getY(), width, height);
            topLeftLine.setLine(startPoint.x, (startPoint.y - height), (currentP.getX() - width), currentP.getY());
            topRightLine.setLine((startPoint.x + width), (startPoint.y - height), currentP.getX(), currentP.getY());
            bottomLeftLine.setLine(startPoint.x, startPoint.y, (currentP.getX() - width), (currentP.getY() + height));
            bottomRightLine.setLine(startPoint.x + width, startPoint.y, currentP.getX(), (currentP.getY() + height));
         }

         if (startPoint.x == currentP.getX() || startPoint.y == currentP.getY()) {
            backRectangle.setRect(startPoint.x, startPoint.y, width, height);
            frontRectangle.setRect(currentP.getX(), currentP.getY(), width, height);
            topLeftLine.setLine(startPoint.x, startPoint.y, currentP.getX(), currentP.getY());
            topRightLine.setLine(startPoint.x, startPoint.y, currentP.getX(), currentP.getY());
            bottomLeftLine.setLine(startPoint.x, startPoint.y, currentP.getX(), currentP.getY());
            bottomRightLine.setLine(startPoint.x, startPoint.y, currentP.getX(), currentP.getY());
         }

         cubeComponents.add(backRectangle);
         cubeComponents.add(frontRectangle);
         cubeComponents.add(topLeftLine);
         cubeComponents.add(topRightLine);
         cubeComponents.add(bottomLeftLine);
         cubeComponents.add(bottomRightLine);

         return cubeComponents;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (tempCube != null){
            g2.setColor(drawingPanel.getColorOfFigure());
            Stroke dashedStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[]{5}, 0);
            g2.setStroke(dashedStroke);
            for (Shape cubeElement : tempCube){
                g2.draw(cubeElement);
            }
        }
    }

    public void setShapesListWindow(ShapesListWindow shapesListWindow) {
        this.shapesListWindow = shapesListWindow;
    }

}
