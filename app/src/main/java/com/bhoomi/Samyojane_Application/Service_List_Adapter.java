package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Service_List_Adapter extends BaseAdapter implements Filterable {

    private Context context;
    private ArrayList<String> SlNo;
    private ArrayList<String> Applicant_Name;
    private ArrayList<String> GSC_First_Part;
    private ArrayList<String> Applicant_ID;
    private ArrayList<String> DueDate;
    private ArrayList<String> ServiceCode;
    private ArrayList<String> ServiceName;
    private ArrayList<String> VillageCode;
    private ArrayList<String> HabitationCode;
    private ArrayList<String> Option_Flag;
    private ArrayList<String> selected_items = new ArrayList<>();
    private String applicant_name;
    private String applicant_Id;
    private String serviceCode, serviceName, villageCode, habitationCode, town_code, ward_code;
    private String item_position;
    private String district, taluk, VA_Name, hobli,VA_Circle_Name, village_name;
    private String district_Code, taluk_Code, hobli_Code, va_Circle_code, eng_certi,option_Flag;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private Intent i;
    private DataTransferInterface dataTransferInterface;
    public Activity activity;
    LayoutInflater inflater;

    Service_List_Adapter(Context context, ArrayList<String> slNo, ArrayList<String> applicant_Name,
                         ArrayList<String> gsc_firstPart, ArrayList<String> rd_No, ArrayList<String> dueDate,
                         ArrayList<String> serviceCode, ArrayList<String> serviceName, ArrayList<String> villageCode,
                         ArrayList<String> habitationCode, ArrayList<String> option_Flag, Activity a, DataTransferInterface dtInterface) {

        this.context = context;
        this.SlNo = slNo;
        this.Applicant_Name = applicant_Name;
        this.GSC_First_Part = gsc_firstPart;
        this.Applicant_ID = rd_No;
        this.DueDate = dueDate;
        this.ServiceCode = serviceCode;
        this.ServiceName = serviceName;
        this.VillageCode = villageCode;
        this.HabitationCode = habitationCode;
        this.Option_Flag = option_Flag;
        this.activity = a;
        this.dataTransferInterface = dtInterface;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Applicant_ID.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Service_ViewHolder service_ViewHolder;

        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list,parent, false);
            service_ViewHolder=new Service_ViewHolder(convertView);
            convertView.setTag(service_ViewHolder);
        }
        else {
            service_ViewHolder = (Service_ViewHolder) convertView.getTag();
        }

        Date c = Calendar.getInstance().getTime();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        String formattedDate = df.format(c);

        String due_Date_C = DueDate.get(position);

        try {

            Log.d("formattedDate_Ser", ""+formattedDate);
            Log.d("due_Date_C", ""+due_Date_C);

            Date date1 = df.parse(formattedDate);
            Date date2 = df.parse(due_Date_C);

            if (date1.after(date2) || date1.equals(date2)) {
                //#FFEE0808
                service_ViewHolder.gsc_first_part.setTextColor(Color.parseColor("#FFEE0808"));
                service_ViewHolder.app_Id.setTextColor(Color.parseColor("#FFEE0808"));
                service_ViewHolder.app_dueDate.setTextColor(Color.parseColor("#FFEE0808"));
                Log.d("Date", "Date1 is after Date2");
            }else{
                service_ViewHolder.gsc_first_part.setTextColor(Color.parseColor("#ff000000"));
                service_ViewHolder.app_Id.setTextColor(Color.parseColor("#ff000000"));
                service_ViewHolder.app_dueDate.setTextColor(Color.parseColor("#ff000000"));
                Log.d("Date", "Date1 is before Date2");
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("ParseException", ""+e.getMessage());
        }

        service_ViewHolder.sl_No.setText(SlNo.get(position));
        service_ViewHolder.app_Name.setText(Applicant_Name.get(position));
        service_ViewHolder.gsc_first_part.setText(GSC_First_Part.get(position));
        service_ViewHolder.app_Id.setText(Applicant_ID.get(position));
        service_ViewHolder.app_dueDate.setText(DueDate.get(position));
        service_ViewHolder.app_ServiceCode.setText(ServiceCode.get(position));
        service_ViewHolder.app_ServiceName.setText(ServiceName.get(position));
        service_ViewHolder.tvVillageCode.setText(VillageCode.get(position));
        service_ViewHolder.tvHabitationCode.setText(HabitationCode.get(position));
        service_ViewHolder.tvOption_Flag.setText(Option_Flag.get(position));

        district_Code = Field_Report.Global.district_Code1;
        district = Field_Report.Global.district_Name1;
        taluk_Code = Field_Report.Global.taluk_Code1;
        taluk = Field_Report.Global.taluk_Name1;
        hobli_Code = Field_Report.Global.hobli_Code1;
        hobli = Field_Report.Global.hobli_Name1;
        va_Circle_code = Field_Report.Global.VA_Circle_Code1;
        VA_Circle_Name = Field_Report.Global.VA_Circle_Name1;
        VA_Name = Field_Report.Global.VA_Name1;
        village_name = Field_Report.Global.VillageName1;

        service_ViewHolder.app_Name.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        service_ViewHolder.app_Name.setOnClickListener(v -> {
            applicant_name = service_ViewHolder.app_Name.getText().toString();
            applicant_Id = service_ViewHolder.app_Id.getText().toString();
            item_position = String.valueOf(position);
            serviceCode = service_ViewHolder.app_ServiceCode.getText().toString();
            serviceName = service_ViewHolder.app_ServiceName.getText().toString();
            villageCode = service_ViewHolder.tvVillageCode.getText().toString();
            habitationCode = service_ViewHolder.tvHabitationCode.getText().toString();
            town_code = "9999";
            ward_code = "255";
            option_Flag = service_ViewHolder.tvOption_Flag.getText().toString();

            Log.d("Applicant_Name", ""+applicant_name);
            Log.d("Applicant_Id", ""+applicant_Id);
            Log.d("Item_Position", ""+item_position);
            Log.d("serviceCode", ""+serviceCode);
            Log.d("serviceName", ""+serviceName);
            Log.d("villageCode", ""+villageCode);
            Log.d("habitationCode", ""+habitationCode);
            Log.d("strSearchVillageName", village_name);
            Log.d("va_Circle_Code", va_Circle_code);
            Log.d("town_code", ""+town_code);
            Log.d("ward_code", ward_code);
            Log.d("option_Flag",option_Flag);

            if(applicant_Id!=null) {

                openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(context);
                database = openHelper.getWritableDatabase();

                @SuppressLint("Recycle")
                Cursor cursor = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME
                        + " where " + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No + "=" + applicant_Id, null);

                if (cursor.getCount() > 0) {
                    if (cursor.moveToNext()) {
                        eng_certi = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Eng_Certificate));
                        Log.d("Service_List", "" + eng_certi);
                    }
                }
                if (Objects.equals(eng_certi, "E")) {
                    i = new Intent(context, New_Request.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } else {
                    i = new Intent(context, New_Request_Kan.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                i.putExtra("applicant_name", applicant_name);
                i.putExtra("applicant_Id", applicant_Id);
                i.putExtra("serviceCode", serviceCode);
                i.putExtra("strSearchServiceName", serviceName);
                i.putExtra("villageCode", villageCode);
                i.putExtra("habitationCode", habitationCode);
                i.putExtra("district_Code", district_Code);
                i.putExtra("districtCode", district);
                i.putExtra("taluk_Code", taluk_Code);
                i.putExtra("taluk", taluk);
                i.putExtra("hobli_Code", hobli_Code);
                i.putExtra("hobli", hobli);
                i.putExtra("va_Circle_Code", va_Circle_code);
                i.putExtra("VA_Circle_Name", VA_Circle_Name);
                i.putExtra("VA_Name", VA_Name);
                i.putExtra("eng_certi",eng_certi);
                i.putExtra("strSearchVillageName", village_name);
                i.putExtra("town_code", town_code);
                i.putExtra("ward_code", ward_code);
                i.putExtra("option_Flag",option_Flag);
                context.startActivity(i);
                ((Activity) context).finish();
                Log.d("Service", "" + serviceCode);
            }
            else{
                Toast.makeText(context, "Applicant Id Missing", Toast.LENGTH_SHORT).show();
            }
        });

        service_ViewHolder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (service_ViewHolder.checkbox.isChecked()){
                applicant_Id = service_ViewHolder.app_Id.getText().toString();
                Log.d("Checked_applicant_Id", "" + applicant_Id);
                selected_items.add(applicant_Id);
                Log.d("selected_items", "" + selected_items);
            } else {
                applicant_Id = service_ViewHolder.app_Id.getText().toString();
                Log.d("UnChecked_applicant_Id", "" + applicant_Id);
                selected_items.remove(applicant_Id);
                Log.d("selected_items", "" + selected_items);
            }
            dataTransferInterface.setValues(selected_items);
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
class Service_ViewHolder{
    TextView sl_No, app_Name, gsc_first_part, app_Id,app_dueDate, app_ServiceCode, app_ServiceName, tvVillageCode,
            tvHabitationCode, tvOption_Flag;
    CheckBox checkbox;
    Service_ViewHolder(View view) {
        sl_No = view.findViewById(R.id.sl_No);
        app_Name = view.findViewById(R.id.app_Name);
        gsc_first_part = view.findViewById(R.id.gsc_first_part);
        app_Id = view.findViewById(R.id.app_Id);
        app_dueDate = view.findViewById(R.id.app_dueDate);
        app_ServiceCode = view.findViewById(R.id.app_ServiceCode);
        app_ServiceName = view.findViewById(R.id.app_ServiceName);
        tvVillageCode = view.findViewById(R.id.tvVillageCode);
        tvHabitationCode = view.findViewById(R.id.tvHabitationCode);
        tvOption_Flag = view.findViewById(R.id.tvOption_Flag);
        checkbox = view.findViewById(R.id.checkbox);
    }
}
