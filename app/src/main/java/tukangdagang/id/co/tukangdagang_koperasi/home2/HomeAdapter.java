package tukangdagang.id.co.tukangdagang_koperasi.home2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import tukangdagang.id.co.tukangdagang_koperasi.BeritaKoprasi;
import tukangdagang.id.co.tukangdagang_koperasi.R;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.path;

/**
 * Created by User on 2/12/2018.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private static final String TAG = "HomeAdapter";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mIdkoperasi = new ArrayList<>();
    private Context mContext;

    public HomeAdapter(Context context, ArrayList<String> names, ArrayList<String> imageUrls, ArrayList<String> idKoperasi) {
        mNames = names;
        mImageUrls = imageUrls;
        mIdkoperasi = idKoperasi;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
if (mImageUrls.get(position).equals("")) {
    Glide.with(mContext)
            .asBitmap()
            .load(R.drawable.profile)
            .into(holder.image);
}else{
    Glide.with(mContext)
            .asBitmap()
            .load(path+mImageUrls.get(position))
            .into(holder.image);
}

        holder.name.setText(mNames.get(position));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on an image: " + mNames.get(position));
//                Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(),BeritaKoprasi.class);
                intent.putExtra("namakoperasi", mNames.get(position));
                intent.putExtra("idkoperasi", mIdkoperasi.get(position));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.titleTextView);
        }
    }
}
