import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DrawingFrame extends JFrame {
    private static final String INCORRECT_OPEN_TYPE_OF_FILE = "Incorrect type of file!\n " +
            "Select file with extension jpg ";
    private static final String INCORRECT_SAVE_TYPE_OF_FILE = "Incorrect type of file!\n " +
            "Select directory.";
    private static final String TYPE_OF_FILE_ERROR = "error choose of file";
    private static final String INPUT_ERROR = "error input";
    private final DrawingPanel drawingPanel;
    private final JTextField radiusField;

    public DrawingFrame() {
        setTitle("Рисование");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawingPanel = new DrawingPanel();
        drawingPanel.setPreferredSize(new Dimension(1800, 950));

        JButton BLUE_BUTTON = new JButton();
        JButton GREEN_BUTTON = new JButton();
        JButton RED_BUTTON = new JButton();
        BLUE_BUTTON.setBackground(Color.BLUE);
        RED_BUTTON.setBackground(Color.RED);
        GREEN_BUTTON.setBackground(Color.GREEN);

        JButton OPEN_BUTTON = new JButton("Открыть");
        JButton SAVE_BUTTON = new JButton("Сохранить");
        JButton CLEAR_BUTTON = new JButton("Отчистить");

        JButton RADIUS_BUTTON = new JButton("изменить толщину линии");

        radiusField = new JTextField(20);
        radiusField.setSize(100, 20);


        JScrollPane scrollPane = new JScrollPane(drawingPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

         scrollPane.setPreferredSize(new Dimension(500, 400));

        setLayout(new BorderLayout());
        // setLocationByPlatform(true);
        add(scrollPane, BorderLayout.CENTER);

        JPanel colorPanel = new JPanel();
        colorPanel.add(RED_BUTTON);
        colorPanel.add(GREEN_BUTTON);
        colorPanel.add(BLUE_BUTTON);
        colorPanel.add(RADIUS_BUTTON);
        colorPanel.add(radiusField);
        add(colorPanel, BorderLayout.SOUTH);

        JPanel actionPanelButtons = new JPanel();
        actionPanelButtons.add(SAVE_BUTTON);
        actionPanelButtons.add(OPEN_BUTTON);
        actionPanelButtons.add(CLEAR_BUTTON);
        add(actionPanelButtons, BorderLayout.NORTH);


        this.pack();
        this.setVisible(true);

        RED_BUTTON.addActionListener(e -> drawingPanel.setColor(Color.RED));
        GREEN_BUTTON.addActionListener(e -> drawingPanel.setColor(Color.GREEN));
        BLUE_BUTTON.addActionListener(e -> drawingPanel.setColor(Color.BLUE));

        CLEAR_BUTTON.addActionListener(e -> drawingPanel.clear());

        SAVE_BUTTON.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileChooser.getSelectedFile();
                    String pathToFile = file.getPath();
                    String newFilePath = pathToFile + ".jpg";
                    file = new File(newFilePath);
                    if (pathToFile.contains(".")) {
                        throw new IOException(INCORRECT_SAVE_TYPE_OF_FILE);
                    }
                    System.out.println(file.getAbsolutePath());
                    ImageIO.write(drawingPanel.getMyImage(), "jpg", file);
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(),
                            TYPE_OF_FILE_ERROR, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        OPEN_BUTTON.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    String nameFile = file.getName();
                    int lastPoint = nameFile.lastIndexOf(".");
                    //  Метод `lastIndexOf(".")` ищет последнюю точку в имени файла и возвращает ее индекс.
                    //  Если точка не найдена, возвращается -1.
                    if (lastPoint == -1) {
                        throw new IOException();
                    }
                    String typeExtension = nameFile.substring(lastPoint + 1);
                    if (!typeExtension.equals("jpg")) {
                        throw new IOException(INCORRECT_OPEN_TYPE_OF_FILE);
                    }

                    drawingPanel.setMyImage(ImageIO.read(file));
                    drawingPanel.setMyGraphics((Graphics2D) drawingPanel.getMyImage().getGraphics());
                    drawingPanel.repaint();
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(),
                            TYPE_OF_FILE_ERROR, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        RADIUS_BUTTON.addActionListener(e -> {

                try {
                    drawingPanel.changeRadius(Integer.parseInt(radiusField.getText()));
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(),
                            INPUT_ERROR, JOptionPane.ERROR_MESSAGE);
                }

        });
    }

}

