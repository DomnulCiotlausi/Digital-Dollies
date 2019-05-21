import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SwingPaint {
	private JButton clearBtn, blackBtn, blueBtn, greenBtn, redBtn, magentaBtn, undoBtn, reflectBtn, showBtn, saveBtn,
			loadBtn, removeBtn;
	private JSlider thickSlider, sectorSlider;
	private DrawArea drawArea;
	private Gallery gallery;

	private ActionListener actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == clearBtn) {
				drawArea.clearAll();
			} else if (e.getSource() == blackBtn) {
				drawArea.black();
			} else if (e.getSource() == blueBtn) {
				drawArea.blue();
			} else if (e.getSource() == greenBtn) {
				drawArea.green();
			} else if (e.getSource() == redBtn) {
				drawArea.red();
			} else if (e.getSource() == magentaBtn) {
				drawArea.magenta();
			} else if (e.getSource() == undoBtn) {
				drawArea.undo();
			} else if (e.getSource() == reflectBtn) {
				drawArea.reflect();
			} else if (e.getSource() == showBtn) {
				drawArea.show();
			} else if (e.getSource() == saveBtn) {
				gallery.addImage(drawArea.export());
			} else if (e.getSource() == loadBtn) {
				drawArea.loadImage(gallery.getImage());
			} else if (e.getSource() == removeBtn) {
				gallery.remove();
			}
		}
	};

	private ChangeListener changeListener = new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() == thickSlider) {
				drawArea.thick(((JSlider) e.getSource()).getValue());
			} else if (e.getSource() == sectorSlider) {
				drawArea.setSectorLines(((JSlider) e.getSource()).getValue());
			}
		}

	};

	public static void main(String[] args) {
		new SwingPaint().init();
	}

	public void init() {
		JFrame frame = new JFrame("Arsenie Boca");
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		drawArea = new DrawArea();
		content.add(drawArea, BorderLayout.CENTER);

		gallery = new Gallery();
		content.add(gallery, BorderLayout.EAST);

		JPanel controls = new JPanel();

		clearBtn = new JButton("Clear");
		clearBtn.addActionListener(actionListener);
		blackBtn = new JButton("Black");
		blackBtn.addActionListener(actionListener);
		blueBtn = new JButton("Blue");
		blueBtn.addActionListener(actionListener);
		greenBtn = new JButton("Green");
		greenBtn.addActionListener(actionListener);
		redBtn = new JButton("Red");
		redBtn.addActionListener(actionListener);
		magentaBtn = new JButton("Magenta");
		magentaBtn.addActionListener(actionListener);
		undoBtn = new JButton("Undo");
		undoBtn.addActionListener(actionListener);
		reflectBtn = new JButton("Reflect");
		reflectBtn.addActionListener(actionListener);
		showBtn = new JButton("Show");
		showBtn.addActionListener(actionListener);
		saveBtn = new JButton("Save");
		saveBtn.addActionListener(actionListener);
		loadBtn = new JButton("Load");
		loadBtn.addActionListener(actionListener);
		removeBtn = new JButton("Remove");
		removeBtn.addActionListener(actionListener);

		thickSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
		thickSlider.setMajorTickSpacing(1);
		thickSlider.setPaintTicks(true);
		thickSlider.addChangeListener(changeListener);

		sectorSlider = new JSlider(JSlider.HORIZONTAL, 0, 12, 1);
		sectorSlider.setMajorTickSpacing(1);
		sectorSlider.setPaintTicks(true);
		sectorSlider.addChangeListener(changeListener);

		controls.add(greenBtn);
		controls.add(blueBtn);
		controls.add(blackBtn);
		controls.add(redBtn);
		controls.add(magentaBtn);
		controls.add(clearBtn);
		controls.add(reflectBtn);
		controls.add(showBtn);
		controls.add(undoBtn);
		controls.add(thickSlider);
		controls.add(sectorSlider);
		controls.add(saveBtn);
		controls.add(loadBtn);
		controls.add(removeBtn);

		content.add(controls, BorderLayout.NORTH);

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}