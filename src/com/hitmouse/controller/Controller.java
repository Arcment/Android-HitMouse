package com.hitmouse.controller;

import com.hitmouse.service.MusicService;

import android.content.Context;

public class Controller {
	Context context;  
    MusicService musicService;
	public Controller(Context context) {
		this.context=context;
		musicService = new MusicService(context);
	}   
	 public void play() {  
	        musicService.playBackground();  
	    }  
	   
	    public void stop() {  
	        musicService.stop();  
	    }  
	   
	    public void pauseMusic() {  
	        musicService.pause();  
	    }  
	    
	    public void continuePlay() {  
	        musicService.continuePlay();  
	    }  
	      
	    public void close() {  
	        musicService.close();  
	    }  
	   
	    public void playSound(int resId) {  
	        musicService.playSound(resId);  
	    }  
	    public boolean isPlay(){
	    	return musicService.isNowPlay();
	    }
	    
}
