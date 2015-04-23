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

import com.dao.MusicaDAO;
import com.dto.MusicaDTO;
import com.player.MusicPlayer;
import com.player.PlayerManager;

public class PlayerGUI extends JFrame {
	
	private static final long serialVersionUID = 8487021165177117115L;
	private JPanel contentPane;
	
	private MusicaDAO dao = new MusicaDAO();
	private MusicPlayer mPlayer = new PlayerManager(); 
	
	private List<MusicaDTO> playlist;
	private MusicaDTO selected = null;
	
	private JList<String> listReproducao;
	private DefaultListModel<String> listModelReproducao;
	
	/**
	 * Create the frame.
	 */
	public PlayerGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		playlist = dao.ler();
		
		JLabel lblInformaesMusica = new JLabel("Informações música");
		lblInformaesMusica.setBounds(12, 0, 426, 50);
		lblInformaesMusica.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(lblInformaesMusica);
		
		JPanel panelBotoesAudio = new JPanel();
		panelBotoesAudio.setBounds(12, 62, 426, 50);
		contentPane.add(panelBotoesAudio);
		
		JButton btnAnterior = new JButton("«");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previous();
			}
		});
		panelBotoesAudio.add(btnAnterior);
		
		JButton btnPlay = new JButton(">");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selected == null && playlist.size() > 0) {
					selected = playlist.get(0);
				}
				System.out.println(selected.getArquivo().getAbsolutePath());
				
				mPlayer.play(selected);
			}
		});
		panelBotoesAudio.add(btnPlay);
		
		JButton btnPause = new JButton("||");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mPlayer.pause();
			}
		});
		panelBotoesAudio.add(btnPause);
		
		JButton btnStop = new JButton("[]");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mPlayer.stop();
			}
		});
		panelBotoesAudio.add(btnStop);
		
		JButton btnProxima = new JButton("»");
		btnProxima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});
		panelBotoesAudio.add(btnProxima);
		
		JScrollPane scrollPaneReproducao = new JScrollPane();
		scrollPaneReproducao.setBounds(12, 124, 377, 136);
		contentPane.add(scrollPaneReproducao);
		
		listModelReproducao = new DefaultListModel<String>();
		
		listReproducao = new JList<String>(listModelReproducao);
		scrollPaneReproducao.setViewportView(listReproducao);
		
		JPanel panelBotoesLista = new JPanel();
		panelBotoesLista.setBounds(400, 107, 50, 153);
		contentPane.add(panelBotoesLista);
		
		JButton buttonUp = new JButton("↑");
		buttonUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				posicaoUp();
			}
		});
		panelBotoesLista.add(buttonUp);
		
		JButton buttonDown = new JButton("↓");
		buttonDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				posicaoDown();
			}
		});
		panelBotoesLista.add(buttonDown);
		
		JButton btnEditar = new JButton("e");
		panelBotoesLista.add(btnEditar);
		
		JButton btnExcluir = new JButton("x");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playlist.remove(selected);
				selected=null;
				reloadJPlaylist();
			}
		});
		panelBotoesLista.add(btnExcluir);
		
		JButton btnAdd = new JButton("+");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("Audio", "mp3", "ogg", "wav"));

				int res = fc.showOpenDialog(null);
				if (res == JFileChooser.APPROVE_OPTION) {
					File arquivo = fc.getSelectedFile();
					if(arquivo != null) {
						playlist.add(new MusicaDTO(arquivo, playlist.size()));
						reloadJPlaylist();
					} else { 
						JOptionPane.showMessageDialog(null, "Erro ao carregar arquivo!");
					}
				}
			}
		});
		panelBotoesLista.add(btnAdd);
		
		for (MusicaDTO musica : playlist) {
			listModelReproducao.addElement(musica.getNome());
		}
		
		listReproducao.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = listReproducao.locationToIndex(e.getPoint());
				if (index >= 0) {
					selected = playlist.get(index);
				}
			}
		});
	}
	
	private int corrigeIndex(int index) {
		if (index < 0) {
			index = playlist.size()-1;
		} else if (index >= playlist.size()) {
			index = 0;
		}
		
		return index;
	}
	
	private void previous() {
		int index = corrigeIndex(playlist.indexOf(selected) - 1);
		
		selected = playlist.get(index);
		listReproducao.setSelectedIndex(index);
	}
	
	private void next() {
		int index = corrigeIndex(playlist.indexOf(selected) + 1);
		
		selected = playlist.get(index);
		listReproducao.setSelectedIndex(index);
	}
	
	private void posicaoUp() {
		int index = playlist.indexOf(selected);
		
		if (index > 0) {
			playlist.get(index - 1).setPosicao(index);
			selected.setPosicao(selected.getPosicao() - 1);
			Collections.sort(playlist);
			reloadJPlaylist();
			listReproducao.setSelectedIndex(index-1);
		}
		
	}

	private void posicaoDown() {
		int index = playlist.indexOf(selected);
		
		if (index < playlist.size()-1) {
			playlist.get(index + 1).setPosicao(index);
			selected.setPosicao(selected.getPosicao() + 1);
			Collections.sort(playlist);
			reloadJPlaylist();
			listReproducao.setSelectedIndex(index+1);
		}
	}
	
	public void reloadJPlaylist() {
		// !TODO refazer mais bonito
		listModelReproducao.clear();
		for (MusicaDTO musica : playlist) {
			listModelReproducao.addElement(musica.getNome());
		}
		
		dao.gravar(playlist);
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