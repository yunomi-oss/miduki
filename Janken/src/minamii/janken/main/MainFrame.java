/**
*初期画面
**/
package minamii.janken.main;
import javax.swing.JFrame;

public class MainFrame {
	public static void main(String[] args) {
		  JFrame frame=new JFrame("じゃんけん");									/* 外枠に書かれる名前 */
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);					/* 閉じるボタンがクリックされた時にアプリケーションを終了する */
		  frame.setSize(500,500);													/* 画面の幅と高さ */
		  frame.setLocationRelativeTo(null);										/* ウィンドウを中央に表示 */
		  frame.setResizable(false);												/* ウィンドウサイズ固定にする */
		  frame.setVisible(true);													/* ウィンドウを可視状態にする */
	}
}
