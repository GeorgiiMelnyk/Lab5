package lab4.shapes;

import lab4.application.DrawingPanel;
import lab4.application.MyShape;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

public class LineShape extends MouseAdapter implements MyShape {
    private DrawingPanel drawingPanel;
    private Point startPoint;
    private Line2D tempLine;

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
            drawingPanel.addShape(true, new Line2D.Float(startPoint, e.getPoint()), false, drawingPanel.getColorOfFigure());
        }
        tempLine = null;
        startPoint = null;
        drawingPanel.repaint();
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
}
