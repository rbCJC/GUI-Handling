import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;


public class ViewSpecificDiaries extends JFrame {
    private static final String DATA_FILE = "C:/data/diaryData.txt";
    private static final String CONTENT_SEPARATOR = "-----------------------------------------------------";

    private JPanel mainPanel;
    private JTextArea outputContent;
    private JTextField txtInput;
    private JButton btnLoad;
    private JButton btnClear;
    private JButton btnClose;

    public ViewSpecificDiaries() {
        setTitle("Search Diary Entries");
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        // Add event listeners
        btnLoad.addActionListener(e -> searchAndLoadDiaries());
        btnClear.addActionListener(e -> clearOutput());
        btnClose.addActionListener(e -> dispose());
        txtInput.addActionListener(e -> searchAndLoadDiaries()); // Allow search on Enter key

        checkDataFileExists();
    }

    private void searchAndLoadDiaries() {
        String searchId = txtInput.getText().trim();
        if (searchId.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter an ID to search",
                    "Search Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        outputContent.setText("");
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("%");
                if (parts.length >= 3) {
                    String id = parts[0].trim();
                    if (id.contains(searchId)) {
                        String date = parts[1].trim();
                        String diaryContent = parts[2].trim();

                        content.append(String.format("%s - %s CONTENT:%n", id, date))
                                .append(CONTENT_SEPARATOR).append('\n')
                                .append(diaryContent).append('\n')
                                .append(CONTENT_SEPARATOR).append("\n\n");
                        found = true;
                    }
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this,
                        "No diary entries found with ID: " + searchId,
                        "No Results",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                outputContent.setText(content.toString());
                outputContent.setCaretPosition(0);
                txtInput.setText(""); // Clear search field after successful search
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error searching diary entries: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearOutput() {
        outputContent.setText("");
        txtInput.setText("");
        txtInput.requestFocus();
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

        SwingUtilities.invokeLater(() -> new ViewSpecificDiaries().setVisible(true));
    }
}
