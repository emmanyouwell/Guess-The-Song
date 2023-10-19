package musicPlayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import javazoom.jl.player.advanced.AdvancedPlayer;

@SuppressWarnings("serial")
public class Music extends JFrame implements ActionListener, Runnable {
	static Music p;
	static float index = 1;
	static float[] correct;
	static float[] items;
	static float total=0;
	static String ans;

	Thread t;
	JLabel l1 = new JLabel("ENTER SONG");
	JLabel l2 = new JLabel("Provide Song Path...");
	JButton b1 = new JButton("PLAY");
	JButton b2 = new JButton("PAUSE");
	JButton b3 = new JButton("Open");
	JButton b4 = new JButton("RESUME");
	JButton b5 = new JButton("STOP");
	JButton b6 = new JButton("Check");
	JButton b7 = new JButton("STATS");
	JTextField t1 = new JTextField();
	JTextArea ta1 = new JTextArea();
	JScrollPane sp;
	DefaultListModel model;
	AdvancedPlayer music;

	public File songFile;
	public File currentFile;
	public List<File> files = new ArrayList<>();
	public float[] stats;
	

	Music() {


		setTitle("Music Player");
		t1.setBounds(20, 130, 260, 30);
		t1.setBackground(Color.WHITE);
		b1.setBounds(50, 200, 90, 50);
		b2.setBounds(243, 200, 90, 50);
		b3.setBounds(243, 50, 90, 25);
		b3.setBackground(Color.YELLOW);
		b4.setBounds(147, 200, 90, 50);
		b5.setBounds(147, 260, 90, 50);
		b6.setBounds(283, 130, 90, 29);
		b7.setBounds(50,260, 90, 50);
		l1.setBounds(67, 13, 300, 100);
		l2.setBounds(20, 65, 170, 100);
		ta1.setBounds(50, 320, 283, 200);
		for (int i=0; i<files.size(); i++) {
			ta1.append(files.get(i).getName() + "\n");
		}
		sp = new JScrollPane(ta1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setBounds(50,320,283,200);
		getContentPane().add(sp);
		b1.setEnabled(false);
		b2.setEnabled(false);
		b4.setEnabled(false);
		b5.setEnabled(false);
		b7.setEnabled(false);
		b6.setEnabled(false);
//sp.setPreferredSize(new Dimension(250, 250));
		
		add(b1);
		add(b2);
		add(b3);
		add(b4);
		add(b5);
		add(b6);
		add(b7);
		add(l1);
		add(l1);
		add(t1);
		
		add(sp);
		Font fo = new Font("Broadway", Font.BOLD, 23);
		l1.setFont(fo);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent c) {
				try {
					JFileChooser ch = new JFileChooser();
					FileListAccessory accessory = new FileListAccessory(ch);
	                ch.setAccessory(accessory);

	                int open = ch.showOpenDialog(ch);
	                if (open == JFileChooser.APPROVE_OPTION) {
	                    model = accessory.getModel();
	                    for (int i = 0; i < model.getSize(); i++) {
	                        files.add((File) (model.getElementAt(i)));
	                        ta1.append(((File) model.getElementAt(i)).getName() + "\n");
	                    	
	                    }
	                    stats = new float[files.size()];
	                    correct = new float[files.size()];
	                    items = new float[files.size()];
	                    for (int i=0; i<files.size();i++) {
	                    	stats[i] = 0;
	                    	correct[i] = 0;
	                    	items[i] = 0;
	                    
	                    }
	                   
	                    b1.setEnabled(true);
	            		b2.setEnabled(true);
	            		b4.setEnabled(true);
	            		b5.setEnabled(true);
	            		b7.setEnabled(true);
	            		b6.setEnabled(true);
	                }
//					ch.setDialogTitle("Provide the song path..");
//					ch.showOpenDialog(null);
//					songFile = ch.getSelectedFile();
//					if (!songFile.getName().endsWith(".mp3")) {
//						JOptionPane.showMessageDialog(null, "Please try again!", "INVALID ENTRY",
//								JOptionPane.ERROR_MESSAGE);
//					} else {
//						ta1.append(songFile.getName() + "\n");
//						files.add(songFile);
//						index++;
//						
//					}
	                
	                    
	               
	                
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "No Song Selected!");
				}
			}
		});

		setLayout(null);
		setSize(400, 600);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		p = new Music();
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) {
			t = new Thread(p);
			b1.setEnabled(false);
			t.start();
		} else if (e.getSource() == b2) {
			try {
				t.suspend();
				b2.setEnabled(false);
				b6.setEnabled(false);
				b5.setEnabled(false);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Select Valid Song!", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == b4) {
			try {
				t.resume();
				b2.setEnabled(true);
				b6.setEnabled(true);
				b5.setEnabled(true);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Select Valid Song!", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == b5) {
			try {
				p.stop();
				t.interrupt();
				b1.setEnabled(true);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Select Valid Song!", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == b6) {
			try {
				if (t1.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Type in the text box", "ERROR", JOptionPane.ERROR_MESSAGE);
				} else if (currentFile == null) {
					JOptionPane.showMessageDialog(null, "Play a song!", "ERROR", JOptionPane.ERROR_MESSAGE);
				} else {
					ans = t1.getText().toLowerCase() + ".mp3";
					if (ans.equals(currentFile.getName().toLowerCase())) {
						JOptionPane.showMessageDialog(null, "Correct!", "Correct", JOptionPane.INFORMATION_MESSAGE);
						p.stop();
						t.interrupt();
						t = new Thread(p);
						t.start();
						
						correct[files.indexOf(currentFile)] += 1;
						
						index = items[files.indexOf(currentFile)] += 1;
						
						stats[files.indexOf(currentFile)] = correct[files.indexOf(currentFile)]/index;
						
						
					} else {
						index = items[files.indexOf(currentFile)] += 1;
						
						stats[files.indexOf(currentFile)] = correct[files.indexOf(currentFile)]/index;
						JOptionPane.showMessageDialog(null, currentFile.getName(), "WRONG!", JOptionPane.INFORMATION_MESSAGE);
						p.stop();
						t.interrupt();
						t = new Thread(p);
						t.start();
					}
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			}

		}
		else if (e.getSource() == b7) {
			String msg = "";
//			for (int i=0; i<files.size();i++) {
//				msg += files.get(i).getName() + ": " + ((stats[i]/index) * 100)  + "%\n";
//			}
			statisticView m = new statisticView(files, stats, items);
//			JOptionPane.showMessageDialog(null, msg, "Statistics", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				Random rand = new Random();
				int i = rand.nextInt(files.size());
				
				currentFile = files.get(i);
				
				music = new AdvancedPlayer(new FileInputStream(currentFile));
				music.play();

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getCause() + "\n" + e.getMessage() + "\n" + currentFile.getName(), "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	public void stop() {

		music.close();
	}

}
