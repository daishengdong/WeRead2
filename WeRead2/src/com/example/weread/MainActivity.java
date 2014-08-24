package com.example.weread;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {
	
	private ImageButton imgbt_localbook;
	private ImageButton imgbt_bookstore;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imgbt_localbook = (ImageButton)findViewById(R.id.imgbt_localbook);
		imgbt_bookstore = (ImageButton)findViewById(R.id.imgbt_bookstore);
		ImgbtListener listener = new ImgbtListener();
		imgbt_localbook.setOnClickListener(listener);
		imgbt_bookstore.setOnClickListener(listener);
	}
	
	class ImgbtListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch(v.getId())
			{
			
			case R.id.imgbt_localbook:
				
				intent.setClass(MainActivity.this, LocalBook.class);
				startActivity(intent);
        		MainActivity.this.finish();
				break;
			case R.id.imgbt_bookstore:
	
				intent.setClass(MainActivity.this, BookStoreActivity.class);
				startActivity(intent);
        		MainActivity.this.finish();
				break;
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
