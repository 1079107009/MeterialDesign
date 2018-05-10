package com.android.meterialdesign;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.transition.AutoTransition;
import android.support.transition.Explode;
import android.support.transition.Fade;
import android.support.transition.Slide;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout mRoot;
    private SearchView mSearchView;
    private TextView mTextView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRoot = findViewById(R.id.root);
        mTextView = findViewById(R.id.textView);
        initToolbar();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mSearchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        initSearchView();
        setSearchListener();
        return true;
    }

    private void initSearchView() {
        mSearchView.setQueryHint("搜索");
        //修改searchView的文字颜色
        SearchView.SearchAutoComplete mSearchAutoComplete = mSearchView.findViewById(R.id.search_src_text);
        int white = ContextCompat.getColor(this, android.R.color.white);
        //设置提示文字颜色
        mSearchAutoComplete.setHintTextColor(white);
        //设置内容文字颜色
        mSearchAutoComplete.setTextColor(white);
    }

    private void setSearchListener() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mTextView.setText(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mTextView.setText(newText);
                return false;
            }
        });
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("开始搜索");
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mTextView.setText("关闭搜索");
                return false;
            }
        });
        mSearchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                Toast.makeText(this, "添加", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_delete:
                Toast.makeText(this, "删除", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        Intent intent = new Intent(this, DrawerActivity.class);
        startActivity(intent);
        return true;
    }

    public void onClick(final View view) {
        int id = view.getId();
        Transition transition;
        switch (id) {
            case R.id.button1:
                transition = new AutoTransition();
                break;
            case R.id.button2:
                transition = new Fade();
                break;
            case R.id.button3:
                transition = new Explode();
                break;
            case R.id.button4:
                transition = new Slide(Gravity.BOTTOM);
                break;
            default:
                transition = new AutoTransition();
                break;
        }
        //设置动画时长
        transition.setDuration(500);
        //设置插值器
        transition.setInterpolator(new FastOutSlowInInterpolator());
        TransitionManager.beginDelayedTransition(mRoot, transition);
        view.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }
}
