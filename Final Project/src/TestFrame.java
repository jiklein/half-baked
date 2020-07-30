

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

public class TestFrame extends JDialog {
	Button theButton;
	ButtonListener al;
	
	public TestFrame() {
		al = new ButtonListener();
		theButton = new Button("button");
		theButton.addActionListener(al);
		this.add(theButton);
		this.setSize(200, 200);
		this.setVisible(true);
	}
	
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			System.out.println("clicked");
			synchronized(UIDemo.instance.lock) {
				UIDemo.instance.lock.notify();
			}
		}		
	}
}
