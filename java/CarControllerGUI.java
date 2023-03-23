import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CarControllerGUI extends JFrame implements KeyListener {
    private JButton forwardButton, reverseButton, leftButton, rightButton, brakeButton;

    public CarControllerGUI() {
        // Set up the JFrame
        super("Need For Speed");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up the image label
        JLabel imagePanel = new JLabel();
        ImageIcon image = new ImageIcon("driver.jpg");
        imagePanel.setIcon(image);

        // Set up the buttons
        forwardButton = new JButton("Forward");
        reverseButton = new JButton("Reverse");
        brakeButton = new JButton("Brake");
        leftButton = new JButton("Left");
        rightButton = new JButton("Right");

        // Set up the layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.add(new JLabel());
        buttonPanel.add(forwardButton);
        buttonPanel.add(new JLabel());
        buttonPanel.add(leftButton);
        buttonPanel.add(brakeButton);
        buttonPanel.add(rightButton);
        buttonPanel.add(new JLabel());
        buttonPanel.add(reverseButton);
        buttonPanel.add(new JLabel());
        

        // Add the image label to the panel
        add(imagePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

         // add key listeners to the buttons
        forwardButton.addKeyListener(this);
        reverseButton.addKeyListener(this);
        leftButton.addKeyListener(this);
        rightButton.addKeyListener(this);
        brakeButton.addKeyListener(this);

        // Show the JFrame
        setVisible(true);
    }

    // implement KeyListener methods
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                forwardButton.getModel().setArmed(true);
                forwardButton.getModel().setPressed(true);
                break;
            case KeyEvent.VK_DOWN:
                reverseButton.getModel().setArmed(true);
                reverseButton.getModel().setPressed(true);
                break;
            case KeyEvent.VK_LEFT:
                leftButton.getModel().setArmed(true);
                leftButton.getModel().setPressed(true);
                break;
            case KeyEvent.VK_RIGHT:
                rightButton.getModel().setArmed(true);
                rightButton.getModel().setPressed(true);
                break;

            case KeyEvent.VK_ENTER:
                brakeButton.getModel().setArmed(true);
                brakeButton.getModel().setPressed(true);
                break;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                forwardButton.getModel().setArmed(false);
                forwardButton.getModel().setPressed(false);
                break;
            case KeyEvent.VK_DOWN:
                reverseButton.getModel().setArmed(false);
                reverseButton.getModel().setPressed(false);
                break;
            case KeyEvent.VK_LEFT:
                leftButton.getModel().setArmed(false);
                leftButton.getModel().setPressed(false);
                break;
            case KeyEvent.VK_RIGHT:
                rightButton.getModel().setArmed(false);
                rightButton.getModel().setPressed(false);
                break;
            case KeyEvent.VK_ENTER:
                brakeButton.getModel().setArmed(false);
                brakeButton.getModel().setPressed(false);
                break;
        }
    }
    
    public void keyTyped(KeyEvent e) {}
    
    public static void main(String[] args) {
        new CarControllerGUI();
    }
}
