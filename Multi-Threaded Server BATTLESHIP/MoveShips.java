

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;


public class MoveShips extends JPanel implements ActionListener, KeyListener {

	private UserShips uShips;
    private JButton select;
    private DisplayGame window;

   
    public MoveShips(DisplayGame newView) {
        window = newView;
        JPanel panel = new JPanel();
        select = new JButton("Make Ready");
        add(select, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
       	select.addActionListener(this);
        select.addKeyListener(this);

    }

    public void setShips(UserShips ships) {
        uShips = ships;
    }

   
    public void actionPerformed(ActionEvent ae) {

    }


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int kc = e.getKeyCode();
		window.placeShipsView();
		//System.out.print(kc);
		if (kc > 31 && kc < 41) {
			
			if(kc == KeyEvent.VK_UP) {
			
				uShips.moveUp();
			}
			else if (kc == KeyEvent.VK_DOWN) {
				
				uShips.moveDown();
			}
			    
			else if (kc == KeyEvent.VK_LEFT) {
			    	
				uShips.leftMove();
			}
			    
			else if (kc == KeyEvent.VK_RIGHT) {
				
				uShips.moveRight();
			}
			
		
		}
		
		else if (kc > 9 && kc < 11) {
			
			if(kc == KeyEvent.VK_ENTER) {
				
				 try {
					window.startGameMessage();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
