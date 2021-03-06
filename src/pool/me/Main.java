package pool.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener{
private View profile, myPool, findPool, messages, rewards, route;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        profile = (ImageButton)findViewById(R.id.profile);
        myPool = (ImageButton)findViewById(R.id.my_pool);
        findPool = (ImageButton)findViewById(R.id.find_pool);
        messages = (ImageButton)findViewById(R.id.messages);
        rewards = (ImageButton)findViewById(R.id.rewards);
        route = (ImageButton)findViewById(R.id.route);
        
        profile.setOnClickListener((OnClickListener) this);      
        findPool.setOnClickListener((OnClickListener) this);
        messages.setOnClickListener((OnClickListener) this);
        rewards.setOnClickListener((OnClickListener) this);
        route.setOnClickListener((OnClickListener) this);
        myPool.setOnClickListener(this);
        
	}
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	   MenuInflater inflater = getMenuInflater();
	   inflater.inflate(R.menu.menu, menu);
	   return true;
	}
	*/
	public void onClick(View view){
		if (view == this.profile){
			this.startActivity(new Intent(this, Profile.class));
			Toast toast = Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_LONG);
			toast.show();
		}else if (view == this.myPool){
			this.startActivity(new Intent(this, My_Pool.class));
		}else if (view == this.findPool){
			this.startActivity(new Intent(this, Find_Pool.class));
		}else if (view == this.messages){
			this.startActivity(new Intent(this, Messages.class));
		}else if (view == this.rewards){
			this.startActivity(new Intent(this, Coming_Soon.class));
		}else if (view == this.route){
			this.startActivity(new Intent(this, Route_Map.class));	
		}
	}
	
}
