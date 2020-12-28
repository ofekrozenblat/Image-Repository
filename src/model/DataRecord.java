package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public class DataRecord {
	
	// Singleton access
	private static DataRecord singleInstance;
	
	// Attributes
	private List<ImageModel> modelList;
	
	private DataRecord() {
		modelList = RepositoryManager.read();
	}
	
	public static DataRecord DataRecord() {
		if (singleInstance == null) {
			singleInstance = new DataRecord();
		}
		
		return singleInstance;
	}
	
	/**
	 * Creates a new image model
	 * @param imageFile - The image file to copy in to the repository
	 * @param imgDescription - The description of the image
	 * @return the image model created
	 */
	public ImageModel createNewImageModel(File imageFile, String imgDescription) {
		File copiedImageFile = RepositoryManager.copyImageFileToRepository(imageFile);
		String dateCreated = CalendarManager.getDate();
		
		
		ImageModel imageModel = new ImageModel(copiedImageFile, imgDescription, dateCreated);
		imageModel.setImage(RepositoryManager.loadImage(imageModel.getImageFile()));
		RepositoryManager.write(imageModel);
		modelList.add(imageModel);
		return imageModel;
	}
	
	public void delete(ImageModel imageModel) {
		RepositoryManager.delete(imageModel);
		modelList.remove(imageModel);
	}
	
	public List<ImageModel> getImageModelList(){
		return modelList;
	}
}
