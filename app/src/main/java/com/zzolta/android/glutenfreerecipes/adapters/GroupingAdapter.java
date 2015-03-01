package com.zzolta.android.glutenfreerecipes.adapters;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Zolta.Szekely on 2015-02-28.
 */
public class GroupingAdapter implements ExpandableListAdapter {

    private List<String> groups;
    private List<List<String>> groupedItems;
    /**
     * Works with LinearLayout, might work with other layouts too, though not tested.
     */
    private int groupLayoutResource;
    /**
     * Required: TextView
     */
    private int groupedItemsLayoutResource;
    /**
     * Required: ID of a TextView
     */
    private int groupedItemId;

    private LayoutInflater inflater;

    public GroupingAdapter setGroups(List<String> groups) {
        this.groups = groups;
        return this;
    }

    public GroupingAdapter setGroupedItems(List<List<String>> groupedItems) {
        this.groupedItems = groupedItems;
        return this;
    }

    public GroupingAdapter setGroupLayoutResource(int groupLayoutResource) {
        this.groupLayoutResource = groupLayoutResource;
        return this;
    }

    public GroupingAdapter setGroupedItemsLayoutResource(int groupedItemsLayoutResource) {
        this.groupedItemsLayoutResource = groupedItemsLayoutResource;
        return this;
    }

    public GroupingAdapter setGroupedItemId(int groupedItemId) {
        this.groupedItemId = groupedItemId;
        return this;
    }

    public GroupingAdapter setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
        return this;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final List<String> child = groupedItems.get(groupPosition);

        if (convertView == null) {
            convertView = inflater.inflate(groupLayoutResource, null);
        }

        final TextView textView = (TextView) convertView.findViewById(groupedItemId);
        textView.setText(child.get(childPosition));

        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(groupedItemsLayoutResource, null);
        }

        ((TextView) convertView).setText(groups.get(groupPosition));

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupedItems.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupedItems.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}