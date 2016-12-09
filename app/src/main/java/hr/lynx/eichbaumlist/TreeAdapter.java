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

    private static final int PARENT_OFFSET = 1;
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
        item.position = holder.getAdapterPosition();
        holder.setItem(item);
        holder.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isEnabled()) {
                    processPosition((TreeItem) view.getTag(R.integer.tag_tree_item));
                }
            }
        });
    }

    private void processPosition(TreeItem item) {
        if (item.isExpanded) {
            collapse(item);
        } else {
            expand(item);
        }
        item.isExpanded = !item.isExpanded;
        notifyDataSetChanged();
    }

    private void expand(TreeItem item) {
        // get selected item's position as starting position for its children
        int parentPosition = item.position;
        int childrenCount = item.children.size();
        TreeItem child;
        for (int i = 0; i < childrenCount; i++) {
            child = item.children.get(i);
            // add child below it's parent
            child.position = parentPosition + PARENT_OFFSET + i;
            treeItems.add(child.position, child);
        }
    }

    private void collapse(TreeItem item) {
        int childrenCount = item.children.size();
        TreeItem child;
        for (int i = 0; i < childrenCount; i++) {
            child = item.children.get(i);
            if (child.isExpanded) {
                // go recursive if children are expanded
                collapse(child);
                child.isExpanded = !child.isExpanded;
            }
            treeItems.remove(child);
        }
    }


    public static class ListViewHolder extends RecyclerView.ViewHolder {

        ToggleButton icon;
        TextView title;

        ListViewHolder(View itemView) {
            super(itemView);
            // todo swap these views with customizable views
            icon = (ToggleButton) itemView.findViewById(R.id.expand_icon);
            title = (TextView) itemView.findViewById(R.id.text);
        }

        public void setItem(TreeItem item) {
            // set left padding to indicate child status
            itemView.setPadding((item.parent.level + item.level) * 50, 0, 0, 0);

            title.setText((String) item.value);
            // set expandable if the item contains children
            icon.setEnabled(item.children != null);
            // set TreeItem in relation to this view as view's tag for later extraction
            icon.setTag(R.integer.tag_tree_item, item);
        }

        public void setClickListener(View.OnClickListener listener) {
            icon.setOnClickListener(listener);
        }
    }
}