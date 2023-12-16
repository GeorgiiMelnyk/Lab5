package lab5.application;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class WindowForOpenFile extends JFileChooser {
    private DrawingPanel drawingPanel;
    private ShapeEditor shapeEditor;
    private ShapesListWindow shapesListWindow;
    private int result;
    private String fileContent;
    private String projectFolderPath = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "saved_files";

    public WindowForOpenFile(ShapeEditor parentFrame, DrawingPanel drawingPanel, ShapesListWindow shapesListWindow){
        super();
        this.shapeEditor = parentFrame;
        this.drawingPanel = drawingPanel;
        createDialog();
    }

    private void createDialog(){
        this.setDialogTitle("Оберіть файл який хочете выдкрити");
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
                    // Теперь у вас есть содержимое файла в виде строки (fileContent)
                } catch (IOException e) {
                    e.printStackTrace(); // Обработка ошибок чтения файла
                }
            } else {
                // В случае, если выбран не файл, а каталог или что-то другое
                JOptionPane.showMessageDialog(shapeEditor, "Ім'я файлу вказано некоректно, або обран неправильний файл\nБудь ласка, вкажіть файл коректно", "Помилка", JOptionPane.ERROR_MESSAGE);
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
            switch (currentLine[0]){
                case "Point":
                    drawingPanel.addFigureObject(extractPointObjectFromLine(currentLine));
                    break;
                case "Line":
                    break;
                case "Line With Circles":
                    break;
                case "Rectangle":
                    break;
                case "Ellipse":
                    break;
                case "Pencil Line":
                    break;
                case "Cube":
                    break;
                default: break;
            }
        }
        drawingPanel.repaint();
    }

    private FigureObject extractPointObjectFromLine(String[] pointFigureDatas){

        String nameOfFigure = pointFigureDatas[0];

        String[] rgb = pointFigureDatas[1].split(",");
        int red = Integer.parseInt(rgb[0]);
        int green = Integer.parseInt(rgb[1]);
        int blue = Integer.parseInt(rgb[2]);
        Color pointColor = new Color(red, green, blue);

        Point2D startPoint = new Point2D.Double(Double.parseDouble(pointFigureDatas[2]), Double.parseDouble(pointFigureDatas[3]));
        Point2D endPoint = new Point2D.Double(Double.parseDouble(pointFigureDatas[4]), Double.parseDouble(pointFigureDatas[5]));

        FigureObject pointObject = new FigureObject(
                Arrays.<Shape>asList(new Ellipse2D.Double(startPoint.getX()  - 2, startPoint.getY() - 2, 4, 4)),
                Arrays.asList(true),
                Arrays.asList(pointColor),
                startPoint,
                endPoint,
                nameOfFigure
        );
        return pointObject;
    }

}


