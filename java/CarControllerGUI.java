import javax.swing.*;
import java.awt.*;

public class CarControllerGUI extends JFrame {
    private JButton upButton, downButton, leftButton, rightButton, stopButton;

    public CarControllerGUI() {
        // Set up the JFrame
        super("Need For Speed");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        // Set up the buttons
        upButton = new JButton("Forward");
        downButton = new JButton("Reverse");
        stopButton = new JButton("Brake");
        leftButton = new JButton("Left");
        rightButton = new JButton("Right");

        // Set up the layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.add(new JLabel());
        buttonPanel.add(upButton);
        buttonPanel.add(new JLabel());
        buttonPanel.add(leftButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(rightButton);
        buttonPanel.add(new JLabel());
        buttonPanel.add(downButton);
        buttonPanel.add(new JLabel());
        
        
        // Set up the image label
        JLabel imageLabel = new JLabel();
        ImageIcon image = new ImageIcon("driver.jpg");
        imageLabel.setIcon(image);

        // Add the image label to the panel
        add(imageLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        // Show the JFrame
        setVisible(true);
    }

    public static void main(String[] args) {
        CarControllerGUI gui = new CarControllerGUI();
    }
}