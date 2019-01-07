package tukangdagang.id.co.tukangdagang_koperasi.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tukangdagang.id.co.tukangdagang_koperasi.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class faCaripedagang extends Fragment {


    public faCaripedagang() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_caripedagang, container, false);
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
