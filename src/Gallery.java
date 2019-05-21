import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Gallery extends JPanel {

	private ArrayList<JLabel> labels = new ArrayList<JLabel>();
	private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	private int clicked;
	private JLabel text = new JLabel("No image selected.", JLabel.LEFT);

	public Gallery() {
		init();
	}

	public void init() {
		this.setPreferredSize(new Dimension(300, 1080));
		setLayout(null);

		this.addMouseListener(new MouseListener() {
			private boolean on = false;

			@Override
			public void mouseClicked(MouseEvent e) {
				if (on) {
					int y = e.getX();
					int x = e.getY();

					if (y >= 20 && y <= 140) {
						if (x >= 20 && x <= 140) {
							clicked = 1;
						} else if (x >= 160 && x <= 280) {
							clicked = 3;
						} else if (x >= 300 && x <= 420) {
							clicked = 5;
						} else if (x >= 440 && x <= 560) {
							clicked = 7;
						} else if (x >= 580 && x <= 700) {
							clicked = 9;
						} else if (x >= 720 && x <= 840) {
							clicked = 11;
						}
					} else if (y >= 160 && y <= 280) {
						if (x >= 20 && x <= 140) {
							clicked = 2;
						} else if (x >= 160 && x <= 280) {
							clicked = 4;
						} else if (x >= 300 && x <= 420) {
							clicked = 6;
						} else if (x >= 440 && x <= 560) {
							clicked = 8;
						} else if (x >= 580 && x <= 700) {
							clicked = 10;
						} else if (x >= 720 && x <= 840) {
							clicked = 12;
						}
					} else {
						clicked = 0;
					}

					if (clicked != 0) {
						if (clicked <= images.size()) {
							text.setText("Image selected: " + clicked);
						}
					} else {
						text.setText("No image selected.");
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				on = true;

			}

			@Override
			public void mouseExited(MouseEvent e) {
				on = false;

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

		show();
	}

	public void addImage(BufferedImage image) {
		images.add(image);
		if (labels.size() >= 12) {
			System.err.println("Too many images");
		} else {
			labels.add(createIcon(image));
			show();
		}
	}

	public void saveImages() {
		int index = 1;
		for (BufferedImage image : images) {
			try {
				ImageIO.write((RenderedImage) image, "png", new File("image" + index++ + ".png"));
			} catch (IOException e) {
				System.err.println("Error saving the image.");
			}
		}
	}

	public void remove() {
		try {
			images.remove(images.get(clicked - 1));
			labels.remove(labels.get(clicked - 1));

		} catch (Exception e) {

		}
		clear();
		text.setText("No image selected.");
		clicked = 0;
	}

	public BufferedImage getImage() {
		return images.get(clicked - 1);
	}

	public JLabel createIcon(BufferedImage image) {
		Image newImage = image.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(newImage);
		JLabel label = new JLabel(icon);
		return label;
	}

	public void show() {
		add(text);
		text.setBounds(20, 860, 300, 40);

		int space = 20;
		int size = 120;
		int index = 1;
		int x = 20, y = 20;

		for (JLabel l : labels) {
			add(l);
			if (index % 2 == 1) {
				l.setBounds(x, y, size, size);
				x += (space + size);
			} else {
				l.setBounds(x, y, size, size);
				x = 20;
				y += (space + size);

			}
			index++;
		}
	}

	public void clear() {
		this.removeAll();
		this.revalidate();
		this.repaint();
		show();
	}
}
