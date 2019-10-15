package jrizani.jrspinner;
/*=============================*/
/*            AUTHOR           */
/*          JULIAN NR          */
/* juliannoorrizani@gmail.com  */
/*         19 Feb 2019         */
/*=============================*/

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * dialog that showed when use multiple spinner
 */
public class MultipleDialog extends DialogFragment {

    /**
     * items of spinner
     */
    private String[] data;
    /**
     * title of dialog
     */
    private String title;

    private EditText etSearch;
    private RecyclerView recyclerView;
    private TextView tvTitle;
    private Adapter adapter;
    private ImageView reset;
    private ImageView ivSearch;
    private View root;
    private TextView tvDone;
    private CardView card;

    /**
     * the spinner view
     */
    private JRSpinner view;
    /**
     * on multiple items selected listener
     */
    private JRSpinner.OnSelectMultipleListener listener;
    /**
     * selected items position
     */
    private List<Integer> selected;

    public MultipleDialog() {
    }

    /**
     * method to set the listener
     * @param listener on multiple items selected listener
     * @param view spinner view
     */
    public void setListener(JRSpinner.OnSelectMultipleListener listener, JRSpinner view) {
        this.listener = listener;
        this.view = view;
    }

    /**
     * method to create dialog object
     * @param title title of dialog
     * @param data items of spinner
     * @param selected selected items position
     * @return the dialog
     */
    public static MultipleDialog newInstance(String title, String[] data, List<Integer> selected) {
        MultipleDialog instance = new MultipleDialog();
        Bundle arguments = new Bundle();
        arguments.putStringArray("data", data);
        arguments.putString("title", title);
        arguments.putIntegerArrayList("selected", (ArrayList<Integer>) selected);
        instance.setArguments(arguments);
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);

        if (getArguments() != null && getArguments().getStringArray("data") != null && getArguments().getString("title") != null) {
            data = getArguments().getStringArray("data");
            title = getArguments().getString("title");
            selected = new ArrayList<>(getArguments().getIntegerArrayList("selected"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jrspinner_layout_dialog, container, false);
        etSearch = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.recyclerView);
        tvTitle = view.findViewById(R.id.title);
        reset = view.findViewById(R.id.reset);
        ivSearch = view.findViewById(R.id.search_icon);
        root = view.findViewById(R.id.root);
        card = view.findViewById(R.id.card);
        tvDone = view.findViewById(R.id.tv_done);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/co_headline_w01_reg.ttf");
        tvTitle.setTypeface(typeface);
        etSearch.setTypeface(typeface);
        tvDone.setTypeface(typeface);

        if (data.length < 10) {
            etSearch.setVisibility(View.GONE);
            ivSearch.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        android.app.Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvDone.setVisibility(View.VISIBLE);
        if (data != null) {
            tvTitle.setText(title);
            adapter = new Adapter(true, new Adapter.Listener() {
                @Override
                public void onClick(Pair<Integer, String> item, int position) {
                    if (selected.contains(item.first)) {
                        for (int i = 0; i < selected.size(); i++) {
                            if (selected.get(i).equals(item.first)) {
                                selected.remove(i);
                                adapter.removeSelect(item.first);
                                break;
                            }
                        }
                    } else {
                        selected.add(item.first);
                        adapter.addSelect(item.first);
                    }
                }
            }, getActivity());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter.update(data, selected);
            recyclerView.setAdapter(adapter);
            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    adapter.update(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (!editable.toString().isEmpty()) {
                        reset.setVisibility(View.VISIBLE);
                    } else {
                        reset.setVisibility(View.GONE);
                    }
                }
            });

            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.reset();
                    etSearch.setText("");
                }
            });

            tvDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MultipleDialog.this.view.setSelected(selected);
                    if (listener != null) {
                        listener.onMultipleSelected(selected);
                    }
                    dismiss();
                }
            });

            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //do nothing
                }
            });
        } else {
            dismiss();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        selected.clear();
    }
}
