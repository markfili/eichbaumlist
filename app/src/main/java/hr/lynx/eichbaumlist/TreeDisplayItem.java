package hr.lynx.eichbaumlist;

/**
 * Example class for displaying data in the tree list view.
 * Implement Treeable to be able to correctly extract data from your object when the item is clicked.
 * <p>
 * Created by marko on 23/01/17.
 */
public class TreeDisplayItem implements Treeable {
    private String text;

    public TreeDisplayItem(String text) {
        this.text = text;
    }

    @Override
    public String getTitle() {
        return text;
    }

    // more values can be added in Treeable interface
    // ex.
    //    @Override
    //    public String getType() {
    //        return "";
    //    }
    //
    //    @Override
    //    public int getId() {
    //        return 323;
    //    }
}
