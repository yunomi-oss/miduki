package minamii.janken.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Main {
	public static  MainFrame MainFrame;
	public static boolean loop;

	public static void main(String[] args) {
		MainFrame = new MainFrame();																/* MainFrameをインスタンス化（MainFrame.javaにかかれているものを呼び出す） */

		loop = true;																									/* loopの初期値をtrueに設定 */

		Graphics gra = MainFrame.panel.image.createGraphics();							/* Shootingpanelで書かれているimage（ウィンドウ画面）を取得 */



		EnumShootingScreen screen = EnumShootingScreen.START;


		/* プレイヤー用のx,y座標 */
		int playerX = 0;
		int playerY = 0;

		int bulletInterval = 0;																							/* 弾を打つ間隔 */


        int score = 0;
        int level = 0;
        long levelTimer = 0;

        ArrayList<Bullet> bullets_player = new ArrayList<>();									/* 弾の配列（プレイヤー） */
        ArrayList<Bullet> bullets_enemy = new ArrayList<>();									/* 弾の配列（敵） */
        ArrayList<Enemy> enemies = new ArrayList<>();										/* 敵の配列 */

		/* ループの処理時間を設定（処理が早すぎるので待機時間を設ける） */
		/* FPS:1秒間あたりに表示される画像（フレーム）数を表す単位 */
		long startTime;																							/* 処理の開始時間 */

		int fps = 60;																									/* FPSを30に設定 */

		long fpsTime = 0;

		int FPS = 0;

		int FPSCount = 0;

        Random random = new Random();															/* ランダムに敵を表示させる初期値 */



		while(loop) {
			if((System.currentTimeMillis() - fpsTime) >= 1000) {
				fpsTime = System.currentTimeMillis();							/* 現在の時刻をミリ秒の単位で返却 */

				FPS = FPSCount;

				FPSCount = 0;

				System.out.println(FPS);											/* コンソールにカウントを表示 */
			}
			FPSCount ++;																/* FPSのカウントをメインループの中で+1ずつ増やす */

			startTime = System.currentTimeMillis();									/* 処理開始前の時刻（Unix時間）を取得 */

			gra.setColor(Color.BLACK);													/* 描画した内容をリセットするために黒で塗りつぶす（背景色にもなる） */

			gra.fillRect(0, 0, 500, 500);												/* 塗りつぶし範囲 */



			switch(screen){
				case START:
					/* 開始文字（SHOOTING）の 描画位置設定*/
					gra.setColor(Color.WHITE);
					Font startfont = new Font("SansSerif", Font.PLAIN, 60);
					gra.setFont(startfont);
					FontMetrics startmetrics = gra.getFontMetrics(startfont);
					gra.drawString("SHOOTING",250 - (startmetrics.stringWidth("SHOOTING") / 2), 150);

					/* 開始文字（スペースキーを押してSTART）の 描画位置設定*/
					Font spacefont = new Font("SansSerif", Font.PLAIN, 20);
					gra.setFont(spacefont);
					FontMetrics spacemetrics = gra.getFontMetrics(spacefont);
					gra.drawString("スペースキーを押してSTART",250 - (spacemetrics.stringWidth("スペースキーを押してSTART") / 2), 250);


					if(Keyboad.isKeyPressed(KeyEvent.VK_SPACE)) {												/* スペースが押された場合 */
						screen = EnumShootingScreen.GAME;																/* GAMEに画面遷移 */

						/* 弾と敵をの座標を初期化 */
						bullets_player = new ArrayList<>();
						enemies = new ArrayList<>();

				        /* プレイヤーの座標 */
				        playerX = 235;
				        playerY = 430;
					}
					break;

				case GAME:

                    if(System.currentTimeMillis() - levelTimer > 10 * 1000) {							/* タイマーに合わせてレベルを上げていく */
                        levelTimer = System.currentTimeMillis();							/* タイマーに合わせてレベルを上げていく */
                        level++;
                    }


					/* プレイヤーの描画 凸*/
					gra.setColor(Color.WHITE);
                    gra.fillRect(playerX + 10, playerY, 10, 10);
                    gra.fillRect(playerX, playerY + 10, 30, 10);

                    /* プレイヤー弾を発射(描画も含む) */
					gra.setColor(Color.WHITE);
                    for (int i = 0; i < bullets_player.size(); i++) {
                        Bullet bullet = bullets_player.get(i);
                        gra.fillRect(bullet.x, bullet.y, 5, 5);				/* 弾サイズ */
                        bullet.y -= 10;											/* ｙを-10のスピードで移動 */
                        if (bullet.y < 0) {										/* 弾が領域外の場合 */
                            bullets_player.remove(i);						/* 消去 */
                            i--;
                        }

                        for (int l = 0; l < enemies.size(); l++) {						/* 敵の当たり判定を追加 */
                            Enemy enemy = enemies.get(l);
                            if(bullet.x>=enemy.x&&bullet.x<=enemy.x+30&&						/* 敵の凸を長方形の領域で考えた場合、弾が当たれば消去 */
                            bullet.y>=enemy.y&&bullet.y<=enemy.y+20) {
                                enemies.remove(l);																	/* 敵を消す */
                                score += 10;
                            }
                        }

                    }


                    /* 敵を描画 */
                    gra.setColor(Color.RED);
                    for (int i = 0; i < enemies.size(); i++) {
                        Enemy enemy = enemies.get(i);
                        gra.fillRect(enemy.x, enemy.y, 30, 10);
                        gra.fillRect(enemy.x + 10, enemy.y + 10, 10, 10);
                        enemy.y += 3;															/* 3ずつ移動する */
                        if(enemy.y > 500) {													/* 領域外の場合 */
                            enemies.remove(i);												/* 消去 */
                            i--;																		/* 1個ずつ */
                        }
///////////////////////////////////////
                        if(random.nextInt(level<50?80 - level:30)==1) bullets_enemy.add(new Bullet(enemy.x + 12, enemy.y));

                        if((enemy.x>=playerX&&enemy.x<=playerX+30&&
                                enemy.y>=playerY&&enemy.y<=playerY+20)||
                            (enemy.x+30>=playerX&&enemy.x+30<=playerX+30&&
                                    enemy.y+20>=playerY&&enemy.y+20<=playerY+20)) {
                            screen = EnumShootingScreen.GAME_OVER;
                            score += (level - 1) * 100;
                        }
/////////////////////////////////////////
                    }
                    if(random.nextInt(level<10?30 - level:10)==1) enemies.add(new Enemy(random.nextInt(470), 0));
/////////////////////////////////////////


                    /* 敵の弾を描画 */
                    gra.setColor(Color.RED);
                    for (int j = 0; j < bullets_enemy.size(); j++) {
                        Bullet bullet = bullets_enemy.get(j);
                        gra.fillRect(bullet.x, bullet.y, 5, 5);				/* 弾サイズ */
                        bullet.y += 5;											/* ｙを5のスピードで移動 */
                        if (bullet.y > 500) {										/* 弾が領域外の場合 */
                        	bullets_enemy.remove(j);						/* 消去 */
                            j--;
                        }
///////////////////////////////////////
                        if(bullet.x>=playerX&&bullet.x<=playerX+30&&
                        bullet.y>=playerY&&bullet.y<=playerY+20) {										/* プレイヤーの凸に弾が当たった場合、ゲームオーバーに遷移 */
                            screen = EnumShootingScreen.GAME_OVER;
                            score += (level - 1) * 100;
                        }
///////////////////////////////////////
                    }




                    /* ←　→　↑　↓でそれぞれ座標8ずつ動かす （&&playerX>0で領域作成）*/
                    if(Keyboad.isKeyPressed(KeyEvent.VK_LEFT)&&playerX>-8) playerX-=8;
                    if(Keyboad.isKeyPressed(KeyEvent.VK_RIGHT)&&playerX<470) playerX+=8;
                    if(Keyboad.isKeyPressed(KeyEvent.VK_UP)&&playerY>10) playerY-=8;
                    if(Keyboad.isKeyPressed(KeyEvent.VK_DOWN)&&playerY<440) playerY+=8;

                    if(Keyboad.isKeyPressed(KeyEvent.VK_SPACE)&&bulletInterval==0) {					/* スペースで弾発射（タイマーが0のとき） */
                        bullets_player.add(new Bullet(playerX + 12, playerY));
                        bulletInterval = 6;																									/* 間隔を8開ける */
                    }
                    if(bulletInterval>0) bulletInterval--;																			/* タイマーが0以上なら1ずつ減る */

                    /* スコアの表示 */
                    gra.setColor(Color.WHITE);
                    Font font = new Font("SansSerif", Font.PLAIN, 20);
                    FontMetrics metrics = gra.getFontMetrics(font);
                    gra.setFont(font);
                    gra.drawString("SCORE:" + score, 470 - metrics.stringWidth("SCORE:" + score), 430);
                    gra.drawString("LEVEL:" + level, 470 - metrics.stringWidth("LEVEL:" + level), 450);


					break;
///////////////////////////////////////
				case GAME_OVER:
                    gra.setColor(Color.WHITE);
                    font = new Font("SansSerif", Font.PLAIN, 50);
                    gra.setFont(font);
                    metrics = gra.getFontMetrics(font);
                    gra.drawString("Game Over", 250 - (metrics.stringWidth("Game Over") / 2), 100);
                    font = new Font("SansSerif", Font.PLAIN, 20);
                    gra.setFont(font);
                    metrics = gra.getFontMetrics(font);
                    gra.drawString("Score:"+score, 250 - (metrics.stringWidth("Score:"+score) / 2), 150);
                    gra.drawString("ESCキー押下でスタートメニューへ", 250 - (metrics.stringWidth("ESCキー押下でスタートメニューへ") / 2), 200);
                    if(Keyboad.isKeyPressed(KeyEvent.VK_ESCAPE)) {
                        screen = EnumShootingScreen.START;
                    }
					break;
///////////////////////////////////////
			}




			/* FPSカウントの描画位置設定 */
			gra.setColor(Color.WHITE);
			gra.setFont(new Font("SansSerif", Font.PLAIN, 15));
			gra.drawString(FPS + "FPS", 0, 460);

			MainFrame.panel.draw();																			/* ウィンドウの描画 */



			try{
				long runTime = System.currentTimeMillis() - startTime;						/* 現在の時刻 - 開始時刻（ミリ秒の単位） */
				if(runTime < (1000 / fps)) {
					Thread.sleep((1000 / fps) - runTime);													/* 待機時間の計算（プログラムを停止させる） */
				}

			}
			catch (InterruptedException e){																	/* エラー処理 */
				e.printStackTrace();																					/* 実行中エラーが発生した際に、直前に実行していた関数やメソッドなどの履歴を表示する */
			}
		}
	}
}
