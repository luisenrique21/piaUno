package com.example.piano;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ingenieriajhr.blujhr.BluJhr;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    BluJhr bluetooth;
    List<String> requiredPermissions;
    ArrayList<String> devicesBluetooth = new ArrayList<String>();
    LinearLayout viewConn;
    ListView listDeviceBluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bluetooth = new BluJhr(this);
        bluetooth.onBluetooth();
        listDeviceBluetooth = findViewById(R.id.disList);

        listDeviceBluetooth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long j) {
                if (!devicesBluetooth.isEmpty()) {
                    bluetooth.connect(devicesBluetooth.get(i));
                    bluetooth.setDataLoadFinishedListener(new BluJhr.ConnectedBluetooth() {
                        @Override
                        public void onConnectState(@NotNull BluJhr.Connected connected) {
                            if (connected == BluJhr.Connected.True) {
                                Toast.makeText(getApplicationContext(), "True", Toast.LENGTH_SHORT).show();
                                listDeviceBluetooth.setVisibility(View.GONE);
                                viewConn.setVisibility(View.VISIBLE);
                                rxReceived();
                            } else {
                                if (connected == BluJhr.Connected.Pending) {
                                    Toast.makeText(getApplicationContext(), "Pending", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (connected == BluJhr.Connected.False) {
                                        Toast.makeText(getApplicationContext(), "False", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (connected == BluJhr.Connected.Disconnect) {
                                            Toast.makeText(getApplicationContext(), "Desconectado", Toast.LENGTH_SHORT).show();
                                            listDeviceBluetooth.setVisibility(View.VISIBLE);
                                            viewConn.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
    }
        private void rxReceived() {

            bluetooth.loadDateRx(new BluJhr.ReceivedData() {
                @Override
                public void rxDate(@NonNull String s) {
                    //consola.setText(consola.getText().toString()+s);
                }
            });

        }
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if (bluetooth.checkPermissions(requestCode,grantResults)){
                Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show();
                bluetooth.initializeBluetooth();
            }else{
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.S){
                    bluetooth.initializeBluetooth();
                }else{
                    Toast.makeText(this, "Algo salio mal", Toast.LENGTH_SHORT).show();
                }
            }

            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
}