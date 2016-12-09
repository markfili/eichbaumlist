package hr.lynx.eichbaumlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

/**
 * Created by marko on 23/11/16.
 */

class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.ListViewHolder> {

    private static final String TAG = TreeAdapter.class.getSimpleName();
    private List<TreeItem> treeItems;

    TreeAdapter(TreeItem root) {
        this.treeItems = root.children;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return treeItems.get(position).resId;
    }

    @Override
    public int getItemCount() {
        return treeItems.size();
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, int position) {
        final TreeItem item = treeItems.get(position);
        holder.itemView.setPadding((item.parent.level + item.level) * 50, 0, 0, 0);
        holder.title.setText((String) item.value);
        holder.itemView.setTag(R.integer.tag_tree_item, item);
        item.position = holder.getAdapterPosition() + 1;
        holder.icon.setEnabled(item.children != null);
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TreeItem treeItem = (TreeItem) ((LinearLayout) view.getParent()).getTag(R.integer.tag_tree_item);
                if (view.isEnabled()) {
                    processPosition(treeItem.position - 1);
                }
            }
        });
    }

    private void processPosition(int holderPosition) {
        TreeItem item = treeItems.get(holderPosition);
        if (item.isExpanded) {
            collapse(item);
        } else {
            expand(item);
        }
        item.isExpanded = !item.isExpanded;
        notifyDataSetChanged();
    }

    private void expand(TreeItem item) {
        int parentPosition = item.position;
        int childrenCount = item.children.size();
        TreeItem child;
        for (int i = 0; i < childrenCount; i++) {
            child = item.children.get(i);
            child.position = parentPosition + i;
            treeItems.add(child.position, child);
        }
    }

    private void collapse(TreeItem item) {
        int childrenCount = item.children.size();
        TreeItem child;
        for (int i = 0; i < childrenCount; i++) {
            child = item.children.get(i);
            if (child.isExpanded) {
                collapse(child);
                child.isExpanded = !child.isExpanded;
            }
            treeItems.remove(child);
        }
    }


    static class ListViewHolder extends RecyclerView.ViewHolder {

        ToggleButton icon;
        TextView title;

        ListViewHolder(View itemView) {
            super(itemView);
            icon = (ToggleButton) itemView.findViewById(R.id.expand_icon);
            title = (TextView) itemView.findViewById(R.id.text);
        }
    }
}