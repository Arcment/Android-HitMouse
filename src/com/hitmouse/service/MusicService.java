package com.hitmouse.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.hitmouse.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class MusicService {
			MediaPlayer player;
			SoundPool pool;
			Context context;
			Map<Integer,Integer> soundMap;
			public MusicService(Context context) {
				super();
				this.context=context;
				initMedia();
				initSound();
			}
			private void initSound() {
				
				pool = new SoundPool(3, AudioManager.STREAM_MUSIC, 5);
				soundMap = new HashMap<Integer, Integer>();
				soundMap.put(R.raw.hit_sound, pool.load(context,R.raw.hit_sound,1));
				soundMap.put(R.raw.dizzy, pool.load(context,R.raw.dizzy,1));
				soundMap.put(R.raw.laugh, pool.load(context,R.raw.laugh,1));
				
			}
			private void initMedia() {
				player = MediaPlayer.create(context, R.raw.background_sound);
			 

				//循环
				player.setLooping(true);
				
				
			}
			public void playSound(int resId){
				Integer soundId =soundMap.get(resId);
				if(soundId!=null){
					pool.play(soundId, 1, 1, 1, 0, 1);
				}
				
			}
			public void playBackground(){
				player.start();
			}
			//停止播放
			public void stop(){
				if(player.isPlaying()){
					player.stop();
//					try {
//						player.prepare();
//						//再次开始后从头开始
//						player.seekTo(0);
//					} catch (IllegalStateException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
			}
			//暂停
			public void pause(){
				if(player.isPlaying()){
					player.pause();
				}
			}
			//继续
			public void continuePlay() {  
		        player.start();  
		    }  
			//退出游戏
			public void close(){
				if(player==null){
					return;
				}
				if(player.isPlaying()){
					player.stop();
				}
			}
			//判断当前音乐
			public Boolean isNowPlay(){  
		        if(player.isPlaying()){  
		            return true;  
		        }  
		        return false;  
		    }  
}
