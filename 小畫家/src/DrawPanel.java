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

	private ShapeBasic currentShape;//目前圖形
	private Color currentColor;//現在顏色
	private boolean filledShape;//是否填滿
	private String shapeType;//圖形種類
	private JLabel statusLabel;//X,Y座標
	private int strokeWidth;//筆刷粗細

	// 建構程式
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
//畫圖以及將圖形存在陣列中
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
	

	//上一步
	public void clearLastShape() {
		if (shapeCount > 0) {
			shapeCount--;
			repaint();
		}
	}
	//清除畫布
	public void clearDrawing() {
		shapeCount = 0;
		repaint();
	}
	//填滿
	public void setFilledShape(boolean isFilled) {
		filledShape = isFilled;
	}
	//圖形的種類
	public void setShapeType(String shapeType) {
		this.shapeType = shapeType;
	}
	//設定粗細
	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	//透過鼠標取得X,Y座標
	private class MouseHandler extends MouseAdapter implements MouseMotionListener {
		//點擊
		public void mousePressed(MouseEvent e) {
			if (currentShape != null)
				return;
			
			currentShape = new ShapeBasic(e.getX(), e.getY(), e.getX(), e.getY(), currentColor, filledShape, shapeType);//建立圖形
			currentShape.setStrokeWidth(strokeWidth);

			repaint();
		}
		//放開
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
		//拖曳
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
			statusLabel.setText(String.format("指標位置: " + "(%d,%d)", e.getX(), e.getY()));
		}

	}
}
