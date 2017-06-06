package designprjalgorithm.goketmon;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;

public class OutputActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        final AssetManager am = getResources().getAssets();
        TextView result = (TextView) findViewById(R.id.result1);
        String temp = "";
        for (int i = 0; i < Manager.getManagerInstance(am).getSolutionList1().size(); i++)//전체 solutionList 출력을 위한 포문
        {
            temp  += "Vertex : ";
            temp +=  String.valueOf(Manager.getManagerInstance(am).getSolutionList1().get(i).getVertexNum());//vertex number
            temp += "\n";
        }
        result.setText(temp);

        TextView monsterNum = (TextView)findViewById(R.id.monsterNum);
        monsterNum.setText("Num of monster : " + String.valueOf(Manager.getManagerInstance(am).getCatchNum()));
    }
}
