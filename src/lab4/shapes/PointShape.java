package lab4.shapes;

import lab4.application.DrawingPanel;
import lab4.application.MyShape;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class PointShape extends MouseAdapter implements MyShape {
    private DrawingPanel drawingPanel;

    public PointShape(DrawingPanel drawingPanel){
        this.drawingPanel = drawingPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        drawingPanel.addShape(true, new Ellipse2D.Double(e.getX() - 2, e.getY() - 2, 4, 4), true, drawingPanel.getColorOfFigure());
        drawingPanel.repaint();
    }


    @Override
    public void draw(Graphics g) {
    }
}
