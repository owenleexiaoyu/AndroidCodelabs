package cc.lixiaoyu.cakeknife.sample;

import androidx.appcompat.app.AppCompatActivity;
import cc.lixiaoyu.cakeknife.CakeKnife;
import cc.lixiaoyu.cakeknife.annotations.BindView;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_textview)
    public TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CakeKnife.bind(this);
        mTextView.setText("Owen");
    }
}
