/**
*ウィンドウ表示のクラス
**/
package minamii.janken.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Shootingpanel extends JPanel{
	public BufferedImage image;														/* チラツキを無くすため一気に表示するためバッファリングを使用 */

	public Shootingpanel() {
		super();																	/* 新しいパネルの作成 */

		this.image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
	}

	@Override																		/* 継承元であるスーパークラス（親クラス）で定義されているメソッドを、サブクラス（子クラス）で書き換える */
	public void paint(Graphics g) {
		super.paint (g);															/* ペイント関数 */
		g.drawImage(image, 0, 0, this);												/* 指定されたイメージの領域分描画 */
	}

	public void draw() {																/* 描画結果を表示 */
		this.repaint();
	}
}

