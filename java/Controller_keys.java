import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.border.Border;

//import apple.laf.JRSUIConstants.Direction;

public class Controller_keys {
	
	private String description;
    
    public Controller_keys() {
    	
    	this.description = ""; 
		
	}
    
    
    public void setFrame() {
    	// creating the frame
    	JFrame frame = new JFrame();
        frame.setTitle("Need For Speed");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 500);
		frame.setFocusable(true);

		// creating the panel
		JPanel panel = new JPanel();
		
		
        panel.setBounds(100,50,100,50);
        panel.setLocation(400, 200);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        // creating a label to go in the panel 
        JLabel Direction = new JLabel(); 
        Direction.setBorder(BorderFactory.createLineBorder(Color.RED));
        panel.add(Direction, BorderLayout.SOUTH);

        Direction.setText("Direction: "); 

		keysss(frame, Direction);

		frame.add(panel, BorderLayout.SOUTH);
    }
    
    //key presses 
    public void keysss(JFrame frame, JLabel Direction) {
    	frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				JavaNetworking socket= new JavaNetworking();
				switch (keyCode) {
				case KeyEvent.VK_UP:
                    Direction.setText("Direction: forward");
                    description = "Direction: forward";
					socket.move('f'); // -> GUI push forward to socket
					break;
				case KeyEvent.VK_DOWN:
                    Direction.setText("Direction: Reverse");
                    description = "Direction: Reverse"; 
					socket.move('b');
					break;
				case KeyEvent.VK_RIGHT:
                    Direction.setText("Direction: Right");
                    description = "Direction: Right"; 
					socket.move('r');
					break;
				case KeyEvent.VK_LEFT:
                    Direction.setText("Direction: Left");
                    description = "Direction: Left"; 
					socket.move('l');
					break;
				}
                
			}

			@Override
			public void keyReleased(KeyEvent e) {
				int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_LEFT ){
                    Direction.setText("Direction: Break");
                }
				

			}

		});
    }
    
    public String getDescription() {
    	return this.description; 
    }

	public static void main(String[] args) {
		Controller_keys c = new Controller_keys();
		c.setFrame();
	}
}


