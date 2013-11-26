package com.birdy.dynamiclistview;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;

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
        final DynamicListView listView = (DynamicListView) view.findViewById(R.id.listview);
        final View deleteTopView = view.findViewById(R.id.delete_topview);
        final View addModeView = view.findViewById(R.id.add_mode_topview);
        final View addItemContainer = view.findViewById(R.id.add_item_container);
        final View addItemView = view.findViewById(R.id.add_topview);
        final View editItemView = view.findViewById(R.id.edit_topview);
        
        addItemView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addItemContainer.setVisibility(View.GONE);
				addModeView.setVisibility(View.VISIBLE);
				hideKeyboard(editItemView);
				
				CharSequence itemName = ((TextView) editItemView).getText();
				
				((StableArrayAdapter)listView.getAdapter()).getTextValues();
				
				listView.insertValue(itemName == null ? "New item" : itemName.toString(), 0);
			}
		});
        
        addModeView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addModeView.setVisibility(View.GONE);
				addItemContainer.setVisibility(View.VISIBLE);
				editItemView.requestFocus();
				showKeyboard(editItemView);
			}
		});

        listView.setCheeseList(mCheeseList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemMovedToTopListener(new OnItemMovedToTopListener() {
			@Override
			public void onActivated() {
				deleteTopView.setVisibility(View.VISIBLE);
				addModeView.setVisibility(View.GONE);
				addItemContainer.setVisibility(View.GONE);
				hideKeyboard(editItemView);
				deleteTopView.setBackgroundColor(getResources().getColor(R.color.delete_passive_color));
			}
			
			@Override
			public void onItemMove(String item, boolean isOnTop) {
				deleteTopView.setBackgroundColor(getResources().getColor(isOnTop ? R.color.delete_active_color : R.color.delete_passive_color));
			}
			
			@Override
			public void onDeactivated(String item, boolean isOnTop) {
				deleteTopView.setVisibility(View.GONE);
				addModeView.setVisibility(View.VISIBLE);
			}
		});
	}
	
	private void hideKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
			      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	
	private void showKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
			      Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(view, 0);
	}

}
