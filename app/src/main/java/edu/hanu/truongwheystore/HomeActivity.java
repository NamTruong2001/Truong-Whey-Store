package edu.hanu.truongwheystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.hanu.truongwheystore.Adapter.ViewPagerAdapter;
import edu.hanu.truongwheystore.Model.Product;

public class HomeActivity extends AppCompatActivity {
    private ViewPager homeViewPager;
    private BottomNavigationView bottomNav;
    public static List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        products = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Product product = document.toObject(Product.class);
                                products.add(product);
                                Log.d("Proddducts", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("Proddducts", "Error getting documents.", task.getException());
                        }
                    }
                });
        setContentView(R.layout.activity_home);
        initView();
        setUpBottomNav();
        setUpViewPager();
    }

    private void initView() {
        homeViewPager = findViewById(R.id.viewPager);
        bottomNav = findViewById(R.id.nav_view);
    }

    private void setUpBottomNav() {
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();

                        homeViewPager.setCurrentItem(0);
                        break;
                    case R.id.action_category:
                        Toast.makeText(getApplicationContext(), "Cate", Toast.LENGTH_SHORT).show();

                        homeViewPager.setCurrentItem(1);
                        break;
                    case R.id.action_search:
                        Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_SHORT).show();

                        homeViewPager.setCurrentItem(2);
                        break;
                    case R.id.action_fav:
                        Toast.makeText(getApplicationContext(), "Favourite", Toast.LENGTH_SHORT).show();

                        homeViewPager.setCurrentItem(3);
                        break;
                    case R.id.action_viewDetail:
                        Toast.makeText(getApplicationContext(), "detail", Toast.LENGTH_SHORT).show();

                        homeViewPager.setCurrentItem(4);
                        break;
                }

                return true;
            }
        });
    }

    private void setUpViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        homeViewPager.setAdapter(viewPagerAdapter);
        homeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNav.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case 1:
                        bottomNav.getMenu().findItem(R.id.action_category).setChecked(true);
                        break;
                    case 2:
                        bottomNav.getMenu().findItem(R.id.action_search).setChecked(true);
                        break;
                    case 3:
                        bottomNav.getMenu().findItem(R.id.action_fav).setChecked(true);
                        break;
                    case 4:
                        bottomNav.getMenu().findItem(R.id.action_viewDetail).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}