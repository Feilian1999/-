/*�m�W:���L�o �Ǹ�:107403512 �t��:���2A*/
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class Main extends JFrame{
	public static void main(String[] args) {
		Layout labelFrame = new Layout();
		labelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		labelFrame.setSize(700, 500);
		labelFrame.setLocationRelativeTo(null);
		labelFrame.setVisible(true);
		labelFrame.showWelcome();
	}
}
