package model;

import java.awt.Image;
import java.io.File;
import java.util.Date;

import gui.ImagePanelView;

public class ImageModel {
	
	private File imageFile;
	private Image image;
	private String imageDescription;
	private ImagePanelView imagePanelView;
	private String dateCreated;
	
	public ImageModel(File imageFile, String imageDescription, String dateCreated) {
		this.imageFile = imageFile;
		this.imageDescription = imageDescription;
		this.dateCreated = dateCreated;
		
		// On creation the image is null (loaded later in application loading, or when being created during a session)
		image = null;
		
		// Create corresponding image panel view
		imagePanelView = new ImagePanelView(this);
	}
	
	public void setImage(Image image) {
		this.image = image;
		// Update the corresponding image panel view
		imagePanelView.updateImage();
	}
	
	public ImagePanelView getImagePanelView(){
		return imagePanelView;
	}
	
	public String getImageDescription() {
		return imageDescription;
	}
	
	public String getImageName() {
		return imageFile.getName();
	}
	
	public String getImageDateCreated() {
		return dateCreated;
	}
	
	public File getImageFile() {
		return imageFile;
	}
	
	public Image getImage() {
		return image;
	}
	
	/**
	 * Checks whether the view of the image model has been selected
	 * @return true if the corresponding image panel has been selected by the user, false otherwise
	 */
	public boolean isSelected() {
		return imagePanelView.isSelected();
	}
}
