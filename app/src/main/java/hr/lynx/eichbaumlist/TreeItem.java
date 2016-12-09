package hr.lynx.eichbaumlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by marko on 23/11/16.
 */

public class TreeItem {

    public boolean isExpanded;
    public int resId;
    public List<TreeItem> children;
    public int level;
    Object value;
    public int position = -1;
    public TreeItem parent;

    public TreeItem() {
        parent = null;
    }

    public TreeItem value(Object value) {
        this.value = value;
        return this;
    }

    public TreeItem layout(int resId) {
        this.resId = resId;
        return this;
    }

    public TreeItem children(TreeItem... children) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        if (parent == null) level = -1;
        for (TreeItem child : children) {
            child.parent = this;
            child.level = level + 1;
        }
        Collections.addAll(this.children, children);
        return this;
    }


}
