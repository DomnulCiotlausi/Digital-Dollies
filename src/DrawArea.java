import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class DrawArea extends JComponent {

	private BufferedImage image;
	private Graphics2D g2;
	private int currentX, currentY, oldX, oldY;
	private int thick = 1;
	private int sectorLines = 1;
	private double unit = (Math.PI / 4);
	private ArrayList<Line2D> arrayLine = new ArrayList<Line2D>();
	private ArrayList<Color> arrayColor = new ArrayList<Color>();
	private ArrayList<BasicStroke> arrayStroke = new ArrayList<BasicStroke>();
	private ArrayList<Boolean> arrayReflect = new ArrayList<Boolean>();
	private boolean reflect = false, show = true;
	private int numImage = 1;
	private int count = 1;

	public DrawArea() {
		setDoubleBuffered(false);
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				oldX = e.getX();
				oldY = e.getY();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				currentX = e.getX();
				currentY = e.getY();

				g2.drawLine(oldX, oldY, currentX, currentY);
				
				arrayLine.add(new Line2D.Double(oldX, oldY, currentX, currentY));
				saveStats();
				
				if (reflect) {
					// image.getheight
					g2.translate(getWidth() / 2, getHeight() / 2);
					for (int i = 0; i < 2 * sectorLines - 1; i++) {
						g2.rotate(unit);
						g2.drawLine(oldX - getWidth() / 2, oldY - getWidth() / 4, currentX - getWidth() / 2,
								currentY - getWidth() / 4);
						arrayLine.add(new Line2D.Double(oldX, oldY, currentX, currentY));
						saveStats();
					}

					for (int i = 0; i < 2 * sectorLines - 1; i++) { // IDK
						g2.rotate(-unit);
					}
					g2.translate(-getWidth() / 2, -getHeight() / 2);
				}
				repaint();

				oldX = currentX;
				oldY = currentY;
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image == null) {
			image = (BufferedImage) createImage(getSize().width, getSize().height);
			g2 = (Graphics2D) image.getGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			clear();
		}
		System.err.println(count);
		g2.setStroke(new BasicStroke(thick));
		g.drawImage(image, 0, 0, null);
	}

	public void drawSectorLines() {
		if (show) {
			g2.translate(getWidth() / 2, getHeight() / 2);
			int thick = this.thick;
			Color color = g2.getColor();
			g2.setStroke(new BasicStroke(1));
			g2.setColor(Color.black);
			for (int i = 0; i < sectorLines; i++) {
				g2.drawLine(0, getHeight(), 0, -getHeight());
				g2.rotate(unit);
			}
			for (int i = 0; i < sectorLines; i++) { // IDK
				g2.rotate(-unit);
			}
			g2.translate(-getWidth() / 2, -getHeight() / 2);
			g2.setStroke(new BasicStroke(thick));
			g2.setColor(color);
			repaint();
		}
	}

	public void redraw() {
		clear();
		if (!reflect) {
			for (Line2D l : arrayLine) {
				g2.draw(l);
			}
		} else {
			for (Line2D l : arrayLine) {
				g2.draw(l);
				g2.translate(getWidth() / 2, getHeight() / 2);
				for (int i = 0; i < 2 * sectorLines - 1; i++) {
					g2.rotate(unit);
					g2.drawLine((int) (l.getX1() - getWidth() / 2), (int) (l.getY1() - getWidth() / 4),
							(int) (l.getX2() - getWidth() / 2), (int) (l.getY2() - getWidth() / 4));
				}
				for (int i = 0; i < 2 * sectorLines - 1; i++) {
					g2.rotate(-unit);
				}
				g2.translate(-getWidth() / 2, -getHeight() / 2);
			}
		}
		repaint();
	}

	public void clearSectorLines() {
		redraw();
	}

	public void clear() {
		Color tempColor = g2.getColor();
		g2.setPaint(Color.white);
		g2.fillRect(0, 0, getSize().width, getSize().height);
		if (!Double.isNaN(unit)) {
			drawSectorLines();
		}
		g2.setPaint(tempColor);
		repaint();
	}

	public void clearAll() {
		clear();
		arrayLine.clear();
	}
	/// AICI COAE
	public void saveStats(){
		arrayColor.add(g2.getColor());
		arrayStroke.add(new BasicStroke(thick));
		arrayReflect.add(reflect);
	}

	public void red() {
		g2.setPaint(Color.red);
	}

	public void black() {
		g2.setPaint(Color.black);
	}

	public void magenta() {
		g2.setPaint(Color.magenta);
	}

	public void green() {
		g2.setPaint(Color.green);
	}

	public void blue() {
		g2.setPaint(Color.blue);
	}

	public void thick(int thick) {
		this.thick = thick;
	}

	public void undo() {
		for (int i = 0; i < 100; i++) {
			try {
				arrayLine.remove(arrayLine.size() - 1);
			} catch (Exception e) {
				arrayLine.clear();
			}
		}
		redraw();
	}

	public void setSectorLines(int sectorLines) {
		this.sectorLines = sectorLines;
		float x = (float) sectorLines;
		unit = (Math.PI / x);
		clear();
		if (!reflect) {
			for (Line2D l : arrayLine) {
				g2.draw(l);
			}
		} else {
			for (Line2D l : arrayLine) {
				g2.draw(l);
				g2.translate(getWidth() / 2, getHeight() / 2);
				for (int i = 0; i < 2 * this.sectorLines - 1; i++) {
					g2.rotate(unit);
					g2.drawLine((int) (l.getX1() - getWidth() / 2), (int) (l.getY1() - getWidth() / 4),
							(int) (l.getX2() - getWidth() / 2), (int) (l.getY2() - getWidth() / 4));
				}
				for (int i = 0; i < 2 * this.sectorLines - 1; i++) {
					g2.rotate(-unit);
				}
				g2.translate(-getWidth() / 2, -getHeight() / 2);
			}
		}
		repaint();
	}

	public void reflect() {
		reflect = !reflect;
	}

	public void show() {
		show = !show;
		if (show) {
			this.drawSectorLines();
		} else {
			this.clearSectorLines();
		}
	}

	public void save() throws IOException {
		ImageIO.write((RenderedImage) image, "png", new File("image" + numImage++ + ".png"));
	}

	public BufferedImage export() {
		this.clearSectorLines();
		BufferedImage i = (BufferedImage) createImage(getSize().width, getSize().height);
		Graphics2D g22 = (Graphics2D) i.getGraphics();
		g22.drawImage(image, 0, 0, null);
		return i;
	}

	public ImageIcon getImage() {
		ImageIcon icon = new ImageIcon(image);
		return icon;
	}

	public void loadImage(BufferedImage image) {
		//clearAll();

		// ColorModel cm = image.getColorModel();
		// boolean isAplhaPremultiplied = cm.isAlphaPremultiplied();
		// WritableRaster raster = image.copyData(null);
		// this.image = new BufferedImage(cm, raster, isAplhaPremultiplied,
		// null);

		// this.image = image.getSubimage(0, 0, image.getWidth(),
		// image.getHeight());

		// this.getGraphics().drawImage(image, 0, 0, null);

		// GraphicsEnvironment ge =
		// GraphicsEnvironment.getLocalGraphicsEnvironment();
		// GraphicsDevice gd = ge.getDefaultScreenDevice();
		// GraphicsConfiguration gc = gd.getDefaultConfiguration();
		// this.image = gc.createCompatibleImage(image.getWidth(null),
		// image.getHeight(null), Transparency.BITMASK);
		
		this.image = image;
		count++;

		repaint();
	}

}