package com.example.asm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm.TinTuc.DetailNewsActivity;
import com.example.asm.TinTuc.XMLParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class News extends AppCompatActivity {
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayLink = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ListView lv;
    Intent intent;
    EditText edtURL;
    Button btn;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("TIN TỨC");
        setContentView(R.layout.activity_news);

        lv = findViewById(R.id.list_TinTuc);
        edtURL = findViewById(R.id.edt_URL);
        btn = findViewById(R.id.btn_Tim);
        txt = findViewById(R.id.txt_hienThi);

        AsyncTask<String,Void,String> content = new RSSFeed().execute("https://ngoisao.net/rss/the-thao.rss");
        intent = new Intent(News.this, DetailNewsActivity.class);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String link = arrayLink.get(position);
                intent.putExtra("linkURL",link);
                startActivity(intent);
            }
        });
        arrayAdapter = new ArrayAdapter(News.this,android.R.layout.simple_list_item_1,arrayList);
        lv.setAdapter(arrayAdapter);
    }

    public void TimKiem(View view) {
        arrayList.remove(arrayLink);
        if (edtURL.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(News.this,"Thông tin không được để trống",Toast.LENGTH_SHORT).show();
            return;
        }
        txt.setText("Kết nối thành công: Tin đã được load về!");
        AsyncTask<String,Void,String> content = new RSSFeed().execute(edtURL.getText().toString());
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        arrayAdapter.notifyDataSetChanged();
        lv.setAdapter(arrayAdapter);
    }

    public class RSSFeed extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader reader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line="";
                while ((line=bufferedReader.readLine())!=null){
                    content.append(line);
                }
                bufferedReader.close();
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLParser xmlParse = new XMLParser();
            try {
                Document document = xmlParse.getDocument(s);
                NodeList nodeList = document.getElementsByTagName("item");
                String title="";
                for (int i=0;i<nodeList.getLength();i++){
                    Element element = (Element)nodeList.item(i);
                    title += xmlParse.getValue(element,"title")+"\n";
                    arrayList.add(title);
                    arrayLink.add(xmlParse.getValue(element,"link"));
                }
                arrayAdapter.notifyDataSetChanged();
            }catch (IOException e){
                e.printStackTrace();
            }catch (SAXException e){
                e.printStackTrace();
            }
        }
    }
}