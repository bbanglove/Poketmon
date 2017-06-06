package designprjalgorithm.goketmon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //private Manager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isRunIntro = getIntent().getBooleanExtra("intro", true);
        if(isRunIntro){
            beforeIntro();
        } else{
            afterIntro(savedInstanceState);

        }
    }

    //인트로 화면
    private void beforeIntro(){
        //2초간 인트로화면을 출력한다
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("intro", false);
                startActivity(intent);
                //액티비티 이동시 페이드효과를 통해 부드러운 처리가 되도록
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        }, 2000);
    }

    //인트로 화면 이후
    private void afterIntro(Bundle savedInstanceState){
        //기본 테마 지정
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
    }

    //Option1 onClick 이벤트
    public void onClickOption1(View view)
    {
        Intent intent = new Intent(this, Option1Activity.class);
        startActivity(intent);
    }
    //Option2 onClick 이벤트
    public void onClickOption2(View view)
    {
        Intent intent = new Intent(this, Option2Activity.class);
        startActivity(intent);
    }
    //Option3 onClick 이벤트
    public void onClickOption3(View view)
    {
        Intent intent = new Intent(this, Option3Activity.class);
        startActivity(intent);
    }
    //profile Activity 이벤트


}
