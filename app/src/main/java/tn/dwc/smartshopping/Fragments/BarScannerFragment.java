package tn.dwc.smartshopping.Fragments;

        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Typeface;
        import android.media.MediaPlayer;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Vibrator;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.github.mrengineer13.snackbar.SnackBar;
        import com.google.zxing.BarcodeFormat;
        import com.google.zxing.Result;

        import java.util.ArrayList;
        import java.util.List;

        import me.dm7.barcodescanner.zxing.ZXingScannerView;
        import tn.dwc.smartshopping.Activities.DetailBarScanActivity;
        import tn.dwc.smartshopping.Activities.DetailQRScanActivity;
        import tn.dwc.smartshopping.Entities.Maps_Shelve;
        import tn.dwc.smartshopping.Entities.Products;
        import tn.dwc.smartshopping.R;
        import tn.dwc.smartshopping.SQLite.Maps_ShelveBDD;
        import tn.dwc.smartshopping.SQLite.ProductsBDD;

public class BarScannerFragment extends Fragment implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    List<BarcodeFormat> barcodeFormats;
    MediaPlayer  mp;
    Vibrator v;
    Intent intent;
    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private static final String SELECTED_FORMATS = "SELECTED_FORMATS";
    private static final String CAMERA_ID = "CAMERA_ID";
Boolean found = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        barcodeFormats= new ArrayList<>();
        barcodeFormats.add(BarcodeFormat.EAN_13);
       // barcodeFormats.add(BarcodeFormat.QR_CODE);
      /*  barcodeFormats.add(BarcodeFormat.UPC_A);
        barcodeFormats.add( BarcodeFormat.UPC_E);

        barcodeFormats.add( BarcodeFormat.EAN_8);
        barcodeFormats.add( BarcodeFormat.RSS_14);
        barcodeFormats.add(BarcodeFormat.CODE_39);
        barcodeFormats.add( BarcodeFormat.CODE_93);
        barcodeFormats.add( BarcodeFormat.CODE_128);
        barcodeFormats.add( BarcodeFormat.ITF);
        barcodeFormats.add( BarcodeFormat.CODABAR);

        barcodeFormats.add(BarcodeFormat.DATA_MATRIX);
        barcodeFormats.add( BarcodeFormat.PDF_417);*/
        mScannerView = new ZXingScannerView(getActivity());
mScannerView.setFormats(barcodeFormats);






        mScannerView.setAutoFocus(true);


       // mp = MediaPlayer.create(getActivity(), R.raw.scan);

       v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        return mScannerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }






    @Override
    public void handleResult(final Result rawResult) {
        mp.start();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                v.vibrate(500);

                Log.e("result", rawResult.getText());
                List<Products> productsList = new ArrayList<>();
                ProductsBDD productsBDD = new ProductsBDD(getActivity());
                productsBDD.open();

                productsList = productsBDD.selectAll();
                productsBDD.close();
                for (int i = 0; i < productsList.size(); i++) {
                    if (productsList.get(i).getProduct_id().equals(rawResult.getText().toString())) {
                        found = true;
                       intent = new Intent(getActivity(), DetailBarScanActivity.class);
                        intent.putExtra("product_id", productsList.get(i).getProduct_id());
                        intent.putExtra("product_name", productsList.get(i).getProduct_name());
                        intent.putExtra("product_price", productsList.get(i).getProduct_price());
                        intent.putExtra("product_sheve_id", productsList.get(i).getProduct_sheve_id());
                        if (productsList.get(i).getProduct_sub_category().equals("none")) {
                            intent.putExtra("product_category", productsList.get(i).getProduct_category());
                        } else {

                            intent.putExtra("product_category", productsList.get(i).getProduct_category() + " - " + productsList.get(i).getProduct_sub_category());
                        }
                        intent.putExtra("product_description", productsList.get(i).getProduct_desc());
                        intent.putExtra("product_picture", productsList.get(i).getProduct_image());

                        startActivity(intent);
                        getActivity().finish();

                        new SnackBar.Builder(getActivity())

                                .withMessage("Code Found") //   Spent / Budget :"+
                                .withTypeFace(Typeface.DEFAULT_BOLD)


                                .withActionMessage("Close")
                                .withStyle(SnackBar.Style.INFO)
                                .withTextColorId(R.color.my_color_2)
                                .withBackgroundColorId(R.color.my_color)
                                .withDuration(SnackBar.MED_SNACK)
                                .show();
                    }

                }
                if (found == false) {
                    mScannerView.startCamera();

                    new SnackBar.Builder(getActivity())

                            .withMessage("Please Scan Qr codes Located on the nearest shelve") //   Spent / Budget :"+
                            .withTypeFace(Typeface.DEFAULT_BOLD)


                            .withActionMessage("Close")
                            .withStyle(SnackBar.Style.INFO)
                            .withTextColorId(R.color.my_color_2)
                            .withBackgroundColorId(R.color.my_color)
                            .withDuration(SnackBar.MED_SNACK)
                            .show();

}

            }

        }, 500);



          //  Toast.makeText(getActivity(), "Contents = " + rawResult.getText() +
            //        ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();









    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }


}