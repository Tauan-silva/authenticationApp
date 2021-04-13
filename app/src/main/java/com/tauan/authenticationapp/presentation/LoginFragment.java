package com.tauan.authenticationapp.presentation;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tauan.authenticationapp.R;
import com.tauan.authenticationapp.data.User;
import com.tauan.authenticationapp.databinding.FragmentLoginBinding;
import com.tauan.authenticationapp.viewmodel.UserViewModel;

import java.util.concurrent.atomic.AtomicReference;


public class LoginFragment extends Fragment implements View.OnClickListener{

    private FragmentLoginBinding binding;
    private NavController navController;
    private UserViewModel viewModel;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding.btnLogin.setOnClickListener(this);
        binding.btnTxtFirstTime.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onClick(View view) {
        String email, password;
        email = binding.edtEmailLogin.getText().toString().trim();
        password = binding.edtPasswordLogin.getText().toString().trim();
        if(view.getId() == R.id.btnLogin){
            if (!validateEmail() && !validatePassword()) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            } else {

                LoginFragmentDirections.ActionLoginFragmentToHomeFragment action;
                action = LoginFragmentDirections.actionLoginFragmentToHomeFragment("name", email, "phone");
                navController.navigate(action);
            }
        }else if(view.getId() == R.id.btnTxtFirstTime){
            navController.navigate(R.id.action_loginFragment_to_registerFragment);
        }
    }


    private Boolean validateEmail() {
        String emailPattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        String email = binding.edtEmailLogin.getText().toString().trim();
        if (email.isEmpty()) {
            binding.containerEmailLogin.setError("Field cannot be empty");
            return false;
        } else if (!email.matches(emailPattern)) {
            binding.containerEmailLogin.setError("Email invalid");
            return false;
        } else {
            binding.containerEmailLogin.setError(null);
            binding.containerEmailLogin.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String password = binding.edtPasswordLogin.getText().toString().trim();
        if (password.isEmpty()) {
            binding.containerPasswordLogin.setError("Field cannot be empty");
            return false;
        }
        if (!password.matches("\\A\\w{4,20}\\z")) {
            binding.containerPasswordLogin.setError("White spaces are not allowed");
            return false;
        }

        if(password.length() < 6){
            binding.containerPasswordLogin.setError("Password must be 6 or more character");
            return false;
        }else {
            binding.containerPasswordLogin.setError(null);
            binding.containerPasswordLogin.setErrorEnabled(false);
            return true;
        }
    }
}