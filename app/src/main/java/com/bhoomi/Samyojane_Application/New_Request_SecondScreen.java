package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class New_Request_SecondScreen extends AppCompatActivity{
    TextView applicant_infor, tvServiceName, tvOr, tv_for_Aadhaar;
    String applicant_name;
    String applicant_Id;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    String raisedLoc, name, fatherName, motherName, rationCardNo, aadharNo, mobileNo, address1, address2, address3, report_No, pinCode;
    String add_pin;
    String appImage=null;
    EditText tvName, tvFatherName, tvMotherName;
    EditText tvRationCardNo, tvMobile, tvAddress1, tvAddress2, tvAddress3, tvPinCode;
    TextView txt_raiseLoc, tvHobli, tvTaluk, tvVA_Name, tv_IDName;
    String district, taluk,hobli,va_Circle_Code,VA_Circle_Name, VA_Name, district_Code, taluk_Code, hobli_Code;
    Button btnNext, btnBack;
    String item_position;
    String village_Code,habitationCode, service_Code;
    String strSearchVillageName, strSearchServiceName;
    GPSTracker gpsTracker;
    double latitude, longitude, accuracy;
    Button btnScan, btnCaptureAadhaar;
    TextView tvNameUID, tvAddressUID, tvUID;
    LinearLayout lAadhaarDetails, lQrCodeScan, lCompleteAadhaar, lUID;
    ImageView store_Aadhaar_image;
    //qr code scanner object
    IntentIntegrator qrScan;
    int Aadhaar=0;
    private static final int CAMERA_REQUEST_1 = 2;
    String UID=null, name_UID=null, address_UID3=null, address_UID4=null;
    String send_UID, send_AadharPhoto;
    byte[] imageInByte, imageInByte1;
    static String store_1;
    static String getString=null, getString1=null;
    ProgressDialog dialog;
    ImageView scannerImage, captureImage;
    String service_name, village_name, town_Name, ward_Name, town_code, ward_code, option_Flag;
    Set_and_Get_Service_Parameter set_and_get_service_parameter;
    SqlLiteOpenHelper_Class sqlLiteOpenHelper_class;
    int len;
    String eng_certi, GSC_FirstPart;
    boolean return_Value;
    InputMethodManager imm;
    InputMethodSubtype ims;
    TextView txt_ReportNo;
    Character code, code_Value='5';
    String str;
    int Id_Code;
    String Id_Name;

    private static final String ALGORITHM = "AES";
    private static final String KEY = "1Hbfh667adfDEJ78";
    public static Key key;
    public static String decryptedValue;
    String encryptUID_No, encryptAadhaar_Photo;

    private InputFilter filter = (source, start, end, dest, dstart, dend) -> {
        Log.d("Source",""+source);
        String blockCharacterSet = "~`!@#$%^&*()_-''+={}[]:/?><,.\\\"\";£€¢¥₩§|×÷¿■□♤♡◇♧°•○●☆▪¤《》¡₹Π℅®©™∆√¶";
        if (source != null && blockCharacterSet.contains(("" + source))) {
            Log.d("Blocked",""+source);
            return "";
        }
        return null;
    };

    private InputFilter filter_Eng = (source, start, end, dest, dstart, dend) -> {
        Log.d("Source",""+source);
        String l1 = "ಅಆಇಈಉಊಋಎಏಐಒಓಔಅಂಅಃ";
        String l2 = "ಕಕಾಕಿಕೀಕುಕೂಕೃಕೆಕೇಕೈಕೊಕೋಕೌಕಂಕಃಕ್";
        String l3 = "ಖಖಾಖಿಖೀಖುಖೂಖೃಖೆಖೇಖೈಖೊಖೋಖೌಖಂಖಃಖ್";
        String l4 = "ಗಗಾಗಿಗೀಗುಗೂಗೃಗೆಗೇಗೈಗೊಗೋಗೌಗಂಗಃಗ್";
        String l5 = "ಘಘಾಘಿಘೀಘುಘೂಘೃಘೆಘೇಘೈಘೊಘೋಘೌಘಂಘಃಘ್";
        String l6 = "ಙಙಾಙಿಙೀಙುಙೂಙೃಙೆಙೇಙೈಙೊಙೋಙೌಙಂಙಃಙ್";
        String l7 = "ಚಚಾಚಿಚೀಚುಚೂಚೃಚೆಚೇಚೈಚೊಚೋಚೌಚಂಚಃಚ್";
        String l8 = "ಛಛಾಛಿಛೀಛುಛೂಛೃಛೆಛೇಛೈಛೊಛೋಛೌಛಂಛಃಛ್";
        String l9 = "ಜಜಾಜಿಜೀಜುಜೂಜೃಜೆಜೇಜೈಜೊಜೋಜೌಜಂಜಃಜ್";
        String l10 = "ಝಝಾಝಿಝೀಝುಝೂಝೃಝೆಝೇಝೈಝೊಝೋಝೌಝಂಝಃಝ್";
        String l11 = "ಞಞಾಞಿಞೀಞುಞೂಞೃಞೆಞೇಞೈಞೊಞೋಞೌಞಂಞಃಞ್";
        String l12 = "ಟಟಾಟಿಟೀಟುಟೂಟೃಟೆಟೇಟೈಟೊಟೋಟೌಟಂಟಃಟ್";
        String l13 = "ಠಠಾಠಿಠೀಠುಠೂಠೃಠೆಠೇಠೈಠೊಠೋಠೌಠಂಠಃಠ್";
        String l14 = "ಡಡಾಡಿಡೀಡುಡೂಡೃಡೆಡೇಡೈಡೊಡೋಡೌಡಂಡಃಡ್";
        String l15 = "ಢಢಾಢಿಢೀಢುಢೂಢೃಢೆಢೇಢೈಢೊಢೋಢೌಢಂಢಃಢ್";
        String l16 = "ಣಣಾಣಿಣೀಣುಣೂಣೃಣೆಣೇಣೈಣೊಣೋಣೌಣಂಣಃಣ್";
        String l17 = "ತತಾತಿತೀತುತೂತೃತೆತೇತೈತೊತೋತೌತಂತಃತ್";
        String l18 = "ಥಥಾಥಿಥೀಥುಥೂಥೃಥೆಥೇಥೈಥೊಥೋಥೌಥಂಥಃಥ್";
        String l19 = "ದದಾದಿದೀದುದೂದೃದೆದೇದೈದೊದೋದೌದಂದಃದ್";
        String l20 = "ಧಧಾಧಿಧೀಧುಧೂಧೃಧೆಧೇಧೈಧೊಧೋಧೌಧಂಧಃಧ್";
        String l21 = "ನನಾನಿನೀನುನೂನೃನೆನೇನೈನೊನೋನೌನಂನಃನ್";
        String l22 = "ಪಪಾಪಿಪೀಪುಪೂಪೃಪೆಪೇಪೈಪೊಪೋಪೌಪಂಪಃಪ್";
        String l23 = "ಫಫಾಫಿಫೀಫುಫೂಫೃಫೆಫೇಫೈಫೊಫೋಫೌಫಂಫಃಫ್";
        String l24 = "ಬಬಾಬಿಬೀಬುಬೂಬೃಬೆಬೇಬೈಬೊಬೋಬೌಬಂಬಃಬ್";
        String l25 = "ಭಭಾಭಿಭೀಭುಭೂಭೃಭೆಭೇಭೈಭೊಭೋಭೌಭಂಭಃಭ್";
        String l26 = "ಮಮಾಮಿಮೀಮುಮೂಮೃಮೆಮೇಮೈಮೊಮೋಮೌಮಂಮಃಮ್";
        String l27 = "ಯಯಾಯಿಯೀಯುಯೂಯೃಯೆಯೇಯೈಯೊಯೋಯೌಯಂಯಃಯ್";
        String l28 = "ರರಾರಿರೀರುರೂರೃರೆರೇರೈರೊರೋರೌರಂರಃರ್";
        String l29 = "ಱಱಾಱಿಱೀಱುಱೂಱೃಱೆಱೇಱೈಱೊಱೋಱೌಱಂಱಃಱ್";
        String l30 = "ಲಲಾಲಿಲೀಲುಲೂಲೃಲೆಲೇಲೈಲೊಲೋಲೌಲಂಲಃಲ್";
        String l31 = "ವವಾವಿವೀವುವೂವೃವೆವೇವೈವೊವೋವೌವಂವಃವ್";
        String l32 = "ಶಶಾಶಿಶೀಶುಶೂಶೃಶೆಶೇಶೈಶೊಶೋಶೌಶಂಶಃಶ್";
        String l33 = "ಷಷಾಷಿಷೀಷುಷೂಷೃಷೆಷೇಷೈಷೊಷೋಷೌಷಂಷಃಷ್";
        String l34 = "ಸಸಾಸಿಸೀಸುಸೂಸೃಸೆಸೇಸೈಸೊಸೋಸೌಸಂಸಃಸ್";
        String l35 = "ಹಹಾಹಿಹೀಹುಹೂಹೃಹೆಹೇಹೈಹೊಹೋಹೌಹಂಹಃಹ್";
        String l36 = "ಳಳಾಳಿಳೀಳುಳೂಳೃಳೆಳೇಳೈಳೊಳೋಳೌಳಂಳಃಳ್";
        String l37 = "ೞೞಾೞಿೞೀೞುೞೂೞೃೞೆೞೇೞೈೞೊೞೋೞೌೞಂೞಃೞ್";
        String op1 = "~`!@#$%^&*()_-''+={}[]:/?><,.\\\"\";£€¢¥₩§|×÷¿■□♤♡◇♧°•○●☆▪¤《》¡₹Π℅®©™∆√¶1234567890೧೨೩೪೫೬೭೮೯೦";

        String blockCharacterSet = l1+l2+l3+l4+l5+l6+l7+l8+l9+l10+l11+l12+l13+l14+l15+l16+l17+l18+l19+l20
                +l21+l22+l23+l24+l25+l26+l27+l28+l29+l30+l31+l32+l33+l34+l35+l36+l37+op1;
        if (source != null && blockCharacterSet.contains(("" + source))) {
            Log.d("Blocked",""+source);
            return "";
        }
//        for ( int i = start ; i < end ; i++) {
//            String checkMe = String. valueOf (source.charAt(i));
//            //Pattern pattern = Pattern.compile("[\\u0C80-\\u0CFF]");
//            Pattern pattern = Pattern.compile("a-zA-Z");
//            Matcher re = pattern.matcher(checkMe);
//            if (!re.matches()) {
//                Log.d("Filter_valid","blocked");
//                return "";
//            }
//        }
        Log.d("Filter_valid","Not blocked");
        return null;
    };

    private InputFilter filter_Kan = (source, start, end, dest, dstart, dend) -> {
        Log.d("Source",""+source);
        String l1 = "abcdefghijklmnopqrstuvwxyz";
        String l2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String op1 = "~`!@#$%^&*()_-''+={}[]:/?><,.\\\"\";£€¢¥₩§|×÷¿■□♤♡◇♧°•○●☆▪¤《》¡₹Π℅®©™∆√¶1234567890೧೨೩೪೫೬೭೮೯೦";
        String blockCharacterSet = l1+l2+op1;

        for (int i = start; i < end; i++) {
            Log.d("source.charAt(i)",""+i+" : "+source.charAt(i));
            if (source != null && blockCharacterSet.contains(("" + source.charAt(i)))) {
                Log.d("Blocked",""+source);
                return "";
            }
        }
        Log.d("Filter_valid","Not blocked");
        return null;
    };

    private InputFilter filter_Eng_Add = (source, start, end, dest, dstart, dend) -> {
        Log.d("Source",""+source);
        String l1 = "ಅಆಇಈಉಊಋಎಏಐಒಓಔಅಂಅಃ";
        String l2 = "ಕಕಾಕಿಕೀಕುಕೂಕೃಕೆಕೇಕೈಕೊಕೋಕೌಕಂಕಃಕ್";
        String l3 = "ಖಖಾಖಿಖೀಖುಖೂಖೃಖೆಖೇಖೈಖೊಖೋಖೌಖಂಖಃಖ್";
        String l4 = "ಗಗಾಗಿಗೀಗುಗೂಗೃಗೆಗೇಗೈಗೊಗೋಗೌಗಂಗಃಗ್";
        String l5 = "ಘಘಾಘಿಘೀಘುಘೂಘೃಘೆಘೇಘೈಘೊಘೋಘೌಘಂಘಃಘ್";
        String l6 = "ಙಙಾಙಿಙೀಙುಙೂಙೃಙೆಙೇಙೈಙೊಙೋಙೌಙಂಙಃಙ್";
        String l7 = "ಚಚಾಚಿಚೀಚುಚೂಚೃಚೆಚೇಚೈಚೊಚೋಚೌಚಂಚಃಚ್";
        String l8 = "ಛಛಾಛಿಛೀಛುಛೂಛೃಛೆಛೇಛೈಛೊಛೋಛೌಛಂಛಃಛ್";
        String l9 = "ಜಜಾಜಿಜೀಜುಜೂಜೃಜೆಜೇಜೈಜೊಜೋಜೌಜಂಜಃಜ್";
        String l10 = "ಝಝಾಝಿಝೀಝುಝೂಝೃಝೆಝೇಝೈಝೊಝೋಝೌಝಂಝಃಝ್";
        String l11 = "ಞಞಾಞಿಞೀಞುಞೂಞೃಞೆಞೇಞೈಞೊಞೋಞೌಞಂಞಃಞ್";
        String l12 = "ಟಟಾಟಿಟೀಟುಟೂಟೃಟೆಟೇಟೈಟೊಟೋಟೌಟಂಟಃಟ್";
        String l13 = "ಠಠಾಠಿಠೀಠುಠೂಠೃಠೆಠೇಠೈಠೊಠೋಠೌಠಂಠಃಠ್";
        String l14 = "ಡಡಾಡಿಡೀಡುಡೂಡೃಡೆಡೇಡೈಡೊಡೋಡೌಡಂಡಃಡ್";
        String l15 = "ಢಢಾಢಿಢೀಢುಢೂಢೃಢೆಢೇಢೈಢೊಢೋಢೌಢಂಢಃಢ್";
        String l16 = "ಣಣಾಣಿಣೀಣುಣೂಣೃಣೆಣೇಣೈಣೊಣೋಣೌಣಂಣಃಣ್";
        String l17 = "ತತಾತಿತೀತುತೂತೃತೆತೇತೈತೊತೋತೌತಂತಃತ್";
        String l18 = "ಥಥಾಥಿಥೀಥುಥೂಥೃಥೆಥೇಥೈಥೊಥೋಥೌಥಂಥಃಥ್";
        String l19 = "ದದಾದಿದೀದುದೂದೃದೆದೇದೈದೊದೋದೌದಂದಃದ್";
        String l20 = "ಧಧಾಧಿಧೀಧುಧೂಧೃಧೆಧೇಧೈಧೊಧೋಧೌಧಂಧಃಧ್";
        String l21 = "ನನಾನಿನೀನುನೂನೃನೆನೇನೈನೊನೋನೌನಂನಃನ್";
        String l22 = "ಪಪಾಪಿಪೀಪುಪೂಪೃಪೆಪೇಪೈಪೊಪೋಪೌಪಂಪಃಪ್";
        String l23 = "ಫಫಾಫಿಫೀಫುಫೂಫೃಫೆಫೇಫೈಫೊಫೋಫೌಫಂಫಃಫ್";
        String l24 = "ಬಬಾಬಿಬೀಬುಬೂಬೃಬೆಬೇಬೈಬೊಬೋಬೌಬಂಬಃಬ್";
        String l25 = "ಭಭಾಭಿಭೀಭುಭೂಭೃಭೆಭೇಭೈಭೊಭೋಭೌಭಂಭಃಭ್";
        String l26 = "ಮಮಾಮಿಮೀಮುಮೂಮೃಮೆಮೇಮೈಮೊಮೋಮೌಮಂಮಃಮ್";
        String l27 = "ಯಯಾಯಿಯೀಯುಯೂಯೃಯೆಯೇಯೈಯೊಯೋಯೌಯಂಯಃಯ್";
        String l28 = "ರರಾರಿರೀರುರೂರೃರೆರೇರೈರೊರೋರೌರಂರಃರ್";
        String l29 = "ಱಱಾಱಿಱೀಱುಱೂಱೃಱೆಱೇಱೈಱೊಱೋಱೌಱಂಱಃಱ್";
        String l30 = "ಲಲಾಲಿಲೀಲುಲೂಲೃಲೆಲೇಲೈಲೊಲೋಲೌಲಂಲಃಲ್";
        String l31 = "ವವಾವಿವೀವುವೂವೃವೆವೇವೈವೊವೋವೌವಂವಃವ್";
        String l32 = "ಶಶಾಶಿಶೀಶುಶೂಶೃಶೆಶೇಶೈಶೊಶೋಶೌಶಂಶಃಶ್";
        String l33 = "ಷಷಾಷಿಷೀಷುಷೂಷೃಷೆಷೇಷೈಷೊಷೋಷೌಷಂಷಃಷ್";
        String l34 = "ಸಸಾಸಿಸೀಸುಸೂಸೃಸೆಸೇಸೈಸೊಸೋಸೌಸಂಸಃಸ್";
        String l35 = "ಹಹಾಹಿಹೀಹುಹೂಹೃಹೆಹೇಹೈಹೊಹೋಹೌಹಂಹಃಹ್";
        String l36 = "ಳಳಾಳಿಳೀಳುಳೂಳೃಳೆಳೇಳೈಳೊಳೋಳೌಳಂಳಃಳ್";
        String l37 = "ೞೞಾೞಿೞೀೞುೞೂೞೃೞೆೞೇೞೈೞೊೞೋೞೌೞಂೞಃೞ್";
        String op1 = "~`!@$%^&*()_-''+={}[]:?><.\"\";£€¢¥₩§|×÷¿■□♤♡◇♧°•○●☆▪¤《》¡₹Π℅®©™∆√¶";

        String blockCharacterSet = l1+l2+l3+l4+l5+l6+l7+l8+l9+l10+l11+l12+l13+l14+l15+l16+l17+l18+l19+l20
                +l21+l22+l23+l24+l25+l26+l27+l28+l29+l30+l31+l32+l33+l34+l35+l36+l37+op1;
        if (source != null && blockCharacterSet.contains(("" + source))) {
            Log.d("Blocked",""+source);
            return "";
        }
//        for ( int i = start ; i < end ; i++) {
//            String checkMe = String. valueOf (source.charAt(i));
//            //Pattern pattern = Pattern.compile("[\\u0C80-\\u0CFF]");
//            Pattern pattern = Pattern.compile("a-zA-Z");
//            Matcher re = pattern.matcher(checkMe);
//            if (!re.matches()) {
//                Log.d("Filter_valid","blocked");
//                return "";
//            }
//        }
        Log.d("Filter_valid","Not blocked");
        return null;
    };

    private InputFilter filter_Kan_Add = (source, start, end, dest, dstart, dend) -> {
        Log.d("Source",""+source);
        String l1 = "abcdefgijklmnopqrsuvwxyz";
        String l2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String op1 = "~`!@$%^&*()_-''+={}[]:?><.\"\";£€¢¥₩§|×÷¿■□♤♡◇♧°•○●☆▪¤《》¡₹Π℅®©™∆√¶";
        String blockCharacterSet = l1+l2+op1;

        for (int i = start; i < end; i++) {
            Log.d("source.charAt(i)",""+i+" : "+source.charAt(i));
            if (source != null && blockCharacterSet.contains(("" + source.charAt(i)))) {
                Log.d("Blocked",""+source);
                return "";
            }
        }
        Log.d("Filter_valid","Not blocked");
        return null;
    };

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint({"SetTextI18n", "HardwareIds", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_request_secondscreen);

        tvTaluk = findViewById(R.id.taluk);
        tvHobli = findViewById(R.id.hobli);
        tvVA_Name = findViewById(R.id.VA_name);

        btnBack = findViewById(R.id.btnBack);
        txt_raiseLoc = findViewById(R.id.txt_raiseLoc);
        tvName = findViewById(R.id.tvName);
        tvFatherName = findViewById(R.id.tvFatherNme);
        tvMotherName = findViewById(R.id.tvMotherName);
        tv_IDName = findViewById(R.id.tv_IDName);
        tvRationCardNo = findViewById(R.id.tvRationCardNo);
        tvRationCardNo.setFilters(new InputFilter[] { filter });
        tvMobile = findViewById(R.id.tvMobile);
        tvAddress1 = findViewById(R.id.tvAddress1);
        tvAddress2 = findViewById(R.id.tvAddress2);
        tvAddress3 = findViewById(R.id.tvAddress3);
        tvPinCode = findViewById(R.id.tvPinCode);
        btnNext = findViewById(R.id.btnNext);
        tvServiceName = findViewById(R.id.tvServiceName);
        btnScan = findViewById(R.id.buttonScan);
        btnCaptureAadhaar = findViewById(R.id.btnCaptureAadhaar);
        tvUID = findViewById(R.id.tvUID);
        tvNameUID = findViewById(R.id.tvNameUID);
        tvAddressUID = findViewById(R.id.tvAddressUID);
        //tvQrCodeScanner = findViewById(R.id.tvQrCodeScanner);
        lAadhaarDetails = findViewById(R.id.lAadhaarDetails);
        lQrCodeScan = findViewById(R.id.lQrCodeScan);
        store_Aadhaar_image = findViewById(R.id.store_Aadhaar_image);
        lCompleteAadhaar = findViewById(R.id.lCompleteAadhaar);
        lUID = findViewById(R.id.lUID);
        scannerImage = findViewById(R.id.scannerImage);
        captureImage = findViewById(R.id.captureImage);
        tvOr = findViewById(R.id.tvOr);
        txt_ReportNo = findViewById(R.id.txt_ReportNo);
        tv_for_Aadhaar = findViewById(R.id.tv_for_Aadhaar);

//        tvName.setFilters(new InputFilter[] { filter });
//        tvFatherName.setFilters(new InputFilter[] { filter });
//        tvMotherName.setFilters(new InputFilter[] { filter });

        //intializing scan object
        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);

        store_Aadhaar_image.setVisibility(View.GONE);
        lAadhaarDetails.setVisibility(View.GONE);

        PhoneNumberUtils.formatNumber(tvMobile.getText().toString());
        tvMobile.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10)}); // 10 is max digits

        PhoneNumberUtils.formatNumber(tvPinCode.getText().toString());
        tvPinCode.setFilters(new InputFilter[] {new InputFilter.LengthFilter(6)});

        applicant_infor= findViewById(R.id.applicant_Information);
        applicant_infor.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        Intent i = getIntent();
        district = i.getStringExtra("districtCode");
        taluk = i.getStringExtra("taluk");
        VA_Name = i.getStringExtra("VA_Name");
        applicant_name = i.getStringExtra("applicant_name");
        applicant_Id = i.getStringExtra("applicant_Id");
        district_Code = i.getStringExtra("district_Code");
        taluk_Code = i.getStringExtra("taluk_Code");
        hobli_Code = i.getStringExtra("hobli_Code");
        hobli = i.getStringExtra("hobli");
        va_Circle_Code = i.getStringExtra("va_Circle_Code");
        VA_Circle_Name = i.getStringExtra("VA_Circle_Name");
        item_position = i.getStringExtra("item_position");
        village_Code = i.getStringExtra("villageCode");
        habitationCode = i.getStringExtra("habitationCode");
        service_Code = i.getStringExtra("serviceCode");
        service_name = i.getStringExtra("strSearchServiceName");
        village_name = i.getStringExtra("strSearchVillageName");
        report_No = i.getStringExtra("report_No");
        town_code = i.getStringExtra("town_code");
        town_Name = i.getStringExtra("town_Name");
        ward_code = i.getStringExtra("ward_code");
        ward_Name = i.getStringExtra("ward_Name");
        option_Flag = i.getStringExtra("option_Flag");

        Log.d("New_Request_Sec", ""+district_Code);
        Log.d("New_Request_Sec", ""+taluk_Code);
        Log.d("New_Request_Sec",""+hobli_Code);
        Log.d("New_Request_Sec",""+village_name);
        Log.d("New_Request_Sec_Hab",""+habitationCode);
        Log.d("New_Request_Sec", "town_code: "+town_code);
        Log.d("New_Request_Sec", "town_Name: "+town_Name);
        Log.d("New_Request_Sec", "ward_code: "+ward_code);
        Log.d("New_Request_Sec", "ward_Name: "+ward_Name);
        Log.d("New_Request_Sec","option_Flag: "+ option_Flag);
        Log.d("New_Request_Sec","service_name: "+ service_name);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.loading));
        dialog.setIndeterminate(true);

