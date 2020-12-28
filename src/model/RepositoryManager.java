package model;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public class RepositoryManager {
	
	private static final File DATA_FILE = new File("Repository/Data.txt");
	
	// Delimiters
	private static final String DELIMITER = "\\----------------\\"; // Separates whole sections
	private static final String INTERNAL_DELIMITER = "<p>"; // Separates parts of a single section
	
	/**
	 * Writes the given image model reference to the Data file
	 * @param imageModel  - Image model to write
	 */
	public static void write(ImageModel imageModel) {
		try {
			FileWriter fileWriter = new FileWriter(DATA_FILE, true);
			fileWriter.write(imageModel.getImageName() + "\n");
			fileWriter.write(INTERNAL_DELIMITER + "\n");
			fileWriter.write(imageModel.getImageDateCreated() + "\n");
			fileWriter.write(INTERNAL_DELIMITER + "\n");
			fileWriter.write(imageModel.getImageDescription() + "\n");
			fileWriter.write(INTERNAL_DELIMITER + "\n");
			fileWriter.write(DELIMITER + "\n");
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Deletes the given image model reference from the Data file
	 * @param imageModel - Image model to delete
	 */
	public static void delete(ImageModel imageModel) {
			List<String> data = new LinkedList<String>();
			
			try {
				data = Files.readAllLines(DATA_FILE.toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Go through the data (Each position in data corresponds to a single line in data file)
			for(int i = 0; i < data.size(); i++) {
				// Search for the same image file name
				if (data.get(i).equals(imageModel.getImageName())) {
					// Delete everything from the image file name until the next delimiter 
					while (data.size() >= 1 && !data.get(i).equals(DELIMITER)) {
						data.remove(i);
					}
					
					// delete delimiter as well
					if (data.get(i).equals(DELIMITER))
						data.remove(i);
					
				}
			}
			
			try {
				// Clear the data file
				clear();
				FileWriter fileWriter = new FileWriter(DATA_FILE, true);
				for (String str : data) {
					fileWriter.write(str + "\n");
				}
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		// Finally, delete the corresponding image file from the repository
		deleteImageFile(imageModel.getImageFile());
	}
	
	/**
	 * Reads the data file and creates image models
	 * @return List of ImageModels created from the Data file
	 */
	public static List<ImageModel> read(){
		List<String> data = new LinkedList<String>();
		List<ImageModel> imageModelList = new LinkedList<ImageModel>();
		
		try {
			data = Files.readAllLines(DATA_FILE.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Start of section
		List<String> sectionData = new LinkedList<String>();
		int lastDelimiterIndex = -1; // Indicates index of the last delimiter seen 
									//(starts at -1 so any data at index 0 is not skipped)
		
		for (int i = 0; i < data.size(); i++) {
			// Search for delimiters (separate sections)
			if (data.get(i).equals(DELIMITER)) {
				// Add everything from last delimiter seen (exclusive) up to the new position of seen delimiter (exclusive)
				for (int j = lastDelimiterIndex+1; j < i; j++) {
					sectionData.add(data.get(j)); // Retrieve everything in the section
				}
				lastDelimiterIndex = i;
				imageModelList.add(retreiveImageModel(sectionData)); // Create image model from section data
				sectionData.clear();
			}
		}
		
		return imageModelList;
	}
	
	/**
	 * Copies the given image file to repository and returns copied file
	 * @param imageFile - The image file to copy
	 * @return copied file in repository
	 */
	public static File copyImageFileToRepository(File imageFileToCopy) {
		String targetLocation = "Repository/" + imageFileToCopy.getName();
		Path targetPath = Paths.get(targetLocation);
		
		try {
			Files.copy(imageFileToCopy.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new File(targetLocation);
	}
	
	/**
	 * Loads image file from repository
	 * @param imageFile - Image file to load
	 * @return image that was loaded
	 */
	public static Image loadImage(File imageFile) {
		Image image = null;
		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}
	
	/**
	 * Deletes the image file from the repository
	 * @param imageFileToDelete - The image file to delete
	 */
	private static void deleteImageFile(File imageFileToDelete) {
		imageFileToDelete.delete();
	}
	
	private static ImageModel retreiveImageModel(List<String> sectionData) {
		String fileName = new String();
		String imageDescription = new String();
		String imageDateCreated = new String();
		
		int numOfDelimitersSeen = 0;
		int lastDelimiterIndex = -1; // Indicates index of the last delimiter seen
									// Note: Need to start at -1 since at index 0 data may exist and may be skipped
									// if I assume that lastDelimiterIndex starts at 0
		
		for (int i = 0; i < sectionData.size(); i++) {
			if (sectionData.get(i).equals(INTERNAL_DELIMITER)) {
				numOfDelimitersSeen++;
				
				for (int j = lastDelimiterIndex+1; j < i; j++) {
					if (numOfDelimitersSeen == 1) {
						fileName += sectionData.get(j);
					
					}else if (numOfDelimitersSeen == 2) {
						imageDateCreated += sectionData.get(j);
						
					}else if(numOfDelimitersSeen == 3) {
						// Note for description: Each position in the list refers to a new line
						imageDescription += sectionData.get(j) + "\n"; 
					}
				}
				lastDelimiterIndex = i;
			}
		}
		File imageFile = new File("Repository/" + fileName);
		
		return new ImageModel(imageFile, imageDescription, imageDateCreated);
	}
	
	private static void clear() {
		// Clear the data file
		try {
			// Set appending to false
			FileWriter fileWriter = new FileWriter(DATA_FILE, false);
			fileWriter.write("");
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}