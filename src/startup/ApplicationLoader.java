package startup;

import java.awt.Image;
import gui.*;
import model.*;

public class ApplicationLoader {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new ApplicationLoader();
	}
	
	/**
	 * Starts up the application, loading any necessary resources.
	 */
	public ApplicationLoader() {
		ProgressBarView progressBarView = new ProgressBarView(2);
		
		// Initialize classes
		progressBarView.setStatus("Loading data");
		RepositoryManager.checkDataFile();
		DataRecord.DataRecord(); // Create first (singleton) instance
		progressBarView.changeIntervals(DataRecord.DataRecord().getImageModelList().size() + 2);
		
		progressBarView.setStatus("Loading images");
		loadImages(progressBarView);
		
		// Initialize GUI
		progressBarView.setStatus("Loading GUI");
		ApplicationWindow window = new ApplicationWindow();
		ImagePanelListView imagePanelListView = window.getImagePanelListView();
		AddImagePanelView addImagePanelView = window.getAddImagePanelView();
		
		GraphicUserManager guiManager = new GraphicUserManager(imagePanelListView, addImagePanelView);
		progressBarView.finish();
		
		// Show ApplicationWindow
		window.frame.setVisible(true);
	}
	
	/**
	 * Loads the images on application start up.
	 * @param progressBarView - Progress bar to show progress.
	 */
	private void loadImages(ProgressBarView progressBarView) {
		for (ImageModel imageModel : DataRecord.DataRecord().getImageModelList()) {
			Image img = RepositoryManager.loadImage(imageModel.getImageFile());
			imageModel.setImage(img);
			progressBarView.progress();
		}
	}
}
