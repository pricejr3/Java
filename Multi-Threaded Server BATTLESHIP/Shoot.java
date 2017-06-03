
 */
import javax.swing.*;

import java.awt.event.*;
import java.io.IOException;

public class Shoot extends JPanel implements ActionListener {
	public JButton shoot = new JButton();
	public DisplayGame viewer;

	public Shoot(DisplayGame newView) {
		viewer = newView;
		setOpaque(false);

		shoot.setText("Fire");

		add(shoot);

		shoot.addActionListener(this);

	}

	public void enable() {
		shoot.setEnabled(true);
	}

	public void disable() {
		shoot.setEnabled(false);
	}

	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if (source == shoot) {
			try {
				viewer.firing();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
