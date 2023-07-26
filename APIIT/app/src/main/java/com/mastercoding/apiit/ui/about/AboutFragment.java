package com.mastercoding.apiit.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mastercoding.apiit.R;

import java.util.ArrayList;
import java.util.List;


public class AboutFragment extends Fragment {

    private ImageView imageViewMap;
    private ViewPager viewPager;
    private CourseAdapter adapter;
    private List<CourseModel> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view =inflater.inflate(R.layout.fragment_about, container, false);

        imageViewMap = view.findViewById(R.id.image_view_map);
       list = new ArrayList<>();

       list.add(new CourseModel(R.drawable.ic_cse,"Computer Science & Engineering","Graduates can successfully pursue their career as systems Analyst, Operations Mangers, Database Administrators, Analyst Programmers, Data Communication Specialists, Data Scientists, Machine Learning Engineers to name for few."));
       list.add(new CourseModel(R.drawable.ic_mechenical,"Mechatronics Engineering","Careers span the range of fields which are normally covered in mechanical, electrical, and computer engineering. Roles include designing consumer machines, industrial machines and robotics and automation for advanced manufacturing robot control systems."));
       list.add(new CourseModel(R.drawable.ic_eee,"Electrical and Electronics Engineering","Career choices are in diverse areas such as Power Systems, Electrical equipment manufacturing and testing, Biomedical Engineering and Computer Systems Engineering and also as technical experts on engineering projects in the Banking and Finance Industry. Graduates can land government jobs in electrical department and hydro power projects."));

       adapter = new CourseAdapter(getContext(), list);

       viewPager = view.findViewById(R.id.view_pager);

       viewPager.setAdapter(adapter);

        ImageView imageView = view.findViewById(R.id.college_image);

        Glide.with(getContext()).load("https://www.apiit.edu.in/images/About-apiit/about-apiit-building-photo.jpg").into(imageView);



        imageViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:29.437252, 76.960311?q=29.437252, 76.960311");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });





        return view;




    }
}