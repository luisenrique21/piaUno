package com.example.piano;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ingenieriajhr.blujhr.BluJhr;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.example.piano.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding2;
    private BluJhr bluetooth;
    Boolean initConexion=false;
    Boolean offHilo=false;
    TextView pantalla;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding2 = FragmentSecondBinding.inflate(inflater, container, false);
        return binding2.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding2.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
        //  final ListView dispList = binding.dispList;
     /*   bluetooth.onBluetooth();
        binding.dispList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                bluetooth.deviceBluetooth();
                //bluetooth.deviceBluetooth();
            }
        });*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding2 = null;
    }

}