package com.example.soporte.myapplogin.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.soporte.myapplogin.R;
import com.example.soporte.myapplogin.utils.RSS.ParseRSS;
import com.example.soporte.myapplogin.utils.RSS.RssFeedModel;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * Created by soporte on 13/12/2017.
 */

public class rssFragment extends Fragment implements Response.ErrorListener, Response.Listener<String> {

    public rssFragment() {

    }

    ListView listado;
    StringRequest stringRequest;
    String RSS = "http://www.unillanos.edu.co/index.php?option=com_content&view=category&id=3&Itemid=16&format=feed&type=rss";
    List<RssFeedModel> listadoRSS;
    ProgressBar PB;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V =inflater.inflate(R.layout.fragment_rss, container, false);
        listado = (ListView)V.findViewById(R.id.listado);
        listado.setVisibility(View.INVISIBLE);

       // PB=(ProgressBar)V.findViewById(R.id.progressBar2);



        return V;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();

        stringRequest = new StringRequest(Request.Method.GET, RSS,
                this,this);

        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
    }

    @Override
    public void onResponse(String response) {
        //Log.i("RtaRss",response);
        try {
            listadoRSS = ParseRSS.ParseFeed(response);

            Adapter adapter=new noticiasAdapter();
            listado.setAdapter((ListAdapter) adapter);
            listado.setVisibility(View.VISIBLE);
           // PB.setVisibility(View.INVISIBLE);



        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    class noticiasAdapter extends BaseAdapter{
        LayoutInflater inflater;
        public noticiasAdapter(){
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return listadoRSS.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                //view = inflater.from(parent.getContext()).inflate(R.layout.item_promocion, parent, false);
                view = inflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, viewGroup, false);
            }
            String titulo = listadoRSS.get(i).getTitle();

            ((TextView)view.findViewById(android.R.id.text1)).setText(titulo);
            ((TextView)view.findViewById(android.R.id.text2)).setText(listadoRSS.get(i).getDate());

            return view;
        }
    }
}
