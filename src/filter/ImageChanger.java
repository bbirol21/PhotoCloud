package filter;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import exceptions.FilterOutOfBondsException;
import logging.BaseLogger;
import simulation.DiscoverPage;
import simulation.EditPage;
import simulation.ProfilePage;
import simulation.SearchPage;

import javax.swing.*;
import java.awt.*;

import users.Hobbyist;
import users.Professional;
import users.User;
/**
 * 
 * @author student
 *
 */
public class ImageChanger implements ActionListener {
	
	private Photo photo;
	private FilterPreference filterPreference;
	private User user;
	private ImageMatrix finalImage;
	
	private static JFrame frame;
	private static JPanel panel;
	private static JButton search, discover, filter, apply, save, revert;
	private static JLabel infoLabel, infolabel2, photoLabel, filterAmountLabel, message;
	private static JTextField filterAmountField;
	/**
	 * 
	 * @param us
	 * @param p
	 * @param fp
	 */
	public ImageChanger(User us, Photo p, FilterPreference fp) {
		this.photo = p;
		this.filterPreference = fp;
		this.user = us;
		
		frame = new JFrame();
		panel = new JPanel();
		
		frame.setSize(350, 570);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(panel);
		panel.setLayout(null);
		
		filter = new JButton("Filter P.");
		filter.setBounds(10, 20, 80, 25);
		filter.addActionListener(this);
		panel.add(filter);
		
		search = new JButton("Search");
		search.setBounds(120, 20, 80, 25);
		search.addActionListener(this);
		panel.add(search);
		
		discover = new JButton("Discover");
		discover.setBounds(230, 20, 80, 25);
		discover.addActionListener(this);
		panel.add(discover);
		
        finalImage = Photo.getResizedImage(p.getImageMatrix(), 300, 300);
		
		photoLabel = new JLabel(new ImageIcon(finalImage.getBufferedImage()));
		photoLabel.setBounds(20, 50, 300, 300);
		panel.add(photoLabel);
		
		filterAmountLabel = new JLabel("Filter Level scale of 1 to 10: ");
		filterAmountLabel.setBounds(20, 360, 300, 25);
		panel.add(filterAmountLabel);
		
		filterAmountField = new JTextField();
		filterAmountField.setBounds(200, 360, 40, 25);
		panel.add(filterAmountField);
		
		message = new JLabel("");
		message.setBounds(20, 390, 300, 25);
		panel.add(message);
		
		apply = new JButton("Apply Filter!");
		apply.setBounds(20, 420, 300, 25);
		apply.addActionListener(this);
		panel.add(apply);
		
		revert = new JButton("Revert the Photo");
		revert.setBounds(20, 450, 300, 25);
		revert.addActionListener(this);
		panel.add(revert);
		
		save = new JButton("Save Changes!");
		save.setBounds(20, 480, 300, 25);
		save.addActionListener(this);
		panel.add(save);
		
		frame.setVisible(true);
		
	}
	
	
	
	
	/**
	 * For resizing the image and making it the size that is desired for the program with the height and width parameters
	 * @param originalImage
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static ImageMatrix resizeImage(ImageMatrix originalImage, int newWidth, int newHeight) {
	    BufferedImage bufferedImage = originalImage.getBufferedImage();
	    Image scaledImage = bufferedImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
	    BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2d = resizedImage.createGraphics();
	    g2d.drawImage(scaledImage, 0, 0, null);
	    g2d.dispose();
	    return new ImageMatrix(resizedImage);
	    
	    //This method takes the original image (ImageMatrix instance), the desired new width, and the desired new height as parameters.
	}
	
	
	
	/**
	 * For applying the blur filter for the images
	 * It does by 
	 * @param image
	 * @param doublewindowSize
	 * @return
	 */
	
