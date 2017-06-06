package designprjalgorithm.goketmon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class Option3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option3);
    }

    public void onClickHome(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickContinueOp3(View view)
    {
        final AssetManager am = getResources().getAssets();
        Manager.getManagerInstance(am).calcAlgorithm3();
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(Option3Activity.this);
        alt_bld.setMessage("Agree to check the outputs?").setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        Intent intent = new Intent(Option3Activity.this, Output3Activity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                dialog.cancel();
            }
        });
        AlertDialog alert = alt_bld.create();
        alert.setTitle("Move to output");
        alert.show();
    }
}
