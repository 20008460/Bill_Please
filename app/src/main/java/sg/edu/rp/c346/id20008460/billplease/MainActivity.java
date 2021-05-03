package sg.edu.rp.c346.id20008460.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        EditText amt;
        EditText noPax;
        EditText discount;

        TextView total;
        TextView eachPax;
        TextView notValid;

        Button splitBtn;
        Button resetBtn;

        ToggleButton gstTBTN;
        ToggleButton svsTBTN;

        RadioGroup rgPayment;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amt = findViewById(R.id.editAmt) ;
        noPax = findViewById(R.id.editPax);
        discount = findViewById(R.id.editDiscount);

        total = findViewById(R.id.totalBill);
        eachPax = findViewById(R.id.eachPay);
        notValid = findViewById(R.id.invalid);

        splitBtn = findViewById(R.id.tbtnSplit);
        resetBtn = findViewById(R.id.tbtnReset);

        gstTBTN = findViewById(R.id.tbtnGST);
        svsTBTN = findViewById(R.id.tbtnSVS);

        rgPayment = findViewById(R.id.payment);

        splitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = false;

                if (!amt.getText().toString().trim().isEmpty() && !noPax.getText().toString().trim().isEmpty() && !discount.getText().toString().trim().isEmpty()) {

                    double amtX = Double.parseDouble(amt.getText().toString());
                    int numPax = Integer.parseInt(noPax.getText().toString());
                    double disRate = Double.parseDouble(discount.getText().toString()) / 100;

                    if (amtX != 0.0 && numPax != 0 ) {
                        if (svsTBTN.isChecked() && gstTBTN.isChecked()) {
                            amtX *= 1.17;
                        } else if (svsTBTN.isChecked() && !gstTBTN.isChecked()) {
                            amtX *= 1.10;

                        } else if (!svsTBTN.isChecked() && gstTBTN.isChecked()) {
                            amtX *= 1.07;

                        } else {
                            amtX = amtX;
                        }


                        if (discount.getText().toString().trim().length() != 0) {

                            amtX *= 1 - (disRate);


                        }

                        total.setText("Total Bill: $" + String.format("%.2f", amtX));

                        int checkedRadioId = rgPayment.getCheckedRadioButtonId();

                        if (numPax > 1) {
                            if (checkedRadioId == R.id.payNow) {

                                eachPax.setText("Each Pays: $" + String.format("%.2f,", amtX / numPax) + " via PayNow to 912345678");
                            } else {
                                eachPax.setText("Each Pays: $" + String.format("%.2f,", amtX / numPax) + " in cash");
                            }
                        } else {
                            if (checkedRadioId == R.id.payNow) {
                                eachPax.setText("Each Pays: $" + String.format("%.2f,", amtX / numPax) + " via PayNow to 912345678");
                            } else {
                                eachPax.setText("Each Pays: $" + String.format("%.2f,", amtX / numPax) + " in cash");
                            }
                        }
                        notValid.setText("");

                    } else if (amtX == 0.0 || numPax == 0 ) {

                        notValid.setText("Invalid");
                        eachPax.setText("");
                        total.setText("");

                    }

                } else {

                    notValid.setText("Invalid");
                    eachPax.setText("");
                    total.setText("");

                    if (discount.getText().toString().trim().equalsIgnoreCase("")) {
                        discount.setError("This field can not be blank");
                    }

                    if (amt.getText().toString().trim().equalsIgnoreCase("")) {
                        amt.setError("This field can not be blank");
                    }

                    if (noPax.getText().toString().trim().equalsIgnoreCase("")) {
                        noPax.setError("This field can not be blank");
                    }

                }
            }
        });


        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amt.setText("");
                noPax.setText("");
                discount.setText("");

                svsTBTN.setChecked(false);
                gstTBTN.setChecked(false);

                rgPayment.check(R.id.Cash);

                total.setText("");
                eachPax.setText("");
                notValid.setText("");

            }
        });


    }
}