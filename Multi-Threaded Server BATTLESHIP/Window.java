
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Window extends JFrame {
	
    public DisplayGui gui;
    public JFrame jFrame;
    public JButton servSide;
    public JButton cliSide;
    public JButton quit;
    JLayeredPane container;
    JPanel displayed;



    public Window(DisplayGui view) {
    	
        this.gui = view;
        setName("Battleship");
        setTitle("Battleship");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame = new JFrame("Menu");
        createMenu();
        container = new JLayeredPane();
        displayed = new JPanel();
        displayed.setBounds(0, 0, 600, 500);
        container.add(displayed, 1);
        getContentPane().add(container);
    }
    
    public void displayBoard() {
        displayed.setVisible(true);
        displayed.repaint();
    }

    public void notDisplayBoard() {
        displayed.setVisible(false);
        displayed.repaint();
    }

    public void displayGame(JPanel view) {
        container.add(view, 2);
    }


    public void resizingWindow() {
        Insets size = getInsets();
        setSize(600, 500 + size.top + 50);
    }

    public void playerExit(boolean mode) {
        quit.setEnabled(mode);
        cliSide.setEnabled(!mode);
        servSide.setEnabled(!mode);
    }


    private void createMenu() {
    	
    	jFrame.setLocation(450,550);
        jFrame.setVisible(true);
        jFrame.setSize(150, 100);
        jFrame.setLayout(new GridLayout(4,1));
        jFrame.setResizable(false);
        servSide = new JButton("Host Game");
        cliSide = new JButton("Join Game");
        quit = new JButton("Exit Game");
        jFrame.add(servSide);
        jFrame.add(cliSide);
        jFrame.add(quit);
        jFrame.setVisible(true);


        servSide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
					gui.createServer();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
    
        cliSide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               gui.createClient();
            }
        });


        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
					gui.exitGUi();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

    }

}
