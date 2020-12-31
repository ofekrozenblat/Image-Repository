package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import gui.*;

public class GraphicUserManager {
	
	// Graphic views
	private ImagePanelListView imagePanelListView;
	private AddImagePanelView addImagePanelView;
	
	// Data communication
	private DataRecord dataRecord;
	
	public GraphicUserManager(ImagePanelListView imagePanelListView, AddImagePanelView addImagePanelView) {
		this.imagePanelListView = imagePanelListView;
		this.addImagePanelView = addImagePanelView;
		
		dataRecord = DataRecord.DataRecord();
		
		createEvents();
	}
	
	private void createEvents() {
		createAddImagePanelViewEvents();
		createImagePanelListViewEvents();
	}
	
	private void createAddImagePanelViewEvents() {
		addImagePanelView.buttonAddImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addImage(addImagePanelView.getImageFile(), addImagePanelView.getImageDescription());
			}
		});
		
		addImagePanelView.buttonBrowserImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addImagePanelView.showOpenDialog();
			}
		});
	}
	
	private void createImagePanelListViewEvents() {
		imagePanelListView.buttonDeleteImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Keeps track of which image models to remove (only selected ones)
				// (Avoid Concurrent modification as can not remove the image models
				// from the panel list during the first for loop)
				List<ImageModel> imageModelsToRemove = new LinkedList<ImageModel>();
				for (ImageModel imageModel : imagePanelListView.getImageModelList()) {
					if (imageModel.isSelected()) {
						imageModelsToRemove.add(imageModel);
					}
				}
				
				// Informative message to user if attempts to delete with no images selected
				if (imageModelsToRemove.size() == 0) {
					JOptionPane.showMessageDialog(addImagePanelView, "No images selected for deletion", 
							"Image Repository", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				// Confirm the user wants to delete the images
				String message;
				if (imageModelsToRemove.size() == 1) {
					message = "Are you sure you want to delete the following image:\n";
				}else {
					message = "Are you sure you want to delete the following " + 
							imageModelsToRemove.size() + " images:\n";
				}
				
				for (ImageModel imageModel : imageModelsToRemove) {
					message += imageModel.getImageName() + "\n";
				}
				
				int returnVal = JOptionPane.showConfirmDialog(addImagePanelView, message, 
							"Image Repository", JOptionPane.YES_NO_OPTION);
				
				if (returnVal == JOptionPane.OK_OPTION) {
					for (ImageModel imageModel : imageModelsToRemove) {
						imagePanelListView.removeImageModel(imageModel);
						dataRecord.delete(imageModel);
					}
				}else {
					// Unselect the images if user does not want to delete
					for (ImageModel imageModel : imageModelsToRemove) {
						imageModel.setSelectedFalse();
					}
				}
				
			}
		});
		
		imagePanelListView.txtSearchRepository.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				searchImage(imagePanelListView.txtSearchRepository.getText());
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				searchImage(imagePanelListView.txtSearchRepository.getText());
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				
			}
		});
		
	}
	
	private void addImage(File imageFile, String imgDescription) {
		// Check whether the there is no image file selected
		if (imageFile == null) {
			JOptionPane.showMessageDialog(addImagePanelView, "No image file uploaded", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// Check that the image file name doesn't already exist
		for (ImageModel imageModel : dataRecord.getImageModelList()) {
			if (imageModel.getImageName().equals(imageFile.getName())) {
				JOptionPane.showMessageDialog(addImagePanelView, "Image file already exists", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		imagePanelListView.addImageModel(dataRecord.createNewImageModel(imageFile, imgDescription));
		addImagePanelView.clearFields();
		JOptionPane.showMessageDialog(addImagePanelView, "Image file succesfully added", "Image Repository", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void searchImage(String fileName) {
		List<ImageModel> imageModelDataList = dataRecord.getImageModelList();
		List<ImageModel> imageModelsToShow = new LinkedList<ImageModel>();
		
		for (ImageModel imageModel : imageModelDataList) {
			if (imageModel.getImageName().toLowerCase().contains(fileName.toLowerCase())) {
				imageModelsToShow.add(imageModel);
			}
		}
		
		imagePanelListView.clearListView();
		
		for (ImageModel imageModel : imageModelsToShow) {
			imagePanelListView.addImageModel(imageModel);
		}
	}
}
