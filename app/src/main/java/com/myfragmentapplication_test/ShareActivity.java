package com.myfragmentapplication_test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import utils.UtilityClass;

public class ShareActivity extends AppCompatActivity implements ShareActivityFragment.OnShareItemClickedListener{
    public static final String SHARE_MESSAGE = "SHARE_MESSAGE";

    @Bind(R.id.linearLayoutShareContent)
    LinearLayout linearLayoutShareContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(linearLayoutShareContent != null){
           //To avoid overlapping with same fragment, if the instance is not null
            if(savedInstanceState != null){
                return;
            }

            ShareActivityFragment shareActivityFragment = new ShareActivityFragment();
            Bundle bundle = new Bundle();
            bundle.putString(SHARE_MESSAGE, "Hello everybody");
            shareActivityFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(linearLayoutShareContent.getId(),shareActivityFragment,ShareActivityFragment.TAG).commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Both are null and empty", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if(NavUtils.getParentActivityIntent(this)== null) {
                    onBackPressed();
                }else{
                    NavUtils.navigateUpFromSameTask(this);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onShareItemClicked(String message) {
        UtilityClass.showToastMessage(this, "Received from Fragment:"+message);
    }
}
