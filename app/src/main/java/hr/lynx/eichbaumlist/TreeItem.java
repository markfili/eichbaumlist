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

    public TreeItem() {

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
        Collections.addAll(this.children, children);
        return this;
    }


}
