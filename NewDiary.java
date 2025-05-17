

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NewDiary extends JFrame {
    private static final String DATA_FILE = "C:/data/diaryData.txt";

    private JPanel mainPanel;
    private JTextField txtId;
    private JTextField txtDate;
    private JTextArea txtContent;
    private JButton btnSave;
    private JButton btnClose;

    public NewDiary() {
        setTitle("New Diary Entry");
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500,300);
        setLocationRelativeTo(null);

        // Add event listeners
        btnSave.addActionListener(e -> saveDiary());
        btnClose.addActionListener(e -> dispose());

        checkDataFileExists();
    }

    private void saveDiary() {
        try {
            String id = txtId.getText().trim();
            String date = txtDate.getText().trim();
            String content = txtContent.getText().trim();

            if (id.isEmpty() || date.isEmpty() || content.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "All fields must be filled out",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String data = String.format("%s %% %s %% %s%n", id, date, content);
            try (FileWriter writer = new FileWriter(DATA_FILE, true)) {
                writer.write(data);
            }

            JOptionPane.showMessageDialog(this,
                    "Diary entry saved successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            clearFields();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error saving diary entry: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtDate.setText("");
        txtContent.setText("");
        txtId.requestFocus();
    }

    private void checkDataFileExists() {
        try {
            Path dataDir = Paths.get("C:/data");
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error creating data directory: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new NewDiary().setVisible(true));
    }
}
