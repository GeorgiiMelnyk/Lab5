package lab4.shapes;

import lab4.application.DrawingPanel;
import lab4.application.MyShape;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class RectangleShape extends MouseAdapter implements MyShape {
    private DrawingPanel drawingPanel;
    private Point startPoint;
    private Rectangle2D tempRectangle;

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
            drawingPanel.addShape(true, new Rectangle2D.Float(
                    Math.min(startPoint.x, e.getX()),
                    Math.min(startPoint.y, e.getY()),
                    Math.abs(startPoint.x - e.getX()),
                    Math.abs(startPoint.y - e.getY())
            ), true, drawingPanel.getColorOfFigure());
            drawingPanel.addShape(false, new Rectangle2D.Float(
                    Math.min(startPoint.x, e.getX()),
                    Math.min(startPoint.y, e.getY()),
                    Math.abs(startPoint.x - e.getX()),
                    Math.abs(startPoint.y - e.getY())
            ), false, Color.BLACK);
            tempRectangle = null;
            startPoint = null;
            drawingPanel.repaint();
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
}
