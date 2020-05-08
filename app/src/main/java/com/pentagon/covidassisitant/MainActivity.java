package com.pentagon.covidassisitant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private SwipeRefreshLayout mSwipe;
    private RecyclerView mRecycler;
    private List<Case> mList;
    private TextView mTest, mChat, mDate, mTotal, mRecovered, mDead, mTextSwipe;
    private Button mMore;
    private GraphView mGraph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);
        mTest = findViewById(R.id.activity_main_text_view_test);
        mSwipe = findViewById(R.id.activity_main_swipe);
        mTextSwipe = findViewById(R.id.activity_main_text_view_swipe);
        mRecycler = findViewById(R.id.activity_main_recycler);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mChat = findViewById(R.id.activity_main_text_view_chat);
        mTotal = findViewById(R.id.activity_main_text_view_total);
        mDate = findViewById(R.id.activity_main_text_view_date);
        mRecovered = findViewById(R.id.activity_main_text_view_recovered);
        mDead = findViewById(R.id.activity_main_text_view_dead);
        mGraph = findViewById(R.id.activity_main_graph_view);
        mMore = findViewById(R.id.activity_main_button_more);
        mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }
        });
        mTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });
        mChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ChatActivity.class));
            }
        });
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                jsonParse();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipe.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        jsonParse();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTextSwipe.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTextSwipe.setVisibility(View.INVISIBLE);
            }
        }, 1500);
    }

    private void jsonParse() {
        String url = "https://api.covid19india.org/data.json";
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    LineGraphSeries<DataPoint> Total, Recovered, Dead;
                    Total = new LineGraphSeries<>();
                    Recovered = new LineGraphSeries<>();
                    Dead = new LineGraphSeries<>();
                    int T, R, D;
                    JSONArray jsonArray = response.getJSONArray("cases_time_series");
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        T = Integer.parseInt(object.getString("totalconfirmed"));
                        D = Integer.parseInt(object.getString("totaldeceased"));
                        R = Integer.parseInt(object.getString("totalrecovered"));
                        Total.appendData(new DataPoint(i, T), true, 80);
                        Recovered.appendData(new DataPoint(i, R), true, 80);
                        Dead.appendData(new DataPoint(i, D), true, 80);
                        if (i == jsonArray.length()-1){
                            mDate.setText(object.getString("date"));
                            mTotal.setText(object.getString("totalconfirmed"));
                            mRecovered.setText(object.getString("totalrecovered"));
                            mDead.setText(object.getString("totaldeceased"));
                        }
                    }
                    Total.setColor(Color.BLUE);
                    Recovered.setColor(Color.GREEN);
                    Dead.setColor(Color.RED);
                    mGraph.getViewport().setMaxX(jsonArray.length());
                    mGraph.getViewport().setXAxisBoundsManual(true);
                    mGraph.setTitle("Corona cases in india (30 Jan onwards)");
                    mGraph.addSeries(Total);
                    mGraph.addSeries(Recovered);
                    mGraph.addSeries(Dead);
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                try {
                    mList.clear();
                    JSONArray jsonArrayState = response.getJSONArray("statewise");
                    for (int i = 0; i<jsonArrayState.length(); i++){
                        JSONObject object = jsonArrayState.getJSONObject(i);
                        Case newCase = new Case(object.getString("state"),
                                object.getString("recovered"),
                                object.getString("deaths"),
                                object.getString("confirmed"));
                        mList.add(newCase);
                    }
                    CaseAdapter caseAdapter = new CaseAdapter(MainActivity.this, mList);
                    mRecycler.setAdapter(caseAdapter);
                }catch (JSONException e){
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);
    }

}