package com.leafo3.main;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.leafo3.adapters.LeafRecordAdapter;
import com.leafo3.data.DBHelper;
import com.leafo3.main.R;
import com.leafo3.model.Leaf;

import java.util.List;

public class LeafRecordActivity extends ActionBarActivity {
    private DBHelper helper;
    private ListView listView;
    private List<Leaf> leafs;
    private LeafRecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaf_record);
        this.helper = new DBHelper(LeafRecordActivity.this);
        this.leafs = this.helper.getAll();
        this.listView = (ListView)findViewById(R.id.activity_record_list_view);
        if(this.leafs.isEmpty()){
            //shows a message
            TextView textView = (TextView)findViewById(R.id.activity_record_no_leafs_text_view);
            textView.setVisibility(View.VISIBLE);
            this.listView.setVisibility(View.GONE);
        }else{
            //set adapter
            this.adapter = new LeafRecordAdapter(LeafRecordActivity.this, leafs);
            this.listView.setAdapter(this.adapter);
        }
    }


}
