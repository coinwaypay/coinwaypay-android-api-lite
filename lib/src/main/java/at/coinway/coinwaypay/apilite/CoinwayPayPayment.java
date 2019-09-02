package at.coinway.coinwaypay.apilite;

import android.support.annotation.NonNull;

import java.math.BigDecimal;

public final class CoinwayPayPayment {

    public static Builder builder() {
        return new Builder();
    }

    private String mAffiliateKey;
    private String mReferenceId;
    private String mCallback;
    private BigDecimal mTotal;
    private boolean mUseDemo;

    public String getAffiliateKey() {
        return mAffiliateKey;
    }
    public String getReferenceId() {
        return mReferenceId;
    }
    public String getCallback() {
        return mCallback;
    }
    public BigDecimal getTotal() {
        return mTotal;
    }
    public boolean getUseDemo() { return mUseDemo; }

    private CoinwayPayPayment(Builder builder) {
        mAffiliateKey = builder.mAffiliateKey;
        mReferenceId = builder.mReferenceId;
        mTotal = builder.mTotal;
        mCallback = builder.mCallback;
        mUseDemo = builder.mUseDemo;
    }

    public static final class Builder {
        // required
        private String mAffiliateKey;
        private String mCallback;
        private BigDecimal mTotal;
        // optional
        private String mReferenceId = null;
        private boolean mUseDemo = false;

        private Builder() {

        }

        public Builder affiliateKey(@NonNull String affiliateKey) {
            this.mAffiliateKey = affiliateKey;

            return this;
        }
        public Builder total(@NonNull BigDecimal total) {
            this.mTotal = total;

            return this;
        }
        public Builder callback(@NonNull String callback) {
            this.mCallback = callback;

            return this;
        }
        public Builder referenceId(String referenceId) {
            this.mReferenceId = referenceId;

            return this;
        }
        public Builder useDemo(boolean useDemo) {
            mUseDemo = useDemo;

            return this;
        }

        public CoinwayPayPayment build() throws MissingParameterException {
            if(mAffiliateKey == null || mAffiliateKey.isEmpty())
                throw new MissingParameterException("No affiliate key found");
            if(mCallback == null || mCallback.isEmpty())
                throw new MissingParameterException("No callback value found");
            if(mTotal == null)
                throw new MissingParameterException("No total value found");

            return new CoinwayPayPayment(this);
        }
    }
}
