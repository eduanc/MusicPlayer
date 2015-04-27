package com.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.dao.MusicDAO;
import com.dto.MusicDTO;
import com.player.MusicPlayer;
import com.player.PlayerManager;
import com.thread.ThreadProgressBar;

public class PlayerGUI extends JFrame {
	
	private static final long serialVersionUID = 8487021165177117115L;
	private JPanel contentPane;
	private static JSlider progressBar;
	private static JLabel lblInfoMusic;
	
	private MusicDAO dao;	
	private static MusicPlayer mPlayer = new PlayerManager(); 
	
	private static List<MusicDTO> playlist;
	private static MusicDTO selected = null;
	
	private static JTable reproductionTable;
	
	private static ThreadProgressBar threadProgressBar;
	
	/**
	 * Create the frame.
	 */
	public PlayerGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 479, 310);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		this.contentPane.setLayout(null);
		
		playlist = new ArrayList<MusicDTO>();
		
		lblInfoMusic = new JLabel("Informações música");
		lblInfoMusic.setBounds(12, -11, 426, 35);
		lblInfoMusic.setHorizontalAlignment(JLabel.CENTER);
		this.contentPane.add(lblInfoMusic);
		
		JPanel audioButtonsPanel = new JPanel();
		audioButtonsPanel.setBounds(12, 23, 426, 35);
		this.contentPane.add(audioButtonsPanel);
		
		JButton btnPrevious = new JButton("«");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				changeMusic(-1);				
			}
		});
		audioButtonsPanel.add(btnPrevious);
		
		JButton btnPlay = new JButton(">");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!playlist.isEmpty()){				
					if (selected == null && playlist.size() > 0) {
						selected = playlist.get(0);
						//reproductionList.setSelectedIndex(0);
						reproductionTable.setRowSelectionInterval(0, 0);
					}								
					mPlayer.play(0, selected);
					updateMusicInfo();
				}
			}
		});
		audioButtonsPanel.add(btnPlay);
		
		JButton btnPause = new JButton("||");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mPlayer.pause();
			}
		});
		audioButtonsPanel.add(btnPause);
		
		JButton btnStop = new JButton("[]");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mPlayer.stop();
			}
		});
		audioButtonsPanel.add(btnStop);
		
		JButton btnProxima = new JButton("»");
		btnProxima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeMusic(1);				
			}
		});
		audioButtonsPanel.add(btnProxima);
		
		JPanel listButtonsPanel = new JPanel();
		listButtonsPanel.setBounds(400, 107, 50, 153);
		this.contentPane.add(listButtonsPanel);
		
		JButton buttonUp = new JButton("↑");
		buttonUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!playlist.isEmpty()){
					positionUp();
				}
			}
		});
		listButtonsPanel.add(buttonUp);
		
		JButton buttonDown = new JButton("↓");
		buttonDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!playlist.isEmpty()){
					positionDown();
				}
			}
		});
		listButtonsPanel.add(buttonDown);
		
		JButton btnEdit = new JButton("e");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!playlist.isEmpty() &&  selected != null){
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								new TagEditionGUI(playlist, selected).setVisible(true);							
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}				
		});
		
		listButtonsPanel.add(btnEdit);
		
		JButton btnExclude = new JButton("x");
		btnExclude.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!playlist.isEmpty()){
					playlist.remove(selected);
					selected=null;
					reloadJPlaylist();
				}
			}
		});
		listButtonsPanel.add(btnExclude);
		
		JButton btnAdd = new JButton("+");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("Audio", "mp3", "ogg", "wav", "flac"));

				int res = fc.showOpenDialog(null);
				if (res == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					if(file != null) {
						playlist.add(new MusicDTO(file, playlist.size()));
						reloadJPlaylist();
					} else { 
						JOptionPane.showMessageDialog(null, "Erro ao carregar arquivo!");
					}
				}
			}
		});
		listButtonsPanel.add(btnAdd);
		
		progressBar = new JSlider();
		progressBar.setValue(0);
		progressBar.setBounds(12, 58, 426, 16);
		contentPane.add(progressBar);
		progressBar.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.getSource() instanceof JSlider) {
					int value = ((JSlider) e.getSource()).getValue() * selected.getDuration() / 100;
					mPlayer.stop();
					mPlayer.play(value, selected);
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) { }
			
			@Override
			public void mouseExited(MouseEvent e) { }
			
			@Override
			public void mouseEntered(MouseEvent e) { }
			
			@Override
			public void mouseClicked(MouseEvent e) { }
		});
		

		JPanel panel = new JPanel();
		panel.setBounds(12, 70, 377, 51);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblOpesDaPlaylist = new JLabel("Opções da Playlist:");
		lblOpesDaPlaylist.setBounds(120, 0, 136, 25);
		panel.add(lblOpesDaPlaylist);
		
		JButton btnNova = new JButton("Nova");
		btnNova.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//reproductionListModel.clear();
				playlist.clear();
				reproductionTable = new JTable(getMusicTableData(), getMusicTableNames());
			}
		});		
		btnNova.setBounds(251, 25, 125, 25);
		panel.add(btnNova);
		
		JButton btnExportar = new JButton("Exportar");
		btnExportar.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {				
			    JFileChooser fc = new JFileChooser();
			    // Demonstrate "Save" dialog:
			    int rVal = fc.showSaveDialog(null);
			    if (rVal == JFileChooser.APPROVE_OPTION) {
			    	dao = new MusicDAO(fc.getCurrentDirectory().toString()+"/"+fc.getSelectedFile().getName()+".xml");
			    	dao.imprint(playlist);
			    }			    		   			    
			}
		});
		btnExportar.setBounds(125, 25, 131, 25);
		panel.add(btnExportar);
						
		JButton btnImportar = new JButton("Importar");
		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("XML", "xml"));

				int res = fc.showOpenDialog(null);
				if (res == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					if(file != null) {
						dao = new MusicDAO(file.getAbsolutePath());
						playlist = dao.read();		
						loadJPlaylist();
					} else { 
						JOptionPane.showMessageDialog(null, "Erro ao carregar arquivo!");
					}
				}
			}
		});
		btnImportar.setBounds(1, 25, 125, 25);
		panel.add(btnImportar);
		
		
		reproductionTable = new JTable();
		reproductionTable.setModel(new DefaultTableModel(getMusicTableData(), getMusicTableNames()));
		reproductionTable.setBounds(0, 0, 377, 377);
		reproductionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reproductionTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int index = reproductionTable.getSelectedRow();
				if (index >= 0) {
					selected = playlist.get(index);
				}
			}
		});
		
		JScrollPane reproductionScrollPane = new JScrollPane();
		reproductionScrollPane.setBounds(12, 124, 377, 136);
		reproductionScrollPane.setViewportView(reproductionTable);
		this.contentPane.add(reproductionScrollPane);
	};
	
	private String[] getMusicTableNames() {
		return new String[] {"Title", "Author", "Album"};
	}
	
	private String[][] getMusicTableData() {
		String[][] names = new String[playlist.size()][3];
		for(int i = 0; i < playlist.size(); i++) {
			names[i][0] = playlist.get(i).getName();
			names[i][1] = playlist.get(i).getAuthor();
			names[i][2] = playlist.get(i).getAlbum();
		}
		
		return names;
	}
			
	private static int fixIndex(int index) {
		if (index < 0) {
			index = playlist.size()-1;
		} else if (index >= playlist.size()) {
			index = 0;
		}		
		return index;
	}
	
	private static void changeProcedures(int op){
		int index = fixIndex(playlist.indexOf(selected) + op);
		
		//reproductionList.setSelectedIndex(index);
		reproductionTable.setRowSelectionInterval(index, index);
		selected = playlist.get(index);
		updateMusicInfo();
	}
	
	private static void updateMusicInfo() {
		lblInfoMusic.setText(selected.getName() + " - " + selected.getAuthor());
	}
	
	public static void autoChangeMusic(int op){
		changeProcedures(op);
		
		stopProgressBar();
		mPlayer.autoChange(selected);
	}
	
	public static void changeMusic(int op){
		changeProcedures(op);
		
		mPlayer.change(selected);
	}
	
	@SuppressWarnings("deprecation")
	public static void startProgressBar(int value) {
		if (threadProgressBar == null) {
			threadProgressBar = new ThreadProgressBar(value, selected.getDuration(), progressBar);
			threadProgressBar.start();
		} else {
			threadProgressBar.setValue(value);
			threadProgressBar.setMax(selected.getDuration());
			threadProgressBar.resume();
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void pauseProgressBar() {
		threadProgressBar.suspend();
	}
	
	@SuppressWarnings("deprecation")
	public static void resumeProgressBar() {
		threadProgressBar.resume();
	}
	
	@SuppressWarnings("deprecation")
	public static void stopProgressBar() {
		//threadProgressBar.suspend();
		threadProgressBar.stop();
		threadProgressBar = null;
		
	}
				
	private void positionUp() {
		int index = playlist.indexOf(selected);
		
		if (index > 0  && selected != null) {
			playlist.get(index - 1).setPosition(index);
			selected.setPosition(selected.getPosition() - 1);
			Collections.sort(playlist);
			reloadJPlaylist();
			//reproductionList.setSelectedIndex(index-1);
			reproductionTable.setRowSelectionInterval(index-1, index-1);
		}
		
	}

	private void positionDown() {
		int index = playlist.indexOf(selected);
		
		if (index < playlist.size()-1 && selected != null) {
			playlist.get(index + 1).setPosition(index);
			selected.setPosition(selected.getPosition() + 1);
			Collections.sort(playlist);
			reloadJPlaylist();
			//reproductionList.setSelectedIndex(index+1);
			reproductionTable.setRowSelectionInterval(index+1, index+1);
		}
	}
		
	private void loadJPlaylist(){
		reproductionTable.setModel(new DefaultTableModel(getMusicTableData(), getMusicTableNames()));
	}
	
	public void reloadJPlaylist() {
		reproductionTable.setModel(new DefaultTableModel(getMusicTableData(), getMusicTableNames()));
		
		this.dao.imprint(playlist);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new PlayerGUI().setVisible(true);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}