package lab5.shapes;

import lab5.application.DrawingPanel;
import lab5.application.FigureObject;
import lab5.application.MyShape;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;

public class PointShape extends MouseAdapter implements MyShape {
    private DrawingPanel drawingPanel;

    public PointShape(DrawingPanel drawingPanel){
        this.drawingPanel = drawingPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        FigureObject figureObject = new FigureObject( Arrays.<Shape>asList(new Ellipse2D.Double(e.getX() - 2, e.getY() - 2, 4, 4)),
                Arrays.asList(true),
                Arrays.asList(drawingPanel.getColorOfFigure()),
                e.getPoint(),
                e.getPoint(),
                "Point"
        );

        drawingPanel.addFigureObject(figureObject);
       /* System.out.println(figureObject.getNameOfFigure());
        System.out.println(figureObject.getPoint1().toString());
        System.out.println(figureObject.getPoint2().toString());*/
        //drawingPanel.addShape(true, new Ellipse2D.Double(e.getX() - 2, e.getY() - 2, 4, 4), true, drawingPanel.getColorOfFigure());
        drawingPanel.repaint();
    }


    @Override
    public void draw(Graphics g) {
    }
}
