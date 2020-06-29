package gauss;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


@SuppressWarnings("serial")
public class popup extends JFrame {
	 public void setMessage(String message) {
		this.message = message;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	String message = "some message";
	 String header = "Header";
	public  popup(String header, String message) {
		this.message = message;
		this.header = header;
		JPanel panel = new JPanel();
		add(panel);
		setSize(300, 125);
		Dimension scrSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		Insets toolHeight = java.awt.Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		setLocation(scrSize.width - getWidth(), scrSize.height - toolHeight.bottom - getHeight());

		setAlwaysOnTop(true);
		ImageIcon icon = createImageIcon("../images/splash.png", "a pretty but meaningless splat");

		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0f;
		constraints.weighty = 1.0f;
		constraints.insets = new Insets(5, 5, 5, 5);
		JLabel headLabel = new JLabel(header);
		headLabel.setIcon(icon);
		headLabel.setOpaque(false);
		panel.add(headLabel, constraints);
		constraints.gridx++;
		constraints.weightx = 0f;
		constraints.weighty = 0f;
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.NORTH;
		/*
		JButton closeBtn = new JButton("X");
		closeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		closeBtn.setMargin(new Insets(1, 4, 1, 4));
		closeBtn.setFocusable(false);
		panel.add(closeBtn);
		*/
		constraints.gridx = 0;
		constraints.gridy++;
		constraints.weightx = 1.0f;
		constraints.weighty = 1.0f;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.fill = GridBagConstraints.BOTH;
		JLabel msgLabel = new JLabel("<HtMl>" + message);
		panel.add(msgLabel, constraints);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		new Thread() {

			@Override
			public void run() {
				try {
					Thread.sleep(5000);
					dispose();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}.start();
		
	}
	
	public popup() {
		// TODO Auto-generated constructor stub
	}

	public void main(String head, String msg) {
		popup pop = new popup(head, msg);
		pop.setUndecorated(true);
		pop.setVisible(true);
	}
	
	public void reshow() {
		new Thread() {

			@Override
			public void run() {
				try {
					Thread.sleep(5000);
					dispose();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}.start();
	}
	
	protected ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgUrl = getClass().getResource(path);
		if(imgUrl != null) {
			return new ImageIcon(imgUrl, description);
		} else {
			System.err.println("Can't find file " + path);
			return null;
		}
	}
}
