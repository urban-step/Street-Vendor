package com.example.tbc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tbc.R;

import java.util.ArrayList;
import java.util.List;

public class CommodityImageAdapter extends BaseAdapter {
        private Context mContext;

        // Keep all Images in array
        public Integer[] mThumbIds = {
                R.drawable.pic_101,R.drawable.pic_102,
                R.drawable.pic_103, R.drawable.pic_104,
                R.drawable.pic_105, R.drawable.pic_106,
                R.drawable.pic_107, R.drawable.pic_108,
                R.drawable.pic_109, R.drawable.pic_110,
                R.drawable.pic_111, R.drawable.pic_112, R.drawable.pic_113

        };
        public List<Integer> selectedPositions = new ArrayList<>();

        public String[] mTexts= {"Clothes/वस्त्र","fruits and vegetables/फल और सबजीया","Prepared food/बनाया हुआ खाना","Mobile Accessories/मोबाइल से जुड़े सामान","Electronics Appliances/इलेक्ट्रॉनिक उपकरण","Jewellery/आभूषण","Shoes/जूते",
                "Leather Items/चमड़े की वस्तु","Tea and Coffee/चाय और कॉफी","Juices/रस","Make-up/शृंगार","Home Utility/घर की उपयोगिता","Other/अन्य"};


        public CommodityImageAdapter(Context c){
            mContext = c;
        }

        @Override
        public int getCount() {
            return mThumbIds.length;
        }

        @Override
        public Object getItem(int position) {
            return mThumbIds[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(mContext);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.commodity_item, null);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

            TextView textView = gridView.findViewById(R.id.grid_item_text);
            imageView.setImageResource(mThumbIds[position]);
            textView.setText(mTexts[position]);


        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }


}
