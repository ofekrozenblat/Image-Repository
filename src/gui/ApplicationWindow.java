package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.CardLayout;
import java.awt.Dimension;

public class ApplicationWindow {

	// Application window frame
	public JFrame frame;
	public static final int WINDOW_WIDTH = 1220, WINDOW_HEIGHT = 800;
	
	// Views
	private ImagePanelListView imagePanelListView;
	private AddImagePanelView addImagePanelView;
	private JMenuItem menuImageAdd;
	private JMenuItem menuViewRepository;
	

	/**
	 * Create the application.
	 */
	public ApplicationWindow() {
		initializeWindow();
		createEvents();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeWindow() {
		frame = new JFrame();
		frame.setTitle("Image Repository");
		frame.setResizable(false);
		frame.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		frame.getContentPane().setEnabled(false);
		frame.getContentPane().setMaximumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		frame.setLocationRelativeTo(null);
		
		// ------------- Create Menu Bar
		JMenuBar menuBar = new JMenuBar();
		
		JMenu mnItem = new JMenu("Manage Repository");
		menuBar.add(mnItem);
		
		menuImageAdd = new JMenuItem("Add Image");
		mnItem.add(menuImageAdd);
		
		menuViewRepository = new JMenuItem("View Repository");
		mnItem.add(menuViewRepository);
		
		frame.setJMenuBar(menuBar);
		// -----------------------------
		
		// ------------- Create Image View Panel
		imagePanelListView = new ImagePanelListView(WINDOW_WIDTH, WINDOW_HEIGHT);
		// -----------------------------
		
		// ------------- Create Add Image Panel
		addImagePanelView = new AddImagePanelView();
		// -----------------------------
		
		frame.getContentPane().add(imagePanelListView);
		frame.getContentPane().add(addImagePanelView);
		imagePanelListView.setVisible(true);
		addImagePanelView.setVisible(false);
	}
	
	public ImagePanelListView getImagePanelListView() {
		return imagePanelListView;
	}
	
	public AddImagePanelView getAddImagePanelView() {
		return addImagePanelView;
	}
	
	private void createEvents() {
		menuImageAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imagePanelListView.setVisible(false);
				addImagePanelView.setVisible(true);
			}
		});
		
		menuViewRepository.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imagePanelListView.setVisible(true);
				addImagePanelView.setVisible(false);
			}
		});
	}
}
