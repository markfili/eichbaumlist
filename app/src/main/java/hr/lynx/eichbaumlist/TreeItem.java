package hr.lynx.eichbaumlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base class for TreeList Items, contains children and data about its position and expansion state.
 * Created by marko on 23/11/16.
 */

public class TreeItem {

    public boolean isExpanded;
    public int resId;
    public List<TreeItem> children;
    public int level;
    public int position = -1;
    public TreeItem parent;

    // value to be displayed right of the icon
    Treeable value;

    // root element without a parent :(
    public TreeItem() {
        parent = null;
    }

    // Builder pattern to create an element with value, ...
    public TreeItem value(Treeable value) {
        this.value = value;
        return this;
    }

    // ... preferred Layout resourceId, and ...
    public TreeItem layout(int resId) {
        this.resId = resId;
        return this;
    }

    // ... its children
    public TreeItem children(TreeItem... children) {
        // create new list to collect children
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        // if this element is at root level, its children have position 0
        if (parent == null) level = -1;
        for (TreeItem child : children) {
            // add parent to children to collect parent position in TreeAdapter
            child.parent = this;
            // level + 1 to add left padding
            child.level = level + 1;
        }
        Collections.addAll(this.children, children);
        return this;
    }


}
