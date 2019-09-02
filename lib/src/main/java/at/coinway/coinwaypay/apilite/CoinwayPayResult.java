package at.coinway.coinwaypay.apilite;

import android.net.Uri;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class CoinwayPayResult {
    public static Parser parser() {
        return new Parser();
    }

    private String mResult;
    private String mState;
    private String mSid;
    private String mReferenceId;
    private String mCurrency;
    private String mCryptoCurrency;
    private String mErrorMessage;
    private boolean mDemo;


    public String getResult() { return mResult; }
    public String getState() { return mState; }
    public String getSid() { return mSid; }
    public String getReferenceId() { return mReferenceId; }
    public String getCurrency() { return mCurrency; }
    public String getCryptoCurrency() { return mCryptoCurrency; }
    public String getErrorMessage() { return mErrorMessage; }
    public boolean isDemo() { return mDemo; }

    private CoinwayPayResult(CoinwayPayResult.Parser parser) {
        mResult = parser.mResult;
        mState = parser.mState;
        mSid = parser.mSid;
        mReferenceId = parser.mReferenceId;
        mCurrency = parser.mCurrency;
        mCryptoCurrency = parser.mCryptoCurrency;
        mErrorMessage = parser.mErrorMessage;
        mDemo = parser.mDemo;
    }

    @NonNull
    @Override
    public String toString() {

        String r = "CoinwayPayResult " + mResult + "\n";
        r += "State: " + mState + "\n";
        r += "Sid: " + mSid + "\n";
        r += "ReferenceId: " + mReferenceId + "\n";
        r += "Currency: " + mCurrency + "\n";
        r += "Crypto Currency: " + mCryptoCurrency + "\n";
        r += "Error Message: " + mErrorMessage + "\n";
        r += "Demo: " + mDemo + "\n";

        return r;
    }

    public static final class Parser {
        private String mResult;
        private String mState;
        private String mSid;
        private String mReferenceId;
        private String mCurrency;
        private String mCryptoCurrency;
        private String mErrorMessage;
        private boolean mDemo;

        private Parser() {

        }

        public CoinwayPayResult parse(@NonNull Uri uri) throws ParseUriException {

            mResult = uri.getQueryParameter("cp-result");
            mState = uri.getQueryParameter("cp-state");
            mSid = uri.getQueryParameter("cp-sid");
            mReferenceId = uri.getQueryParameter("cp-reference-id");
            mCryptoCurrency = uri.getQueryParameter("cp-crypto-currency");
            mCurrency = uri.getQueryParameter("cp-currency");
            mErrorMessage = uri.getQueryParameter("cp-error-message");
            mDemo = "true".equals(uri.getQueryParameter("cp-is-demo"));

            if(mResult == null || mResult.isEmpty())
                throw new ParseUriException("Parameter cp-result not found");

            return new CoinwayPayResult(this);
        }
    }
}
