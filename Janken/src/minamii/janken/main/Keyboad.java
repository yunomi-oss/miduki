/**
*キーボードイベントのクラス
**/


package minamii.janken.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Keyboad extends KeyAdapter{
	private static ArrayList<Integer> pressedButtons = new ArrayList<>();							/* 入力しているキーを配列で保存 */

    public static boolean isKeyPressed(int keyCode) {															/* キーが押されているかの確認 */
        return pressedButtons.contains(keyCode);																	/* 押されているならtrueそうでない場合はfalse */
    }

	public  Keyboad() {
		pressedButtons = new ArrayList<>();
	}



	@Override
	public void keyPressed(KeyEvent e) {						/* キーを押したとき */
        super.keyPressed(e);
        if(!pressedButtons.contains(e.getKeyCode())) pressedButtons.add(e.getKeyCode());						/* getKeyCodeでキー番号を取得（ArrayListに既に同じ番号が無いか確認する[containsで確認できる]） */

	}



    @Override
    public void keyReleased(KeyEvent e) {					/* キーを離したとき */
        super.keyReleased(e);
        pressedButtons.remove((Integer) e.getKeyCode());							/* キー番号はintなのでintergerにキャストして消す */
    }

}