//        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        assert imm != null;
//        ims = imm.getCurrentInputMethodSubtype();
//        String locale = ims.getLocale();
//        Locale locale2 = new Locale(locale);
//        String currentLanguage = locale2.getDisplayLanguage();
//        Log.d("lang:", "" + currentLanguage);
//        if (!Objects.equals(currentLanguage, "kn_in")) {
//            return_Value = searchPackage();
//            Log.d("return_Value", "" +return_Value);
//            if(!return_Value){
//                buildAlertMessage();
//            }else {
//                imm.showInputMethodPicker();
//            }
//        }


        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_SecondScreen.this);
        database = openHelper.getWritableDatabase();

        @SuppressLint("Recycle")
        Cursor cursor1=database.rawQuery("SELECT "+DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code+", "+DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code+" FROM "+ DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" where "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.District_Code+"="+district_Code+" and "+DataBaseHelperClass_btnDownload_ServiceTranTable.Taluk_Code+"="+taluk_Code
                +" and "+DataBaseHelperClass_btnDownload_ServiceTranTable.Hobli_Code+"="+hobli_Code+" and "+DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name+"='"+applicant_name
                +"' and "+DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No+"="+applicant_Id, null);
        if (cursor1.getCount()>0){
//            if (cursor1.moveToFirst()){
//                village_Code = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code));
//                service_Code = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code));
//            }

            openHelper = new DataBaseHelperClass_VillageNames(New_Request_SecondScreen.this);
            database = openHelper.getWritableDatabase();

            @SuppressLint("Recycle")
            Cursor cursor = database.rawQuery("select "+getString(R.string.village_table_habitation_name)+" from "+ DataBaseHelperClass_VillageNames.TABLE_NAME
                    +" where "+DataBaseHelperClass_VillageNames.HM_village_code+"='"+village_Code+"' and "+DataBaseHelperClass_VillageNames.HM_habitation_code+"="+habitationCode, null);
            if(cursor.getCount()>0) {
                if(cursor.moveToFirst()){
                    strSearchVillageName = cursor.getString(cursor.getColumnIndex(getString(R.string.village_table_habitation_name)));
                    Log.d("VillageName", ""+strSearchVillageName);
                }
            }

            openHelper = new DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster(New_Request_SecondScreen.this);
            database = openHelper.getWritableDatabase();

            @SuppressLint("Recycle")
            Cursor cursor2 = database.rawQuery("select "+getString(R.string.facility_table_name)+" from "
                    +DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME+" where "+DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_facility_code+"="+service_Code, null);
            if(cursor2.getCount()>0) {
                if(cursor2.moveToFirst()){
                    strSearchServiceName = cursor2.getString(cursor2.getColumnIndex(getString(R.string.facility_table_name)));
                    Log.d("ServiceName", ""+strSearchServiceName);
                }
            }
            Log.d("village_Code1", ""+village_Code);
            Log.d("habitationCode1",""+habitationCode);
            Log.d("service_Code1", ""+service_Code);
            Log.d("village_Name1", ""+strSearchVillageName);
            Log.d("service_Name1", ""+strSearchServiceName);
        }
        Log.d("village_Code1", ""+village_Code);
        Log.d("habitationCode", ""+habitationCode);
        Log.d("service_Code1", ""+service_Code);
        Log.d("Village_NameSecScreen", ""+strSearchVillageName);
        Log.d("Service_NameSecScreen", ""+strSearchServiceName);

        tvTaluk.setText(taluk);
        tvHobli.setText(hobli);
        tvVA_Name.setText(VA_Name);
        tvServiceName.setText(strSearchServiceName);

        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_SecondScreen.this);
        database = openHelper.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor=database.rawQuery("SELECT * FROM "+ DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME
                +" where "+DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name+"='"+applicant_name+"'"+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No+"="+applicant_Id, null);
        if(cursor.getCount()>0) {
            if(cursor.moveToFirst()){
                raisedLoc = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Raised_Location));
                name = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name));
                fatherName = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Father_Name));
                motherName = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Mother));
                Id_Code = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.ID_TYPE));
                rationCardNo = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.RationCard_No));
                mobileNo = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Mobile_No));
                address1 = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Address1));
                address2 = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Address2));
                address3 = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Address3));
                add_pin = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_applicant_cadd_pin));
                eng_certi = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Eng_Certificate));
                GSC_FirstPart = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_GSCFirstPart));
            }
        }


        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(this, str, str, str);
        sqlLiteOpenHelper_class.open_ID_Type_Tbl();
        Id_Name = sqlLiteOpenHelper_class.GetID_Name(Id_Code, getString(R.string.id_name));

        Log.d("ID_Name: ", ""+Id_Name);
        Log.d("Eng_Certi", eng_certi);
        Log.d("Raised_Location: ",""+raisedLoc);

        if(Objects.equals(raisedLoc, "N") || Objects.equals(raisedLoc, "S")){
            txt_raiseLoc.setText(getString(R.string.nadakacheri_raised));
        }else if(Objects.equals(raisedLoc, "P") || Objects.equals(raisedLoc, "B") || Objects.equals(raisedLoc, "K") || Objects.equals(raisedLoc, "G") || Objects.equals(raisedLoc, "I")){
            txt_raiseLoc.setText(getString(R.string.online));
        }else if(Objects.equals(raisedLoc, "E")){
            txt_raiseLoc.setText(getString(R.string.seva_sindhu));
        }else if(Objects.equals(raisedLoc, "W")){
            txt_raiseLoc.setText(getString(R.string.wallet));
        }else {
            txt_raiseLoc.setText(getString(R.string.not_specified));
        }

        tvName.setText(applicant_name);
        tvFatherName.setText(fatherName);
        tvMotherName.setText(motherName);
        tv_IDName.setText(Id_Name);
        if(Id_Code==19){
            tv_for_Aadhaar.setText(rationCardNo);
            tv_for_Aadhaar.setVisibility(View.VISIBLE);
            tvRationCardNo.setVisibility(View.GONE);
            tvRationCardNo.setText(rationCardNo);
        }else {
            tv_for_Aadhaar.setVisibility(View.GONE);
            tvRationCardNo.setVisibility(View.VISIBLE);
            tvRationCardNo.setText(rationCardNo);
        }
        tvMobile.setText(mobileNo);
        tvAddress1.setText(address1);
        tvAddress2.setText(address2);
        tvAddress3.setText(address3);
        tvPinCode.setText(add_pin);
        txt_ReportNo.setText(report_No);

        if (eng_certi.equals("K")){
            tvName.setFilters(new InputFilter[] { filter_Kan });
            tvFatherName.setFilters(new InputFilter[] { filter_Kan });
            tvMotherName.setFilters(new InputFilter[] { filter_Kan });
            tvAddress1.setFilters(new InputFilter[] { filter_Kan_Add });
            tvAddress2.setFilters(new InputFilter[] { filter_Kan_Add });
            tvAddress3.setFilters(new InputFilter[] { filter_Kan_Add });
        }else if (eng_certi.equals("E")){
            tvName.setFilters(new InputFilter[] { filter_Eng });
            tvFatherName.setFilters(new InputFilter[] { filter_Eng });
            tvMotherName.setFilters(new InputFilter[] { filter_Eng });
            tvAddress1.setFilters(new InputFilter[] { filter_Eng_Add });
            tvAddress2.setFilters(new InputFilter[] { filter_Eng_Add });
            tvAddress3.setFilters(new InputFilter[] { filter_Eng_Add });
        }
        tvName.setOnTouchListener((v, event) -> {
            if(Objects.equals(eng_certi, "K")){
                check_Kannada_Key_lang();
            }
            else if(Objects.equals(eng_certi, "E")) {
                check_English_Key_lang();
            }
            return false;
        });

