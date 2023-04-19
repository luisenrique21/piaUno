package com.example.piano;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.ingenieriajhr.blujhr.BluJhr;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.service.autofill.OnClickAction;
import android.view.InputEvent;
import android.view.View;
import android.os.Build;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.piano.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ActivityMainBinding binding2;
    private BluJhr bluetooth;
    List<String> requiredPermissions;
    ArrayList<String> devicesBluetooth = new ArrayList<String>();
    LinearLayout viewConn;
    ListView listDeviceBluetooth;
    Button blueon;
    // Button buttonSend;
    TextView consola;
    EditText edtTx;
    BluetoothAdapter bluetoothAdapter;

    // private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    /*private BluetoothProfile.ServiceListener profileListener = new BluetoothProfile.ServiceListener() {

        public void onServiceEnable(){
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            if(profile==BluetoothProfile.HEADSET){
                bluetoothHeadset=(BluetoothHeadset) proxy;
            }
        }
        public void onServiceDisconnected(int profile) {
            if(profile == BluetoothProfile.HEADSET){
                bluetoothHeadset = null;
            }
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //IntentFilter filter= new IntentFilter(BluetoothDevice.ACTION_FOUND);
        //registerReceiver(receiver, filter);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();

        blueon=findViewById(R.id.button);
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Agregar piano", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        blueon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bluetoothAdapter==null){
                    showToast("Bluetooth no disponible");
                }
                else {
                    if (!bluetoothAdapter.isEnabled()) {
                        showToast("Encendiendo Bluetooth...");
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(intent, REQUEST_ENABLE_BT);
                    } else {
                        showToast("Bluetooth encendido");
                    }
                }
            }
        });



        /*ListView dislist= findViewById(R.id.listDeviceBluetooth2);
        final BluJhr bluetooth = new BluJhr(this, dislist,MainActivity.class);
        //BluJhr bluetooth;
        bluetooth.onBluetooth();

        dislist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
               // bluetooth.bluetoothSeleccion(i);
                bluetooth.deviceBluetooth();
            }
        });*/

    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

     /*private void rxReceived() {
 
         bluetooth.loadDateRx(new BluJhr.ReceivedData() {
             @Override
             public void rxDate(@NonNull String s) {
                 //consola.setText(consola.getText().toString()+s);
             }
         });
 
     }*/
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
   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (!bluetooth.stateBluetoooth() && requestCode == 100){
            bluetooth.initializeBluetooth();
        }else{
            if (requestCode == 100){
                devicesBluetooth = bluetooth.deviceBluetooth();
                if (!devicesBluetooth.isEmpty()){
                    ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,devicesBluetooth);
                    listDeviceBluetooth.setAdapter(adapter);
                }else{
                    Toast.makeText(this, "No tienes vinculados dispositivos", Toast.LENGTH_SHORT).show();
                }

            }
        }

        super.onActivityResult(requestCode, resultCode, data);

     }   */

   /*private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
            }
        }
    };
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}