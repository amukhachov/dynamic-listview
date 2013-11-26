package com.birdy.dynamiclistview;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.birdy.dynamiclistview.DynamicListView.OnItemMovedToTopListener;

public class DynamicListNavigationFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.listview_fragment_layout, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Activity activity = getActivity();
		View view = getView();
		
		ArrayList<String>mCheeseList = new ArrayList<String>();
        for (int i = 0; i < Cheeses.sCheeseStrings.length; ++i) {
            mCheeseList.add(Cheeses.sCheeseStrings[i]);
        }

        StableArrayAdapter adapter = new StableArrayAdapter(activity, R.layout.text_view, mCheeseList);
        DynamicListView listView = (DynamicListView) view.findViewById(R.id.listview);
        final View deleteTopView = view.findViewById(R.id.delete_topview);
        final View addTopView = view.findViewById(R.id.add_item_topview);

        listView.setCheeseList(mCheeseList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemMovedToTopListener(new OnItemMovedToTopListener() {
			@Override
			public void onActivated() {
				deleteTopView.setVisibility(View.VISIBLE);
				addTopView.setVisibility(View.GONE);
				deleteTopView.setBackgroundColor(getResources().getColor(R.color.delete_passive_color));
			}
			
			@Override
			public void onItemMove(String item, boolean isOnTop) {
				deleteTopView.setBackgroundColor(getResources().getColor(isOnTop ? R.color.delete_active_color : R.color.delete_passive_color));
			}
			
			@Override
			public void onDeactivated(String item, boolean isOnTop) {
				deleteTopView.setVisibility(View.GONE);
				addTopView.setVisibility(View.VISIBLE);
			}
		});
	}

}
