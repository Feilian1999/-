import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DrawPanel extends JPanel {
	private ShapeBasic[] shapes;
	private int shapeCount;

	private ShapeBasic currentShape;//�ثe�ϧ�
	private Color currentColor;//�{�b�C��
	private boolean filledShape;//�O�_��
	private String shapeType;//�ϧκ���
	private JLabel statusLabel;//X,Y�y��
	private int strokeWidth;//����ʲ�

	// �غc�{��
	public DrawPanel(JLabel statusLabel) {
		shapes = new ShapeBasic[100];
		shapeCount = 0;

		setDrawingColor(Color.BLACK);
		setFilledShape(false);

		setBackground(Color.WHITE);

		MouseHandler mouseHandler = new MouseHandler();
		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);

		this.statusLabel = statusLabel;

	}
//�e�ϥH�αN�ϧΦs�b�}�C��
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 0; i < shapeCount; i++)
			shapes[i].draw(g);

		if (currentShape != null)
			currentShape.draw(g);
	}

	public void setDrawingColor(Color c) {
		currentColor = c;
	}
	

	//�W�@�B
	public void clearLastShape() {
		if (shapeCount > 0) {
			shapeCount--;
			repaint();
		}
	}
	//�M���e��
	public void clearDrawing() {
		shapeCount = 0;
		repaint();
	}
	//��
	public void setFilledShape(boolean isFilled) {
		filledShape = isFilled;
	}
	//�ϧΪ�����
	public void setShapeType(String shapeType) {
		this.shapeType = shapeType;
	}
	//�]�w�ʲ�
	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	//�z�L���Ш��oX,Y�y��
	private class MouseHandler extends MouseAdapter implements MouseMotionListener {
		//�I��
		public void mousePressed(MouseEvent e) {
			if (currentShape != null)
				return;
			
			currentShape = new ShapeBasic(e.getX(), e.getY(), e.getX(), e.getY(), currentColor, filledShape, shapeType);//�إ߹ϧ�
			currentShape.setStrokeWidth(strokeWidth);

			repaint();
		}
		//��}
		public void mouseReleased(MouseEvent e) {
			if (currentShape == null)
				return;

			currentShape.setX2(e.getX());
			currentShape.setY2(e.getY());

			if (shapeCount < shapes.length) {
				shapes[shapeCount] = currentShape;
				shapeCount++;
			}
			currentShape = null;
			repaint();
		}
		//�즲
		public void mouseDragged(MouseEvent e) {
			if (currentShape != null) {
				currentShape.setX2(e.getX());
				currentShape.setY2(e.getY());
				
				currentShape.addPoint(e.getPoint());
				repaint();
			}

			mouseMoved(e);
		}

		public void mouseMoved(MouseEvent e) {
			statusLabel.setText(String.format("���Ц�m: " + "(%d,%d)", e.getX(), e.getY()));
		}

	}
}
