package gui;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

import model.DataRecord;
import model.ImageModel;

public class ImagePanelListView extends JPanel{
	
	// Private Views
	private JPanel viewPanel;
	private JScrollPane scrollPane;
	private JLabel labelSearchRepository;
	private JLabel labelDeleteImage;
	
	// Public views
	public JButton buttonDeleteImage;
	public JTextField txtSearchRepository;
	
	// Attributes
	private final int width, height;
	private List<ImageModel> imageModelList; // Keeps track of what image models are being viewed
	
	public ImagePanelListView(int width, int height) {
		this.width = width;
		this.height = height;
		setLayout(null);
		setMaximumSize(new Dimension(width, height));
		
		initializeComponents();
		initializeImageModelList();
	}
	
	private void initializeComponents() {
		viewPanel = new JPanel();
		viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.LINE_AXIS));
		viewPanel.setAlignmentY(TOP_ALIGNMENT);
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(viewPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(0, 0, width, 620);
		scrollPane.setMaximumSize(new Dimension(width, 620));	
		add(scrollPane);
		
		labelSearchRepository = new JLabel("Search Repository:");
		labelSearchRepository.setBounds(10, 650, 300, 25);
		labelSearchRepository.setFont(ViewStandards.labelLargeFont());
		add(labelSearchRepository);
		
		labelDeleteImage = new JLabel("Delete Selected Images:");
		labelDeleteImage.setBounds(820, 650, 300, 25);
		labelDeleteImage.setFont(ViewStandards.labelLargeFont());
		add(labelDeleteImage);
		
		txtSearchRepository = new JTextField();
		txtSearchRepository.setBounds(10, 675, 300, 40);
		txtSearchRepository.setFont(ViewStandards.labelMediumFont());
		add(txtSearchRepository);
		
		buttonDeleteImage = new JButton();
		buttonDeleteImage.setBounds(820, 675, 300, 40);
		buttonDeleteImage.setText("Delete Images");
		buttonDeleteImage.setFont(ViewStandards.labelMediumFont());
		add(buttonDeleteImage);
	}
	
	private void initializeImageModelList() {
		imageModelList = new LinkedList<ImageModel>();
		for (ImageModel imageModel : DataRecord.DataRecord().getImageModelList()) {
			addImageModel(imageModel);
		}
	}
	
	public void addImageModel(ImageModel imgModel) {
		if (!imageModelList.contains(imgModel)) {
			imageModelList.add(imgModel);
		}
		
		// Add space before
		viewPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		// Add component
		viewPanel.add(imgModel.getImagePanelView());
		// Add space after
		viewPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		updateView();
	}
	
	public void removeImageModel(ImageModel imgModel) {
		imageModelList.remove(imgModel);
		// Need to remove everything because rigid areas need to be removed as well (no direct reference to them)
		viewPanel.removeAll();
		
		for (ImageModel imageModel : imageModelList) {
			addImageModel(imageModel);
		}
		
		updateView();
	}
	
	public List<ImageModel> getImageModelList(){
		return imageModelList;
	}
	
	public void clearListView() {
		viewPanel.removeAll();
		imageModelList.clear();
		updateView();
	}
	
	private void updateView() {
		revalidate();
		repaint();
	}
}
