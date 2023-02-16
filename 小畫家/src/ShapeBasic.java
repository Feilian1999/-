import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class ShapeBasic {
	private int x1;
	private int y1;
	private int x2;
	private int y2;//座標 
	private Color ShapeColor;//顏色
	private boolean filled;//是否填滿
	private String shapetype;//圖形類型
	private float strokeWidth;//筆刷大小
	private static float k = 2;

	private ArrayList<Point> points = new ArrayList<Point>();//給筆刷使用的陣列

	public ShapeBasic() {
		this(0, 0, 0, 0, Color.BLACK, false, null);
	}

	// 建構程式
	public ShapeBasic(int x1, int y1, int x2, int y2, Color color, boolean isFilled, String shapetype) {
		setX1(x1);
		setY1(y1);
		setX2(x2);
		setY2(y2);
		setColor(color);
		setFilled(isFilled);
		setShapetype(shapetype);
	}
	//筆刷
	public ShapeBasic(ArrayList<Point> points) {
		this(0, 0, 0, 0, Color.BLACK, false, null);
		setPoints(points);
	}

	public void setX1(int x1) {
		this.x1 = (x1 >= 0 ? x1 : 0);
	}

	public int getX1() {
		return x1;
	}

	public void setX2(int x2) {
		this.x2 = (x2 >= 0 ? x2 : 0);
	}

	public int getX2() {
		return x2;
	}

	public void setY1(int y1) {
		this.y1 = (y1 >= 0 ? y1 : 0);
	}

	public int getY1() {
		return y1;
	}

	public void setY2(int y2) {
		this.y2 = (y2 >= 0 ? y2 : 0);
	}

	public int getY2() {
		return y2;
	}

	public void setColor(Color color) {
		ShapeColor = color;
	}

	public Color getColor() {
		return ShapeColor;
	}

	public int getUpperLeftX() {
		return Math.min(getX1(), getX2());
	}

	public int getUpperLeftY() {
		return Math.min(getY1(), getY2());
	}

	public int getWidth() {
		return Math.abs(getX2() - getX1());
	}

	public int getHeight() {
		return Math.abs(getY2() - getY1());
	}

	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean isFilled) {
		filled = isFilled;
	}
	//預設shapetype的功能
	public void setShapetype(String shapetype) {
		this.shapetype = shapetype != null ? shapetype : "筆刷";
	}

	public String getShapetype() {
		return shapetype;
	}

	public float getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(float strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	public ArrayList<Point> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	public void addPoint(Point point) {
		points.add(point);
	}

	public void draw(Graphics g) {
		g.setColor(getColor());// 決定顏色
		((Graphics2D) g).setStroke(new BasicStroke(getStrokeWidth() * k));

		switch (shapetype) {
		case "矩形":
			if (isFilled())// 透過if else 來判斷是否填滿，透過getUpperLeft以左上角作為定點
				g.fillRect(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());
			else
				g.drawRect(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());// 長方形
			break;
		case "橢圓形":
			if (isFilled())
				g.fillOval(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());
			else
				g.drawOval(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());// 橢圓形
			break;
		case "直線":
			if (isFilled()) {
				g.drawLine(getX1(), getY1(), getX2(), getY2());// 拉直線
			} else {
				((Graphics2D) g).setStroke(new BasicStroke(getStrokeWidth() * k, BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_ROUND, 3.5f, new float[] { 15, 10, }, 0f));
				g.drawLine(getX1(), getY1(), getX2(), getY2());// 虛線
			}
			break;
		case "圓角矩形":
			if (isFilled())
				g.fillRoundRect(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight(), 50, 50);
			else
				g.drawRoundRect(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight(), 50, 50);// 圓角矩形
			break;
		case "筆刷":
		    for (int i = 1; i < points.size(); i++)
		        ((Graphics2D) g).draw(new Line2D.Float(points.get(i-1), points.get(i)));//
			break;
		}

	}
}
