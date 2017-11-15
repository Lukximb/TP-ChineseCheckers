package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class ButtonsAdapter implements ActionListener {
	ClientGUI gui;
	
	public ButtonsAdapter(ClientGUI gui) {
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == gui.submitButton) {
			try {
				gui.client.p.printStr(gui.sendString());
				gui.outputField.setText(gui.client.p.getReply());
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
}
