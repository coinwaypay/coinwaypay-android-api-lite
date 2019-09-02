package at.coinway.coinwaypay.apilite;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class CoinwayPayApiLite {
    public static void checkout(Activity activity, CoinwayPayPayment payment, int requestCode) {


        Intent payIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                "coinwaypay://pay/1.0"
                        + "?affiliate-key=" + payment.getAffiliateKey()
                        + "&callback=" + payment.getCallback()
                        + "&total=" + payment.getTotal().toString()
                        + "&reference-id=" + payment.getReferenceId()
                        + "&use-demo=" + ("" + payment.getUseDemo()).toLowerCase()));

        payIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivityForResult(payIntent, requestCode);
    }
}
