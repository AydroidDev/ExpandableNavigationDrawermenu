package com.example.vishalhalani.expandablenavigationdrawermenu;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<MenuModel> listDataHeader;
    private HashMap<MenuModel, List<MenuModel>> listDataChild;

    public ExpandableListAdapter(Context context, List<MenuModel> listDataHeader,
                                 HashMap<MenuModel, List<MenuModel>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public MenuModel getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {


        MenuModel menuModel = getChild(groupPosition, childPosition);
        final String childText = getChild(groupPosition, childPosition).menuName;

        if (convertView == null) {

            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_child, null);
        }

        TextView txtListChild = convertView
                .findViewById(R.id.lblListItem);
        ImageView menuIcon = convertView.findViewById(R.id.child_menu_icon);


        if(!TextUtils.isEmpty(menuModel.getIcon()))
        {
            //TODO ADD GLIDE GRADLE AND UNCOMMENT TO LOAD
//            GlideApp.with(context)
//                    .load(getImage(menuModel.getIcon()))
//                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                    .into(menuIcon);

        }



        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        if (this.listDataChild.get(this.listDataHeader.get(groupPosition)) == null)
            return 0;
        else
            return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                    .size();
    }

    @Override
    public MenuModel getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).menuName;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_header, null);

        }

        TextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
        ImageView img = (ImageView) convertView.findViewById(R.id.group_icon);
        View dividerView=(View)convertView.findViewById(R.id.header_divider);
//        if(groupPosition == 0 || groupPosition == 11)
//        {
//            dividerView.setVisibility(View.VISIBLE);
//        }else
//        {
//            dividerView.setVisibility(View.INVISIBLE);
//        }

        if(getChildrenCount(groupPosition)>0)
        {
            img.setVisibility(View.VISIBLE);
            if (isExpanded) {
                img.setImageResource(R.drawable.ic_expand_less_white_24dp);
            } else {
                img.setImageResource(R.drawable.ic_expand_white_24dp);
            }
        }else
        {
            img.setVisibility(View.INVISIBLE);
        }

        lblListHeader.setText(headerTitle);


        return convertView;
    }
    public int getImage(String imageName) {


        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}