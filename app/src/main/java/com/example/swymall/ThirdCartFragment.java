package com.example.swymall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.swymall.Models.GroceryItem;
import com.example.swymall.Models.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import static com.example.swymall.SecondCartFragment.ORDER_KEY;

public class ThirdCartFragment extends Fragment {
    private Button btnBack, btnCheckout;
    private TextView txtItems, txtAddress, txtPhoneNumber, txtTotalPrice;
    private RadioGroup rgPayment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_third, container, false);

        initViews(view);

        Bundle bundle = getArguments();
        if (null != bundle) {
            final String jsonOrder = bundle.getString(ORDER_KEY);
            if (null != jsonOrder) {
                Gson gson = new Gson();
                Type type = new TypeToken<Order>() {}.getType();
                final Order order = gson.fromJson(jsonOrder, type);
                if (null != order) {
                    String items = "";
                    for (GroceryItem i: order.getItems()) {
                        items += "\n\t" + i.getName();
                    }

                    txtItems.setText(items);
                    txtAddress.setText(order.getAddress());
                    txtPhoneNumber.setText(order.getPhoneNumber());
                    txtTotalPrice.setText(String.valueOf(order.getTotalPrice()));


                    btnBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle backBundle = new Bundle();
                            backBundle.putString(ORDER_KEY, jsonOrder);
                            SecondCartFragment secondCartFragment = new SecondCartFragment();
                            secondCartFragment.setArguments(backBundle);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, secondCartFragment);
                            transaction.commit();
                        }
                    });

                    btnCheckout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (rgPayment.getCheckedRadioButtonId()) {
                                case R.id.rbPayPal:
                                    order.setPaymentMethod("PayPal");
                                    break;
                                case R.id.rbCreditCard:
                                    order.setPaymentMethod("Credit Card");
                                    break;
                                default:
                                    order.setPaymentMethod("Unknown");
                                    break;
                            }

                            order.setSuccess(true);

                            // TODO: 4/27/2020 Send Your Request with Retrofit
                        }
                    });
                }
            }
        }

        return view;
    }

    private void initViews(View view) {
        rgPayment = view.findViewById(R.id.rgPaymentMethod);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtPhoneNumber = view.findViewById(R.id.txtPhoneNumber);
        txtItems = view.findViewById(R.id.txtItems);
        txtTotalPrice = view.findViewById(R.id.txtPrice);
        btnBack = view.findViewById(R.id.btnBack);
        btnCheckout = view.findViewById(R.id.btnCheckout);
    }
}
