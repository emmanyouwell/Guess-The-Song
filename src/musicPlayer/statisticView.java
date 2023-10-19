package musicPlayer;

import java.awt.Color;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class statisticView {
	JFrame f;
	JTable j;

	statisticView(List<File> list, float[] stats, float[] items) {
		try {

			float[] highest = new float[3];
			float[] highFreq = new float[3];
			StatItemPair[] pairs = new StatItemPair[stats.length];
			int[] indices = new int[3];
			f = new JFrame();
			f.setTitle("Statistical Sheet");
			String[][] data = new String[list.size()][stats.length];

			for (int i = 0; i < list.size(); i++) {

				for (int j = 0; j < 3; j++) {
					if (j == 0) {
						data[i][j] = list.get(i).getName();
					} else if (j == 1) {
						data[i][j] = Float.toString((stats[i]) * 100) + "%";
					} else if (j == 2) {
						data[i][j] = Float.toString(items[i]);
					}
				}
			}

			highest[0] = 0;
			highFreq[0] = 0;
			for (int i = 0; i < stats.length; i++) {
				pairs[i] = new StatItemPair(stats[i], items[i], i);
			}

			Arrays.sort(pairs, (a, b) -> {
	            int statComparison = Float.compare(b.stat, a.stat);
	            if (statComparison != 0) {
	                return statComparison;
	            }
	            return Float.compare(b.item, a.item);
	        });
			for (int i = 0; i < 3; i++) {
				indices[i] = pairs[i].index;
			}
			for (int i = 0; i < Math.min(3, pairs.length); i++) {
				System.out.println("Stat: " + pairs[i].stat + ", Item: " + pairs[i].item +", index: " + pairs[i].index);

			}



			// Column Names
			String[] columnNames = { "Song Title", "Sucess Rate", "Frequency" };

			// Initializing the JTable
			j = new JTable(data, columnNames);
			j.setBounds(30, 40, 200, 300);
			j.setSelectionBackground(Color.yellow);
			
			for (int i = 0; i < Math.min(3, pairs.length); i++) {
				if (pairs[i].stat>0) {
					j.addRowSelectionInterval(indices[i], indices[i]);
				}

			}
       

			j.setBackground(Color.white);
			j.setForeground(Color.BLUE);

			// adding it to JScrollPane
			JScrollPane sp = new JScrollPane(j);
			f.add(sp);
			f.setSize(500, 600);
			f.setLocationRelativeTo(null);
			// Frame Visible = true
			f.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