	public static ImageMatrix applyBlurFilter(ImageMatrix image, double doublewindowSize) {
		int windowSize = (int) doublewindowSize;
		windowSize *= 2;
	    int width = image.getWidth();
	    int height = image.getHeight();
	    
	    ImageMatrix blurredImage = new ImageMatrix(width, height);
	    
	    // Apply blur filter by averaging neighboring pixels
	    for (int i = 0; i < width; i++) {
	        for (int j = 0; j < height; j++) {
	            int avgPixel = calculateAveragePixel(image, i, j, windowSize);
	            blurredImage.setPixel(i, j, avgPixel);
	        }
	    }
	    
	    return blurredImage;
	}
	
	/**
	 * 
	 * @param image
	 * @param x
	 * @param y
	 * @param windowSize
	 * @return
	 */
	private static int calculateAveragePixel(ImageMatrix image, int x, int y, int windowSize) {
		// Initialize variables to keep track of sum and count
		int sumRed = 0;
	    int sumGreen = 0;
	    int sumBlue = 0;
	    int count = 0;

	    // Calculate the half-window size
	    int halfWindow = windowSize / 2;

	    // Iterate through the pixels within the window
	    for (int i = x - halfWindow; i <= x + halfWindow; i++) {
	        for (int j = y - halfWindow; j <= y + halfWindow; j++) {
	            if (ImageMatrix.isPixelValid(image, i, j)) {
	                int pixel = image.getPixel(i, j);
	                sumRed += image.getRed(pixel);
	                sumGreen += image.getGreen(pixel);
	                sumBlue += image.getBlue(pixel);
	                count++;
	            }
	         // Increment the count of valid pixels
	        }
	    }
	    // Calculate the average 
	    int avgRed = sumRed / count;
	    int avgGreen = sumGreen / count;
	    int avgBlue = sumBlue / count;

	    // Create and return the average pixel value
	    return ImageMatrix.convertRGB(avgRed, avgGreen, avgBlue);
	}


	
	/**
	 * For sharpening filter adding and subtracting image methods were used
	 * @param image
	 * @param sharpenLevel
	 * @return
	 */
	
	public static ImageMatrix applySharpenFilter(ImageMatrix image, double sharpenLevel) {
	    // Step 1: Blur the image
	    ImageMatrix blurredImage = applyBlurFilter(image, 5);

	    // Step 2: Calculate the details by subtracting the blurred image from the original image
	    ImageMatrix detailsImage = subtractImages(image, blurredImage);

	    // Step 3: Add the details to the original image to make it sharp
	    ImageMatrix sharpenedImage = addImages(image, detailsImage, sharpenLevel);

	    return sharpenedImage;
	}

