package hr.lynx.eichbaumlist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.tree_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        TreeItem root = new TreeItem();
        TreeItem parent1 = new TreeItem().layout(R.layout.tree_item).value("Parent 1");
        TreeItem parent2 = new TreeItem().layout(R.layout.tree_item).value("Parent 2");

        root.children(parent1, parent2);

        TreeItem child11 = new TreeItem().layout(R.layout.tree_item).value("Child 11");
        TreeItem child12 = new TreeItem().layout(R.layout.tree_item).value("Child 12");
        TreeItem child13 = new TreeItem().layout(R.layout.tree_item).value("Child 13");
        TreeItem child14 = new TreeItem().layout(R.layout.tree_item).value("Child 14");

        TreeItem child111 = new TreeItem().layout(R.layout.tree_item).value("Child 111");
        TreeItem child112 = new TreeItem().layout(R.layout.tree_item).value("Child 112");
        TreeItem child113 = new TreeItem().layout(R.layout.tree_item).value("Child 113");
        TreeItem child114 = new TreeItem().layout(R.layout.tree_item).value("Child 114");

        parent1.children(child11, child12, child13, child14);
        child11.children(child111, child112, child113, child114);


        TreeItem child21 = new TreeItem().layout(R.layout.tree_item).value("Child 21");
        TreeItem child22 = new TreeItem().layout(R.layout.tree_item).value("Child 22");
        TreeItem child23 = new TreeItem().layout(R.layout.tree_item).value("Child 23");
        TreeItem child24 = new TreeItem().layout(R.layout.tree_item).value("Child 24");

        TreeItem child211 = new TreeItem().layout(R.layout.tree_item).value("Child 211");
        TreeItem child212 = new TreeItem().layout(R.layout.tree_item).value("Child 212");
        TreeItem child213 = new TreeItem().layout(R.layout.tree_item).value("Child 213");
        TreeItem child214 = new TreeItem().layout(R.layout.tree_item).value("Child 214");

        parent2.children(child21, child22, child23, child24);
        child21.children(child211, child212, child213, child214);

        TreeAdapter adapter = new TreeAdapter(root);
        mRecyclerView.setAdapter(adapter);
    }

}
