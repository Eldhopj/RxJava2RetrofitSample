package com.example.eldho.rxjava2sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.eldho.rxjava2sample.Adapter.PostAdapter;
import com.example.eldho.rxjava2sample.Model.PostResponse;
import com.example.eldho.rxjava2sample.Network.RetrofitClient;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<PostAdapter> mListItems;
    private PostAdapter mRecyclerAdapter;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchData();
    }

    private void fetchData(){
        compositeDisposable.add(RetrofitClient.getInstance().getApi().getPosts()
                /*  io():Typically this is used for network calls, it Creates and returns a Scheduler intended for IO-bound work. Again it’s bounded like computation.
                    newThread(): Creates a new Thread for each unit of work. This is costly since it creates a separate thread everytime.
                    trampoline(): Useful for queueing operations. This runs the tasks on the current thread. So it’ll run your code after the current task on the thread is complete.
                *   computation(): Creates and returns a Scheduler intended for computational work. This should be used for parallel work since the thread pool is bound. I/O operations shouldn’t be done here.*/
                .subscribeOn(Schedulers.io()) //subscribeOn works downstream and upstream
                .observeOn(AndroidSchedulers.mainThread())//observeOn works downstream only
                .subscribe(new Consumer<List<PostResponse>>() { // The class acts up on the emitted data from the observer
                    @Override
                    public void accept(List<PostResponse> postResponses) {
                        initRecyclerView(postResponses);
                    }
                }));
    }


    private void initRecyclerView(List<PostResponse> listItems){

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true); // setting it to true allows some optimization to our view , avoiding validations when mRecyclerAdapter content changes

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)); //it can be GridLayoutManager or StaggeredGridLayoutManager

        mRecyclerAdapter = new PostAdapter(listItems, this);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL)); // Divider decorations
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
