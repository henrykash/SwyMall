package com.example.swymall;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.swymall.Models.GroceryItem;
import com.example.swymall.Models.Review;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.swymall.GroceryItemActivity.GROCERY_ITEM_KEY;

public class AddReviewDialog extends DialogFragment {

    //in order to add this dialog fragment to the GroceryItemActivity we shall use a callBack Interface
    public interface AddReview {
        void onAddReviewResult(Review review);
    }

    //create an instance of the above interface
    private AddReview addReview;


    //initialize the ui elements
    private TextView txtItemName, txtWarning;
    private EditText edtTxtReview;
    private EditText edtTxtUserName;
    private Button btnAddReview;

    @NonNull
    @Override  //first override the onCreateDialog method
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //create the view object to inflate the dialog_add_review layout file
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_review, null);
        //call the initViews method inside onCreateDialog method
        initViews(view);

        //now create the alert dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);
        //we are going to pass our GroceryItem with a Bundle with the arguments of this dialog
        Bundle bundle = getArguments();
        if (null != bundle) {
            //check if the bundle is not null and pass it the GroceryItem
            final GroceryItem item = bundle.getParcelable(GROCERY_ITEM_KEY);
            if (null != item) {
                //if the item is not null the set the texts
                txtItemName.setText(item.getName());
                btnAddReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userName = edtTxtUserName.getText().toString();
                        String review = edtTxtReview.getText().toString();
                        String date = getCurrentDate();

                        //before adding the review to the shared preferences check is username and review is empty if yes give a warning message
                        if (userName.equals("") || review.equals("")) {
                            txtWarning.setText("Fill all the blanks");
                            txtWarning.setVisibility(View.VISIBLE);
                        } else {
                            txtWarning.setVisibility(View.GONE);
                            //now initialize the interface after receiving the userName ,date and review
                            try {
                                addReview = (AddReview) getActivity();
                                addReview.onAddReviewResult(new Review(item.getId(), userName, review, date));
                                dismiss(); //method to dismiss the dialog
                            } catch (ClassCastException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
            }
        }

        //remember to return the builder.create
        return builder.create();
    }

    //method to get the calender for the current date
    private String getCurrentDate() {
        //create an instance of the calender that one coming from java util packages
        Calendar calendar = Calendar.getInstance();
        //create a simple date format
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        return sdf.format(calendar.getTime());
    }

    private void initViews(View view) {
        txtItemName = view.findViewById(R.id.txtItemName);
        txtWarning = view.findViewById(R.id.txtWarning);
        edtTxtReview = view.findViewById(R.id.edtTxtReview);
        edtTxtUserName = view.findViewById(R.id.edtTxtUserName);
        btnAddReview = view.findViewById(R.id.btnAddReview);
    }
}
