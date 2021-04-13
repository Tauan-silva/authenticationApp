package com.tauan.authenticationapp.presentation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.tauan.authenticationapp.databinding.FragmentRegisterBinding;
import com.tauan.authenticationapp.viewmodel.UserViewModel;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private NavController navController;
    private UserViewModel viewModel;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        SimpleMaskFormatter formatter = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher maskTextWatcher = new MaskTextWatcher(binding.edtPhoneSignUp, formatter);
        binding.edtPhoneSignUp.addTextChangedListener(maskTextWatcher);

        binding.btnSignUp.setOnClickListener(view1 -> {
            if (!validateEmail() && !validateName() && !validatePassword() && !validatePhone()) {
                return;
            } else {
                    String name, email, password, phone;
                    name = binding.edtNameSignUp.getText().toString().trim();
                    email = binding.edtEmailSignUp.getText().toString().trim();
                    password = binding.edtPasswordSignUp.getText().toString().trim();
                    phone = binding.edtPhoneSignUp.getText().toString().trim();

                    RegisterFragmentDirections.ActionRegisterFragmentToHomeFragment action;
                    action = RegisterFragmentDirections.actionRegisterFragmentToHomeFragment(name, email, password, phone);
                    navController.navigate(action);


            }
        });
    }

    private Boolean validateName() {
        String name = binding.edtNameSignUp.getText().toString().trim();
        if (name.isEmpty()) {
            binding.containerNameSignUp.setError("Field cannot be empty");
            return false;
        } else if (name.length() < 4) {
            binding.containerNameSignUp.setError("Name too short");
            return false;
        } else if (name.length() > 15) {
            binding.containerNameSignUp.setError("Name too long");
            return false;
        } else {
            binding.containerNameSignUp.setError(null);
            binding.containerNameSignUp.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        String email = binding.edtEmailSignUp.getText().toString().trim();
        if (email.isEmpty()) {
            binding.containerEmailSignUp.setError("Field cannot be empty");
            return false;
        } else if (!email.matches(emailPattern)) {
            binding.containerEmailSignUp.setError("Email invalid");
            return false;
        } else if (email.matches("\\A\\w{4,20}\\z")) {
            binding.containerEmailSignUp.setError("White spaces are not allowed");
            return false;
        } else {
            binding.containerEmailSignUp.setError(null);
            binding.containerEmailSignUp.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String password = binding.edtPasswordSignUp.getText().toString().trim();
        if (password.isEmpty()) {
            binding.containerPasswordSignUp.setError("Password is required");
            return false;
        } else if (password.length() < 6) {
            binding.containerPasswordSignUp.setError("Password must be 6 or more character");
            return false;
        } else {

            binding.containerPasswordSignUp.setError(null);
            binding.containerPasswordSignUp.setErrorEnabled(false);
            return true;

        }
    }

    private Boolean validatePhone() {
        String phone = binding.edtPhoneSignUp.getText().toString().trim();

        if (phone.isEmpty()) {
            binding.containerPhoneSignUp.setError("Number phone is required");
            return false;
        } else if (phone.length() < 11) {
            binding.containerPhoneSignUp.setError("Number phone invalid");
            return false;
        } else {
            binding.containerPhoneSignUp.setError(null);
            binding.containerPhoneSignUp.setErrorEnabled(false);
            return true;
        }
    }
}