//        tvName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (eng_certi.equals("K")){
//                    tvName.setFilters(new InputFilter[] { filter_Kan });
//                }else if (eng_certi.equals("E")){
//                    tvName.setFilters(new InputFilter[] { filter_Eng });
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

        tvFatherName.setOnTouchListener((v, event) -> {
            if(Objects.equals(eng_certi, "K")){
                check_Kannada_Key_lang();
            }
            else if(Objects.equals(eng_certi, "E")) {
                check_English_Key_lang();
            }
            return false;
        });

        tvMotherName.setOnTouchListener((v, event) -> {
            if(Objects.equals(eng_certi, "K")){
                check_Kannada_Key_lang();
            }
            else if(Objects.equals(eng_certi, "E")) {
                check_English_Key_lang();
            }
            return false;
        });

        tvAddress1.setOnTouchListener((v, event) -> {
            if(Objects.equals(eng_certi, "K")){
                check_Kannada_Key_lang();
            }
            else if(Objects.equals(eng_certi, "E")) {
                check_English_Key_lang();
            }
            return false;
        });
        tvAddress2.setOnTouchListener((v, event) -> {
            if(Objects.equals(eng_certi, "K")){
                check_Kannada_Key_lang();
            }
            else if(Objects.equals(eng_certi, "E")) {
                check_English_Key_lang();
            }
            return false;
        });
        tvAddress3.setOnTouchListener((v, event) -> {
            if(Objects.equals(eng_certi, "K")){
                check_Kannada_Key_lang();
            }
            else if(Objects.equals(eng_certi, "E")) {
                check_English_Key_lang();
            }
            return false;
        });
        gpsTracker = new GPSTracker(getApplicationContext(), this);

        btnScan.setOnClickListener(v -> {
            //initiating the qr code scan
            qrScan.initiateScan();
            Aadhaar=1;
        });

        btnCaptureAadhaar.setOnClickListener(v -> {
            Aadhaar=2;
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_1);
        });

        btnNext.setOnClickListener(v -> {

            if (gpsTracker.canGetLocation()) {
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();
                accuracy = gpsTracker.getAccuracy();
                Log.d("Location", "\nLat: "+latitude+"\nLong: "+longitude+"\nAccuracy: "+accuracy);
            } else {
                //gpsTracker.showSettingsAlert();
                Toast.makeText(getApplicationContext(), getString(R.string.switch_on_gps), Toast.LENGTH_SHORT).show();
            }

            applicant_name = tvName.getText().toString();
            fatherName = tvFatherName.getText().toString();
            motherName = tvMotherName.getText().toString();
            rationCardNo =tvRationCardNo.getText().toString();
            mobileNo = tvMobile.getText().toString();
            address1 = tvAddress1.getText().toString();
            address2 = tvAddress2.getText().toString();
            address3 = tvAddress3.getText().toString();
            pinCode = tvPinCode.getText().toString();

            Log.d("Values", ""+applicant_name);
            Log.d("Values", ""+fatherName);
            Log.d("Values", ""+motherName);
            Log.d("Values", ""+rationCardNo);
            Log.d("Values", ""+mobileNo);
            Log.d("Values", ""+address1);
            Log.d("Values", ""+address2);
            Log.d("Values", ""+address3);
            Log.d("Values", ""+pinCode);

            if(latitude!=0.0 && longitude!=0.0) {
                if(TextUtils.isEmpty(applicant_name)){
                    tvName.setError(getString(R.string.field_canno_null));
                }else {
                    if (TextUtils.isEmpty(fatherName)) {
                        tvFatherName.setError(getString(R.string.field_canno_null));
                    } else {
                        if (TextUtils.isEmpty(motherName)) {
                            tvMotherName.setError(getString(R.string.field_canno_null));
                        } else {
                            if (TextUtils.isEmpty(rationCardNo)) {
                                tvRationCardNo.setError(getString(R.string.field_canno_null));
                            } else {
                                if (TextUtils.isEmpty(mobileNo)) {
                                    tvMobile.setError(getString(R.string.field_canno_null));
                                } else {
                                    if (!isValidMobile(mobileNo)) {
                                        tvMobile.setError(getString(R.string.enter_valid_phone_num));
                                    } else {
                                        if (TextUtils.isEmpty(address1)) {
                                            tvAddress1.setError(getString(R.string.field_canno_null));
                                        } else {
                                            if (TextUtils.isEmpty(address2)) {
                                                tvAddress2.setError(getString(R.string.field_canno_null));
                                            } else {
                                                if (TextUtils.isEmpty(pinCode)) {
                                                    tvPinCode.setError(getString(R.string.field_canno_null));
                                                } else {
                                                    code = pinCode.charAt(0);
                                                    Log.d("code",""+code);
                                                    if (code.equals(code_Value)){
                                                        Log.d("code: ", "code value matched");
                                                        len = pinCode.length();
                                                        if (len != 6) {
                                                            tvPinCode.setError(getString(R.string.enter_valid_pincode));
                                                        } else {
                                                            CheckAadhaarDetails();
                                                            finish();
                                                        }
                                                    }else {
                                                        Log.d("code: ", "code value do not matched");
                                                        tvPinCode.setError(getString(R.string.enter_valid_pincode));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                runOnUiThread(() -> {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(New_Request_SecondScreen.this);
                    dialog.setCancelable(false);
                    dialog.setTitle("Alert");
                    dialog.setMessage(getString(R.string.cannot_get_location) );
                    dialog.setNegativeButton(getString(R.string.ok), (dialog1, which) -> {
                        //Action for "Cancel".
                        dialog1.cancel();
                    });

                    final AlertDialog alert = dialog.create();
                    alert.show();
                });
                //Toast.makeText(getApplicationContext(), "Please Switch on the GPS", Toast.LENGTH_SHORT).show();
            }
        });
//        btnClear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UID="";
//                name_UID="";
//                scannerImage.setVisibility(View.VISIBLE);
//                btnScan.setVisibility(View.VISIBLE);
//                lAadhaarDetails.setVisibility(View.GONE);
//                captureImage.setVisibility(View.VISIBLE);
//                btnCaptureAadhaar.setVisibility(View.VISIBLE);
//                tvUID.setText("");
//                tvNameUID.setText("");
//                //tvAddressUID.setText(address_UID+","+ address_UID1+", "+ address_UID2+", "+ address_UID3+", "+ address_UID4);
//                tvAddressUID.setText("");
//
//            }
//        });

        btnBack.setOnClickListener(v -> onBackPressed());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void check_Kannada_Key_lang(){
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        ims = imm.getCurrentInputMethodSubtype();
        String locale = ims.getLocale();
        Locale locale2 = new Locale(locale);
        String currentLanguage = locale2.getDisplayLanguage();
        Log.d("lang:", "" + currentLanguage);
        if (!Objects.equals(currentLanguage, "kn_in")) {
            Toast.makeText(getApplicationContext(), getString(R.string.switch_kannada), Toast.LENGTH_SHORT).show();
            return_Value = searchPackage();
            Log.d("return_Value", "" +return_Value);
            if(!return_Value){
                Log.d("Enter", "!return_value");
                buildAlertMessage();
            }else {
                imm.showInputMethodPicker();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void check_English_Key_lang(){
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        ims = imm.getCurrentInputMethodSubtype();
        String locale = ims.getLocale();
        Locale locale2 = new Locale(locale);
        String currentLanguage = locale2.getDisplayLanguage();
        String[] split_curr = currentLanguage.split("_");
        String cur_value = split_curr[0];
        Log.d("cur_value", cur_value);
        Log.d("lang:", "" + currentLanguage);
        if (!Objects.equals(cur_value, "en")) {
            Toast.makeText(getApplicationContext(), getString(R.string.switch_english), Toast.LENGTH_SHORT).show();
            imm.showInputMethodPicker();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean searchPackage(){

        InputMethodInfo inputMethodInfo;
        PackageManager packageManager = getPackageManager();
        List<InputMethodInfo> str;
        str = imm.getEnabledInputMethodList();
        System.out.println(str);
        List<String> list = new ArrayList<>();

        for (int i = 0; i < str.size(); i++) {
            System.out.println(str.get(i));
            inputMethodInfo = imm.getEnabledInputMethodList().get(i);
            String str2 = inputMethodInfo.loadLabel(packageManager).toString();
            Log.d("Package_List", str2);
            list.add(str2);
        }
        Log.d("Print_List", String.valueOf(list));

        boolean get = false;

        for(String s1 : list){
            if(s1.contains("Samyojane")){
                get=true;
            }
        }
        Log.d("search", String.valueOf(get));
        return get;

    }

    private  void buildAlertMessage() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.keyboard_language))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                    startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS));
                    onBackPressed();
                })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> buildAlert());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private  void buildAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.must_change_keyboard))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), (dialog, id) -> {
                    startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS));
                    onBackPressed();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private  void buildAlertMessageGoingBack() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.discard_changes))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                    New_Request_SecondScreen.super.onBackPressed();
                    Intent i = new Intent(New_Request_SecondScreen.this, New_Request.class);
                    i.putExtra("applicant_Id", applicant_Id);
                    i.putExtra("applicant_name",applicant_name);
                    i.putExtra("district_Code", district_Code);
                    i.putExtra("taluk_Code", taluk_Code);
                    i.putExtra("hobli_Code", hobli_Code);
                    i.putExtra("districtCode", district);
                    i.putExtra("taluk", taluk);
                    i.putExtra("RI_Name", VA_Name);
                    i.putExtra("hobli", hobli);
                    i.putExtra("va_Circle_Code", va_Circle_Code);
                    i.putExtra("VA_Circle_Name", VA_Circle_Name);
                    i.putExtra("VA_Name", VA_Name);
                    i.putExtra("strSearchServiceName", service_name);
                    i.putExtra("strSearchVillageName", village_name);
                    i.putExtra("villageCode", village_Code);
                    i.putExtra("habitationCode", habitationCode);
                    i.putExtra("serviceCode", service_Code);
                    i.putExtra("report_No", report_No);
                    i.putExtra("option_Flag", option_Flag);
                    i.putExtra("town_Name", town_Name);
                    i.putExtra("town_code", town_code);
                    i.putExtra("ward_Name", ward_Name);
                    i.putExtra("ward_code", ward_code);
                    i.putExtra("eng_certi",eng_certi);
                    startActivity(i);
                    finish();
                })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean isValidMobile(String phone) {
        boolean check;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            check = phone.length() == 10;
        } else {
            check=false;
        }
        return check;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    public void CheckAadhaarDetails(){

        btnNext.setText(getString(R.string.loading_C));
        String str;
        Intent i;

        name = tvName.getText().toString();
        fatherName = tvFatherName.getText().toString();
        motherName = tvMotherName.getText().toString();
        rationCardNo =tvRationCardNo.getText().toString();
        mobileNo = tvMobile.getText().toString();
        address1 = tvAddress1.getText().toString();
        address2 = tvAddress2.getText().toString();
        address3 = tvAddress3.getText().toString();
        pinCode = tvPinCode.getText().toString();

        Log.d("Values", ""+name);
        Log.d("Values", ""+fatherName);
        Log.d("Values", ""+motherName);
        Log.d("Values", ""+rationCardNo);
        Log.d("Values", ""+mobileNo);
        Log.d("Values", ""+address1);
        Log.d("Values", ""+address2);
        Log.d("Values", ""+address3);
        Log.d("Values", ""+pinCode);
        Log.d("Values", ""+report_No);

        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_SecondScreen.this);
        database = openHelper.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor2=database.rawQuery("SELECT * FROM "+ DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME
                +" where "+DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code+"='"+service_Code+"'"+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No+"="+applicant_Id, null);
        if(cursor2.getCount()>0) {
            if(cursor2.moveToFirst()){
                appImage = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_applicant_photo));
            }
        }else {
            appImage=null;
        }

        if (appImage==null){
            appImage=null;
        }

        if (Aadhaar == 1) {
            try {
                encryptUID_No = encrypt(UID);
                send_UID = encryptUID_No;
            } catch (Exception e) {
                e.printStackTrace();
            }
            aadharNo = UID;

            if(latitude!=0.0 && longitude!=0.0) {

                openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_SecondScreen.this);
                database = openHelper.getWritableDatabase();

                @SuppressLint("Recycle")
                Cursor cursor = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1 + " where "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No + "=" + applicant_Id, null);
                if (cursor.getCount() > 0) {

                    database.execSQL("update "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1 + " set "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.VA_Accepts_Applicant_information+"='"+"NO"+"',"
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name + "='" + name + "', "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Father_Name + "='" + fatherName + "', "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Mother + "='" + motherName + "', "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.U_RationCard_No + "='" + rationCardNo + "', "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.U_Mobile_No + "=" + mobileNo + ","
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Address1 + "='" + address1 +"',"
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Address2 + "='" + address2 +"',"
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Address3 + "='" + address3 +"',"
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.PinCode + "=" +pinCode +","
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Report_No + "='" +report_No +"',"
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.UID + "='" + send_UID + "',"
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.AadhaarPhoto + "='" + send_AadharPhoto + "'" + " where "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.District_Code + "=" + district_Code + " and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Taluk_Code + "=" + taluk_Code + " and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Hobli_Code + "=" + hobli_Code + " and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No + "=" + applicant_Id + " and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code + "=" + village_Code + " and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Habitation_code+"="+ habitationCode+" and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code+"="+town_code+" and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code+"="+ward_code+" and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code + "=" + service_Code);

                    Log.d("Database", "ServiceParameterTable Database Updated");

                    switch (service_Code) {
                        case "6":
                        case "9": {
                            Log.d("Service:", "" + service_Code);
                            if(Objects.equals(eng_certi, "E")) {
                                Log.d("Application", "English");
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_Income_Parameters.class);
                            }else {
                                Log.d("Application", "Kannada");
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_Income_Parameters_Kan.class);
                            }
                            i.putExtra("districtCode", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("applicant_Id", applicant_Id);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("hobli", hobli);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("district_Code", district_Code);
                            i.putExtra("taluk_Code", taluk_Code);
                            i.putExtra("hobli_Code", hobli_Code);
                            i.putExtra("rationCardNo", rationCardNo);
                            i.putExtra("aadharNo", aadharNo);
                            i.putExtra("mobileNo", mobileNo);
                            i.putExtra("address1", address1);
                            i.putExtra("item_position", item_position);
                            i.putExtra("strSearchServiceName", strSearchServiceName);
                            i.putExtra("strSearchVillageName", strSearchVillageName);
                            i.putExtra("serviceCode", service_Code);
                            i.putExtra("villageCode", village_Code);
                            i.putExtra("habitationCode", habitationCode);
                            i.putExtra("eng_certi", eng_certi);
                            i.putExtra("option_Flag", option_Flag);
                            i.putExtra("town_Name", town_Name);
                            i.putExtra("town_code", town_code);
                            i.putExtra("ward_Name", ward_Name);
                            i.putExtra("ward_code", ward_code);
                            startActivity(i);
                            finish();
                            break;
                        }
                        case "7":
                        case "8": {
                            Log.d("Service:", "" + service_Code);
                            if(Objects.equals(eng_certi, "E")) {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_sc_st_certi_Parameters.class);
                            }
                            else {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_sc_st_certi_Parameters_Kan.class);
                            }
                            i.putExtra("districtCode", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("applicant_Id", applicant_Id);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("hobli", hobli);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("district_Code", district_Code);
                            i.putExtra("taluk_Code", taluk_Code);
                            i.putExtra("hobli_Code", hobli_Code);
                            i.putExtra("rationCardNo", rationCardNo);
                            i.putExtra("aadharNo", aadharNo);
                            i.putExtra("mobileNo", mobileNo);
                            i.putExtra("address1", address1);
                            i.putExtra("item_position", item_position);
                            i.putExtra("strSearchServiceName", strSearchServiceName);
                            i.putExtra("strSearchVillageName", strSearchVillageName);
                            i.putExtra("serviceCode", service_Code);
                            i.putExtra("villageCode", village_Code);
                            i.putExtra("habitationCode", habitationCode);
                            i.putExtra("eng_certi", eng_certi);
                            i.putExtra("option_Flag", option_Flag);
                            i.putExtra("town_Name", town_Name);
                            i.putExtra("town_code", town_code);
                            i.putExtra("ward_Name", ward_Name);
                            i.putExtra("ward_code", ward_code);
                            startActivity(i);
                            finish();
                            break;
                        }
                        case "10": {
                            Log.d("Service:", "" + service_Code);
                            if(Objects.equals(eng_certi, "E")) {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Resident_Parameters.class);
                            }else {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Resident_Parameters_Kan.class);
                            }
                            i.putExtra("districtCode", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("applicant_Id", applicant_Id);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("hobli", hobli);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("district_Code", district_Code);
                            i.putExtra("taluk_Code", taluk_Code);
                            i.putExtra("hobli_Code", hobli_Code);
                            i.putExtra("rationCardNo", rationCardNo);
                            i.putExtra("aadharNo", aadharNo);
                            i.putExtra("mobileNo", mobileNo);
                            i.putExtra("address1", address1);
                            i.putExtra("item_position", item_position);
                            i.putExtra("strSearchServiceName", strSearchServiceName);
                            i.putExtra("strSearchVillageName", strSearchVillageName);
                            i.putExtra("serviceCode", service_Code);
                            i.putExtra("villageCode", village_Code);
                            i.putExtra("habitationCode", habitationCode);
                            i.putExtra("eng_certi", eng_certi);
                            i.putExtra("option_Flag", option_Flag);
                            i.putExtra("town_Name", town_Name);
                            i.putExtra("town_code", town_code);
                            i.putExtra("ward_Name", ward_Name);
                            i.putExtra("ward_code", ward_code);
                            startActivity(i);
                            finish();
                            break;
                        }
                        default:
                            Toast.makeText(getApplicationContext(), getString(R.string.service_not_availabel), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
                else {
                    set_and_get_service_parameter = new Set_and_Get_Service_Parameter();
                    set_and_get_service_parameter.setDistrict_Code(district_Code);
                    set_and_get_service_parameter.setTaluk_Code(taluk_Code);
                    set_and_get_service_parameter.setHobli_Code(hobli_Code);
                    set_and_get_service_parameter.setVa_Circle_Code(va_Circle_Code);
                    set_and_get_service_parameter.setVillage_Code(village_Code);
                    set_and_get_service_parameter.setHabitation_code(habitationCode);
                    set_and_get_service_parameter.setTown_Code(town_code);
                    set_and_get_service_parameter.setWard_Code(ward_code);
                    set_and_get_service_parameter.setService_Code(String.valueOf(service_Code));
                    set_and_get_service_parameter.setRD_No(applicant_Id);
                    set_and_get_service_parameter.setApplicant_Name(name);
                    set_and_get_service_parameter.setFather_Name(fatherName);
                    set_and_get_service_parameter.setMother_Name(motherName);
                    set_and_get_service_parameter.setMobile_No(mobileNo);
                    set_and_get_service_parameter.setRationCard_No(rationCardNo);
                    set_and_get_service_parameter.setAddress1(address1);
                    set_and_get_service_parameter.setAddress2(address2);
                    set_and_get_service_parameter.setAddress3(address3);
                    set_and_get_service_parameter.setEng_Certify(eng_certi);
                    set_and_get_service_parameter.setGSC_First_Part(GSC_FirstPart);
                    set_and_get_service_parameter.setST_applicant_photo(appImage);
                    set_and_get_service_parameter.setPinCode(pinCode);
                    set_and_get_service_parameter.setReport_No(report_No);
                    set_and_get_service_parameter.setUID(send_UID);
                    set_and_get_service_parameter.setAadhaarPhoto(send_AadharPhoto);


                    database.execSQL("insert into " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1
                            + "(ST_district_code, ST_taluk_code, ST_hobli_code, ST_va_Circle_Code, ST_village_code, ST_habitation_code, ST_town_code, ST_ward_no, ST_facility_code, ST_GSC_No, ST_applicant_name, ST_father_name, ST_mother_name, ST_Upd_mobile_no," +
                            "ST_Upd_ID_NUMBER, ST_applicant_caddress1, ST_applicant_caddress2,ST_applicant_caddress3, ST_Eng_Certificate, ST_GSCFirstPart, ST_applicant_photo, UID, AadhaarPhoto, ST_PinCode, Report_No, VA_Accepts_Applicant_information)" +
                            " values ("
                            + set_and_get_service_parameter.getDistrict_Code() + ","
                            + set_and_get_service_parameter.getTaluk_Code() + ","
                            + set_and_get_service_parameter.getHobli_Code() + ","
                            + set_and_get_service_parameter.getVa_Circle_Code() + ","
                            + set_and_get_service_parameter.getVillage_Code() + ","
                            + set_and_get_service_parameter.getHabitation_code() + ","
                            + set_and_get_service_parameter.getTown_Code() + ","
                            + set_and_get_service_parameter.getWard_Code() + ","
                            + set_and_get_service_parameter.getService_Code() + ","
                            + set_and_get_service_parameter.getRD_No() + ",'"
                            + set_and_get_service_parameter.getApplicant_Name() + "','"
                            + set_and_get_service_parameter.getFather_Name() + "','"
                            + set_and_get_service_parameter.getMother_Name() + "','"
                            + set_and_get_service_parameter.getMobile_No() + "','"
                            + set_and_get_service_parameter.getRationCard_No() + "','"
                            + set_and_get_service_parameter.getAddress1() + "','"
                            + set_and_get_service_parameter.getAddress2() + "','"
                            + set_and_get_service_parameter.getAddress3() + "','"
                            + set_and_get_service_parameter.getEng_Certify()+"',"
                            + set_and_get_service_parameter.getGSC_First_Part()+",'"
                            + set_and_get_service_parameter.getST_applicant_photo()+"','"
                            + set_and_get_service_parameter.getUID() + "','"
                            + set_and_get_service_parameter.getAadhaarPhoto() + "',"
                            + set_and_get_service_parameter.getPinCode() + ",'"
                            + set_and_get_service_parameter.getReport_No() +"','NO')");

                    Log.d("Database", "ServiceParameterTable Database Inserted");

                    switch (service_Code) {
                        case "6":
                        case "9": {
                            Log.d("Service:", "6 or 9");
                            if(Objects.equals(eng_certi, "E")) {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_Income_Parameters.class);
                            } else {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_Income_Parameters_Kan.class);
                            }
                            i.putExtra("districtCode", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("applicant_Id", applicant_Id);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("hobli", hobli);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("district_Code", district_Code);
                            i.putExtra("taluk_Code", taluk_Code);
                            i.putExtra("hobli_Code", hobli_Code);
                            i.putExtra("rationCardNo", rationCardNo);
                            i.putExtra("aadharNo", aadharNo);
                            i.putExtra("mobileNo", mobileNo);
                            i.putExtra("address1", address1);
                            i.putExtra("item_position", item_position);
                            i.putExtra("strSearchServiceName", strSearchServiceName);
                            i.putExtra("strSearchVillageName", strSearchVillageName);
                            i.putExtra("serviceCode", service_Code);
                            i.putExtra("villageCode", village_Code);
                            i.putExtra("habitationCode", habitationCode);
                            i.putExtra("eng_certi", eng_certi);
                            i.putExtra("option_Flag", option_Flag);
                            i.putExtra("town_Name", town_Name);
                            i.putExtra("town_code", town_code);
                            i.putExtra("ward_Name", ward_Name);
                            i.putExtra("ward_code", ward_code);
                            startActivity(i);
                            finish();
                            break;
                        }
                        case "7":
                        case "8": {
                            Log.d("Service:", "8");
                            if(Objects.equals(eng_certi, "E")) {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_sc_st_certi_Parameters.class);
                            }else {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_sc_st_certi_Parameters_Kan.class);
                            }
                            i.putExtra("districtCode", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("applicant_Id", applicant_Id);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("hobli", hobli);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("district_Code", district_Code);
                            i.putExtra("taluk_Code", taluk_Code);
                            i.putExtra("hobli_Code", hobli_Code);
                            i.putExtra("rationCardNo", rationCardNo);
                            i.putExtra("aadharNo", aadharNo);
                            i.putExtra("mobileNo", mobileNo);
                            i.putExtra("address1", address1);
                            i.putExtra("item_position", item_position);
                            i.putExtra("strSearchServiceName", strSearchServiceName);
                            i.putExtra("strSearchVillageName", strSearchVillageName);
                            i.putExtra("serviceCode", service_Code);
                            i.putExtra("villageCode", village_Code);
                            i.putExtra("habitationCode", habitationCode);
                            i.putExtra("eng_certi", eng_certi);
                            i.putExtra("option_Flag", option_Flag);
                            i.putExtra("town_Name", town_Name);
                            i.putExtra("town_code", town_code);
                            i.putExtra("ward_Name", ward_Name);
                            i.putExtra("ward_code", ward_code);
                            startActivity(i);
                            finish();
                            break;
                        }
                        case "10": {
                            Log.d("Service:", "10");
                            if(Objects.equals(eng_certi, "E")) {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Resident_Parameters.class);
                            }else {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Resident_Parameters_Kan.class);
                            }
                            i.putExtra("districtCode", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("applicant_Id", applicant_Id);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("hobli", hobli);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("district_Code", district_Code);
                            i.putExtra("taluk_Code", taluk_Code);
                            i.putExtra("hobli_Code", hobli_Code);
                            i.putExtra("rationCardNo", rationCardNo);
                            i.putExtra("aadharNo", aadharNo);
                            i.putExtra("mobileNo", mobileNo);
                            i.putExtra("address1", address1);
                            i.putExtra("item_position", item_position);
                            i.putExtra("strSearchServiceName", strSearchServiceName);
                            i.putExtra("strSearchVillageName", strSearchVillageName);
                            i.putExtra("serviceCode", service_Code);
                            i.putExtra("villageCode", village_Code);
                            i.putExtra("habitationCode", habitationCode);
                            i.putExtra("eng_certi", eng_certi);
                            i.putExtra("option_Flag", option_Flag);
                            i.putExtra("town_Name", town_Name);
                            i.putExtra("town_code", town_code);
                            i.putExtra("ward_Name", ward_Name);
                            i.putExtra("ward_code", ward_code);
                            startActivity(i);
                            finish();
                            break;
                        }
                        default:
                            Toast.makeText(getApplicationContext(), getString(R.string.service_not_availabel), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
            else {
                runOnUiThread(() -> {
                    btnNext.setText(getString(R.string.certified));
                    AlertDialog.Builder dialog = new AlertDialog.Builder(New_Request_SecondScreen.this);
                    dialog.setCancelable(false);
                    dialog.setTitle(getString(R.string.alert));
                    dialog.setMessage(getString(R.string.cannot_get_location) );
                    dialog.setNegativeButton(getString(R.string.ok), (dialog1, which) -> {
                        //Action for "Cancel".
                        dialog1.cancel();
                    });

                    final AlertDialog alert = dialog.create();
                    alert.show();
                });
            }

            str="Success";
        } else if (Aadhaar == 2) {
            try {
                encryptAadhaar_Photo = encrypt(store_1);
                send_AadharPhoto = encryptAadhaar_Photo;
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(latitude!=0.0 && longitude!=0.0) {
                openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_SecondScreen.this);
                database = openHelper.getWritableDatabase();

                @SuppressLint("Recycle")
                Cursor cursor = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1 + " where "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No + "=" + applicant_Id, null);
                if (cursor.getCount() > 0) {
                    database.execSQL("update " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1 + " set "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.VA_Accepts_Applicant_information+"='"+"NO"+"',"
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name + "='" + name + "', "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Father_Name + "='" + fatherName + "', "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Mother + "='" + motherName + "', "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.U_RationCard_No + "='" + rationCardNo + "', "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.U_Mobile_No + "=" + mobileNo + ","
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Address1 + "='" + address1 +"',"
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Address2 + "='" + address2 +"',"
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Address3 + "='" + address3 +"',"
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.PinCode + "=" + pinCode + ","
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Report_No + "='"+report_No+"',"
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.UID + "='" + send_UID + "',"
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.AadhaarPhoto + "='" + send_AadharPhoto + "'" + " where "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.District_Code + "=" + district_Code + " and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Taluk_Code + "=" + taluk_Code + " and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Hobli_Code + "=" + hobli_Code + " and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No + "=" + applicant_Id + " and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code + "=" + village_Code + " and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code+"="+town_code+" and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code+"="+ward_code+" and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code + "=" + service_Code);
                    Log.d("Database", "ServiceParameterTable Database Updated");

                    switch (service_Code) {
                        case "6":
                        case "9": {
                            Log.d("Service:", "" + service_Code);
                            if(Objects.equals(eng_certi, "E")) {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_Income_Parameters.class);
                            }else {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_Income_Parameters_Kan.class);
                            }
                            i.putExtra("districtCode", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("applicant_Id", applicant_Id);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("hobli", hobli);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("district_Code", district_Code);
                            i.putExtra("taluk_Code", taluk_Code);
                            i.putExtra("hobli_Code", hobli_Code);
                            i.putExtra("rationCardNo", rationCardNo);
                            i.putExtra("aadharNo", aadharNo);
                            i.putExtra("mobileNo", mobileNo);
                            i.putExtra("address1", address1);
                            i.putExtra("item_position", item_position);
                            i.putExtra("strSearchServiceName", strSearchServiceName);
                            i.putExtra("strSearchVillageName", strSearchVillageName);
                            i.putExtra("serviceCode", service_Code);
                            i.putExtra("villageCode", village_Code);
                            i.putExtra("habitationCode", habitationCode);
                            i.putExtra("eng_certi", eng_certi);
                            i.putExtra("option_Flag", option_Flag);
                            i.putExtra("town_Name", town_Name);
                            i.putExtra("town_code", town_code);
                            i.putExtra("ward_Name", ward_Name);
                            i.putExtra("ward_code", ward_code);
                            startActivity(i);
                            finish();
                            break;
                        }
                        case "7":
                        case "8": {
                            Log.d("Service:", "" + service_Code);
                            if(Objects.equals(eng_certi, "E")) {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_sc_st_certi_Parameters.class);
                            }else {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_sc_st_certi_Parameters_Kan.class);
                            }
                            i.putExtra("districtCode", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("applicant_Id", applicant_Id);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("hobli", hobli);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("district_Code", district_Code);
                            i.putExtra("taluk_Code", taluk_Code);
                            i.putExtra("hobli_Code", hobli_Code);
                            i.putExtra("rationCardNo", rationCardNo);
                            i.putExtra("aadharNo", aadharNo);
                            i.putExtra("mobileNo", mobileNo);
                            i.putExtra("address1", address1);
                            i.putExtra("item_position", item_position);
                            i.putExtra("strSearchServiceName", strSearchServiceName);
                            i.putExtra("strSearchVillageName", strSearchVillageName);
                            i.putExtra("serviceCode", service_Code);
                            i.putExtra("villageCode", village_Code);
                            i.putExtra("habitationCode", habitationCode);
                            i.putExtra("eng_certi", eng_certi);
                            i.putExtra("option_Flag", option_Flag);
                            i.putExtra("town_Name", town_Name);
                            i.putExtra("town_code", town_code);
                            i.putExtra("ward_Name", ward_Name);
                            i.putExtra("ward_code", ward_code);
                            startActivity(i);
                            finish();
                            break;
                        }
                        case "10": {
                            Log.d("Service:", "" + service_Code);
                            if(Objects.equals(eng_certi, "E")) {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Resident_Parameters.class);
                            }else {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Resident_Parameters_Kan.class);
                            }
                            i.putExtra("districtCode", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("applicant_Id", applicant_Id);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("hobli", hobli);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("district_Code", district_Code);
                            i.putExtra("taluk_Code", taluk_Code);
                            i.putExtra("hobli_Code", hobli_Code);
                            i.putExtra("rationCardNo", rationCardNo);
                            i.putExtra("aadharNo", aadharNo);
                            i.putExtra("mobileNo", mobileNo);
                            i.putExtra("address1", address1);
                            i.putExtra("item_position", item_position);
                            i.putExtra("strSearchServiceName", strSearchServiceName);
                            i.putExtra("strSearchVillageName", strSearchVillageName);
                            i.putExtra("serviceCode", service_Code);
                            i.putExtra("villageCode", village_Code);
                            i.putExtra("habitationCode", habitationCode);
                            i.putExtra("eng_certi", eng_certi);
                            i.putExtra("option_Flag", option_Flag);
                            i.putExtra("town_Name", town_Name);
                            i.putExtra("town_code", town_code);
                            i.putExtra("ward_Name", ward_Name);
                            i.putExtra("ward_code", ward_code);
                            startActivity(i);
                            finish();
                            break;
                        }
                        default:
                            Toast.makeText(getApplicationContext(), getString(R.string.service_not_availabel), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
                else {
                    openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_SecondScreen.this);
                    database = openHelper.getWritableDatabase();

                    set_and_get_service_parameter = new Set_and_Get_Service_Parameter();
                    set_and_get_service_parameter.setDistrict_Code(district_Code);
                    set_and_get_service_parameter.setTaluk_Code(taluk_Code);
                    set_and_get_service_parameter.setHobli_Code(hobli_Code);
                    set_and_get_service_parameter.setVa_Circle_Code(va_Circle_Code);
                    set_and_get_service_parameter.setVillage_Code(village_Code);
                    set_and_get_service_parameter.setHabitation_code(habitationCode);
                    set_and_get_service_parameter.setTown_Code(town_code);
                    set_and_get_service_parameter.setWard_Code(ward_code);
                    set_and_get_service_parameter.setService_Code(String.valueOf(service_Code));
                    set_and_get_service_parameter.setRD_No(applicant_Id);
                    set_and_get_service_parameter.setApplicant_Name(name);
                    set_and_get_service_parameter.setFather_Name(fatherName);
                    set_and_get_service_parameter.setMother_Name(motherName);
                    set_and_get_service_parameter.setMobile_No(mobileNo);
                    set_and_get_service_parameter.setRationCard_No(rationCardNo);
                    set_and_get_service_parameter.setAddress1(address1);
                    set_and_get_service_parameter.setAddress2(address2);
                    set_and_get_service_parameter.setAddress3(address3);
                    set_and_get_service_parameter.setEng_Certify(eng_certi);
                    set_and_get_service_parameter.setGSC_First_Part(GSC_FirstPart);
                    set_and_get_service_parameter.setST_applicant_photo(appImage);
                    set_and_get_service_parameter.setPinCode(pinCode);
                    set_and_get_service_parameter.setReport_No(report_No);
                    set_and_get_service_parameter.setUID(send_UID);
                    set_and_get_service_parameter.setAadhaarPhoto(send_AadharPhoto);

                    database.execSQL("insert into " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1
                            + "(ST_district_code, ST_taluk_code, ST_hobli_code, ST_va_Circle_Code, ST_village_code, ST_habitation_code, ST_town_code, ST_ward_no, ST_facility_code, ST_GSC_No, ST_applicant_name, ST_father_name, ST_mother_name, ST_Upd_mobile_no, " +
                            "ST_Upd_ID_NUMBER, ST_applicant_caddress1, ST_applicant_caddress2,ST_applicant_caddress3, ST_Eng_Certificate, ST_GSCFirstPart, ST_applicant_photo, UID, AadhaarPhoto, ST_PinCode, Report_No, VA_Accepts_Applicant_information)" +
                            " values ("
                            + set_and_get_service_parameter.getDistrict_Code() + ","
                            + set_and_get_service_parameter.getTaluk_Code() + ","
                            + set_and_get_service_parameter.getHobli_Code() + ","
                            + set_and_get_service_parameter.getVa_Circle_Code() + ","
                            + set_and_get_service_parameter.getVillage_Code() + ","
                            + set_and_get_service_parameter.getHabitation_code() + ","
                            + set_and_get_service_parameter.getTown_Code() + ","
                            + set_and_get_service_parameter.getWard_Code() + ","
                            + set_and_get_service_parameter.getService_Code() + ","
                            + set_and_get_service_parameter.getRD_No() + ",'"
                            + set_and_get_service_parameter.getApplicant_Name() + "','"
                            + set_and_get_service_parameter.getFather_Name() + "','"
                            + set_and_get_service_parameter.getMother_Name() + "','"
                            + set_and_get_service_parameter.getMobile_No() + "','"
                            + set_and_get_service_parameter.getRationCard_No() + "','"
                            + set_and_get_service_parameter.getAddress1() + "','"
                            + set_and_get_service_parameter.getAddress2() + "','"
                            + set_and_get_service_parameter.getAddress3() + "','"
                            + set_and_get_service_parameter.getEng_Certify() + "',"
                            + set_and_get_service_parameter.getGSC_First_Part() + ",'"
                            + set_and_get_service_parameter.getST_applicant_photo() + "','"
                            + set_and_get_service_parameter.getUID() + "','"
                            + set_and_get_service_parameter.getAadhaarPhoto() + "',"
                            + set_and_get_service_parameter.getPinCode() + ",'"
                            + set_and_get_service_parameter.getReport_No() +"','NO')");

                    Log.d("Database", "ServiceParameterTable Database Inserted");

                    switch (service_Code) {
                        case "6":
                        case "9": {
                            Log.d("Service:", "6");
                            if(Objects.equals(eng_certi, "E")) {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_Income_Parameters.class);
                            }else {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_Income_Parameters_Kan.class);
                            }
                            i.putExtra("districtCode", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("applicant_Id", applicant_Id);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("hobli", hobli);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("district_Code", district_Code);
                            i.putExtra("taluk_Code", taluk_Code);
                            i.putExtra("hobli_Code", hobli_Code);
                            i.putExtra("rationCardNo", rationCardNo);
                            i.putExtra("aadharNo", aadharNo);
                            i.putExtra("mobileNo", mobileNo);
                            i.putExtra("address1", address1);
                            i.putExtra("item_position", item_position);
                            i.putExtra("strSearchServiceName", strSearchServiceName);
                            i.putExtra("strSearchVillageName", strSearchVillageName);
                            i.putExtra("serviceCode", service_Code);
                            i.putExtra("villageCode", village_Code);
                            i.putExtra("habitationCode", habitationCode);
                            i.putExtra("eng_certi", eng_certi);
                            i.putExtra("option_Flag", option_Flag);
                            i.putExtra("town_Name", town_Name);
                            i.putExtra("town_code", town_code);
                            i.putExtra("ward_Name", ward_Name);
                            i.putExtra("ward_code", ward_code);
                            startActivity(i);
                            finish();
                            break;
                        }
                        case "7":
                        case "8": {
                            Log.d("Service:", "8");
                            if(Objects.equals(eng_certi, "E")) {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_sc_st_certi_Parameters.class);
                            }else {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_sc_st_certi_Parameters_Kan.class);
                            }
                            i.putExtra("districtCode", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("applicant_Id", applicant_Id);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("hobli", hobli);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("district_Code", district_Code);
                            i.putExtra("taluk_Code", taluk_Code);
                            i.putExtra("hobli_Code", hobli_Code);
                            i.putExtra("rationCardNo", rationCardNo);
                            i.putExtra("aadharNo", aadharNo);
                            i.putExtra("mobileNo", mobileNo);
                            i.putExtra("address1", address1);
                            i.putExtra("item_position", item_position);
                            i.putExtra("strSearchServiceName", strSearchServiceName);
                            i.putExtra("strSearchVillageName", strSearchVillageName);
                            i.putExtra("serviceCode", service_Code);
                            i.putExtra("villageCode", village_Code);
                            i.putExtra("habitationCode", habitationCode);
                            i.putExtra("eng_certi", eng_certi);
                            i.putExtra("option_Flag", option_Flag);
                            i.putExtra("town_Name", town_Name);
                            i.putExtra("town_code", town_code);
                            i.putExtra("ward_Name", ward_Name);
                            i.putExtra("ward_code", ward_code);
                            startActivity(i);
                            finish();
                            break;
                        }
                        case "10": {
                            Log.d("Service:", "10");
                            if(Objects.equals(eng_certi, "E")) {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Resident_Parameters.class);
                            }else {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Resident_Parameters_Kan.class);
                            }
                            i.putExtra("districtCode", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("applicant_Id", applicant_Id);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("hobli", hobli);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("district_Code", district_Code);
                            i.putExtra("taluk_Code", taluk_Code);
                            i.putExtra("hobli_Code", hobli_Code);
                            i.putExtra("rationCardNo", rationCardNo);
                            i.putExtra("aadharNo", aadharNo);
                            i.putExtra("mobileNo", mobileNo);
                            i.putExtra("address1", address1);
                            i.putExtra("item_position", item_position);
                            i.putExtra("strSearchServiceName", strSearchServiceName);
                            i.putExtra("strSearchVillageName", strSearchVillageName);
                            i.putExtra("serviceCode", service_Code);
                            i.putExtra("villageCode", village_Code);
                            i.putExtra("habitationCode", habitationCode);
                            i.putExtra("eng_certi", eng_certi);
                            i.putExtra("option_Flag", option_Flag);
                            i.putExtra("town_Name", town_Name);
                            i.putExtra("town_code", town_code);
                            i.putExtra("ward_Name", ward_Name);
                            i.putExtra("ward_code", ward_code);
                            startActivity(i);
                            finish();
                            break;
                        }
                        default:
                            Toast.makeText(getApplicationContext(), getString(R.string.service_not_availabel), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
            else {
                runOnUiThread(() -> {
                    btnNext.setText(getString(R.string.certified));
                    AlertDialog.Builder dialog = new AlertDialog.Builder(New_Request_SecondScreen.this);
                    dialog.setCancelable(false);
                    dialog.setTitle(getString(R.string.alert));
                    dialog.setMessage(getString(R.string.cannot_get_location) );
                    dialog.setNegativeButton(getString(R.string.ok), (dialog12, which) -> {
                        //Action for "Cancel".
                        dialog12.cancel();
                    });

                    final AlertDialog alert = dialog.create();
                    alert.show();
                });
            }

            str="Success";
        } else {

            if(latitude!=0.0 && longitude!=0.0) {
                openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_SecondScreen.this);
                database = openHelper.getWritableDatabase();

                @SuppressLint("Recycle")
                Cursor cursor = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1 + " where "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No + "=" + applicant_Id, null);
                if (cursor.getCount() > 0) {
                    database.execSQL("update " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1 + " set "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.VA_Accepts_Applicant_information+"='"+"NO"+"',"
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name + "='" + name + "', "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Father_Name + "='" + fatherName + "', "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Mother + "='" + motherName + "', "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.U_RationCard_No + "='" + rationCardNo + "', "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.U_Mobile_No + "=" + mobileNo + ","
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Address1 + "='" + address1 +"',"
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Address2 + "='" + address2 +"',"
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Address3 + "='" + address3 +"',"
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.PinCode + "=" + pinCode+","
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Report_No + "='"+report_No+"',"
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.UID + "='" + send_UID + "',"
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.AadhaarPhoto + "='" + send_AadharPhoto + "'" + " where "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.District_Code + "=" + district_Code + " and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Taluk_Code + "=" + taluk_Code + " and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Hobli_Code + "=" + hobli_Code + " and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No + "=" + applicant_Id + " and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code + "=" + village_Code + " and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code+"="+town_code+" and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code+"="+ward_code+" and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code + "=" + service_Code);

                    Log.d("Database", "ServiceParameterTable Database Updated");

                    switch (service_Code) {
                        case "6":
                        case "9": {
                            Log.d("Service:", "" + service_Code);
                            if(Objects.equals(eng_certi, "E")) {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_Income_Parameters.class);
                            }else {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_Income_Parameters_Kan.class);
                            }
                            i.putExtra("districtCode", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("applicant_Id", applicant_Id);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("hobli", hobli);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("district_Code", district_Code);
                            i.putExtra("taluk_Code", taluk_Code);
                            i.putExtra("hobli_Code", hobli_Code);
                            i.putExtra("rationCardNo", rationCardNo);
                            i.putExtra("aadharNo", aadharNo);
                            i.putExtra("mobileNo", mobileNo);
                            i.putExtra("address1", address1);
                            i.putExtra("item_position", item_position);
                            i.putExtra("strSearchServiceName", strSearchServiceName);
                            i.putExtra("strSearchVillageName", strSearchVillageName);
                            i.putExtra("serviceCode", service_Code);
                            i.putExtra("villageCode", village_Code);
                            i.putExtra("habitationCode", habitationCode);
                            i.putExtra("eng_certi", eng_certi);
                            i.putExtra("option_Flag", option_Flag);
                            i.putExtra("town_Name", town_Name);
                            i.putExtra("town_code", town_code);
                            i.putExtra("ward_Name", ward_Name);
                            i.putExtra("ward_code", ward_code);
                            startActivity(i);
                            finish();
                            break;
                        }
                        case "7":
                        case "8": {
                            Log.d("Service:", "" + service_Code);
                            if(Objects.equals(eng_certi, "E")) {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_sc_st_certi_Parameters.class);
                            }else {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_sc_st_certi_Parameters_Kan.class);
                            }
                            i.putExtra("districtCode", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("applicant_Id", applicant_Id);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("hobli", hobli);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("district_Code", district_Code);
                            i.putExtra("taluk_Code", taluk_Code);
                            i.putExtra("hobli_Code", hobli_Code);
                            i.putExtra("rationCardNo", rationCardNo);
                            i.putExtra("aadharNo", aadharNo);
                            i.putExtra("mobileNo", mobileNo);
                            i.putExtra("address1", address1);
                            i.putExtra("item_position", item_position);
                            i.putExtra("strSearchServiceName", strSearchServiceName);
                            i.putExtra("strSearchVillageName", strSearchVillageName);
                            i.putExtra("serviceCode", service_Code);
                            i.putExtra("villageCode", village_Code);
                            i.putExtra("habitationCode", habitationCode);
                            i.putExtra("eng_certi", eng_certi);
                            i.putExtra("option_Flag", option_Flag);
                            i.putExtra("town_Name", town_Name);
                            i.putExtra("town_code", town_code);
                            i.putExtra("ward_Name", ward_Name);
                            i.putExtra("ward_code", ward_code);
                            startActivity(i);
                            finish();
                            break;
                        }
                        case "10": {
                            Log.d("Service:", "" + service_Code);
                            if(Objects.equals(eng_certi, "E")) {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Resident_Parameters.class);
                            }else {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Resident_Parameters_Kan.class);
                            }
                            i.putExtra("districtCode", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("applicant_Id", applicant_Id);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("hobli", hobli);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("district_Code", district_Code);
                            i.putExtra("taluk_Code", taluk_Code);
                            i.putExtra("hobli_Code", hobli_Code);
                            i.putExtra("rationCardNo", rationCardNo);
                            i.putExtra("aadharNo", aadharNo);
                            i.putExtra("mobileNo", mobileNo);
                            i.putExtra("address1", address1);
                            i.putExtra("item_position", item_position);
                            i.putExtra("strSearchServiceName", strSearchServiceName);
                            i.putExtra("strSearchVillageName", strSearchVillageName);
                            i.putExtra("serviceCode", service_Code);
                            i.putExtra("villageCode", village_Code);
                            i.putExtra("habitationCode", habitationCode);
                            i.putExtra("eng_certi", eng_certi);
                            i.putExtra("option_Flag", option_Flag);
                            i.putExtra("town_Name", town_Name);
                            i.putExtra("town_code", town_code);
                            i.putExtra("ward_Name", ward_Name);
                            i.putExtra("ward_code", ward_code);
                            startActivity(i);
                            finish();
                            break;
                        }
                        default:
                            Toast.makeText(getApplicationContext(), getString(R.string.service_not_availabel), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
                else {
                    openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_SecondScreen.this);
                    database = openHelper.getWritableDatabase();

                    set_and_get_service_parameter = new Set_and_Get_Service_Parameter();
                    set_and_get_service_parameter.setDistrict_Code(district_Code);
                    set_and_get_service_parameter.setTaluk_Code(taluk_Code);
                    set_and_get_service_parameter.setHobli_Code(hobli_Code);
                    set_and_get_service_parameter.setVa_Circle_Code(va_Circle_Code);
                    set_and_get_service_parameter.setVillage_Code(String.valueOf(village_Code));
                    set_and_get_service_parameter.setHabitation_code(habitationCode);
                    set_and_get_service_parameter.setTown_Code(town_code);
                    set_and_get_service_parameter.setWard_Code(ward_code);
                    set_and_get_service_parameter.setService_Code(String.valueOf(service_Code));
                    set_and_get_service_parameter.setRD_No(applicant_Id);
                    set_and_get_service_parameter.setApplicant_Name(name);
                    set_and_get_service_parameter.setFather_Name(fatherName);
                    set_and_get_service_parameter.setMother_Name(motherName);
                    set_and_get_service_parameter.setMobile_No(mobileNo);
                    set_and_get_service_parameter.setRationCard_No(rationCardNo);
                    set_and_get_service_parameter.setAddress1(address1);
                    set_and_get_service_parameter.setAddress2(address2);
                    set_and_get_service_parameter.setAddress3(address3);
                    set_and_get_service_parameter.setEng_Certify(eng_certi);
                    set_and_get_service_parameter.setGSC_First_Part(GSC_FirstPart);
                    set_and_get_service_parameter.setST_applicant_photo(appImage);
                    set_and_get_service_parameter.setPinCode(pinCode);
                    set_and_get_service_parameter.setReport_No(report_No);
                    set_and_get_service_parameter.setUID(send_UID);
                    set_and_get_service_parameter.setAadhaarPhoto(send_AadharPhoto);

                    database.execSQL("insert into " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1
                            + "(ST_district_code, ST_taluk_code, ST_hobli_code, ST_va_Circle_Code, ST_village_code,ST_habitation_code, ST_town_code, ST_ward_no, ST_facility_code, ST_GSC_No, ST_applicant_name, ST_father_name, ST_mother_name, ST_Upd_mobile_no, ST_Upd_ID_NUMBER, " +
                            " ST_applicant_caddress1, ST_applicant_caddress2,ST_applicant_caddress3, ST_Eng_Certificate, ST_GSCFirstPart, ST_applicant_photo, UID, AadhaarPhoto, ST_PinCode, Report_No, VA_Accepts_Applicant_information)" +
                            " values ("
                            + set_and_get_service_parameter.getDistrict_Code() + ","
                            + set_and_get_service_parameter.getTaluk_Code() + ","
                            + set_and_get_service_parameter.getHobli_Code() + ","
                            + set_and_get_service_parameter.getVa_Circle_Code() + ","
                            + set_and_get_service_parameter.getVillage_Code() + ","
                            + set_and_get_service_parameter.getHabitation_code() + ","
                            + set_and_get_service_parameter.getTown_Code() + ","
                            + set_and_get_service_parameter.getWard_Code() + ","
                            + set_and_get_service_parameter.getService_Code() + ","
                            + set_and_get_service_parameter.getRD_No() + ",'"
                            + set_and_get_service_parameter.getApplicant_Name() + "','"
                            + set_and_get_service_parameter.getFather_Name() + "','"
                            + set_and_get_service_parameter.getMother_Name() + "','"
                            + set_and_get_service_parameter.getMobile_No() + "','"
                            + set_and_get_service_parameter.getRationCard_No() + "','"
                            + set_and_get_service_parameter.getAddress1() + "','"
                            + set_and_get_service_parameter.getAddress2() + "','"
                            + set_and_get_service_parameter.getAddress3() + "','"
                            + set_and_get_service_parameter.getEng_Certify() + "',"
                            + set_and_get_service_parameter.getGSC_First_Part() + ",'"
                            + set_and_get_service_parameter.getST_applicant_photo() + "','"
                            + set_and_get_service_parameter.getUID() + "','"
                            + set_and_get_service_parameter.getAadhaarPhoto() + "',"
                            + set_and_get_service_parameter.getPinCode() + ",'"
                            + set_and_get_service_parameter.getReport_No() +"','NO')");

                    Log.d("Database", "ServiceParameterTable Database Inserted");

                    switch (service_Code) {
                        case "6":
                        case "9": {
                            Log.d("Service:", "" + service_Code);
                            if(Objects.equals(eng_certi, "E")) {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_Income_Parameters.class);
                            }else {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_Income_Parameters_Kan.class);
                            }
                            i.putExtra("districtCode", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("applicant_Id", applicant_Id);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("hobli", hobli);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("district_Code", district_Code);
                            i.putExtra("taluk_Code", taluk_Code);
                            i.putExtra("hobli_Code", hobli_Code);
                            i.putExtra("rationCardNo", rationCardNo);
                            i.putExtra("aadharNo", aadharNo);
                            i.putExtra("mobileNo", mobileNo);
                            i.putExtra("address1", address1);
                            i.putExtra("item_position", item_position);
                            i.putExtra("strSearchServiceName", strSearchServiceName);
                            i.putExtra("strSearchVillageName", strSearchVillageName);
                            i.putExtra("serviceCode", service_Code);
                            i.putExtra("villageCode", village_Code);
                            i.putExtra("habitationCode", habitationCode);
                            i.putExtra("eng_certi", eng_certi);
                            i.putExtra("option_Flag", option_Flag);
                            i.putExtra("town_Name", town_Name);
                            i.putExtra("town_code", town_code);
                            i.putExtra("ward_Name", ward_Name);
                            i.putExtra("ward_code", ward_code);
                            startActivity(i);
                            finish();
                            break;
                        }
                        case "7":
                        case "8": {
                            Log.d("Service:", "" + service_Code);
                            if(Objects.equals(eng_certi, "E")) {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_sc_st_certi_Parameters.class);
                            }else {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Caste_sc_st_certi_Parameters_Kan.class);
                            }
                            i.putExtra("districtCode", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("applicant_Id", applicant_Id);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("hobli", hobli);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("district_Code", district_Code);
                            i.putExtra("taluk_Code", taluk_Code);
                            i.putExtra("hobli_Code", hobli_Code);
                            i.putExtra("rationCardNo", rationCardNo);
                            i.putExtra("aadharNo", aadharNo);
                            i.putExtra("mobileNo", mobileNo);
                            i.putExtra("address1", address1);
                            i.putExtra("item_position", item_position);
                            i.putExtra("strSearchServiceName", strSearchServiceName);
                            i.putExtra("strSearchVillageName", strSearchVillageName);
                            i.putExtra("serviceCode", service_Code);
                            i.putExtra("villageCode", village_Code);
                            i.putExtra("habitationCode", habitationCode);
                            i.putExtra("eng_certi", eng_certi);
                            i.putExtra("option_Flag", option_Flag);
                            i.putExtra("town_Name", town_Name);
                            i.putExtra("town_code", town_code);
                            i.putExtra("ward_Name", ward_Name);
                            i.putExtra("ward_code", ward_code);
                            startActivity(i);
                            finish();
                            break;
                        }
                        case "10": {
                            Log.d("Service:", "" + service_Code);
                            if(Objects.equals(eng_certi, "E")) {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Resident_Parameters.class);
                            }else {
                                i = new Intent(New_Request_SecondScreen.this, New_Request_Resident_Parameters_Kan.class);
                            }
                            i.putExtra("districtCode", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("applicant_Id", applicant_Id);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("hobli", hobli);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("district_Code", district_Code);
                            i.putExtra("taluk_Code", taluk_Code);
                            i.putExtra("hobli_Code", hobli_Code);
                            i.putExtra("rationCardNo", rationCardNo);
                            i.putExtra("aadharNo", aadharNo);
                            i.putExtra("mobileNo", mobileNo);
                            i.putExtra("address1", address1);
                            i.putExtra("item_position", item_position);
                            i.putExtra("strSearchServiceName", strSearchServiceName);
                            i.putExtra("strSearchVillageName", strSearchVillageName);
                            i.putExtra("serviceCode", service_Code);
                            i.putExtra("villageCode", village_Code);
                            i.putExtra("habitationCode", habitationCode);
                            i.putExtra("eng_certi", eng_certi);
                            i.putExtra("option_Flag", option_Flag);
                            i.putExtra("town_Name", town_Name);
                            i.putExtra("town_code", town_code);
                            i.putExtra("ward_Name", ward_Name);
                            i.putExtra("ward_code", ward_code);
                            startActivity(i);
                            finish();
                            break;
                        }
                        default:
                            Toast.makeText(getApplicationContext(), getString(R.string.service_not_availabel), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
            else {
                runOnUiThread(() -> {
                    btnNext.setText(getString(R.string.certified));
                    AlertDialog.Builder dialog = new AlertDialog.Builder(New_Request_SecondScreen.this);
                    dialog.setCancelable(false);
                    dialog.setTitle(getString(R.string.alert));
                    dialog.setMessage(getString(R.string.cannot_get_location) );
                    dialog.setNegativeButton(getString(R.string.ok), (dialog13, which) -> {
                        //Action for "Cancel".
                        dialog13.cancel();
                    });

                    final AlertDialog alert = dialog.create();
                    alert.show();
                });
            }

            str="Success";
        }
        Log.d("StoreData_in_DB", str);
    }

    @SuppressLint({"LongLogTag", "SetTextI18n"})
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == CAMERA_REQUEST_1) {
            Bundle extras = data.getExtras();

            if (extras != null) {
                Bitmap yourImage = extras.getParcelable("data");
                // convert bitmap to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();//The ByteArrayOutputStream class stream creates a buffer in memory and all the data sent to the stream is stored in the buffer.
                assert yourImage != null;
                yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);//Specifies the known formats a bitmap can be compressed into 1.JPEG 2.PNG 3.WEBP
                imageInByte = stream.toByteArray();
                getString = Base64.encodeToString(imageInByte, Base64.DEFAULT);
                Log.e("output before conversion", Arrays.toString(imageInByte));
                store_1 = getString;
                scannerImage.setVisibility(View.GONE);
                lQrCodeScan.setVisibility(View.GONE);
                tvOr.setVisibility(View.GONE);
                store_Aadhaar_image.setVisibility(View.VISIBLE);
                store_Aadhaar_image.setImageBitmap(yourImage);
                Log.d("Image in bytes", "Image Captured");

            }
        }

        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, getString(R.string.result_not_found), Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
