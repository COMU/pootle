package com.example.bsrakdag.pootle;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bsrakdag on 23.04.2015.
 */
public class AboutFragment extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Fragment içi düzenin oluşturulduğu alan(www.cagrikacmaz.com)
        View view =  inflater.inflate(R.layout.fragment_abaout,
                container, false);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;
                } else {
                    return false;
                }
            }
        });
        return view;
    }
    @Override
    public void onPause(){
        super.onPause();

    }
}
