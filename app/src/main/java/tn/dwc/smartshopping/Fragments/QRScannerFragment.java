package tn.dwc.smartshopping.Fragments;

        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Typeface;
        import android.media.MediaPlayer;
        import android.media.Ringtone;
        import android.media.RingtoneManager;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Vibrator;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Toast;

        import com.github.mrengineer13.snackbar.SnackBar;
        import com.google.zxing.BarcodeFormat;
        import com.google.zxing.Result;

        import java.util.ArrayList;
        import java.util.List;

        import me.dm7.barcodescanner.zxing.ZXingScannerView;
        import tn.dwc.smartshopping.Activities.Container;
        import tn.dwc.smartshopping.Activities.DetailQRScanActivity;
        import tn.dwc.smartshopping.Entities.Maps_Shelve;
        import tn.dwc.smartshopping.R;
        import tn.dwc.smartshopping.SQLite.Maps_ShelveBDD;

public class QRScannerFragment extends Fragment implements ZXingScannerView.ResultHandler {
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
        barcodeFormats.add(BarcodeFormat.QR_CODE);
      /*  barcodeFormats.add(BarcodeFormat.UPC_A);
        barcodeFormats.add( BarcodeFormat.UPC_E);
        barcodeFormats.add(BarcodeFormat.EAN_13);
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


      //  mp = MediaPlayer.create(getActivity(), R.raw.scan);

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
      //  mScannerView.startCamera();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                v.vibrate(500);

                Log.e("result", rawResult.getText());
                List<Maps_Shelve> maps_shelves = new ArrayList<>();
                Maps_ShelveBDD maps_shelveBDD = new Maps_ShelveBDD(getActivity());
                maps_shelveBDD.open();

                maps_shelves = maps_shelveBDD.selectAll();
                maps_shelveBDD.close();
                for (int i = 0; i < maps_shelves.size(); i++) {
                    if (maps_shelves.get(i).getQr_ID().equals(rawResult.getText().toString())) {
                        intent = new Intent(getActivity(), DetailQRScanActivity.class);
                        intent.putExtra("shelve_id", maps_shelves.get(i).getShelve_Id());
                        intent.putExtra("shelve_map", maps_shelves.get(i).getMaps_Sh());
                        intent.putExtra("shelve_qr", maps_shelves.get(i).getQr_ID());
                        intent.putExtra("shelve_hb", maps_shelves.get(i).getH_B());
                        found=true;
                        startActivity(intent);
                        getActivity().finish();
                    }

                }
if(found==false){
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