//                    Pattern pattern= Pattern.compile("");
//                    Matcher matcher = pattern.matcher("uid");
                    //converting the data to json
                    JSONObject obj = XML.toJSONObject(result.getContents());
                    Log.d("Scanned_Data", obj.toString());
                    String str= obj.getString("PrintLetterBarcodeData");
                    JSONObject jsonObject = new JSONObject(str);
                    UID = jsonObject.getString("Uid");
                    name_UID = jsonObject.getString("Name");
//                    address_UID = jsonObject.getString("co");
//                    address_UID1 = jsonObject.getString("house");
//                    address_UID2 = jsonObject.getString("street");
//                    address_UID3 = jsonObject.getString("loc");
                    address_UID4 = jsonObject.getString("Vtc");
                    address_UID3 = jsonObject.getString("state");

                    btnScan.setVisibility(View.GONE);
                    scannerImage.setVisibility(View.GONE);
                    captureImage.setVisibility(View.GONE);
                    //tvQrCodeScanner.setVisibility(View.GONE);
                    tvOr.setVisibility(View.GONE);
                    lAadhaarDetails.setVisibility(View.VISIBLE);
                    btnCaptureAadhaar.setVisibility(View.GONE);

                    String mask = UID.replaceAll("\\w(?=\\w{4})", "*");
                    //setting values to textviews
                    tvUID.setText(mask);
                    tvNameUID.setText(name_UID);
                    //tvAddressUID.setText(address_UID+","+ address_UID1+", "+ address_UID2+", "+ address_UID3+", "+ address_UID4);
                    tvAddressUID.setText(address_UID4+", "+address_UID3);
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    try {
                        //converting the data to json
                        JSONObject obj = XML.toJSONObject(result.getContents());
                        Log.d("Scanned_Data", obj.toString());
                        String str= obj.getString("PrintLetterBarcodeData");
                        JSONObject jsonObject = new JSONObject(str);
                        UID = jsonObject.getString("uid");
                        name_UID = jsonObject.getString("name");
//                    address_UID = jsonObject.getString("co");
//                    address_UID1 = jsonObject.getString("house");
//                    address_UID2 = jsonObject.getString("street");
//                    address_UID3 = jsonObject.getString("loc");
                        address_UID4 = jsonObject.getString("vtc");
                        address_UID3 = jsonObject.getString("state");

                        btnScan.setVisibility(View.GONE);
                        scannerImage.setVisibility(View.GONE);
                        captureImage.setVisibility(View.GONE);
                        tvOr.setVisibility(View.GONE);
                        //tvQrCodeScanner.setVisibility(View.GONE);
                        lAadhaarDetails.setVisibility(View.VISIBLE);
                        btnCaptureAadhaar.setVisibility(View.GONE);

                        String mask = UID.replaceAll("\\w(?=\\w{4})", "*");
                        //setting values to textviews
                        tvUID.setText(mask);
                        tvNameUID.setText(name_UID);
                        //tvAddressUID.setText(address_UID+","+ address_UID1+", "+ address_UID2+", "+ address_UID3+", "+ address_UID4);
                        tvAddressUID.setText(address_UID4+", "+address_UID3);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                        //if control comes here
                        //that means the encoded format not matches
                        //in this case you can display whatever data is available on the qrcode
                        //to a toast
                        Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public static String encrypt(String value) throws Exception
    {
        String encryptedValue64;
        Key key = generateKey();
        @SuppressLint("GetInstance")
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] encryptedByteValue = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
        encryptedValue64 = Base64.encodeToString(encryptedByteValue, Base64.DEFAULT);
        return encryptedValue64;

    }

    public static String decrypt(String value) throws Exception
    {
        Key key = generateKey();
        @SuppressLint("GetInstance")
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedValue64 = Base64.decode(value, Base64.DEFAULT);
        byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);
        decryptedValue = new String(decryptedByteValue, StandardCharsets.UTF_8);
        return decryptedValue;

    }

    private static Key generateKey() {
        key = new SecretKeySpec(KEY.getBytes(),ALGORITHM);
        return key;
    }

    @Override
    public void onBackPressed() {
        buildAlertMessageGoingBack();
    }
}
