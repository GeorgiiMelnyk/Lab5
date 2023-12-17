package lab5.application;

import lab5.shapes.CubeShape;
import lab5.shapes.EllipseShape;
import lab5.shapes.LineWithCircles;
import lab5.shapes.RectangleShape;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class WindowForOpenFile extends JFileChooser {
    private DrawingPanel drawingPanel;
    private ShapeEditor shapeEditor;
    private ShapesListWindow shapesListWindow;
    private RectangleShape rectangleShape;
    private EllipseShape ellipseShape;
    private LineWithCircles lineWithCircles;
    private CubeShape cubeShape;
    private int result;
    private String fileContent;
    private final String WINDOW_TITLE = "Оберіть файл який хочете выдкрити";
    private String projectFolderPath = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "saved_files";

    public WindowForOpenFile(ShapeEditor parentFrame, DrawingPanel drawingPanel,
                             ShapesListWindow shapesListWindow, RectangleShape rectangleShape,
                             EllipseShape ellipseShape, LineWithCircles lineWCirclesShape, CubeShape cubeShape){
        super();
        this.shapeEditor = parentFrame;
        this.drawingPanel = drawingPanel;
        this.shapesListWindow = shapesListWindow;
        this.rectangleShape = rectangleShape;
        this.ellipseShape = ellipseShape;
        this.lineWithCircles = lineWCirclesShape;
        this.cubeShape = cubeShape;
        createDialog();
    }

    private void createDialog(){
        this.setDialogTitle(WINDOW_TITLE);
        this.setCurrentDirectory(new File(projectFolderPath));
        this.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter textFilter = new FileNameExtensionFilter("Текстові файли (*.txt)", "txt");
        this.setFileFilter(textFilter);
        result = showDialog(shapeEditor, "Обрати");

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = getSelectedFile();
            if (selectedFile.isFile()) {
                // Считываем содержимое файла
                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    StringBuilder contentBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        contentBuilder.append(line).append("\n");
                    }
                    fileContent = contentBuilder.toString();
                    paintFileContent(fileContent);
                    if(shapesListWindow != null){
                        shapesListWindow.refreshTheTable();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(shapeEditor, "Ім'я файлу вказано некоректно," +
                        " або обран неправильний файл\nБудь ласка, вкажіть файл коректно", "Помилка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void paintFileContent(String fileContent){

        String[] allLines = fileContent.split(";");
        for(int i = 0; i < allLines.length; i++){
            allLines[i] = allLines[i].trim();
        }

        drawingPanel.getFigureObjectsMainList().clear();
        for(int i = 0; i < allLines.length; i++){

            String[] currentLine = allLines[i].split("\\|");

            if (currentLine.length >= 6) {
                String nameOfFigure = currentLine[0];

                String[] rgb = currentLine[1].split(",");
                int red = Integer.parseInt(rgb[0]);
                int green = Integer.parseInt(rgb[1]);
                int blue = Integer.parseInt(rgb[2]);
                Color figureColor = new Color(red, green, blue);

                Point startPoint = new Point(Integer.parseInt(currentLine[2].split("\\.")[0]),
                        Integer.parseInt(currentLine[3].split("\\.")[0]));
                Point endPoint = new Point(Integer.parseInt(currentLine[4].split("\\.")[0]),
                        Integer.parseInt(currentLine[5].split("\\.")[0]));

                Point2D startPoint2D = new Point2D.Double(Double.parseDouble(currentLine[2]),
                        Double.parseDouble(currentLine[3]));
                Point2D endPoint2D = new Point2D.Double(Double.parseDouble(currentLine[4]),
                        Double.parseDouble(currentLine[5]));

                switch (nameOfFigure) {
                    case "Point":
                        drawingPanel.addFigureObject(getPointObject(nameOfFigure, figureColor, startPoint2D, endPoint2D));
                        break;
                    case "Line":
                        drawingPanel.addFigureObject(getLineObject(nameOfFigure,figureColor, startPoint2D, endPoint2D));
                        break;
                    case "Line With Circles":
                        drawingPanel.addFigureObject(getLineWithCirclesObject(nameOfFigure, figureColor,
                                startPoint, endPoint, startPoint2D, endPoint2D));
                        break;
                    case "Rectangle":
                        drawingPanel.addFigureObject(getRectangleObject(nameOfFigure, figureColor,
                                startPoint, endPoint, startPoint2D, endPoint2D));
                        break;
                    case "Ellipse":
                        drawingPanel.addFigureObject(getEllipseObject(nameOfFigure, figureColor,
                                startPoint, endPoint, startPoint2D, endPoint2D));
                        break;
                    case "Pencil Line":
                        String[] allShapesСoordinatesInSecondCell = allLines[i].split("\\|\\|");
                        drawingPanel.addFigureObject(extractAndGetPencilLineObjectFromString(nameOfFigure, figureColor,
                                startPoint, endPoint, allShapesСoordinatesInSecondCell[1]));
                        break;
                    case "Cube":
                        drawingPanel.addFigureObject(getCubeObject(nameOfFigure, figureColor,
                                startPoint, endPoint, startPoint2D, endPoint2D));
                        break;
                    default:
                        break;
                }
            }
        }
        drawingPanel.repaint();
    }

    private FigureObject getPointObject(String nameOfFigure, Color pointColor, Point2D startPoint2D, Point2D endPoint2D){

        FigureObject pointObject = new FigureObject(
                Arrays.<Shape>asList(new Ellipse2D.Double(startPoint2D.getX()  - 2, startPoint2D.getY() - 2, 4, 4)),
                Arrays.asList(true),
                Arrays.asList(pointColor),
                startPoint2D,
                endPoint2D,
                nameOfFigure
        );

        return pointObject;
    }

    private FigureObject getLineObject(String nameOfFigure, Color lineColor, Point2D startPoint2D, Point2D endPoint2D){

        FigureObject lineObject = new FigureObject(Arrays.<Shape>asList(new Line2D.Float(startPoint2D, endPoint2D)),
                Arrays.<Boolean>asList(false), Arrays.<Color>asList(lineColor),
                startPoint2D, endPoint2D, nameOfFigure);

        return lineObject;
    }

    private FigureObject getLineWithCirclesObject(String nameOfFigure, Color lineWithCirclesColor, Point startPoint,
                                                  Point endPoint, Point2D startPoint2D, Point2D endPoint2D){


        FigureObject lineWithCirclesObject = new FigureObject(lineWithCircles.getCurrentLWithCircles(startPoint, endPoint),
                Arrays.<Boolean>asList(false, false, false),
                Arrays.asList(lineWithCirclesColor, lineWithCirclesColor, lineWithCirclesColor),
                startPoint2D, endPoint2D, nameOfFigure);

        return lineWithCirclesObject;
    }

    private FigureObject extractAndGetPencilLineObjectFromString(String nameOfFigure, Color pencilLineColor, Point2D startPoint2D,
                                                                 Point2D endPoint2D, String allShapesСoordinates){

        String[] allPoints = allShapesСoordinates.split("\\|");

        List<Shape> pencilLineShapes = new ArrayList<>();
        for (int i = 0; i < allPoints.length; i++){
            String[] xAndY = allPoints[i].split(" ");
            pencilLineShapes.add(new Ellipse2D.Double(Double.parseDouble(xAndY[0]) - 2, Double.parseDouble(xAndY[1]) - 2, 4, 4));
        }

        List<Boolean> fillStyles = new ArrayList<>();
        List<Color> colorsOfShapes = new ArrayList<>();
        for (int i = 0; i < pencilLineShapes.size(); i++){
            fillStyles.add(true);
            colorsOfShapes.add(pencilLineColor);
        }
        FigureObject pencilLineObject = new FigureObject(pencilLineShapes, fillStyles, colorsOfShapes, startPoint2D, endPoint2D, nameOfFigure);

        return pencilLineObject;
    }

    private FigureObject getRectangleObject(String nameOfFigure, Color rectangleColor, Point startPoint,
                                            Point endPoint, Point2D startPoint2D, Point2D endPoint2D){

        Rectangle2D baseRectangle = rectangleShape.calculateAndGetRectangle(startPoint, endPoint);
        Rectangle2D contourRectangle = rectangleShape.calculateAndGetRectangle(startPoint, endPoint);

        FigureObject rectangleObject = new FigureObject(Arrays.<Shape>asList(baseRectangle, contourRectangle),
                Arrays.<Boolean>asList(true, false), Arrays.<Color>asList(rectangleColor, Color.BLACK),
                startPoint2D, endPoint2D, nameOfFigure);

        return rectangleObject;
    }

    private FigureObject getEllipseObject(String nameOfFigure, Color ellipseColor, Point startPoint,
                                          Point endPoint, Point2D startPoint2D, Point2D endPoint2D){

        Ellipse2D baseEllipse = ellipseShape.calculateAndGetEllipse(startPoint, endPoint);
        Ellipse2D contourEllipse = ellipseShape.calculateAndGetEllipse(startPoint, endPoint);

        FigureObject ellipseObject = new FigureObject(Arrays.<Shape>asList(baseEllipse, contourEllipse), Arrays.<Boolean>asList(true, false),
                Arrays.<Color>asList(ellipseColor, Color.BLACK), startPoint2D, endPoint2D, nameOfFigure);

        return ellipseObject;
    }

    private FigureObject getCubeObject(String nameOfFigure, Color cubeColor, Point startPoint,
                                       Point endPoint, Point2D startPoint2D, Point2D endPoint2D){
        List<Shape> finalCube = cubeShape.calculateAndGetCube(startPoint, endPoint);
        List<Boolean> fillStyles = new ArrayList<>();
        List<Color> colors = new ArrayList<>();
        for (int i = 0; i < finalCube.size(); i++){
            fillStyles.add(false);
            colors.add(cubeColor);
        }
        FigureObject cubeObject = new FigureObject(finalCube, fillStyles, colors, startPoint2D, endPoint2D, nameOfFigure);

        return cubeObject;
    }

}


