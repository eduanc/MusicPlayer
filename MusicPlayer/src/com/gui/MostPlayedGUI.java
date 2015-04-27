package com.gui;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.dao.MusicDAO;
import com.dto.MusicDTO;

public class MostPlayedGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7578681865694362464L;

	private JPanel contentPane;
	
	private HashMap<MusicDTO, Integer> hashMap = new MusicDAO().readMostPlayed();
	private JTable table;

	/**
	 * Create the frame.
	 */
	public MostPlayedGUI() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 5, 433, 255);
		contentPane.add(scrollPane);
		
		table = new JTable();
		TableModel model = new DefaultTableModel(getMusicTableData(), getMusicTableNames());
		table.setModel(model);
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(model);
		rowSorter.setComparator(0, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return Integer.valueOf(o1) > Integer.valueOf(o2) ? 1 : -1;
			}
		});
		rowSorter.toggleSortOrder(0);
		rowSorter.toggleSortOrder(0);
		table.setRowSorter(rowSorter);
		
		scrollPane.setViewportView(table);
	}
	
	private String[] getMusicTableNames() {
		return new String[] {"Played", "%", "Title", "Author", "Album"};
	}
	
	private String[][] getMusicTableData() {
		String[][] names = new String[hashMap.size()][5];
		
		Iterator<Map.Entry<MusicDTO, Integer>> it = hashMap.entrySet().iterator();
		
		int i = 0;
		int total = new MusicDAO().getMostPlayedTotal();
	    while (it.hasNext()) {
	        Map.Entry<MusicDTO, Integer> pair = it.next();
	        MusicDTO item = pair.getKey();
	        int itemCounter = pair.getValue();
	        
	        names[i][0] = itemCounter+"";
	        names[i][1] = itemCounter*100/total+"";
			names[i][2] = item.getName();
			names[i][3] = item.getAuthor();
			names[i][4] = item.getAlbum();
	        
			i++;
	        it.remove();
	    }
		
		return names;
	}

}
