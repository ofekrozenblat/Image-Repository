package gui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import model.ImageModel;

public class ImagePanelView extends JPanel{
	
	// Size (fixed)
	private static final int WIDTH = 500, HEIGHT = 600;
	private static final int IMAGE_WDITH = WIDTH, IMAGE_HEIGHT = HEIGHT/2;
	
	// Views
	private JLabel labelImage;
	private JLabel labelImageName;
	private JLabel labelImageDescription;
	private JTextArea textAreaImageDescription;
	private JLabel labelDateUploaded;
	private JScrollPane scrollPaneTextDescription;
	
	// Attributes
	private final ImageModel imageModel;
	
	// Mouse Listener
	private MouseListener mouseListener;
	private boolean selected; // True if current panel was clicked on (false otherwise)
	
	public ImagePanelView(ImageModel imageModel) {
		this.imageModel = imageModel;
		
		setAlignmentY(TOP_ALIGNMENT);
		setBounds(0, 0, WIDTH, HEIGHT);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.LIGHT_GRAY);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// Create on click event
		createClickEvent();
		
		// Add components to the panel
		initializeComponents();
	}
	
	private void initializeComponents() {
		add(Box.createRigidArea(new Dimension(0, 1)));
		
		labelImage = new JLabel();
		labelImage.setMaximumSize(new Dimension(IMAGE_WDITH, IMAGE_HEIGHT));
		labelImage.setIcon(null); // Initially empty until image is loaded in application loading
		labelImage.setAlignmentX(LEFT_ALIGNMENT);
		add(labelImage);
		
		labelImageName = new JLabel("Name: " + imageModel.getImageName());
		labelImageName.setFont(ViewStandards.labelSmallFont());
		labelImageName.setAlignmentX(LEFT_ALIGNMENT);
		add(labelImageName);
		
		labelDateUploaded = new JLabel("Date Uploaded: " + imageModel.getImageDateCreated());
		labelDateUploaded.setFont(ViewStandards.labelSmallFont());
		labelDateUploaded.setAlignmentX(LEFT_ALIGNMENT);
		add(labelDateUploaded);
		
		labelImageDescription = new JLabel("Description:");
		labelImageDescription.setFont(ViewStandards.labelSmallFont());
		labelImageDescription.setAlignmentX(LEFT_ALIGNMENT);
		add(labelImageDescription);
		
		scrollPaneTextDescription = new JScrollPane();
		scrollPaneTextDescription.setBorder(BorderFactory.createEmptyBorder());
		scrollPaneTextDescription.setOpaque(false);
		scrollPaneTextDescription.setAlignmentX(LEFT_ALIGNMENT);
		scrollPaneTextDescription.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		textAreaImageDescription = new JTextArea(imageModel.getImageDescription());
		textAreaImageDescription.setFont(ViewStandards.labelSmallFont());
		textAreaImageDescription.setLineWrap(true);
		textAreaImageDescription.setOpaque(false);
		textAreaImageDescription.setEditable(false);
		textAreaImageDescription.setWrapStyleWord(true);
		textAreaImageDescription.setForeground(new Color(51, 51, 51));
		textAreaImageDescription.setAlignmentX(LEFT_ALIGNMENT);
		textAreaImageDescription.addMouseListener(mouseListener);
		scrollPaneTextDescription.setViewportView(textAreaImageDescription);
		scrollPaneTextDescription.getViewport().setOpaque(false);
		add(scrollPaneTextDescription);
	}
	
	/**
	 * Override `add` method to make sure all
	 * components added to this panel add the
	 * corresponding mouse listener
	 */
	@Override
	public Component add(Component comp) {
		comp.addMouseListener(mouseListener);
		return super.add(comp);
	}
	
	/**
	 * Checks whether the view has been selected
	 * @return true if the panel has been selected by the user, false otherwise
	 */
	public boolean isSelected() {
		return selected;
	}
	
	public void updateImage() {
		Image img = imageModel.getImage();
		
		if (img != null) {
			img = img.getScaledInstance(IMAGE_WDITH, IMAGE_HEIGHT, Image.SCALE_DEFAULT);
			labelImage.setIcon(new ImageIcon(img));
		}
	}
	
	public void toggleSelected() {
		selected = !selected;
		if (selected) {
			setBorder(BorderFactory.createLineBorder(Color.BLUE, 4));
		}else {
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
	}
	
	private void createClickEvent() {
		mouseListener = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				toggleSelected();
			}
		};
		
		addMouseListener(mouseListener);
	}
	
}
