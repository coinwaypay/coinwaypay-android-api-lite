# CoinwayPAY Android API Lite

The CoinwayPAY Android API Lite provides an API to integrate the CoinwayPAY Payment functionality  directily in other apps.

You have to create an deep link to call the CoinwayPAY App and it will return to your app.

The CoinwayPayPayment object helps you, to build the url.

The sampleapp provides an example implementation of the API.

```java
...
CoinwayPayPayment payment = CoinwayPayPayment.builder()
    .affiliateKey("<incert affiliate key>")
    .callback("coinwaypaysampleapp://pay-result")
    .total(new BigDecimal("25.0"))
    .referenceId("R191001")
    .useDemo(false)
    .build();

CoinwayPayApiLite.checkout(MainActivity.this, payment, PAYMENT_REQUEST_CODE);
...

private void handlePaymentResult(Intent intent) {
    ...
    CoinwayPayResult res = CoinwayPayResult.parser().parse(intent.getData());

    String result = res.getResult();
    String state = res.getState();
    String sid = res.getSid();
    String reference = res.getReferenceId();
    String currency = res.getCurrency();
    String cryptoCurrency = res.getCryptoCurrency();
    String errorMessage = res.getErrorMessage();
    boolean isDemo = res.isDemo();
}
```