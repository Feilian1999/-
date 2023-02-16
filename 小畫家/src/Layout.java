import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Layout extends JFrame implements ItemListener, ActionListener {

	private JLabel label1;// 繪圖工具
	private JLabel label2;// 筆刷大小
	private JLabel space;// 空白
	private JLabel space2;// 還是空白
	private JLabel statusLabel;// 指標位置

	private JComboBox<String> Drawtool;// 繪圖工具下面的選項
	private String tools[] = { "筆刷", "直線", "橢圓形", "矩形", "圓角矩形" };

	private JRadioButton[] size;// 選擇筆刷大小
	private ButtonGroup radioGroup;
	private JCheckBox fill;// 選擇是否填滿
	private JButton color;// 筆刷顏色
	private JButton clear;// 清除畫面
	private JButton undo;// 清除畫面
	private JButton eraser;

	private DrawPanel drawPanel;
	private JColorChooser colorChooser = new JColorChooser();

	public Layout() {
		super("小畫家");
		JPanel toolPanel = new JPanel();
		statusLabel = new JLabel("指標位置: " + "(0,0)");
		drawPanel = new DrawPanel(statusLabel);// 主要的大Panel

		JPanel tool1 = new JPanel();
		JPanel tool2 = new JPanel();
		tool1.setLayout(new GridLayout(2, 0));
		tool2.setLayout(new GridLayout(2, 3));// 排版用的Layout
		statusLabel.setOpaque(true);
		statusLabel.setBackground(Color.black);
		statusLabel.setForeground(Color.white);
		drawPanel.setBackground(Color.white);// X,Y顏色顯示
		colorChooser.setColor(Color.black);//預設黑色

		label1 = new JLabel("繪圖工具");
		label2 = new JLabel("筆刷大小");
		space = new JLabel("");
		space2 = new JLabel("");// label 的字

		Drawtool = new JComboBox<String>(tools);
		Drawtool.addItemListener(this);// drawtool選項

		size = new JRadioButton[3];
		size[0] = new JRadioButton("小", true);
		size[1] = new JRadioButton("中", false);
		size[2] = new JRadioButton("大", false);
		radioGroup = new ButtonGroup();// 用陣列宣告RadioButton
		tool2.add(label2);
		tool2.add(space);
		tool2.add(space2);// 排版需要，有其他方式但我懶得改

		for (int i = 0; i < size.length; i++) {
			tool2.add(size[i]);
			size[i].addItemListener(this);
			radioGroup.add(size[i]);
		} // 用迴圈把Radiobutton加進tool2，還有radioGroup

		fill = new JCheckBox("填滿");
		fill.addItemListener(this);
		fill.setHorizontalTextPosition(JCheckBox.CENTER);
		fill.setVerticalTextPosition(JCheckBox.TOP);// 把checkbox的左右 換成上下

		color = new JButton("筆刷顏色");
		clear = new JButton("清除畫面");
		undo = new JButton("上一步");
		eraser = new JButton("橡皮擦");
		color.addActionListener(this);
		clear.addActionListener(this);
		undo.addActionListener(this);
		eraser.addActionListener(this);// 單純的button

		add(toolPanel, BorderLayout.NORTH);
		add(statusLabel, BorderLayout.SOUTH);
		add(drawPanel);// 設定Panel位置

		tool1.add(label1);
		tool1.add(Drawtool);
		toolPanel.add(tool1);
		toolPanel.add(tool2);
		toolPanel.add(fill);
		toolPanel.add(color);
		toolPanel.add(clear);// 把所有東西加進toolPanel
		toolPanel.add(eraser);
		toolPanel.add(undo);//新增兩個功能

		drawPanel.setStrokeWidth(1);
		fill.setEnabled(false);//預設選項為筆刷，先設好粗細以及填滿不得使用
	}

	// 在CONSOLE中列印文字，getSource來決定選擇的工具
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == Drawtool && e.getStateChange() == ItemEvent.SELECTED) {
			System.out.println("選擇 " + Drawtool.getSelectedItem());// 要防止跑兩次的問題
			if(Drawtool.getSelectedItem().toString() == "筆刷") {
				fill.setEnabled(false);//若選項為筆刷，則不能使用填滿
			}
			else {
				fill.setEnabled(true);
			}
			drawPanel.setShapeType(Drawtool.getSelectedItem().toString());
		} else if (e.getSource() == fill) {
			System.out.println((fill.isSelected() ? "選擇" : "取消") + " 填滿");
			drawPanel.setFilledShape(fill.isSelected());//選擇填滿則填滿
		}
		for (int i = 0; i < size.length; i++) {
			if (e.getSource() == size[i] && size[i].isSelected()) {
				System.out.println("選擇 " + size[i].getText() + " 筆刷");
				drawPanel.setStrokeWidth(i + 1);//設定筆刷大小
				
			}
		}
	}

	// 同上，只是不同Listener
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == color) {
			System.out.println("點選 筆刷顏色");
			JDialog dialog = JColorChooser.createDialog(this, "請選擇顏色", false, colorChooser,
					(e1) -> drawPanel.setDrawingColor(colorChooser.getColor()), null);
//			JDialog dialog = JColorChooser.createDialog(this, "請選擇顏色", false, colorChooser, new ActionListener() {
//				
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					// TODO Auto-generated method stub
//					drawPanel.setDrawingColor(colorChooser.getColor());
//				}
//			} , null);
			dialog.setVisible(true);
		} else if (e.getSource() == clear) {
			System.out.println("點選 清除畫面");
			drawPanel.clearDrawing();
		} else if (e.getSource() == undo) {
			System.out.println("點選 上一步");
			drawPanel.clearLastShape();
		} else if (e.getSource() == eraser) {
			System.out.println("點選 橡皮擦");
			if(eraser.getText() == "橡皮擦") {
				drawPanel.setDrawingColor(drawPanel.getBackground());//使橡皮擦兼具恢復原來筆刷的功能
				eraser.setText("我是一枝筆");
			}
			else {
				drawPanel.setDrawingColor(colorChooser.getColor());
				eraser.setText("橡皮擦");
			}
		}

	}

	// 最前面的迎賓訊息，置中
	public void showWelcome() {
		JOptionPane.showMessageDialog(this, "Welcome", "訊息", JOptionPane.INFORMATION_MESSAGE);
	}
}
