package com.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;
import java.util.ListResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;

import com.dao.MusicaDAO;
import com.dto.MusicaDTO;
import com.player.MusicPlayer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PlayerGUI extends JFrame {

	private static final long serialVersionUID = 8487021165177117115L;
	private JPanel contentPane;
	
	private MusicaDAO dao = new MusicaDAO();
	private MusicPlayer mPlayer;
	
	private List<MusicaDTO> playlist;
	private MusicaDTO selected = null;
	
	private JList<String> listReproducao;
	private DefaultListModel<String> listModelReproducao;

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
		
		JButton btnAnterior = new JButton("<");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previous();
			}
		});
		panelBotoesAudio.add(btnAnterior);
		
		JButton btnPlay = new JButton("play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selected == null && playlist.size() > 0) {
					selected = playlist.get(0);
				}
				
				mPlayer.play(selected);
			}
		});
		panelBotoesAudio.add(btnPlay);
		
		JButton btnPause = new JButton("pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mPlayer.pause();
			}
		});
		panelBotoesAudio.add(btnPause);
		
		JButton btnStop = new JButton("stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mPlayer.stop();
			}
		});
		panelBotoesAudio.add(btnStop);
		
		JButton btnProxima = new JButton(">");
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
		panelBotoesLista.setBounds(388, 124, 50, 136);
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
		
		System.out.println(selected);
	}
	
	private void next() {
		int index = corrigeIndex(playlist.indexOf(selected) + 1);
		
		selected = playlist.get(index);
		listReproducao.setSelectedIndex(index);
		
		System.out.println(selected);
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
	}
}
