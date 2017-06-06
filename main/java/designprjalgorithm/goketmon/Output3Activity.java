package designprjalgorithm.goketmon;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Output3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output3);

        final AssetManager am = getResources().getAssets();
        TextView result = (TextView) findViewById(R.id.result3);
        String temp = "";
        for(int i = 0; i<Manager.getManagerInstance(am).getSolutionList3().size(); i++)
        {
            temp += "Vertex : ";
            temp +=  String.valueOf(Manager.getManagerInstance(am).getSolutionList3().get(i).getVertexNum());
            temp += "  ";
            temp += "get ";
            int id= Manager.getManagerInstance(am).getSolutionList3().get(i).getMonsterId();
            int visited = Manager.getManagerInstance(am).getSolutionList3().get(i).getVistedFlag();
            if(id != 0 && visited == 0)
                temp += Manager.getManagerInstance(am).getMonsterName(id);
            else
                temp += Manager.getManagerInstance(am).getMonsterName(id);
            temp += "\n";
        }
        result.setText(temp);
        TextView monsterNum = (TextView)findViewById(R.id.timeOp3);
        monsterNum.setText("Time : " + String.valueOf(Manager.getManagerInstance(am).getTimeOp3()));
    }
}
