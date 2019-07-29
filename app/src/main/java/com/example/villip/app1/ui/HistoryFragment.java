package com.example.villip.app1.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.villip.app1.R;
import com.example.villip.app1.dataprovider.LinksContract;
public class HistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private final static int LOADER_ID = 0;

    private LinksAdapter linksAdapter;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        linksAdapter = new LinksAdapter(getActivity());
        recyclerView.setAdapter(linksAdapter);

        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);
    }

    public void restartLoader(){
        LoaderManager.getInstance(this).restartLoader(LOADER_ID, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(MainActivity.sortBy) {
            return new CursorLoader(
                    getActivity(),
                    LinksContract.Links.CONTENT_URI,
                    LinksContract.Links.DEFAULT_PROJECTION,
                    null,
                    null,
                    LinksContract.Links.STATUS
            );
        } else {
            return new CursorLoader(
                    getActivity(),
                    LinksContract.Links.CONTENT_URI,
                    LinksContract.Links.DEFAULT_PROJECTION,
                    null,
                    null,
                    LinksContract.Links._ID + " DESC"
            );
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        linksAdapter.setCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        linksAdapter.setCursor(null);
    }

    private class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.ViewHolder> {

        private Cursor cursor;
        private final Context mContext;

        private LinksAdapter(Context context) {
            this.mContext = context;
        }

        public void setCursor(Cursor cursor) {
            this.cursor = cursor;
            notifyDataSetChanged();
        }

        @Override
        public LinksAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.container_link, parent, false));
        }

        @Override
        public void onBindViewHolder(final LinksAdapter.ViewHolder holder, final int position) {
            if (cursor != null) {
                cursor.moveToPosition(position);
                holder.textView.setText(cursor.getString(cursor.getColumnIndex(LinksContract.Links.LINK)));

                int status = cursor.getInt(cursor.getColumnIndex(LinksContract.Links.STATUS));
                holder.setStatus(status);

                int id = cursor.getInt(cursor.getColumnIndex(LinksContract.Links._ID));
                holder.setID(id);

                if(status == 1){
                    holder.textView.setBackgroundColor(android.graphics.Color.GREEN);
                }  if(status == 2){
                    holder.textView.setBackgroundColor(android.graphics.Color.RED);
                }   if(status == 3){
                    holder.textView.setBackgroundColor(android.graphics.Color.GRAY);
                }

            }
        }

        @Override
        public int getItemCount() {
            if (cursor == null) {
                return 0;
            } else {
                return cursor.getCount();
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            private TextView textView;
            private int ID;
            private int status;

            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                textView = itemView.findViewById(R.id.textLink);
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            @Override
            public void onClick(View view) {
                Intent intent = new Intent("DeleteLinkApp2");
                intent.putExtra("linkForDeleteApp1", textView.getText().toString());
                intent.putExtra("IDForDeleteApp1", ID);
                intent.putExtra("StatusForDeleteApp1", status);
                startActivity(intent);
            }
        }
    }
}
