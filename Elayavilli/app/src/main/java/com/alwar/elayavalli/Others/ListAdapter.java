package com.alwar.elayavalli.Others;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alwar.elayavalli.R;

import java.util.ArrayList;
import java.util.List;


public class ListAdapter extends ArrayAdapter<RetrieveBean> implements View.OnClickListener{

        List<RetrieveBean> dataSet;
        Context context;

        private static class ViewHolder {
            TextView tvName;
            TextView tvPlace;
            TextView tvMobileNumber;
        }

        public ListAdapter(List<RetrieveBean> data, Context context) {
            super(context, R.layout.row_item, data);
            this.dataSet = data;
            this.context = context;

        }

        @Override
        public void onClick(View v) {

            int position=(Integer) v.getTag();
            Object object= getItem(position);

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RetrieveBean listModel = getItem(position);
            ViewHolder viewHolder;

            final View view;

            if (convertView == null) {

                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.row_item, parent, false);
                viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                viewHolder.tvPlace = (TextView) convertView.findViewById(R.id.tv_place);
                viewHolder.tvMobileNumber = (TextView) convertView.findViewById(R.id.tv_mobileno);

                view = convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                view = convertView;
            }

            if(listModel.getAdiyenNameStr() != null) {
                viewHolder.tvName.setText(listModel.getAdiyenNameStr());
            }

            viewHolder.tvPlace.setText(listModel.getNativePlaceStr());
            viewHolder.tvMobileNumber.setText(listModel.getMobileNoStr());
            return convertView;
        }

}
