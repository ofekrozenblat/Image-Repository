package gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;


public class ProgressBarView extends JFrame {
	
	// Views
	private JProgressBar progressBar;
	private JLabel labelStatus;
	
	// Attributes
	private static final int WINDOW_WIDTH = 400, WINDOW_HEIGHT = 100;
	private final int maximumValue = 100;
	private final int minimumValue = 0;
	private int intervals;
	
	public ProgressBarView(int intervals) {
		this.intervals = intervals;
		
		setResizable(false);
		setTitle("Image Repository Loading...");
		setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		getContentPane().setMaximumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		setLocationRelativeTo(null);
		
		labelStatus = new JLabel("Loading");
		labelStatus.setFont(ViewStandards.labelSmallBoldFont());
		getContentPane().add(labelStatus);
		
		progressBar = new JProgressBar(minimumValue, maximumValue);
		progressBar.setMaximumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT/10));
		progressBar.setPreferredSize(progressBar.getMaximumSize());
		progressBar.setAlignmentX(LEFT_ALIGNMENT);
		getContentPane().add(progressBar);
		
		setVisible(true);
	}
	
	/**
	 * Change the number of intervals.
	 * @param intervals - Number of intervals.
	 */
	public void changeIntervals(int intervals) {
		this.intervals = intervals;
	}
	
	/**
	 * Sets the status of the progress bar
	 * @param status - String to show as status
	 */
	public void setStatus(String status) {
		labelStatus.setText(status);
	}
	
	/**
	 * Progresses the progress bar by (maximum value/number of intervals). By default maximum value is fixed to 100.
	 */
	public void progress() {
		progressBar.setValue(progressBar.getValue() + maximumValue/intervals);
	}
	
	/**
	 * Sets the progress bar value to maximum value and hides the progress bar.
	 */
	public void finish() {
		progressBar.setValue(maximumValue);
		setStatus("Finished loading");
		setVisible(false);
	}
	
}
