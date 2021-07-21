package com.hitmouse.ui;

import java.util.Random;

import com.example.hitmouse.R;
import com.hitmouse.controller.Controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HitActivity extends Activity {
	private ImageView mouse;
	private TextView timer;
	private TextView scoreView;
	private LinearLayout layout;
	private Handler handler;
	private int score = 0;
	private int recLen = 30;
	Controller controller;
	Boolean musicPlay = false;
	Thread mouseThread;
	Boolean flag = false;

	public int[][] position = new int[][] { { 240, 130 }, { 205, 255 },
			{ 190, 390 }, { 535, 125 }, { 545, 255 }, { 540, 410 },
			{ 850, 130 }, { 865, 255 }, { 895, 400 } };

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hit_layout);
		mouse = (ImageView) findViewById(R.id.mouse);
		timer = (TextView) findViewById(R.id.timer);
		scoreView = (TextView) findViewById(R.id.score);
		layout = (LinearLayout) findViewById(R.id.layout);
		controller = new Controller(HitActivity.this);
		// 布局监听
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.playSound(R.raw.laugh);
			}

		});
		// 如果没有背景音乐则播放
		if (!musicPlay) {
			controller.play();
			musicPlay = true;
		}
		// 难度时间,得分
		Intent intent = getIntent();
		final int diffity = Integer.parseInt(intent.getStringExtra("diffity"));
		final int sum = Integer.parseInt(intent.getStringExtra("score"));

		// 地鼠线程
		mouseThread = new Thread(new Runnable() {
			@Override
			public void run() {
				int index = 0;
				while (!Thread.currentThread().isInterrupted()) {
					if (!controller.isPlay()) {
						controller.play();

					}
					index = new Random().nextInt(position.length);
					// Message m = handler.obtainMessage();
					Message m = Message.obtain();
					m.arg1 = index;
					m.what = 1;
					if (flag == true) {
						m.what = 2;
					}
					handler.sendMessage(m);
					try {
						Thread.sleep(new Random().nextInt(300) + diffity);

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		});

		handler = new Handler() {
			public void handleMessage(Message msg) {
				int index = 0;
				if (!flag)
					mouse.setImageResource(R.drawable.mouse);
				if (msg.what == 1) {
					index = msg.arg1;
					mouse.setX(position[index][0]);
					mouse.setY(position[index][1]);
					mouse.setVisibility(View.VISIBLE);
					// 设置mouse可编辑
					mouse.setEnabled(true);
				}
				if (msg.what == 2) {

					mouse.setImageResource(R.drawable.hit);
					mouse.setVisibility(View.VISIBLE);
					controller.playSound(R.raw.hit_sound);
					controller.playSound(R.raw.dizzy);
					flag = false;

				}

				super.handleMessage(msg);
			}
		};

		mouseThread.start();
		// 时间线程
		new Thread(new MyThread()).start();
		mouse.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// mouseTouch=false;
				mouse.setEnabled(false);
				flag = true;
				score += sum;
				scoreView.setText(score + "");
				return true;
			}
		});
	}

	final Handler timerHandler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				recLen--;
				timer.setText("" + recLen);
				if (recLen == 0) {
					Toast.makeText(HitActivity.this, "游戏结束，您的得分为" + score,
							Toast.LENGTH_LONG).show();
					
					Intent intent = new Intent(HitActivity.this,MainActivity.class);
					startActivity(intent);
					HitActivity.this.finish();
				}
				break;

			default:
			}
			super.handleMessage(msg);
		}
	};

	public class MyThread implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(1000);
					Message message = new Message();
					message.what = 1;
					timerHandler.sendMessage(message);
				} catch (Exception e) {
				}
			}
		}
	}

	@Override
	protected void onPause() {
		if (controller.isPlay()) {
			controller.pauseMusic();
		}
		super.onPause();

	}

	@Override
	protected void onDestroy() {
		if (controller.isPlay()) {
			controller.pauseMusic();
		}
		super.onDestroy();

	}
}
