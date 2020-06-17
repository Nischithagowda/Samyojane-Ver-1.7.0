package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class List_Adapter extends BaseAdapter implements Filterable {
    private Context context;
    ArrayList<String> SlNo;
    private ArrayList<String> Service_Name;
    private ArrayList<String> TotalCount;
    ArrayList<String> VillageName;
    private ArrayList<String> VillageCode;
    private ArrayList<String> HabitationCode;
    ArrayList<String> Option_Flag;
    String villageCode, habitationCode;
    String district, taluk, VA_Name, hobli,VA_Circle_Name;
    private String district_Code, taluk_Code, hobli_Code, va_Circle_code, town_code, ward_code;
    String item_position;
    String serviceName, option_Flag;
    private String villageName;

    List_Adapter(Context context, ArrayList<String > slNo, ArrayList<String> service_Name, ArrayList<String> totalCount, ArrayList<String> villageName, ArrayList<String> villageCode, ArrayList<String> habitationCode, ArrayList<String> option_Flag) {

        this.context=context;
        this.SlNo = slNo;
        this.Service_Name = service_Name;
        this.TotalCount = totalCount;
        this.VillageName = villageName;
        this.VillageCode  = villageCode;
        this.HabitationCode = habitationCode;
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
        final ViewHolder viewHolder;

        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.list_field_report,parent, false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) convertView.getTag();
        }

        district_Code = Field_Report.Global.district_Code1;
        district = Field_Report.Global.district_Name1;
        taluk_Code = Field_Report.Global.taluk_Code1;
        taluk = Field_Report.Global.taluk_Name1;
        hobli_Code = Field_Report.Global.hobli_Code1;
        hobli = Field_Report.Global.hobli_Name1;
        va_Circle_code = Field_Report.Global.VA_Circle_Code1;
        VA_Circle_Name = Field_Report.Global.VA_Circle_Name1;
        VA_Name = Field_Report.Global.VA_Name1;

        viewHolder.sl_No.setText(SlNo.get(position));
        viewHolder.tvServiceName.setText(Service_Name.get(position));
        viewHolder.tvTotalPendency.setText(TotalCount.get(position));
        viewHolder.tvVillageName.setText(VillageName.get(position));
        viewHolder.tvVillageCode.setText(VillageCode.get(position));
        viewHolder.tvHabitationCode.setText(HabitationCode.get(position));
        viewHolder.tvOption_Flag.setText(Option_Flag.get(position));

        viewHolder.btnEdit.setOnClickListener(v -> {
            serviceName = viewHolder.tvServiceName.getText().toString();
            item_position = String.valueOf(position);
            villageName = viewHolder.tvVillageName.getText().toString();
            villageCode = viewHolder.tvVillageCode.getText().toString();
            habitationCode = viewHolder.tvHabitationCode.getText().toString();
            option_Flag = viewHolder.tvOption_Flag.getText().toString();
            town_code = "9999";
            ward_code = "255";

            Intent i = new Intent(context, New_Request_FirstScreen.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("district_Code", district_Code);
            i.putExtra("districtCode", district);
            i.putExtra("taluk_Code", taluk_Code);
            i.putExtra("taluk", taluk);
            i.putExtra("hobli_Code", hobli_Code);
            i.putExtra("hobli", hobli);
            i.putExtra("va_Circle_Code", va_Circle_code);
            i.putExtra("VA_Circle_Name", VA_Circle_Name);
            i.putExtra("VA_Name", VA_Name);
            i.putExtra("strSearchServiceName", serviceName);
            i.putExtra("villageCode", villageCode);
            i.putExtra("habitationCode", habitationCode);
            i.putExtra("strSearchVillageName", villageName);
            i.putExtra("town_code", town_code);
            i.putExtra("ward_code", ward_code);
            i.putExtra("option_Flag", option_Flag);
            ((Activity) context).finish();
            context.startActivity(i);

            Log.d("Item_Position", ""+item_position+" Service_Name :"+serviceName+" Village_Name: "+villageName);
            Log.d("district_Code", ""+district_Code+" taluk_Code :"+taluk_Code+" hobli_Code: "+hobli_Code);
            Log.d("va_Circle_code", ""+va_Circle_code+" villageCode :"+villageCode+" habitationCode:"+habitationCode+" VA_Name: "+VA_Name);
            Log.d("option_Flag", ""+option_Flag);
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
class ViewHolder{
    TextView sl_No, tvServiceName, tvTotalPendency,btnEdit, tvVillageName, tvVillageCode, tvHabitationCode, tvOption_Flag;
    ViewHolder(View view) {
        sl_No = view.findViewById(R.id.sl_No);
        tvServiceName = view.findViewById(R.id.tvServiceName);
        tvTotalPendency = view.findViewById(R.id.tvTotalPendency);
        btnEdit = view.findViewById(R.id.btnEdit);
        tvVillageName = view.findViewById(R.id.tvVillageName);
        tvVillageCode = view.findViewById(R.id.tvVillageCode);
        tvHabitationCode = view.findViewById(R.id.tvHabitationCode);
        tvOption_Flag = view.findViewById(R.id.tvOption_Flag);
    }
}

