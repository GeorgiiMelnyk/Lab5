package lab5.application;

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
            String con = getFileContent();
            System.out.println(con);
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

        for (int i = 0; i < drawingPanel.getFigureObjectsMainList().size(); i++){
            if(i == drawingPanel.getFigureObjectsMainList().size() - 1){
                stringBuilder.append(drawingPanel.getFigureObjectsMainList().get(i).getNameOfFigure()).append("|").
                        append(drawingPanel.getFigureObjectsMainList().get(i).getColors().get(0).getRed()).append(",").
                        append(drawingPanel.getFigureObjectsMainList().get(i).getColors().get(0).getGreen()).append(",").
                        append(drawingPanel.getFigureObjectsMainList().get(i).getColors().get(0).getBlue()).append("|").
                        append(drawingPanel.getFigureObjectsMainList().get(i).getPoint1().getX()).append("|").
                        append(drawingPanel.getFigureObjectsMainList().get(i).getPoint1().getY()).append("|").
                        append(drawingPanel.getFigureObjectsMainList().get(i).getPoint2().getX()).append("|").
                        append(drawingPanel.getFigureObjectsMainList().get(i).getPoint2().getY());
            } else {
                stringBuilder.append(drawingPanel.getFigureObjectsMainList().get(i).getNameOfFigure()).append("|").
                        append(drawingPanel.getFigureObjectsMainList().get(i).getColors().get(0).getRed()).append(",").
                        append(drawingPanel.getFigureObjectsMainList().get(i).getColors().get(0).getGreen()).append(",").
                        append(drawingPanel.getFigureObjectsMainList().get(i).getColors().get(0).getBlue()).append("|").
                        append(drawingPanel.getFigureObjectsMainList().get(i).getPoint1().getX()).append("|").
                        append(drawingPanel.getFigureObjectsMainList().get(i).getPoint1().getY()).append("|").
                        append(drawingPanel.getFigureObjectsMainList().get(i).getPoint2().getX()).append("|").
                        append(drawingPanel.getFigureObjectsMainList().get(i).getPoint2().getY()).append("\n");
            }

        }

        result = stringBuilder.toString();
        return result;
    }

}