package com.example.logonpf.demomapaspoc;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.logonpf.demomapaspoc.adapter.LinhaAdapter;
import com.example.logonpf.demomapaspoc.adapter.OnItemClickListener;
import com.example.logonpf.demomapaspoc.api.ApiUtils;
import com.example.logonpf.demomapaspoc.api.MetroAPI;
import com.example.logonpf.demomapaspoc.model.Linha;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvLinhas;
    private LinhaAdapter mAdapter;
    private MetroAPI mService;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvLinhas = (RecyclerView) findViewById(R.id.rvLinhas);

        mService = ApiUtils.getMetroAPI();

        mAdapter = new LinhaAdapter(new ArrayList<Linha>(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(Linha linha) {
                        Intent telaMapa = new Intent(MainActivity.this, MapsActivity.class);
                        telaMapa.putExtra("LINHA", linha);
                        startActivity(telaMapa);
               /* Toast.makeText(getApplicationContext(),
                        item.getCor(), Toast.LENGTH_SHORT).show();*/
                    }
                });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvLinhas.setLayoutManager(layoutManager);
        rvLinhas.setAdapter(mAdapter);
        rvLinhas.setHasFixedSize(true);

        loadLinhas();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    public void loadLinhas() {

        mService.getLinhas()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Linha>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), "Deu ruim",
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<Linha> linhas) {
                        mAdapter.update(linhas);
                    }
                });
    }

}
