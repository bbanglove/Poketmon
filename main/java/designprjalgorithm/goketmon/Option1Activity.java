package designprjalgorithm.goketmon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;

public class Option1Activity extends Activity {
  // private static Manager appManager = Manager.getManagerInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option1);
       // appManager = Manager.getManagerInstance();
    }

    public void onClickHome(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickContinue(View view)//output result 로 이동
    {
        final int[] selectedItem = {0};
        final AssetManager am = getResources().getAssets();
        EditText inputtime = (EditText)findViewById(R.id.inputTime);
        int time = Integer.parseInt(inputtime.getText().toString());
        Manager.getManagerInstance(am).setTime(time);//입력받은 값을 inputTime으로 넣는다
        final EditText inputmonster = (EditText)findViewById(R.id.inputMonster);
        String monster = inputmonster.getText().toString();
        boolean inm = Manager.getManagerInstance(am).setMonsterByString(monster);
        if(!inm)//만일 존재하지 않는 포켓몬을 입력했을 경우
        {
            final String[] monsters = new String[]{"Pikachu", "Raichu", "Charmander", "Squirtle", "Butterfree", "Slowbro", "Pidgeott", "Koffing", "AeroDactyl", "Charizard"};
            AlertDialog.Builder nullMonster = new AlertDialog.Builder(Option1Activity.this);
            //알림창 속성 설정
            nullMonster.setTitle("Pick monster")//제목 설정
                    .setSingleChoiceItems(monsters, 0, new DialogInterface.OnClickListener(){ //목록클릭시 설정
                        public void onClick(DialogInterface dialog, int index) {
                            selectedItem[0] = index;
                        }
                    })
                    .setPositiveButton("OK",new DialogInterface.OnClickListener(){//확인 버튼 눌렀을때
                        public void onClick(DialogInterface dialog, int index){
                            Toast.makeText(Option1Activity.this, monsters[selectedItem[0]], Toast.LENGTH_SHORT).show();
                            Manager.getManagerInstance(am).setMonster(index + 1);
                        }
                    })
                    .setNeutralButton("CANCEL",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int index){
                            Toast.makeText(Option1Activity.this, "CANCELED", Toast.LENGTH_SHORT).show();
                        }
                    });

            nullMonster.create();//알림창 띄우기
            nullMonster.show();
        }//존재하지 않는 몬스터에 대한 예외처리 끝

        Manager.getManagerInstance(am).calcAlgorithm1();//첫번째 기능 구현 함수 호출

       // Intent intent = new Intent(this, OutputActivity.class);
       // startActivity(intent);
        this.DialogCheck();//페이지 이동 다이얼로그 호출
    }

    private void DialogCheck(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(Option1Activity.this);
        alt_bld.setMessage("Agree to check the outputs?").setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Intent intent = new Intent(Option1Activity.this, OutputActivity.class);
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
