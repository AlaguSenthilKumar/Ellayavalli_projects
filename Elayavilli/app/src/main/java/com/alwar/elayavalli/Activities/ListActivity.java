package com.alwar.elayavalli.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alwar.elayavalli.Constants;
import com.alwar.elayavalli.Others.ListAdapter;
import com.alwar.elayavalli.Others.RetrieveBean;
import com.alwar.elayavalli.R;
import com.alwar.elayavalli.RealmController;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {


    ListView listView;
    RealmController realmController;
    SwipeRefreshLayout pullToRefresh;
    boolean pSearch = false;
    int search_type = 0;
    MenuItem searchItem, item;
    List<RetrieveBean> allDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.toolbar_text);
            TextView textView = getSupportActionBar().getCustomView().findViewById(R.id.tvTitle);
            textView.setText(R.string.app_name);
        }


        listView = (ListView) findViewById(R.id.lv_list);
        realmController = RealmController.with ();
        pullToRefresh = findViewById(R.id.pullToRefresh);
        allDataList = realmController.getAllProfile();

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });

    }

    private void refreshData() {
        pSearch = true;
        search_type = 1;
        setAdapter(allDataList);
    }

    private void setAdapter(List<RetrieveBean> allDataList) {
        ListAdapter adapter = new ListAdapter(allDataList, getApplicationContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RetrieveBean listModel = realmController.getAllProfile().get(position);
                //listModel.getNameStr()
                alertPopup(listModel);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        item = menu.findItem(R.id.add);
        item.setVisible(true);
        searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(true);
        searchItem.setTitle("Search");
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    if (newText.length() != 0) {
                        pSearch = true;
                        search_type = 1;
                        setAdapter(getDetailList(newText, allDataList));
                    } else {
                        refreshData();
                    }

                } catch (Exception e) {
                    Log.v("error", e.getMessage());
                }
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setVisible(false);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                item.setVisible(true);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(ListActivity.this, ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    public static List<RetrieveBean> getDetailList(String newText, List<RetrieveBean> allDatas) {
        List<RetrieveBean> patientDetails = new ArrayList<>();

        for (RetrieveBean c : allDatas) {
            if (c.getAdiyenNameStr().toLowerCase().contains(newText)) {
                patientDetails.add(c);
            }
        }
        return patientDetails;
    }


    private void alertPopup(final RetrieveBean listModel) {

        final String [] listItems = {/*"View Photo", "View ID Card", */ "View Address"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
        builder.setTitle("Select");
        builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertMessage(listModel,listItems[i]);
                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = builder.create();
        mDialog.show();

    }

    private void alertMessage(RetrieveBean listModel, String listItem) {

        if(listItem != null && !"".equals(listItem)) {
            /*if("View Photo".equals(listItem)) {
                Intent intent = new Intent(ListActivity.this, ViewPhotoActivity.class);
                intent.putExtra(Constants.Profile, listModel);
                intent.putExtra(Constants.TYPE, Constants.PHOTO);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }*else if("View ID Card".equals(listItem)) {
                Intent intent = new Intent(ListActivity.this, ViewIDCardActivity.class);
                intent.putExtra(Constants.Profile, listModel);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else */if("View Address".equals(listItem)) {
                Intent intent = new Intent(ListActivity.this, ViewPhotoActivity.class);
                intent.putExtra(Constants.Profile, listModel);
                intent.putExtra(Constants.TYPE, Constants.ADDRESS);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }

    }

}
