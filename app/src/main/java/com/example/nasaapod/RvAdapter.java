package com.example.nasaapod;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nasaapod.dto.Apod;
import com.example.nasaapod.io.NasaService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * https://api.nasa.gov/planetary/apod?date=2019-05-01
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    private final CompositeDisposable compositeDisposable;
    private List<Apod> values;


    public RvAdapter(List<Apod> myDataset) {
        values = myDataset;

        this.compositeDisposable = new CompositeDisposable();
    }

    public RvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.rowlayout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.ViewHolder holder, int position) {

        Apod apod = values.get(position);
        holder.dateTextView.setText(apod.getDate());

        if (apod.getUrl() != null && apod.getUrl().startsWith("https://apod.nasa.gov/")) {
            Log.d("RvAdapter", "Date: " + apod.getDate() + " url: " + apod.getUrl());
            String imagePath = apod.getUrl().substring( "https://apod.nasa.gov/".length());

            Disposable disposable = NasaService.getInstance().getImage(imagePath)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(holder.imageView::setImageBitmap);

            compositeDisposable.add(disposable);

        }

    }

    public void updateApod(Apod apod) {

        int position = values.indexOf(apod);
        values.set(position, apod);
        notifyItemChanged( position);
    }


    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        compositeDisposable.dispose();
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final View layout;
        private final TextView dateTextView;
        private final ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView;
            dateTextView = (TextView) itemView.findViewById(R.id.textView3);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
