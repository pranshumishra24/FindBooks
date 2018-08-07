package com.example.pranshumishra.findbooks;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(new BooksAdapter(this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!QueryUtils.books.get(position).getBuyLink().isEmpty()){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(QueryUtils.books.get(position).getBuyLink()));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getBaseContext(),"No link to buy",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
