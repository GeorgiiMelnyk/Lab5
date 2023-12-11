package lab5.application;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class FigureObject {
    private List<Shape> shapes;
    private List<Boolean> fillStyle;
    private List<Color> colors;
    private Point2D point1;
    private Point2D point2;
    private String nameOfFigure;

    public FigureObject(List<Shape> shapes, List<Boolean> fillStyle,
                        List<Color> colors, Point2D point1, Point2D point2, String nameOfFigure){
        this.shapes = new ArrayList<>(shapes);
        this.fillStyle = new ArrayList<>(fillStyle);
        this.colors = new ArrayList<>(colors);
        this.point1 = point1;
        this.point2 = point2;
        this.nameOfFigure = nameOfFigure;
    }

    public String getNameOfFigure() {
        return nameOfFigure;
    }

    public void setNameOfFigure(String nameOfFigure) {
        this.nameOfFigure = nameOfFigure;
    }
}