	private static ImageMatrix subtractImages(ImageMatrix image1, ImageMatrix image2) {
	    int width = image1.getWidth();
	    int height = image1.getHeight();
	    ImageMatrix subtractedImage = new ImageMatrix(width, height);

	    // Iterate through each pixel
	    for (int x = 0; x < width; x++) {
	        for (int y = 0; y < height; y++) {
	            // Retrieve pixel values of each image
	            int pixel1 = image1.getPixel(x, y);
	            int pixel2 = image2.getPixel(x, y);

	            // Extract color components of each pixel
	            int red1 = image1.getRed(pixel1);
	            int green1 = image1.getGreen(pixel1);
	            int blue1 = image1.getBlue(pixel1);

	            int red2 = image2.getRed(pixel2);
	            int green2 = image2.getGreen(pixel2);
	            int blue2 = image2.getBlue(pixel2);

	            // Calculate the differences and subtract the values
	            int redDiff = Math.abs(red1 - red2);
	            int greenDiff = Math.abs(green1 - green2);
	            int blueDiff = Math.abs(blue1 - blue2);

	            // Calculate the new pixel value for the subtracted image
	            int subtractedPixel = ImageMatrix.convertRGB(
	                redDiff,
	                greenDiff,
	                blueDiff
	            );

	            subtractedImage.setPixel(x, y, subtractedPixel);
	        }
	    }

	    return subtractedImage;
	}
	
	
	/**
	 * 
	 * @param image1
	 * @param image2
	 * @param sharpenLevel
	 * @return
	 */
	private static ImageMatrix addImages(ImageMatrix image1, ImageMatrix image2, double sharpenLevel) {
	    int width = image1.getWidth();
	    int height = image1.getHeight();
	    ImageMatrix addedImage = new ImageMatrix(width, height);

	    // Iterate through each pixel
	    for (int x = 0; x < width; x++) {
	        for (int y = 0; y < height; y++) {
	            // Retrieve pixel values of each image
	            int pixel1 = image1.getPixel(x, y);
	            int pixel2 = image2.getPixel(x, y);

	            // Extract color components of each pixel
	            int red1 = image1.getRed(pixel1);
	            int green1 = image1.getGreen(pixel1);
	            int blue1 = image1.getBlue(pixel1);

	            int red2 = image2.getRed(pixel2);
	            int green2 = image2.getGreen(pixel2);
	            int blue2 = image2.getBlue(pixel2);

	            // Adjust the sharpening level
	            double sharpenFactor = sharpenLevel * 10;

	            // Calculate the sums and apply sharpening
	            int redSum = (int) Math.min(red1 + red2 * sharpenFactor, 255);
	            int greenSum = (int) Math.min(green1 + green2 * sharpenFactor, 255);
	            int blueSum = (int) Math.min(blue1 + blue2 * sharpenFactor, 255);

	            // Calculate the new pixel value for the added image
	            int addedPixel = ImageMatrix.convertRGB(
	                redSum,
	                greenSum,
	                blueSum
	            );

	            addedImage.setPixel(x, y, addedPixel);
	        }
	    }

	    return addedImage;
	}

	
	
	
	
	
	/**
	 * 
	 * @param image
	 * @param value
	 * @return
	 */
	private static ImageMatrix applyGrayscaleFilter(ImageMatrix image, double value) {
	    int width = image.getWidth();
	    int height = image.getHeight();
	    ImageMatrix grayscaleImage = new ImageMatrix(width, height);

	    // Iterate through each pix
	    for (int x = 0; x < width; x++) {
	        for (int y = 0; y < height; y++) {
	            // Retrieve pixel value
	            int pixel = image.getPixel(x, y);

	            // Extract color components
	            int red = image.getRed(pixel);
	            int green = image.getGreen(pixel);
	            int blue = image.getBlue(pixel);

	            // Calculate grayscale value
	            int grayscaleValue = (red + green + blue) / 3;

	            // Create a new pixel with grayscale value for all color components
	            int grayscalePixel = ImageMatrix.convertRGB(
	                grayscaleValue,
	                grayscaleValue,
	                grayscaleValue
	            );

	            grayscaleImage.setPixel(x, y, grayscalePixel);
	        }
	    }

	    return grayscaleImage;
	}
	
	
  
	
	/**
	 * 
	 * @param imageMatrix
	 * @param brightnessLevel
	 * @return
	 */
	public static ImageMatrix applyBrightnessFilter(ImageMatrix imageMatrix, double brightnessLevel) {
	    int width = imageMatrix.getWidth();
	    int height = imageMatrix.getHeight();
	    
	    brightnessLevel-= 5;
	    ImageMatrix adjustedImage = new ImageMatrix(width, height);
	    
	    // Adjust brightness pixel-wise
	    for (int y = 0; y < height; y++) {
	        for (int x = 0; x < width; x++) {
	            int pixel = imageMatrix.getPixel(x, y);
	            int red = imageMatrix.getRed(pixel);
	            int green = imageMatrix.getGreen(pixel);
	            int blue = imageMatrix.getBlue(pixel);
	            
	            red += brightnessLevel*20;
	            green += brightnessLevel*20;
	            blue += brightnessLevel*20;
	            
	            if (red < 0) {
	                red = 0;
	            } else if (red > 255) {
	                red = 255;
	            }
	            
	            if (green < 0) {
	                green = 0;
	            } else if (green > 255) {
	                green = 255;
	            }
	            
	            if (blue < 0) {
	                blue = 0;
	            } else if (blue > 255) {
	                blue = 255;
	            }
	            
	            int adjustedPixel = ImageMatrix.convertRGB(red, green, blue);
	            adjustedImage.setPixel(x, y, adjustedPixel);
	        }
	    }
	    
	    return adjustedImage;
	}




