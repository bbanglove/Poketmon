package designprjalgorithm.goketmon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

public class Option2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option2);
    }

    public void onClickHome(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickContinueOp2(View view)
    {
        final AssetManager am = getResources().getAssets();
        EditText inputtime = (EditText)findViewById(R.id.inputTimeOp2);
        int time = Integer.parseInt(inputtime.getText().toString());
        Manager.getManagerInstance(am).setTime(time);//입력받은 값을 inputTime으로 넣는다
        Manager.getManagerInstance(am).calcAlgorithm2(am);
        this.DialogCheck();
    }

    private void DialogCheck(){

        AlertDialog.Builder alt_bld = new AlertDialog.Builder(Option2Activity.this);
        alt_bld.setMessage("Agree to check the outputs?").setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        Intent intent = new Intent(Option2Activity.this, Output2Activity.class);
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
