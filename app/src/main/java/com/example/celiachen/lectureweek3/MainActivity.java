package com.example.celiachen.lectureweek3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //private ListView mListView;
    private Context mContext;
    private SwipeFlingAdapterView flingContainer;
    private Recipe lastRemoved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);


        mContext = this;
        // data to display
        final ArrayList<Recipe> recipeList = Recipe.getRecipesFromFile("recipes.json", this);

        // create the adapter
        final RecipeAdapter adapter = new RecipeAdapter(this, recipeList);

        flingContainer.setAdapter(adapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                lastRemoved = recipeList.remove(0);

                adapter.notifyDataSetChanged();
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                //recipeList.remove(0);

                recipeList.add(0, lastRemoved);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);

                adapter.notifyDataSetChanged();
            }
        });
        /**
        // find the listview in the layout
        // set the adapter to listview
        mListView = findViewById(R.id.recipe_list_view);
        mListView.setAdapter(adapter);


        // 1. each row should be clickable
        // when the row is clicked,
        // the intent is created and send

        mListView.setOnItemClickListener( new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id){
                Recipe selectedRecipe = recipeList.get(position);

                // create my intent package
                // add all the information needed for detail page
                // startActivity with that intent

                //explicit
                // from, to
                Intent detailIntent = new Intent(mContext, RecipeDetailActivity.class);
                // put title and instruction URL
                detailIntent.putExtra("title", selectedRecipe.title);
                detailIntent.putExtra("url", selectedRecipe.instructionUrl);

                startActivity(detailIntent);

            }
        });
         */
    }
}