	/**
	 * 
	 * @param image
	 * @param contrastModifier
	 * @return
	 */
	public static ImageMatrix applyContrastFilter(ImageMatrix image, double contrastModifier) {
	    int width = image.getWidth();
	    int height = image.getHeight();
	    ImageMatrix contrastedImage = new ImageMatrix(width, height);

	    // Iterate through each pixel
	    for (int x = 0; x < width; x++) {
	        for (int y = 0; y < height; y++) {
	            // Retrieve pixel value
	            int pixel = image.getPixel(x, y);

	            // Extract color components of the pixel
	            int red = image.getRed(pixel);
	            int green = image.getGreen(pixel);
	            int blue = image.getBlue(pixel);

	            // Adjust the contrast using the contrast modifier
	            red = adjustContrastComponent(red, contrastModifier);
	            green = adjustContrastComponent(green, contrastModifier);
	            blue = adjustContrastComponent(blue, contrastModifier);

	            // Calculate the new pixel value for the contrasted image
	            int contrastedPixel = ImageMatrix.convertRGB(
	                red,
	                green,
	                blue
	            );

	            contrastedImage.setPixel(x, y, contrastedPixel);
	        }
	    }

	    return contrastedImage;
	}

	
	/**
	 * 
	 * @param component
	 * @param contrastModifier
	 * @return
	 */
	private static int adjustContrastComponent(int component, double contrastModifier) {
	    // Adjust the contrast using the formula: new_component = (component - 128) * contrastModifier + 128
	    int newComponent = (int) ((component - 128) * contrastModifier + 128);

	    // Ensure the component value is within the valid range of 0-255
	    if (newComponent < 0) {
	        newComponent = 0;
	    } else if (newComponent > 255) {
	        newComponent = 255;
	    }

	    return newComponent;
	}



	/**
	 * 
	 * @param image
	 * @param edgeAmount
	 * @return
	 */
	private static ImageMatrix applySobelOperator(ImageMatrix image, double edgeAmount) {
	    int width = image.getWidth();
	    int height = image.getHeight();
	    ImageMatrix edgeImage = new ImageMatrix(width, height);

	    int[][] sobelX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
	    int[][] sobelY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

	    // Apply Sobel operator
	    for (int y = 1; y < height - 1; y++) {
	        for (int x = 1; x < width - 1; x++) {
	            int sumX = 0;
	            int sumY = 0;
	            for (int i = -1; i <= 1; i++) {
	                for (int j = -1; j <= 1; j++) {
	                    int grayscaleValue = image.getRGB(x + j, y + i) & 0xFF; // Extract the grayscale value from the pixel
	                    sumX += grayscaleValue * sobelX[i + 1][j + 1];
	                    sumY += grayscaleValue * sobelY[i + 1][j + 1];
	                }
	            }
	            int magnitude = (int) (Math.sqrt(sumX * sumX + sumY * sumY) / edgeAmount);
	            edgeImage.setRGB(x, y, magnitude);
	        }
	    }

	    return edgeImage;
	}



