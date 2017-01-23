package hr.lynx.eichbaumlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

/**
 * Created by marko on 23/11/16.
 */

public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.ListViewHolder> {

    private static final int PARENT_OFFSET = 1;
    private List<TreeItem> treeItems;
    private TreeClickListener clickListener;

    public TreeAdapter(TreeItem root, TreeClickListener listener) {
        this.treeItems = root.children;
        this.clickListener = listener;
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
        holder.setIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isEnabled()) {
                    processPosition(item);
                }
            }
        });
        holder.setTextClickListener(clickListener);
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

        Button icon;
        TextView title;
        View spacer;

        ListViewHolder(View itemView) {
            super(itemView);
            spacer = itemView.findViewById(R.id.tree_spacer);
            // todo swap these views with customizable views
            icon = (Button) itemView.findViewById(R.id.expand_icon);
            title = (TextView) itemView.findViewById(R.id.text);
        }

        public void setItem(TreeItem item) {
            // set left padding to indicate child status
            spacer.getLayoutParams().width = 50 * item.level;
            Treeable value = item.value;
            title.setText(value.getTitle());
            // set expandable if the item contains children
            icon.setVisibility(item.children == null ? View.INVISIBLE : View.VISIBLE);
            // set TreeItem in relation to this view as view's tag for later extraction
            icon.setTag(R.integer.tag_tree_item, value);
        }

        public void setIconClickListener(View.OnClickListener listener) {
            icon.setOnClickListener(listener);
        }

        public void setTextClickListener(final TreeClickListener onClickListener) {
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onItemClick((Treeable) view.getTag(R.integer.tag_tree_item));
                }
            });
        }
    }

    public interface TreeClickListener {
        void onItemClick(Treeable item);
    }
}