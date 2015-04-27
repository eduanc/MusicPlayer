package com.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.dao.MusicDAO;
import com.dto.MusicDTO;

public class TagEditionGUI extends JFrame{

	private static final long serialVersionUID = -4723971409315247451L;
	private JPanel contentPane;
	private JTextField tfTitle;
	private JTextField tfArtist;
	private JTextField tfAlbum;
		
	public TagEditionGUI(List<MusicDTO> playlist, MusicDTO music) {				
		setBounds(0, 0, 219, 206);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		this.contentPane.setLayout(null);
		
		JLabel lblTagsEdit = new JLabel("Edição de Tags:");
		lblTagsEdit.setBounds(12, 0, 115, 15);
		contentPane.add(lblTagsEdit);
						
		JLabel lblTitle = new JLabel("Título:");
		lblTitle.setBounds(12, 41, 70, 15);
		contentPane.add(lblTitle);		
		
		tfTitle = new JTextField();
		tfTitle.setBounds(73, 39, 114, 19);						
		contentPane.add(tfTitle);
		tfTitle.setColumns(10);
		
		tfTitle.setText(music.getName());
		
		JLabel lblArtista = new JLabel("Artista:");
		lblArtista.setBounds(12, 68, 70, 15);
		contentPane.add(lblArtista);
				
		tfArtist = new JTextField();
		tfArtist.setBounds(73, 66, 114, 19);		
		contentPane.add(tfArtist);
		tfArtist.setColumns(10);
						
		tfArtist.setText(music.getAuthor());
		
		JLabel lblAlbum = new JLabel("Album:");
		lblAlbum.setBounds(12, 95, 70, 15);
		contentPane.add(lblAlbum);
		
		tfAlbum = new JTextField();
		tfAlbum.setBounds(73, 95, 114, 19);			
		contentPane.add(tfAlbum);
		tfAlbum.setColumns(10);
		
		tfAlbum.setText(music.getAlbum());		
		
		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(102, 122, 97, 25);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				closeWindow();
			}
		});
		contentPane.add(btnCancel);
		
		JButton btnSave = new JButton("Salvar");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				music.setAlbum(tfAlbum.getText());
				music.setName(tfTitle.getText());
				music.setAuthor(tfArtist.getText());
				
				new MusicDAO().edit(playlist, music);
				closeWindow();
			}
		});
		btnSave.setBounds(0, 122, 103, 25);
		contentPane.add(btnSave);
		
	}		
	
	private void closeWindow(){
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
}