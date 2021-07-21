package com.hitmouse.ui;

import com.example.hitmouse.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
		
		
public class DiffityActivity extends Activity implements OnClickListener {
	Button easy;
	Button medium;
	Button difficult;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diffity_layout);
        easy = (Button) findViewById(R.id.easy);
        medium = (Button) findViewById(R.id.medium);
        difficult = (Button) findViewById(R.id.difficult);
        easy.setOnClickListener(this);
        medium.setOnClickListener(this);
        difficult.setOnClickListener(this);
        }
	
	@Override
	public void onClick(View view) {
		 Intent intent = new Intent(DiffityActivity.this,HitActivity.class);
		switch (view.getId()) {
		case R.id.easy:
			String time1 = 1000+"";
			String score1 = 2+"";
			intent.putExtra("diffity", time1);
			intent.putExtra("score", score1);
			startActivity(intent);
			finish();
			break;
		case R.id.medium:
			String time2 = 500+"";
			String score2 = 3+"";
			intent.putExtra("diffity", time2);
			intent.putExtra("score", score2);
			startActivity(intent);
			finish();
			break;
		case R.id.difficult:
			String time3 = 300+"";
			String score3 = 4+"";
			intent.putExtra("diffity", time3);
			intent.putExtra("score", score3);
			startActivity(intent);
			finish();
			break;	
		default:
			break;
		}
		
	}
	
}
