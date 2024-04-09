import javax.swing.*;
import java.awt.event.*;

public class COCOMOGUI extends JFrame implements ActionListener {
    // GUI Components
    private JLabel locLabel;
    private JLabel modeLabel;
    private JTextField locField;
    private JComboBox<String> modeComboBox;
    private JTextArea outputArea;
    private JButton calculateButton;

    // Constructor
    public COCOMOGUI() {
        setTitle("COCOMO Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Initialize GUI components
        locLabel = new JLabel("Lines of Code (LOC):");
        locLabel.setBounds(50, 20, 150, 30);

        locField = new JTextField();
        locField.setBounds(200, 20, 200, 30);

        modeLabel = new JLabel("Development Mode:");
        modeLabel.setBounds(50, 60, 150, 30);

        modeComboBox = new JComboBox<>(new String[]{"Organic", "Semi-detached", "Embedded"});
        modeComboBox.setBounds(200, 60, 200, 30);

        calculateButton = new JButton("Calculate");
        calculateButton.setBounds(150, 100, 200, 30);
        calculateButton.addActionListener(this);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBounds(50, 150, 300, 100);

        // Add components to the frame
        add(locLabel);
        add(locField);
        add(modeLabel);
        add(modeComboBox);
        add(calculateButton);
        add(scrollPane);

        setVisible(true);
    }

    // Action performed when the Calculate button is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton) {
            double loc = Double.parseDouble(locField.getText());
            int mode = modeComboBox.getSelectedIndex() + 1;

            double effort = calculateEffort(loc, mode);
            double time = calculateTime(effort, mode);
            double personnel = calculatePersonnel(effort, time);
            double averageKLOC = calculateAverageKLOC(loc, personnel);

            String output = String.format("Effort required: %.2f person-months\n" +
                            "Time required: %.2f months\n" +
                            "Personnel required: %.2f persons\n" +
                            "Average KLOC per person: %.2f KLOC/person\n",
                    effort, time, personnel, averageKLOC);

            outputArea.setText(output);
        }
    }

    // COCOMO calculations
    private double calculateEffort(double loc, int mode) {
        double a, b;
        switch (mode) {
            case 1: // Organic
                a = 2.4;
                b = 1.05;
                break;
            case 2: // Semi-detached
                a = 3.0;
                b = 1.12;
                break;
            case 3: // Embedded
                a = 3.6;
                b = 1.20;
                break;
            default:
                System.out.println("Invalid mode");
                return -1;
        }
        return a * Math.pow(loc, b);
    }

    private double calculateTime(double effort, int mode) {
        double time;
        switch (mode) {
            case 1: // Organic
                time = 2.5;
                break;
            case 2: // Semi-detached
                time = 2.5;
                break;
            case 3: // Embedded
                time = 2.5;
                break;
            default:
                System.out.println("Invalid mode");
                return -1;
        }
        return time * Math.pow(effort, 0.33);
    }

    private double calculatePersonnel(double effort, double time) {
        return effort / time;
    }

    private double calculateAverageKLOC(double loc, double personnel) {
        return loc / (personnel * 1000); // Convert LOC to KLOC
    }

    public static void main(String[] args) {
        new COCOMOGUI();
    }
}
