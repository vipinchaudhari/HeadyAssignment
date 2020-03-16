package com.heady.ecommerce.view.fragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.heady.ecommerce.utils.Constants;
import com.heady.ecommerce.view.activity.MainActivity;

public class BaseFragment extends Fragment implements Constants {
    protected MainActivity mainActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }
}