	/**
	 * 
	 * @param image
	 * @param edgeAmount
	 * @return
	 */
	public static ImageMatrix applyEdgeDetectionFilter(ImageMatrix image, double edgeAmount) {
        int width = image.getWidth();
        int height = image.getHeight();
        
        edgeAmount -= 6;

        // Convert image to grayscale
        ImageMatrix grayscaleImage = applyGrayscaleFilter(image,10);

        // Apply slight blurring
        ImageMatrix blurredImage = applyBlurFilter(grayscaleImage, 5);

        // Apply edge detection using Sobel operator
        ImageMatrix edgeImage = applySobelOperator(blurredImage, edgeAmount);

        return edgeImage;
    }
	

 
    
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource()== discover) {
			new DiscoverPage(user);
			frame.dispose();
		}
		
		if(e.getSource()== filter) {
			new EditPage(user, photo);
			frame.dispose();
		}
		
		if(e.getSource()== search) {
			new SearchPage(user);
			frame.dispose();
		}
		
		if(e.getSource()== apply) {
			
			try {
				// Get the filter level from the filterAmountField
				double filterlevel = Double.valueOf(filterAmountField.getText());
				
				// Check if the filter amount is empty
				if(filterAmountField.getText().equals("")) {
		        	throw new NullPointerException();
		        }
				
				// Check if the filter level is out of bounds
				if(filterlevel>10 || filterlevel<0) {
					throw new FilterOutOfBondsException("Out of bonds");
				}
				
				// Apply the selected filter based on the enum filterPreference
				if(filterPreference == FilterPreference.BLUR) {
					finalImage = ImageChanger.applyBlurFilter(finalImage, filterlevel);
					photoLabel.setIcon(new ImageIcon(Photo.getResizedImage(finalImage, 300, 300).getBufferedImage()));
					BaseLogger.info().log(user.getNickname() + " applied blur filter");
				}
				if(filterPreference == FilterPreference.SHARPEN) {
					finalImage = ImageChanger.applySharpenFilter(finalImage, filterlevel);
					photoLabel.setIcon(new ImageIcon(Photo.getResizedImage(finalImage, 300, 300).getBufferedImage()));
					BaseLogger.info().log(user.getNickname() + " applied sharpen filter");
				}
				
				if(filterPreference == FilterPreference.BRIGHTNESS) {
					finalImage = ImageChanger.applyBrightnessFilter(finalImage, filterlevel);
					photoLabel.setIcon(new ImageIcon(Photo.getResizedImage(finalImage, 300, 300).getBufferedImage()));
					BaseLogger.info().log(user.getNickname() + " applied blur filter");
				}
				if(filterPreference == FilterPreference.CONTRAST) {
					finalImage = ImageChanger.applyContrastFilter(finalImage, filterlevel);
					photoLabel.setIcon(new ImageIcon(Photo.getResizedImage(finalImage, 300, 300).getBufferedImage()));
					BaseLogger.info().log(user.getNickname() + " applied contrast filter");
				}
				if(filterPreference == FilterPreference.GRAYSCALE) {
					finalImage = ImageChanger.applyGrayscaleFilter(finalImage,filterlevel);
					photoLabel.setIcon(new ImageIcon(Photo.getResizedImage(finalImage, 300, 300).getBufferedImage()));
					BaseLogger.info().log(user.getNickname() + " applied grayscale filter");
				}
				if(filterPreference == FilterPreference.EDGE_DETECTION) {
					finalImage = ImageChanger.applyEdgeDetectionFilter(finalImage,filterlevel);
					photoLabel.setIcon(new ImageIcon(Photo.getResizedImage(finalImage, 300, 300).getBufferedImage()));
					BaseLogger.info().log(user.getNickname() + " applied edge detection filter");
				}
			}
			catch (NullPointerException e1) {
        		message.setText("Please enter a value");
        	}
			
			catch (NumberFormatException e1) {
				message.setText("Invalid input");
				BaseLogger.error().log(user.getNickname() + " entered invalid input for the filter amount");
        	}
			catch (FilterOutOfBondsException e1) {
				message.setText(e1.toString());
				BaseLogger.error().log(user.getNickname() + " entered invalid input for the filter amount");
			}
			
			
		}
		
		if(e.getSource() == save) {
			photo.setImage(finalImage);
			new ProfilePage(user);
			frame.dispose();
		}
		
		if(e.getSource() == revert) {
			// Reset the image to the original image
			finalImage = Photo.getResizedImage(Photo.createImageMatrix(photo.getFilePath()),250,250);
			photoLabel.setIcon(new ImageIcon(Photo.getResizedImage(finalImage, 300, 300).getBufferedImage()));
			
			
			
		}
		
	}



}
