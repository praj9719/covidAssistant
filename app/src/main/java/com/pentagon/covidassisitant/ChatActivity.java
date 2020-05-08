package com.pentagon.covidassisitant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private EditText mEdit;
    private TextView mSend, mTyping;
    private RecyclerView mRecycler;
    private Button mClear;
    private List<Message> mList;
    private String reply, newMessage;
    private RequestQueue mQueue;
    private JSONArray jsonArray;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mQueue = Volley.newRequestQueue(this);
        mRecycler = findViewById(R.id.activity_chat_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);
        mList = new ArrayList<>();
        mTyping = findViewById(R.id.activity_chat_text_view_typing);
        mClear = findViewById(R.id.activity_chat_button_clear);
        mEdit = findViewById(R.id.activity_chat_edit_text);
        mSend = findViewById(R.id.activity_chat_text_view_send);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatActivity();
            }
        });
        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearChat();
            }
        });
        Message message = new Message("नमस्कार\nमी तुमच्या मदतीसाठी येथे आहे. आपण मला कोरोना विषाणूशी संबंधित काहीही विचारू शकता.\neg. corona cases in india or in any other state, कोरोनाची लक्षणे, precautions etc.", false);
        display(message);
        volley();

    }

    private void clearChat() {
        mList.clear();
        Message message = new Message("नमस्कार\nमी तुमच्या मदतीसाठी येथे आहे. आपण मला कोरोना विषाणूशी संबंधित काहीही विचारू शकता.\neg. corona cases in india or in any other state, कोरोनाची लक्षणे, precautions etc.", false);
        display(message);
    }

    private void chatActivity() {
        newMessage = mEdit.getText().toString().trim();
        if (!newMessage.equals("")){
            Message message = new Message(newMessage, true);
            display(message);
            mEdit.setText("");
            mTyping.setVisibility(View.VISIBLE);
            reply = botReply(newMessage);
            mHandler.postDelayed(replyRunnable, 2000);
        }
    }

    private void display(Message message) {
        mList.add(message);
        MessageAdapter messageAdapter = new MessageAdapter(ChatActivity.this, mList);
        mRecycler.setAdapter(messageAdapter);
        mRecycler.smoothScrollToPosition(mList.size());
    }

    private String botReply(String newMessage) {
        volley();
        final String[] words = newMessage.toLowerCase().split(" ");
        String precaution = "घरी रहा\nसुरक्षित अंतर ठेवा\nवारंवार हात धुवा";
        String[] prec = {"precaution", "precautions", "care", "काळजी", "kalaji"};
        String symptoms = "डोकेदुखी\n" + "नाक गळणे\n" + "खोकला\n" + "घसा खवखवणे\n" + "ताप\n" + "अस्वस्थ वाटणे\n" + "शिंका येणे, धाप लागणे\n" + "थकवा जाणवणे\n" + "न्युमोनिया, फुप्फुसात सूज";
        String[] sym = {"symptoms", "symptom", "लक्षणे", "lakshane"};
        String hi = "Hey there! how can i help you?";
        String[] hello = {"hi", "hello", "morning", "afternoon", "नमस्कार", "hey", "namaskar"};
        String greetings = "तुम्हाला मदत करुन आनंद झाला";
        String[] greet = {"thanks", "thank", "good", "great", "bi", "bye", "ok", "धन्यवाद"};
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < prec.length; j++) {
                if (words[i].equals(prec[j])) {
                    return precaution;
                }
            }
            for (int j = 0; j < sym.length; j++) {
                if (words[i].equals(sym[j])) {
                    return symptoms;
                }
            }
            try {
                for (int j = 0; j < jsonArray.length(); j++){
                    try {
                        JSONObject object = jsonArray.getJSONObject(j);
                        if (object.getString("state").equals("Total")){
                            if (words[i].equals("भारत") || words[i].equals("india") || words[i].equals("country")){
                                String info = object.getString("state")+" : \n\n"+
                                        "last updated- "+object.getString("lastupdatedtime")+"\n\n"+
                                        "delta confirmed- "+object.getString("deltaconfirmed")+"\n"+
                                        "delta deaths- "+object.getString("deltadeaths")+"\n"+
                                        "delta recovered- "+object.getString("deltarecovered")+"\n\n"+
                                        "confirmed- "+object.getString("confirmed")+"\n"+
                                        "deaths- "+object.getString("deaths")+"\n"+
                                        "recovered- "+object.getString("recovered")+"\n"+
                                        "active- "+object.getString("active");
                                return info;
                            }
                        }
                        if (words[i].equals(object.getString("state").toLowerCase())){
                            String info = object.getString("state")+" : \n\n"+
                                    "last updated- "+object.getString("lastupdatedtime")+"\n\n"+
                                    "delta confirmed- "+object.getString("deltaconfirmed")+"\n"+
                                    "delta deaths- "+object.getString("deltadeaths")+"\n"+
                                    "delta recovered- "+object.getString("deltarecovered")+"\n\n"+
                                    "confirmed- "+object.getString("confirmed")+"\n"+
                                    "deaths- "+object.getString("deaths")+"\n"+
                                    "recovered- "+object.getString("recovered")+"\n"+
                                    "active- "+object.getString("active");
                            return info;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                Toast.makeText(this, "Internet not available", Toast.LENGTH_SHORT).show();
            }
            for (int j = 0; j < hello.length; j++){
                if (words[i].equals(hello[j])){
                    return hi;
                }
            }
            for (int j = 0; j < greet.length; j++){
                if (words[i].equals(greet[j])){
                    return greetings;
                }
            }
        }
        return "unable to recognise please try something else";
    }
    public void volley(){
        String url = "https://api.covid19india.org/data.json";
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsonArray = response.getJSONArray("statewise");
                } catch (JSONException e) {
                    Toast.makeText(ChatActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);
    }
    private Runnable replyRunnable = new Runnable() {
        @Override
        public void run() {
            mTyping.setVisibility(View.GONE);
            Message replyMSG = new Message(reply, false);
            display(replyMSG);
        }
    };
}

