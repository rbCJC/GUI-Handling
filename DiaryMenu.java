import javax.swing.*;

/**
 * DiaryMenu - Main menu interface for the Diary Application
 * This class provides the main navigation menu with options to:
 * - Add new diary entries
 * - View all diary entries
 * - View specific diary entries
 * - Exit the application
 */
public class DiaryMenu extends JFrame {
    private JPanel mainPanel;
    private JButton btnAddDiary;
    private JButton btnViewAll;
    private JButton btnView;
    private JButton btnExit;

    public DiaryMenu() {
        setTitle("Diary Application");
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300,300);
        setLocationRelativeTo(null);

        // Add event listeners
        btnAddDiary.addActionListener(e -> openNewDiary());
        btnViewAll.addActionListener(e -> openViewDiaries());
        btnView.addActionListener(e -> openViewSpecificDiaries());
        btnExit.addActionListener(e -> System.exit(0));
    }

    private void openNewDiary() {
        new NewDiary().setVisible(true);
    }

    private void openViewDiaries() {
        new ViewDiaries().setVisible(true);
    }

    private void openViewSpecificDiaries() {
        new ViewSpecificDiaries().setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new DiaryMenu().setVisible(true));
    }
}
