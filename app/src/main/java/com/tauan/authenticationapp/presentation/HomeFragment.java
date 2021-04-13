package com.tauan.authenticationapp.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tauan.authenticationapp.databinding.FragmentHomeBinding;
import com.tauan.authenticationapp.viewmodel.UserViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private UserViewModel viewModel;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;

        String name = HomeFragmentArgs.fromBundle(getArguments()).getName();
        String email = HomeFragmentArgs.fromBundle(getArguments()).getEmail();
        String phone = HomeFragmentArgs.fromBundle(getArguments()).getPhone();

        binding.phoneHome.setText(phone);
        binding.emailHome.setText(email);
        binding.userNameHome.setText(name);

    }
}

