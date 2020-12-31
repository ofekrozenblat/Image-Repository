package model;

import java.io.File;
import java.util.List;

public class DataRecord {
	
	// Singleton access
	private static DataRecord singleInstance;
	
	// Attributes
	private List<ImageModel> modelList;
	
	private DataRecord() {
		modelList = RepositoryManager.read();
	}
	
	/**
	 * Creates a new DataRecrod object on the first call, subsequent calls return the same reference.
	 * @return DataRecord reference.
	 */
	public static DataRecord DataRecord() {
		if (singleInstance == null) {
			singleInstance = new DataRecord();
		}
		
		return singleInstance;
	}
	
	/**
	 * Creates a new image model.
	 * @param imageFile - The image file to copy in to the repository.
	 * @param imgDescription - The description of the image.
	 * @return the image model created.
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
	
	/**
	 * Deletes the given image model from data.
	 * @param imageModel - Image model to delete.
	 */
	public void delete(ImageModel imageModel) {
		RepositoryManager.delete(imageModel);
		modelList.remove(imageModel);
	}
	
	/**
	 * Retrieves the list of image models in data.
	 * @return List of image models.
	 */
	public List<ImageModel> getImageModelList(){
		return modelList;
	}
}
