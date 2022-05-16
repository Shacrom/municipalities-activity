package marcos.uv.es.covid19cv;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class GridViewAdapter extends BaseAdapter {
    private ArrayList<SymtomModel> list;
    private Context mContext;
    private LayoutInflater mInflater = null;

    public GridViewAdapter(ArrayList<SymtomModel> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.spinners, null);
            holder.syn = (TextView) view.findViewById(R.id.text);
            holder.cb = (CheckBox) view.findViewById(R.id.checkbox);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        holder.syn.setText(list.get(i).getTitle());

        holder.cb.setChecked(list.get(i).isSelected());

        holder.cb.setTag(i);

        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                list.get(i).setSelected(isChecked);
            }
        });

        return view;
    }

    static class ViewHolder{
        CheckBox cb;
        TextView syn;
    }

    public ArrayList<SymtomModel> getList() {
        return list;
    }
}
