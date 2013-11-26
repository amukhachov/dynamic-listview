/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.listviewdragginganimation;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.birdy.dynamiclistview.R;
import com.example.android.listviewdragginganimation.DynamicListView.OnItemMovedToTopListener;

/**
 * This application creates a listview where the ordering of the data set
 * can be modified in response to user touch events.
 *
 * An item in the listview is selected via a long press event and is then
 * moved around by tracking and following the movement of the user's finger.
 * When the item is released, it animates to its new position within the listview.
 */
public class ListViewDraggingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ArrayList<String>mCheeseList = new ArrayList<String>();
        for (int i = 0; i < Cheeses.sCheeseStrings.length; ++i) {
            mCheeseList.add(Cheeses.sCheeseStrings[i]);
        }

        StableArrayAdapter adapter = new StableArrayAdapter(this, R.layout.text_view, mCheeseList);
        DynamicListView listView = (DynamicListView) findViewById(R.id.listview);
        final View deleteTopView = findViewById(R.id.delete_top_view);

        listView.setCheeseList(mCheeseList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemMovedToTopListener(new OnItemMovedToTopListener() {
			@Override
			public void onActivated() {
				deleteTopView.setVisibility(View.VISIBLE);
				deleteTopView.setBackgroundColor(getResources().getColor(R.color.delete_passive_color));
			}
			
			@Override
			public void onItemMove(String item, boolean isOnTop) {
				deleteTopView.setBackgroundColor(getResources().getColor(isOnTop ? R.color.delete_active_color : R.color.delete_passive_color));
			}
			
			@Override
			public void onDeactivated(String item, boolean isOnTop) {
				deleteTopView.setVisibility(View.GONE);
			}
		});
    }
}