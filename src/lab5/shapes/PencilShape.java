package lab5.shapes;

import lab5.application.DrawingPanel;
import lab5.application.MyShape;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class PencilShape extends MouseAdapter implements MyShape {
    private DrawingPanel drawingPanel;
    private List<Ellipse2D> tempPencilLine = new ArrayList<>();
    private Boolean paintStatus = false;

    public PencilShape(DrawingPanel drawingPanel){
        this.drawingPanel = drawingPanel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.paintStatus = true;
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
            drawingPanel.addShape(true, (new Ellipse2D.Double(e.getX() - 2, e.getY() - 2, 4, 4)), true, drawingPanel.getColorOfFigure());
            for(int i = 1; i < tempPencilLine.size(); i++){
                drawingPanel.addShape(false, tempPencilLine.get(i), true, drawingPanel.getColorOfFigure());
            }
            tempPencilLine.clear();
            this.paintStatus = false;
            drawingPanel.repaint();
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (tempPencilLine != null) {
            Stroke thickerStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
            g2.setStroke(thickerStroke);
            g2.setColor(drawingPanel.getColorOfFigure());
            for (Ellipse2D point : tempPencilLine) {
                g2.fill(point);
            }
        }
    }
}