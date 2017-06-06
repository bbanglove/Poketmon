package designprjalgorithm.goketmon;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Output2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output2);

        final AssetManager am = getResources().getAssets();
        TextView result = (TextView) findViewById(R.id.result2);
        String temp = "";
        int size =0;
        for (int i = 0; i < Manager.getManagerInstance(am).getSolutionList2().size(); i++)//전체 solutionList 출력을 위한 포문
        {
            temp  += "Vertex : ";
            temp +=  String.valueOf(Manager.getManagerInstance(am).getSolutionList2().get(i).getVertexNum());//vertex number
            int visited = Manager.getManagerInstance(am).getSolutionList2().get(i).getVistedFlag();
            temp += "  ";
            temp += "get ";
            int id= Manager.getManagerInstance(am).getSolutionList2().get(i).getMonsterId();
            if(id!= 0 && visited == 0)
            {
                size++;
                Manager.getManagerInstance(am).getSolutionList2().get(i).setVisitedFlag(1);
            }
            if(visited == 0)
                temp += Manager.getManagerInstance(am).getMonsterName(id);
            else
            {
                temp += "None";
            }
            temp += "\n";

        }
        result.setText(temp);

        TextView monsterNum = (TextView)findViewById(R.id.monsterNum2);
        monsterNum.setText("Num of monster : " + String.valueOf(size));
    }
}
