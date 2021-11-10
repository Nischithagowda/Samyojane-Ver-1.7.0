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
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Adarsh on 19-Jun-19.
 */

public class UR_List_Adapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<String> SlNo;
    ArrayList<String> Service_Name;
    ArrayList<String> TotalCount;
    ArrayList<String> TownName;
    ArrayList<String> TownCode;
    ArrayList<String> WardName;
    ArrayList<String> WardCode;
    ArrayList<String> Option_Flag;
    Button btnEdit;
    String district, taluk, VA_Name, hobli, VA_Circle_Name, town_Name, ward_Name, serviceName, villageCode, town_code, ward_code;
    int district_Code, taluk_Code, hobli_Code, va_Circle_code;
    String item_position,option_Flag;

    UR_List_Adapter(Context context, ArrayList<String > slNo, ArrayList<String> service_Name, ArrayList<String> totalCount, ArrayList<String> townName, ArrayList<String> townCode, ArrayList<String> wardName, ArrayList<String> wardCode, ArrayList<String> option_Flag) {

        this.context=context;
        this.SlNo = slNo;
        this.Service_Name = service_Name;
        this.TotalCount = totalCount;
        this.TownName = townName;
        this.TownCode  = townCode;
        this.WardName = wardName;
        this.WardCode = wardCode;
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
        final UR_ViewHolder ur_viewHolder;

        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.list_field_report,parent, false);
            ur_viewHolder=new UR_ViewHolder(convertView);
            convertView.setTag(ur_viewHolder);
        }
        else {
            ur_viewHolder=(UR_ViewHolder) convertView.getTag();
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

        ur_viewHolder.sl_No.setText(SlNo.get(position));
        ur_viewHolder.tvServiceName.setText(Service_Name.get(position));
        ur_viewHolder.tvTotalPendency.setText(TotalCount.get(position));
        ur_viewHolder.tvTownName.setText(TownName.get(position));
        ur_viewHolder.tvTownCode.setText(TownCode.get(position));
        ur_viewHolder.tvWardName.setText(WardName.get(position));
        ur_viewHolder.tvWardCode.setText(WardCode.get(position));
        ur_viewHolder.tvOption_Flag.setText(Option_Flag.get(position));

        btnEdit = convertView.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(v -> {
            serviceName = ur_viewHolder.tvServiceName.getText().toString();
            item_position = String.valueOf(position);
            town_Name = ur_viewHolder.tvTownName.getText().toString();
            town_code = ur_viewHolder.tvTownCode.getText().toString();
            ward_Name = ur_viewHolder.tvWardName.getText().toString();
            ward_code = ur_viewHolder.tvWardCode.getText().toString();
            option_Flag = ur_viewHolder.tvOption_Flag.getText().toString();
            villageCode = "99999";

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
            i.putExtra("villageCode",villageCode);
            i.putExtra("town_Name", town_Name);
            i.putExtra("town_code", town_code);
            i.putExtra("ward_Name", ward_Name);
            i.putExtra("ward_code", ward_code);
            i.putExtra("option_Flag", option_Flag);
            ((Activity) context).finish();
            context.startActivity(i);

            Log.d("Item_Position", ""+item_position
                    +"\nService_Name :"+serviceName
                    +"\ntown_Name: "+town_Name
                    +"\nward_Name :"+ward_Name
                    +"\noption_Flag :"+option_Flag);
            Log.d("district_Code", ""+district_Code+" taluk_Code :"+taluk_Code+" hobli_Code: "+hobli_Code);
            Log.d("va_Circle_code", ""+va_Circle_code+" town_code :"+town_code+" ward_code:"+ward_code+" VA_Name: "+VA_Name);
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
class UR_ViewHolder{
    TextView sl_No, tvServiceName, tvTotalPendency,btnEdit, tvTownName, tvTownCode, tvWardName, tvWardCode, tvOption_Flag;
    UR_ViewHolder(View view) {
        sl_No = view.findViewById(R.id.sl_No);
        tvServiceName = view.findViewById(R.id.tvServiceName);
        tvTotalPendency = view.findViewById(R.id.tvTotalPendency);
        btnEdit = view.findViewById(R.id.btnEdit);
        tvTownName = view.findViewById(R.id.tvTownName);
        tvTownCode = view.findViewById(R.id.tvTownCode);
        tvWardName = view.findViewById(R.id.tvWardName);
        tvWardCode = view.findViewById(R.id.tvWardCode);
        tvOption_Flag = view.findViewById(R.id.tvOption_Flag);
    }
}
