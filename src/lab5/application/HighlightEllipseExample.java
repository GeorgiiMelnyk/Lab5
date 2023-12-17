package lab5.application;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class HighlightEllipseExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Highlight Ellipse Example");

            HighlightEllipse highlightEllipse = new HighlightEllipse();

            JButton highlightButton = new JButton("Highlight Ellipse");
            highlightButton.addActionListener(e -> highlightEllipse.setHighlighted(!highlightEllipse.isHighlighted()));

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(highlightEllipse, BorderLayout.CENTER);
            contentPanel.add(highlightButton, BorderLayout.SOUTH);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setContentPane(contentPanel);
            frame.setVisible(true);
        });
    }
}

class HighlightEllipse extends JComponent {
    private boolean highlighted = false;

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
        repaint();
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        Ellipse2D ellipse = new Ellipse2D.Double(10, height / 2 - 20, width - 20, 40);

        // Рисуем еллипс
        g2d.setColor(new Color(255, 0, 0));
        g2d.fill(ellipse);

        // Подсвечиваем окружность еллипса, если нужно
        if (highlighted) {
            g2d.setColor(new Color(255, 0, 0, 128)); // Полупрозрачный красный цвет
            float thickness = 2.0f;
            Stroke oldStroke = g2d.getStroke();
            g2d.setStroke(new BasicStroke(thickness));
            g2d.draw(ellipse);
            g2d.setStroke(oldStroke);
        }
    }
}
