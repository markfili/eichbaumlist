package hr.lynx.eichbaumlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

/**
 * Created by marko on 23/11/16.
 */

public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.ListViewHolder> {

    int removedItemsCount = 0;
    private List<TreeItem> treeItems;

    public TreeAdapter(List<TreeItem> treeItems) {
        this.treeItems = treeItems;
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
    public void onBindViewHolder(ListViewHolder holder, int position) {
        final TreeItem item = treeItems.get(position);
        holder.itemView.setPadding(item.level * 50, 0, 0, 0);
        holder.title.setText((String) item.value);

        final int holderPosition = treeItems.indexOf(item);
        final ToggleButton icon = holder.icon;

        icon.setEnabled(item.children != null);

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (icon.isEnabled()) {
                    processPosition(holderPosition);
                }
            }
        });
    }

    private void processPosition(int holderPosition) {
        TreeItem item = treeItems.get(holderPosition);
        if (item.isExpanded) {
            removedItemsCount = 0;
            collapse(item);
            notifyItemRangeRemoved(holderPosition + 1, removedItemsCount);
        } else {
            expand(item);
        }
        item.isExpanded = !item.isExpanded;
    }

    private void expand(TreeItem item) {
        int itemPosition = treeItems.indexOf(item);
        int childrenCount = item.children.size();
        TreeItem child;
        for (int i = 0; i < childrenCount; i++) {
            child = item.children.get(i);
            treeItems.add(itemPosition + (i + 1), child);
        }
        notifyItemRangeInserted(itemPosition + 1, childrenCount);
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
            removedItemsCount++;
        }
    }


    static class ListViewHolder extends RecyclerView.ViewHolder {

        public ToggleButton icon;
        public TextView title;

        public ListViewHolder(View itemView) {
            super(itemView);
            icon = (ToggleButton) itemView.findViewById(R.id.expand_icon);
            title = (TextView) itemView.findViewById(R.id.text);
        }
    }
}