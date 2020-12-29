package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class AddImagePanelView extends JPanel {
	
	// Private Views
	private JLabel labelChooseImage;
	private JLabel labelImageView;
	private JLabel labelFileName;
	private JLabel labelDescription;
	private JTextArea textDescription;
	
	// Public Views
	public JButton buttonAddImage;
	public JButton buttonBrowserImage;
	
	// Attributes
	private JFileChooser fc;
	private File imageFile;
	
	public AddImagePanelView() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		// Create a margin of 10 pixels
		setBorder(BorderFactory.createEmptyBorder(0, 10, getPreferredSize().height, getPreferredSize().width));
		
		// Initialize
		initializeComponents();
		initializeVariables();
	}
	
	// Initialize view components of the panel
	private void initializeComponents() {
		labelChooseImage = new JLabel("Choose Image to Add:");
		labelChooseImage.setPreferredSize(new Dimension(300, 25));
		labelChooseImage.setFont(ViewStandards.labelLargeFont());
		labelChooseImage.setAlignmentX(LEFT_ALIGNMENT);
		add(labelChooseImage);
		
		buttonBrowserImage = new JButton("Upload image");
		buttonBrowserImage.setPreferredSize(new Dimension(300, 25));
		buttonBrowserImage.setFont(ViewStandards.labelMediumFont());
		add(buttonBrowserImage);
		
		labelFileName = new JLabel("File name:");
		labelFileName.setPreferredSize(new Dimension(300, 25));
		labelFileName.setFont(ViewStandards.labelMediumFont());
		add(labelFileName);
		
		labelDescription = new JLabel("Image Description:");
		labelDescription.setPreferredSize(new Dimension(300, 25));
		labelDescription.setFont(ViewStandards.labelMediumFont());
		add(labelDescription);
		
		textDescription = new JTextArea();
		textDescription.setLineWrap(true);
		textDescription.setPreferredSize(new Dimension(300, 200));
		textDescription.setMaximumSize(new Dimension(300, 200));
		textDescription.setFont(ViewStandards.labelMediumFont());
		textDescription.setAlignmentX(LEFT_ALIGNMENT);
		add(textDescription);
		
		labelImageView = new JLabel();
		labelImageView.setMaximumSize(new Dimension(300, 300));
		labelImageView.setBorder(BorderFactory.createDashedBorder(null, 5, 5));
		add(labelImageView);
		
		buttonAddImage = new JButton("Add Image");
		buttonAddImage.setPreferredSize(new Dimension(300, 25));
		buttonAddImage.setFont(ViewStandards.labelMediumFont());
		add(buttonAddImage);
	}
	
	private void initializeVariables() {
		fc = new JFileChooser();
		fc.setAcceptAllFileFilterUsed(false); // Don't allow to select any file
		fc.setFileFilter(new FileFilter() { // Only allow files defined by this filter
			
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "Image files";
			}
			
			@Override
			public boolean accept(File f) {
				// TODO Auto-generated method stub
				if (f.isDirectory()) {
					return true;
				}
				
				String fileName = f.getName();
				String regex = "(?i).*(jpg|png|jpeg)$";
				
				if (fileName.matches(regex)) {
					return true;
				}
				
				return false;
			}
		});
	}
	
	public void showOpenDialog() {
		int returnVal = fc.showOpenDialog(this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			imageFile = fc.getSelectedFile();
			labelFileName.setText("File name: " + imageFile.getName());
			showImage(imageFile);
		}
		
	}
	
	public void clearFields() {
		imageFile = null;
		labelImageView.setIcon(null);
		labelFileName.setText("File name:");
		textDescription.setText("");
	}
	
	public File getImageFile() {
		return imageFile;
	}
	
	public String getImageDescription() {
		return textDescription.getText();
	}

	/**
	 * Displays a preview of the image chosen
	 * @param ImageFile - Image file to preview
	 */
	public void showImage(File ImageFile) {
		try {
			Image img = ImageIO.read(ImageFile);
			
			ImageIcon imgIcon = new ImageIcon(img.getScaledInstance(300, 300, Image.SCALE_DEFAULT));
			
			labelImageView.setIcon(imgIcon);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
