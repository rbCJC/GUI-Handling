import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ViewDiaries extends JFrame {
    private static final String DATA_FILE = "C:/data/diaryData.txt";
    private static final String CONTENT_SEPARATOR = "-----------------------------------------------------";

    private JTextArea outputContent;
    private JButton btnLoad;
    private JButton btnClose;

    public ViewDiaries() {
        setTitle("View All Diaries");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initializeUI();
        setSize(400,600);
        setLocationRelativeTo(null);
        checkDataFileExists();
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Text Area for output
        outputContent = new JTextArea(15, 30);
        outputContent.setEditable(false);
        outputContent.setWrapStyleWord(true);
        outputContent.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(outputContent);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLoad = new JButton("Load");
        btnClose = new JButton("Close");

        Dimension buttonSize = new Dimension(109, 35);
        btnLoad.setPreferredSize(buttonSize);
        btnClose.setPreferredSize(buttonSize);

        buttonPanel.add(btnLoad);
        buttonPanel.add(btnClose);

        // Add components to main panel
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add event listeners
        btnLoad.addActionListener(e -> loadDiaries());
        btnClose.addActionListener(e -> dispose());

        setContentPane(mainPanel);
        setMinimumSize(new Dimension(400, 300));
    }

    private void loadDiaries() {
        outputContent.setText("");
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("%");
                if (parts.length >= 3) {
                    String id = parts[0].trim();
                    String date = parts[1].trim();
                    String diaryContent = parts[2].trim();

                    content.append(String.format("%s - %s CONTENT:%n", id, date))
                            .append(CONTENT_SEPARATOR).append('\n')
                            .append(diaryContent).append('\n')
                            .append(CONTENT_SEPARATOR).append("\n\n");
                }
            }
            outputContent.setText(content.toString());
            outputContent.setCaretPosition(0);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading diary entries: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkDataFileExists() {
        Path dataFile = Paths.get(DATA_FILE);
        if (!Files.exists(dataFile)) {
            JOptionPane.showMessageDialog(this,
                    "No diary entries found. The data file does not exist.",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new ViewDiaries().setVisible(true));
    }
}
