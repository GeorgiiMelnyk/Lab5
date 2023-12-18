package lab5.application;

import lab5.shapes.PencilShape;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SaveChooserWindow extends JFileChooser {
    private DrawingPanel drawingPanel;
    private ShapeEditor shapeEditor;
    private int result;
    private String projectFolderPath = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "saved_files";
    private final String exceptionCase = PencilShape.getNAME_OF_FIGURE();

    public SaveChooserWindow(ShapeEditor parentFrame, DrawingPanel drawingPanel) {
        super();
        this.shapeEditor = parentFrame;
        this.drawingPanel = drawingPanel;
        createDialog();
    }

    private void createDialog(){
        this.setDialogTitle("Оберіть папку для збереження файлу");
        this.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.setCurrentDirectory(new File(projectFolderPath));

        result = this.showDialog(shapeEditor, "Обрати");

        if (result == JFileChooser.APPROVE_OPTION) {
            String selectedFolderPath = this.getSelectedFile().getAbsolutePath();

            createAndSaveUniqueTextFile(selectedFolderPath);
        }
    }



    private void createAndSaveUniqueTextFile(String folderPath) {
        try {
            File folder = new File(folderPath);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM.dd_HH-mm");
            String formattedDate = dateFormat.format(new Date());

            String baseFileName = formattedDate + ".txt";
            File file = new File(folder, baseFileName);

            int counter = 1;
            while (file.exists()) {
                String fileName = getUniqueFileName(baseFileName, counter);
                file = new File(folder, fileName);
                counter++;
            }

            String content = getFileContent();

            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getUniqueFileName(String baseFileName, int counter) {
        int dotIndex = baseFileName.lastIndexOf('.');
        String nameWithoutExtension;
        String extension;

        if (dotIndex != -1) {
            nameWithoutExtension = baseFileName.substring(0, dotIndex);
            extension = baseFileName.substring(dotIndex);
        } else {
            nameWithoutExtension = baseFileName;
            extension = "";
        }

        return nameWithoutExtension + " (" + counter + ")" + extension;
    }

    private String getFileContent(){
        String result;
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < drawingPanel.getMainList().size(); i++){

            FigureObject currentF = drawingPanel.getMainList().get(i);

            if(currentF.getNameOfFigure().equals(exceptionCase)){

                buildBaseLine(stringBuilder, currentF);
                stringBuilder.append("||");

                    for(int j = 0; j < currentF.getShapes().size(); j++){
                        double currentX = currentF.getShapes().get(j).getBounds().getX();
                        double currentY = currentF.getShapes().get(j).getBounds().getY();

                        stringBuilder.append(currentX).append(" ").
                                append(currentY).append("|");
                    }
                    stringBuilder.append(";\n");

            }  else {
                buildBaseLine(stringBuilder, currentF);
                stringBuilder.append(";\n");
            }
        }

        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        result = stringBuilder.toString();

        return result;
    }

    private void buildBaseLine(StringBuilder stringBuilder, FigureObject currentFigure){

        stringBuilder.append(currentFigure.getNameOfFigure()).append("|").
                append(currentFigure.getColors().get(0).getRed()).append(",").
                append(currentFigure.getColors().get(0).getGreen()).append(",").
                append(currentFigure.getColors().get(0).getBlue()).append("|").
                append(currentFigure.getPoint1().getX()).append("|").
                append(currentFigure.getPoint1().getY()).append("|").
                append(currentFigure.getPoint2().getX()).append("|").
                append(currentFigure.getPoint2().getY());
    }

}