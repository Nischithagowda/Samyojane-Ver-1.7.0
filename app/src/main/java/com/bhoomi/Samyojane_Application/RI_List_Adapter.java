package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class RI_List_Adapter extends BaseAdapter implements Filterable {
    private Context context;
    ArrayList<String> SlNo;
    ArrayList<String> Service_Name;
    ArrayList<String> TotalCount;
    ArrayList<String> VillageName;
    ArrayList<String> VillageCode;
    ArrayList<String> VA_Circle_Name;
    ArrayList<String> va_Circle_code;
    ArrayList<String> Option_Flag;
    String villageCode, town_code, ward_code, option_Flag;
    String district, taluk, VA_Name, RI_Name, hobli;
    String district_Code, taluk_Code, hobli_Code;
    String item_position;
    String serviceName, villageName, va_circle_Name, va_circle_Code;
    private SQLiteOpenHelper openHelper;
    SQLiteDatabase database;

    RI_List_Adapter(Context context, ArrayList<String > slNo, ArrayList<String> service_Name, ArrayList<String> totalCount, ArrayList<String> VA_Circle_Name, ArrayList<String> va_Circle_code, ArrayList<String> villageName, ArrayList<String> villageCode, ArrayList<String> option_Flag) {
        this.context=context;
        this.SlNo = slNo;
        this.Service_Name = service_Name;
        this.TotalCount = totalCount;
        this.VA_Circle_Name = VA_Circle_Name;
        this.va_Circle_code = va_Circle_code;
        this.VillageName = villageName;
        this.VillageCode  = villageCode;
        this.Option_Flag = option_Flag;
    }

    @Override
    public int getCount() {
        return Service_Name.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final RI_ViewHolder ri_viewHolder;

        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.ri_list_field_report,parent, false);
            ri_viewHolder=new RI_ViewHolder(convertView);
            convertView.setTag(ri_viewHolder);
        }
        else {
            ri_viewHolder=(RI_ViewHolder) convertView.getTag();
        }

        district_Code = RI_Field_Report.Global.district_Code1;
        district = RI_Field_Report.Global.district_Name1;
        taluk_Code = RI_Field_Report.Global.taluk_Code1;
        taluk = RI_Field_Report.Global.taluk_Name1;
        hobli_Code = RI_Field_Report.Global.hobli_Code1;
        hobli = RI_Field_Report.Global.hobli_Name1;
        RI_Name = RI_Field_Report.Global.RI_Name1;

        ri_viewHolder.sl_No.setText(SlNo.get(position));
        ri_viewHolder.tvServiceName.setText(Service_Name.get(position));
        ri_viewHolder.tvTotalPendency.setText(TotalCount.get(position));
        ri_viewHolder.tvVillageCircleName.setText(VA_Circle_Name.get(position));
        ri_viewHolder.tvVillageCircleCode.setText(va_Circle_code.get(position));
        ri_viewHolder.tvVillageName.setText(VillageName.get(position));
        ri_viewHolder.tvVillageCode.setText(VillageCode.get(position));
        ri_viewHolder.tvOption_Flag.setText(Option_Flag.get(position));

        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(v -> {
            serviceName = ri_viewHolder.tvServiceName.getText().toString();
            item_position = String.valueOf(position);
            va_circle_Name = ri_viewHolder.tvVillageCircleName.getText().toString();
            va_circle_Code = ri_viewHolder.tvVillageCircleCode.getText().toString();
            villageName = ri_viewHolder.tvVillageName.getText().toString();
            villageCode = ri_viewHolder.tvVillageCode.getText().toString();
            option_Flag = ri_viewHolder.tvOption_Flag.getText().toString();
            town_code = "9999";
            ward_code = "255";

            Log.d("Item_Position", ""+item_position+" Service_Name :"+serviceName+" Village_Name: "+villageName);
            Log.d("district_Code", ""+district_Code+" taluk_Code :"+taluk_Code+" hobli_Code: "+hobli_Code);
            Log.d("va_Circle_code", ""+va_circle_Code+" villageCode :"+villageCode+" RI_Name: "+RI_Name+" VA_Circle_Name: "+va_circle_Name);

            openHelper=new DataBaseHelperClass_Credentials(context);
            database=openHelper.getWritableDatabase();

            Cursor cursor = database.rawQuery("select * from "+DataBaseHelperClass_Credentials.TABLE_NAME+" where "+ DataBaseHelperClass_Credentials.District_Code+"="+district_Code+" and "
                    + DataBaseHelperClass_Credentials.Taluk_Code+"="+taluk_Code+" and "+DataBaseHelperClass_Credentials.Hobli_Code+"="+hobli_Code+" and "
                    + DataBaseHelperClass_Credentials.VA_circle_Code+"="+va_circle_Code, null);

            if (cursor.getCount()>0){
                if (cursor.moveToNext()){
                    VA_Name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.VA_Name));
                    Log.d("VA_Name:", VA_Name);
                }
            } else {
                cursor.close();
            }
            Intent i = new Intent(context, RI_Field_Report_FirstScreen.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("district_Code", district_Code);
            i.putExtra("districtCode", district);
            i.putExtra("taluk_Code", taluk_Code);
            i.putExtra("taluk", taluk);
            i.putExtra("hobli_Code", hobli_Code);
            i.putExtra("hobli", hobli);
            i.putExtra("va_Circle_Code", va_circle_Code);
            i.putExtra("VA_Circle_Name", va_circle_Name);
            i.putExtra("VA_Name", VA_Name);
            i.putExtra("RI_Name", RI_Name);
            i.putExtra("strSearchServiceName", serviceName);
            i.putExtra("villageCode", villageCode);
            i.putExtra("strSearchVillageName", villageName);
            i.putExtra("town_code", town_code);
            i.putExtra("ward_code", ward_code);
            i.putExtra("option_Flag", option_Flag);
            ((Activity) context).finish();
            context.startActivity(i);

        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
class RI_ViewHolder{
    TextView sl_No, tvServiceName, tvTotalPendency, tvVillageCircleName, tvVillageCircleCode, tvVillageName, tvVillageCode, tvOption_Flag;
    RI_ViewHolder(View view) {
        sl_No = view.findViewById(R.id.sl_No);
        tvServiceName = view.findViewById(R.id.tvServiceName);
        tvTotalPendency = view.findViewById(R.id.tvTotalPendency);
        tvVillageCircleName = view.findViewById(R.id.tvVillageCircleName);
        tvVillageCircleCode = view.findViewById(R.id.tvVillageCircleCode);
        tvVillageName = view.findViewById(R.id.tvVillageName);
        tvVillageCode = view.findViewById(R.id.tvVillageCode);
        tvOption_Flag = view.findViewById(R.id.tvOption_Flag);
    }
}

