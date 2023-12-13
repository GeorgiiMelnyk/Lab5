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

public class PointShape extends MouseAdapter implements MyShape {
    private DrawingPanel drawingPanel;
    private ShapesListWindow shapesListWindow;
    private final String NAME_OF_FIGURE = "Point";

    public PointShape(DrawingPanel drawingPanel){
        this.drawingPanel = drawingPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        FigureObject pointObject = new FigureObject( Arrays.<Shape>asList(new Ellipse2D.Double(e.getX() - 2, e.getY() - 2, 4, 4)),
                Arrays.asList(true),
                Arrays.asList(drawingPanel.getColorOfFigure()),
                e.getPoint(),
                e.getPoint(),
                NAME_OF_FIGURE
        );
        drawingPanel.addFigureObject(pointObject);
        drawingPanel.repaint();

        if(shapesListWindow != null) {
            shapesListWindow.refreshTheTable();
        }
    }


    @Override
    public void draw(Graphics g) {
    }

    public void setShapesListWindow(ShapesListWindow shapesListWindow) {
        this.shapesListWindow = shapesListWindow;
    }
}
