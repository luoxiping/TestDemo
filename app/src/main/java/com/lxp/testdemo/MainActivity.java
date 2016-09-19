package com.lxp.testdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private EditText edit_address, edit_message;
    private Button btn_connect, btn_send;
    private TextView text_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
        initListener();

    }

    private void initWidget() {
        edit_address = (EditText) findViewById(R.id.edit_address);
        edit_message = (EditText) findViewById(R.id.edit_message);
        btn_connect = (Button) findViewById(R.id.btn_connect);
        btn_send = (Button) findViewById(R.id.btn_send);
        text_message = (TextView) findViewById(R.id.text_message);
    }

    private void initListener() {
        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
    }

    private Socket socket = null;
    private BufferedWriter writer = null;
    private BufferedReader reader = null;

    private void connect(){
        final String ipAddress = edit_address.getText().toString().trim();
            AsyncTask<Void, String, Void> read = new AsyncTask<Void, String, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        socket = new Socket(ipAddress, 12345);
                        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        publishProgress("@success");
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "连接失败!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    try {
                        String line;
                        while ((line = reader.readLine()) != null){
                            publishProgress(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onProgressUpdate(String... values) {
                    if (values[0].equals("@success")){
                        Toast.makeText(MainActivity.this, "连接成功!", Toast.LENGTH_SHORT).show();
                    }
                    text_message.append(values[0]);
                    super.onProgressUpdate(values);
                }
            };
            read.execute();

    }

    private void send(){
        try {
            writer.write(edit_message.getText().toString());
            edit_message.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
