package at.coinway.coinwaypay.apilitesample;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.math.BigDecimal;
import java.util.UUID;

import at.coinway.coinwaypay.apilite.CoinwayPayApiLite;
import at.coinway.coinwaypay.apilite.CoinwayPayPayment;
import at.coinway.coinwaypay.apilite.CoinwayPayResult;
import at.coinway.coinwaypay.apilite.MissingParameterException;
import at.coinway.coinwaypay.apilite.ParseUriException;

public class MainActivity extends AppCompatActivity {
    private static final int PAYMENT_REQUEST_CODE = 1;

    private EditText edt_affiliate_key;
    private EditText edt_callback;
    private EditText edt_total;
    private EditText edt_reference;
    private Button btn_start;
    private CheckBox cbx_demo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        handleIntent(intent);
        

        edt_affiliate_key = findViewById(R.id.edt_affiliate_key);
        edt_callback = findViewById(R.id.edt_callback);
        edt_total = findViewById(R.id.edt_total);
        edt_reference = findViewById(R.id.edt_reference);
        btn_start = findViewById(R.id.btn_start);
        cbx_demo = findViewById(R.id.cbx_demo);

        edt_affiliate_key.setText(BuildConfig.AFFILIATE_KEY);
        edt_callback.setText("coinwaypaysampleapp://pay-result");

        // disable fields
        edt_affiliate_key.setInputType(InputType.TYPE_NULL);
        edt_callback.setInputType(InputType.TYPE_NULL);

        btn_start.setOnClickListener(c -> start());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if(intent != null && intent.getData() != null && "pay-result".equals(intent.getData().getHost())) {
            handlePaymentResult(intent);
        }
    }

    private void handlePaymentResult(Intent intent) {
        if(intent == null || intent.getData() == null)
            return;

        Uri uri = intent.getData();

        try {
            CoinwayPayResult result = CoinwayPayResult.parser().parse(uri);

            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Payment Result");
            alertDialog.setMessage(result.toString());
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        } catch (ParseUriException e) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Payment Result Parse Uri Exception");
            alertDialog.setMessage(e.getMessage());
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void start() {
        boolean hasError = false;

        if(edt_total.getText().toString().isEmpty()) {
            edt_total.setError("Required");
            hasError = true;
        }
        else {
            edt_total.setError(null);
        }

        if(hasError)
            return;

        CoinwayPayPayment payment = null;

        try {
            payment = CoinwayPayPayment.builder()
                    .affiliateKey(edt_affiliate_key.getText().toString())
                    .callback(edt_callback.getText().toString())
                    .total(new BigDecimal(edt_total.getText().toString()))
                    .referenceId(edt_reference.getText().toString())
                    .useDemo(cbx_demo.isChecked())
                    .build();
        } catch (MissingParameterException e) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Missing Parameter");
            alertDialog.setMessage(e.getMessage());
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

            return;
        }

        CoinwayPayApiLite.checkout(MainActivity.this, payment, PAYMENT_REQUEST_CODE);
    }
}
