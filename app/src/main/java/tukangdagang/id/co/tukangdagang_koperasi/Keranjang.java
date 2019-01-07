package tukangdagang.id.co.tukangdagang_koperasi;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.app.ModelKeranjang;
import tukangdagang.id.co.tukangdagang_koperasi.app.RvKeranjangAdapter;



/**
 * A simple {@link Fragment} subclass.
 */
public class Keranjang extends Fragment {
    List<ModelKeranjang> lstKeranjang ;
    RvKeranjangAdapter myAdapter;
    RecyclerView myrv ;


    public Keranjang() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_keranjang, container, false);
        // Inflate the layout for this fragment


        lstKeranjang = new ArrayList<>();
        lstKeranjang.add(new ModelKeranjang("Cukur Rambut","Jasa","RP. 200.000","RP. 200.000","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.sticker));
        lstKeranjang.add(new ModelKeranjang("Install Ulang","Jasa","RP. 200.000","RP. 200.000","2 Kilo Gram","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",R.drawable.poster));

        myrv = (RecyclerView) rootView.findViewById(R.id.rvKeranjang);
        myAdapter = new RvKeranjangAdapter(getActivity(),lstKeranjang);
//        myrv.setLayoutManager(new GridLayoutManager(this.getContext(),1));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        myrv.setLayoutManager(layoutManager);
        myrv.setAdapter(myAdapter);
        return rootView;

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
