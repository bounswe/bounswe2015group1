package com.boun.swe.wawwe.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.boun.swe.wawwe.Utils.API;

/**
 * Created by Mert on 31/10/15.
 *
 * <p>
 *    Parent of every Fragment so commons in
 *    fragments will be implemented in here.
 * </p>
 * <p>
 *     Also fragments will be treated as BaseFragment
 *     Ex; fragment transactions...
 * </p>
 */
public class BaseFragment extends Fragment {

    public String TAG;
    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        API.cancelRequestByTag(this.getClass().getSimpleName());
    }
}
