package jrizani.jrspinner;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;
import android.text.InputFilter;
import android.util.AttributeSet;
import java.util.ArrayList;
import java.util.List;

/*=============================*/
/*            AUTHOR           */
/*          JULIAN NR          */
/* juliannoorrizani@gmail.com  */
/*         20 Feb 2019         */
/*=============================*/

/**
 * custom view that used as spinner
 */
public class JRSpinner extends androidx.appcompat.widget.AppCompatEditText {

    public static final int MaxLength = 38;

    /**
     * all items text to show in spinner dialog
     */
    private String[] items;
    /**
     * tint of expand icon
     */
    private int expandTint;
    /**
     * the title of spinner dialog
     */
    private String title = "Select";
    /**
     * listener to listen when item click (used when use non multiple spinner)
     */
    private OnItemClickListener onItemClickListener;
    /**
     * selected position of non multiple spinner
     */
    private int selected = -1;
    /**
     * the field to know that this spinner is multiple or no
     */
    private boolean multiple = false;
    /**
     * selected position of multiple listener
     */
    private List<Integer> multipleSelected = new ArrayList<>();
    /**
     * listener to listen when multiple item selected (used when use multiple spinner)
     */
    private JRSpinner.OnSelectMultipleListener onSelectMultipleListener;

    public JRSpinner(Context context) {
        super(context);
        init(context, null);
    }

    public JRSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public JRSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * method that initialize the view
     *
     * @param attrs attribute of view
     */
    private void init(Context context, AttributeSet attrs) {
        setLongClickable(false);
        setFocusable(false);
        setSingleLine(true);
        expandTint = ContextCompat.getColor(getContext(), R.color.jrspinner_color_default);
        setFilters(new InputFilter[] {new InputFilter.LengthFilter(MaxLength)});

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.JRSpinner);
            if (typedArray != null) {
                title = typedArray.getString(R.styleable.JRSpinner_jrs_title) == null ? "Select" : typedArray.getString(
                        R.styleable.JRSpinner_jrs_title);
                expandTint = typedArray.getColor(R.styleable.JRSpinner_jrs_icon_tint, expandTint);
                multiple = typedArray.getBoolean(R.styleable.JRSpinner_jrs_multiple, false);
                typedArray.recycle();
            }
        }
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/co_headline_w01_reg.ttf"));
    }

    /**
     * method to set tint of expand icon
     *
     * @param expandTint this is the tint
     */
    public void setExpandTint(int expandTint) {
        this.expandTint = expandTint;
        postInvalidate();
    }

    /**
     * method to set the item to show in spinner dialog
     *
     * @param items all items to show
     */
    public void setItems(String[] items) {
        this.items = items;
        postInvalidate();
    }

    /**
     * method to set the title of spinner dialog
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
        postInvalidate();
    }

    /**
     * method to set use multiple spinner or no
     *
     * @param multiple use multiple spinner or no
     */
    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    /**
     * add the item click listener when use non multiple spinner
     *
     * @param onItemClickListener the listener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * add the select multiple item listener when use multiple spinner
     *
     * @param onSelectMultipleListener the listener
     */
    public void setOnSelectMultipleListener(OnSelectMultipleListener onSelectMultipleListener) {
        this.onSelectMultipleListener = onSelectMultipleListener;
    }

    /**
     * it is used to disabled auto spelling check
     *
     * @return suggestion is enabled
     */
    @Override
    public boolean isSuggestionsEnabled() {
        return false;
    }

    /**
     * set the selected item position when use non multiple spinner
     *
     * @param selected the position
     */
    public void setSelected(int selected) {
        if (selected >= 0) {
            this.selected = selected;
            setText(items[selected]);
        }
    }

    /**
     * get the selected item when use non multiple spinner
     *
     * @return item at selected position
     */
    public String getSelected() {
        if (selected >= 0) {
            return items[selected];
        } else {
            return "";
        }
    }

    public List<String> getMultipleSelected() {

        List<String> selectedItemsList = new ArrayList<>();
        for (int i = 0; i < multipleSelected.size(); i++) {
            selectedItemsList.add(items[multipleSelected.get(i)]);
        }

        if (selectedItemsList.size() == 0) {
            selectedItemsList.add("");
        }
        return selectedItemsList;
    }

    /**
     * call when click on spinner view and show the dialog
     */
    @Override
    public boolean performClick() {
        if (!multiple) {
            Dialog dialog = Dialog.newInstance(title, items, selected);
            dialog.setListener(onItemClickListener, JRSpinner.this);
            dialog.show(findActivity(getContext()).getSupportFragmentManager(), dialog.getTag());
        } else {
            MultipleDialog dialog = MultipleDialog.newInstance(title, items, multipleSelected);
            dialog.setListener(onSelectMultipleListener, JRSpinner.this);
            dialog.show(findActivity(getContext()).getSupportFragmentManager(), dialog.getTag());
        }
        return super.performClick();
    }

    /**
     * method to clear selected item(s)
     */
    public void clear() {
        if (multiple) {
            multipleSelected.clear();
        } else {
            selected = -1;
        }
        setText("");
    }

    /**
     * method to get activity of the view
     *
     * @param context context where view found
     * @param <T>     the return must be extend FragmentActivity
     * @return object that extend FragmentActivity
     */
    public static <T extends FragmentActivity> T findActivity(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }

        if (context instanceof FragmentActivity) {
            return (T) context;
        } else {
            ContextWrapper contextWrapper = (ContextWrapper) context;
            Context baseContext = contextWrapper.getBaseContext();
            if (baseContext == null) {
                throw new IllegalStateException("Activity was not found as base context of view!");
            }
            return findActivity(baseContext);
        }
    }

    /**
     * set the selected items position when use multiple spinner
     *
     * @param selected selected positions
     */
    public void setSelected(List<Integer> selected) {
        multipleSelected.clear();
        multipleSelected.addAll(selected);
        displayMultipleSelectedItems(selected);
    }

    private void displayMultipleSelectedItems(List<Integer> selected) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < items.length; i++) {
            if (text.length() == 0 && selected.contains(i)) {
                text.append(items[i]);
            } else if (selected.contains(i)) {
                text.append(", ").append(items[i]);
            }
        }

        String truncatedString;
        if (text.length() >= JRSpinner.MaxLength) {
            truncatedString = text.substring(0, JRSpinner.MaxLength - 3);
            truncatedString = truncatedString + "...";
        } else {
            truncatedString = text.toString();
        }

        setText(truncatedString);
    }

    /**
     * the item click listener
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    /**
     * the select multiple listener
     */
    public interface OnSelectMultipleListener {
        void onMultipleSelected(List<Integer> selectedPosition);
    }
}
