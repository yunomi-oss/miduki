/**
*ウィンドウ作成のクラス
**/
package minamii.janken.main;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame{
	public Shootingpanel panel;

	public  MainFrame(){
		panel = new Shootingpanel();

		this.add(panel);															/* addすることでウィンドウに追加できる */




		this.addKeyListener(new Keyboad());																	/* キーボード入力を受け取る */

		this.setTitle("Shooting");							/* 外枠に書かれる名前 */
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);						/* 閉じるボタンがクリックされた時にアプリケーションを終了する */
		this.setSize(500, 500);														/* 画面の幅と高さ */
		this.setLocationRelativeTo(null);											/* ウィンドウを中央に表示 */
		this.setResizable(false);													/* ウィンドウサイズ固定にする */
		this.setVisible(true);														/* ウィンドウを可視状態にする */

	}
}
