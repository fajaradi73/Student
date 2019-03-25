package com.fingertech.kesforstudent.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fingertech.kesforstudent.Adapter.SearchAdapter;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Auth mApiInterface;
    EditText et_search;
    SearchAdapter searchAdapter;
    String code;
    int status;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    LinearLayout linearLayout;
    private List<JSONResponse.SData> arraylist;
    String sekolah_kode,school_name,school_id;
    CardView btn_selanjutnya;
    ImageView logo;
    TextView footer;
    SharedPreferences sharedpreferences;
    Boolean session = false;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";
    String email, memberid, fullname, member_type;
//    SimpleSearchView simpleSearchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_search       = findViewById(R.id.et_search_sekolah);
        recyclerView    = findViewById(R.id.rv_sekolah);
        btn_selanjutnya = findViewById(R.id.btn_selanjutnya);
        logo            = findViewById(R.id.logo);
        footer          = findViewById(R.id.footer);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
//        simpleSearchView    = findViewById(R.id.searchView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
//
//        simpleSearchView.setOnQueryTextListener(new SimpleOnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                search_school_post(String.valueOf(query));
//                recyclerView.setVisibility(View.VISIBLE);
//                logo.setVisibility(View.GONE);
//                footer.setVisibility(View.GONE);
//                return super.onQueryTextSubmit(query);
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                search_school_post(String.valueOf(newText));
//                recyclerView.setVisibility(View.VISIBLE);
//                logo.setVisibility(View.GONE);
//                footer.setVisibility(View.GONE);
//                return super.onQueryTextChange(newText);
//            }
//
//            @Override
//            public boolean onQueryTextCleared() {
//                return super.onQueryTextCleared();
//            }
//        });

        et_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search_school_post(String.valueOf(s));
                recyclerView.setVisibility(View.VISIBLE);
                logo.setVisibility(View.GONE);
                footer.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        btn_selanjutnya.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,Masuk.class);
            intent.putExtra("school_code",sekolah_kode.toLowerCase());
            intent.putExtra("school_name",school_name);
            startActivity(intent);
        });
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session     = sharedpreferences.getBoolean(session_status, false);
        email       = sharedpreferences.getString(TAG_EMAIL, null);
        memberid    = sharedpreferences.getString(TAG_MEMBER_ID, null);
        fullname    = sharedpreferences.getString(TAG_FULLNAME, null);
        member_type = sharedpreferences.getString(TAG_MEMBER_TYPE, null);

        if (session) {
            Intent intent = new Intent(MainActivity.this, MenuUtama.class);
            intent.putExtra(TAG_EMAIL, email);
            intent.putExtra(TAG_MEMBER_ID, memberid);
            intent.putExtra(TAG_FULLNAME, fullname);
            intent.putExtra(TAG_MEMBER_TYPE, member_type);
            finish();
            startActivity(intent);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    public void search_school_post(final String key){

        Call<JSONResponse.School> postCall = mApiInterface.search_school_post(key);
        postCall.enqueue(new Callback<JSONResponse.School>() {
            @Override
            public void onResponse(Call<JSONResponse.School> call, final Response<JSONResponse.School> response) {

                Log.d("TAG",response.code()+"");

                JSONResponse.School resource = response.body();
                status = resource.status;
                code = resource.code;

                if (status == 1 && code.equals("SS_SCS_0001")) {
                    arraylist = response.body().getData();
                    searchAdapter = new SearchAdapter(arraylist, MainActivity.this);
                    recyclerView.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();
                    searchAdapter.getFilter(key).filter(key);
                    searchAdapter.setOnItemClickListener((view, position) -> {
                        school_name         = response.body().getData().get(position).getSchool_name();
                        sekolah_kode        = response.body().getData().get(position).getSchool_code();
                        school_id           = response.body().getData().get(position).getSchool_id();
                        sekolah_kode        = sekolah_kode.toLowerCase();

                        et_search.setText(school_name);
                        recyclerView.setVisibility(View.GONE);
                        logo.setVisibility(View.VISIBLE);
                        footer.setVisibility(View.VISIBLE);
                        hideKeyboard(MainActivity.this);
                        et_search.clearFocus();
                    });

                } else {
                    if(status == 0 && code.equals("SS_ERR_0001")){
                    }
                }
            }
            @Override
            public void onFailure(Call<JSONResponse.School> call, Throwable t) {
                Log.i("onFailure",t.toString());
            }
        });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
