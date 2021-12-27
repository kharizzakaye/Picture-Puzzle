import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Puzzle extends JFrame implements ActionListener{
	
	ArrayList<Icon> allImages = new ArrayList<Icon>();
	ArrayList<JButton> allButtons = new ArrayList<JButton>();
	JPanel panel;
	
	boolean firstClick = false;
	
	//save the first click here
	JButton firstBtn;
	JButton secondBtn;
	
	public Puzzle() {
		super("Picture Puzzle");
		storeImages(); // load images once
		init();
	}
	
	public void init() {
		panel = new JPanel();
		panel.setLayout(new GridLayout(3,3,1,1));
		getContentPane().add(panel, BorderLayout.CENTER); // add the panel inside JFrame
		
		createButtons();
		
		// reset
		JButton reset = new JButton("RESET");
		getContentPane().add(reset, BorderLayout.SOUTH);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				allButtons.clear();
				getContentPane().removeAll(); // remove all contents inside JFrame
				init();
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); // resizes jframe content
		setVisible(true);
	}
	
	public void storeImages() {
		for(int i = 1; i<10; i++) {
			Icon ic = new ImageIcon(getClass().getResource("dog-puzzle/" + i + ".jpg"));
			allImages.add(ic);
		}
	}
	
	public void createButtons() {
		for (int i = 0; i<9; i++) {
			JButton btn = new JButton(resizeImage(allImages.get(i))); // assign image to button with the same index number
			
			// set a description to the image icon (track correct placement)
			((ImageIcon) btn.getIcon()).setDescription(String.valueOf(i));
			
			// resize image to fit button dimensions
			btn.setPreferredSize(new Dimension(150,150));
			
			// connect buttons to action listener
			btn.addActionListener(this);
			
			allButtons.add(btn);
		}
		
		// shuffle images
		Collections.shuffle(allButtons);
		
		// insert buttons to JPanel
		for(int j = 0; j<9; j++) {
			panel.add(allButtons.get(j));
		}
	}
	
	public Icon resizeImage(Icon input) {
		ImageIcon img = new ImageIcon(((ImageIcon)input).getImage().getScaledInstance(150, 150, DO_NOTHING_ON_CLOSE));
		return img;
	}

	public static void main(String[] args) {
		new Puzzle();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!firstClick) {
			firstClick = true;
			firstBtn = (JButton)e.getSource();
		}
		else {
			firstClick = false;
			secondBtn = (JButton)e.getSource();
			
			// check if 1st and 2nd buttons are different
			if(secondBtn != firstBtn) {
				swap();
			}
			
			// save result after swap
			boolean result = checkWin();
			if(result) {
				JOptionPane.showMessageDialog(this, "Great Job! You WON!", "Congratulations", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}

	public void swap() {
		Icon ic1 = firstBtn.getIcon();
		Icon ic2 = secondBtn.getIcon();
		
		firstBtn.setIcon(ic2);
		secondBtn.setIcon(ic1);
	}
	
	public boolean checkWin() {
		boolean win = true;
		
		for(int i = 0; i<9; i++) {
			if(!((ImageIcon)allButtons.get(i).getIcon()).getDescription().equals(String.valueOf(i))){
				win = false;
				break;
			}
		}
		
		return win;
	}
}
