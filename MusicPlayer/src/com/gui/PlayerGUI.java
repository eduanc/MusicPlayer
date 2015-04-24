package com.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.dao.MusicDAO;
import com.dto.MusicDTO;
import com.player.MusicPlayer;
import com.player.PlayerManager;

public class PlayerGUI extends JFrame {
	
	private static final long serialVersionUID = 8487021165177117115L;
	private JPanel contentPane;
	
	private MusicDAO dao = new MusicDAO();
	private MusicPlayer mPlayer = new PlayerManager(); 
	
	private List<MusicDTO> playlist;
	private MusicDTO selected = null;
	
	private JList<String> reproductionList;
	private DefaultListModel<String> reproductionListModel;
	
	/**
	 * Create the frame.
	 */
	public PlayerGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 450, 300);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		this.contentPane.setLayout(null);
		
		this.playlist = this.dao.read();
		
		JLabel lblInfoMusic = new JLabel("Informações música");
		lblInfoMusic.setBounds(12, 0, 426, 50);
		lblInfoMusic.setHorizontalAlignment(JLabel.CENTER);
		this.contentPane.add(lblInfoMusic);
		
		JPanel audioButtonsPanel = new JPanel();
		audioButtonsPanel.setBounds(12, 62, 426, 50);
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
				if (selected == null && playlist.size() > 0) {
					selected = playlist.get(0);
					reproductionList.setSelectedIndex(0);
				}				
				
				mPlayer.play(0, selected);
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
		
		JScrollPane reproductionScrollPane = new JScrollPane();
		reproductionScrollPane.setBounds(12, 124, 377, 136);
		this.contentPane.add(reproductionScrollPane);
		
		this.reproductionListModel = new DefaultListModel<String>();
		
		this.reproductionList = new JList<String>(this.reproductionListModel);
		reproductionScrollPane.setViewportView(this.reproductionList);
		
		JPanel listButtonsPanel = new JPanel();
		listButtonsPanel.setBounds(400, 107, 50, 153);
		this.contentPane.add(listButtonsPanel);
		
		JButton buttonUp = new JButton("↑");
		buttonUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				positionUp();
			}
		});
		listButtonsPanel.add(buttonUp);
		
		JButton buttonDown = new JButton("↓");
		buttonDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				positionDown();
			}
		});
		listButtonsPanel.add(buttonDown);
		
		JButton btnEdit = new JButton("e");
		listButtonsPanel.add(btnEdit);
		
		JButton btnExclude = new JButton("x");
		btnExclude.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playlist.remove(selected);
				selected=null;
				reloadJPlaylist();
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
		
		for (MusicDTO music : this.playlist) {
			reproductionListModel.addElement(music.getName());
		}
		
		reproductionList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = reproductionList.locationToIndex(e.getPoint());
				if (index >= 0) {
					selected = playlist.get(index);
				}
			}
		});
	}
	
	private int fixIndex(int index) {
		if (index < 0) {
			index = playlist.size()-1;
		} else if (index >= playlist.size()) {
			index = 0;
		}		
		return index;
	}
	
	
	public void changeMusic(int op){
		int index = fixIndex(this.playlist.indexOf(this.selected) + op);		
								
		//TODO: fazer a linha de baixo funcionar
		this.reproductionList.setSelectedIndex(index);			
		this.selected = this.playlist.get(index);		
		
		mPlayer.change(selected);
	}
			
	private void positionUp() {
		int index = this.playlist.indexOf(this.selected);
		
		if (index > 0) {
			this.playlist.get(index - 1).setPosition(index);
			this.selected.setPosition(this.selected.getPosition() - 1);
			Collections.sort(this.playlist);
			reloadJPlaylist();
			this.reproductionList.setSelectedIndex(index-1);
		}
		
	}

	private void positionDown() {
		int index = this.playlist.indexOf(this.selected);
		
		if (index < this.playlist.size()-1) {
			this.playlist.get(index + 1).setPosition(index);
			this.selected.setPosition(this.selected.getPosition() + 1);
			Collections.sort(this.playlist);
			reloadJPlaylist();
			this.reproductionList.setSelectedIndex(index+1);
		}
	}
	
	public void reloadJPlaylist() {
		// !TODO refazer mais bonito
		this.reproductionListModel.clear();
		for (MusicDTO music : this.playlist) {
			this.reproductionListModel.addElement(music.getName());
		}
		
		this.dao.imprint(this.playlist);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerGUI frame = new PlayerGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}