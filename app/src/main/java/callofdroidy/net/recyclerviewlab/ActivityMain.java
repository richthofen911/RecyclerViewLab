package callofdroidy.net.recyclerviewlab;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityMain extends AppCompatActivity {
    private static final String TAG = "ActivityMain";

    private RecyclerView mRecyclerView;
    public MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler;

    private UpdateData updateData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateData();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        handler = new Handler();
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,android.R.color.holo_red_light);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DataStore.dataSet.remove(0);
                        swipeRefreshLayout.setRefreshing(false);
                        mAdapter.notifyItemRemoved(0);

                    }
                }, 2000);

/*
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //generateData();
                        DataStore.dataSet.add(0, new Cell(100, 100));
                        swipeRefreshLayout.setRefreshing(false);
                        mAdapter.notifyItemInserted(0);
                        mRecyclerView.scrollToPosition(0);
                    }
                }, 2000);
*/
            }
        });
        updateData = new UpdateData(mAdapter, DataStore.dataSet);
        updateData.execute();
    }

    public void generateData(){
        for(int i = 0; i < 20; i++){
            DataStore.dataSet.add(new Cell(i, i));
        }
    }

    @Override
    public void onDestroy(){
        if(updateData != null && updateData.getStatus() == AsyncTask.Status.RUNNING)
            updateData.cancel(true);
        super.onDestroy();
    }

    private static class UpdateData extends AsyncTask<String, Object, String>{
        final MyAdapter myAdapter;
        final ArrayList dataSet;

        UpdateData(MyAdapter myAdapter, ArrayList dataSet){
            this.myAdapter = myAdapter;
            this.dataSet = dataSet;
        }

        @Override
        protected String doInBackground(String... args){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Log.e(TAG, "run: done");
                            dataSet.remove(0);
                        }
                    }, 4000);
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
            if(isCancelled()){
                Log.e(TAG, "doInBackground: canceled");
                thread.interrupt();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String str){
            myAdapter.notifyItemRemoved(0);
        }
    }
}
