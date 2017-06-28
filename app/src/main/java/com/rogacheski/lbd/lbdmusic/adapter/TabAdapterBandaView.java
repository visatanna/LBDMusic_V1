package com.rogacheski.lbd.lbdmusic.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rogacheski.lbd.lbdmusic.R;
import com.rogacheski.lbd.lbdmusic.entity.BandEntity;
import com.rogacheski.lbd.lbdmusic.fragments.BandAboutUsPage;
import com.rogacheski.lbd.lbdmusic.fragments.BandCalendarPage;
import com.rogacheski.lbd.lbdmusic.fragments.BandReviewsPage;
import com.rogacheski.lbd.lbdmusic.fragments.BaseFragment;


/**
 * Criado por Vinícius on 28-Apr-17.
 */

public class TabAdapterBandaView  extends FragmentPagerAdapter{

    Context context;
    BandEntity banda;
    int idUser;

    public TabAdapterBandaView(FragmentManager fm, Context context, BandEntity banda ,int idUser ){
        super(fm);
        this.context = context;
        this.banda = banda;
        this.idUser = idUser;
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment = new BaseFragment();
        try {
            switch (position) {
                case 0:
                    BandAboutUsPage fragmentoAboutUs = new BandAboutUsPage();
                    fragmentoAboutUs.setArgument(banda);
                    fragmentoAboutUs.setIdUser(idUser);
                    fragment = fragmentoAboutUs;
                    break;
                case 1:
                    BandCalendarPage fragmentoCalendar = new BandCalendarPage();
                    fragmentoCalendar.setArgument(banda);
                    fragmentoCalendar.setIdUser(idUser);
                    fragment = fragmentoCalendar;
                    break;
                case 2:
                    BandReviewsPage fragmentoReviews = new BandReviewsPage();
                    fragmentoReviews.setArgument(banda);
                    fragmentoReviews.setIdUser(idUser);
                    fragment = fragmentoReviews;
                    break;

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.sobre_nos);
            case 1:
                return context.getString(R.string.calendario);
            case 2:
                return context.getString(R.string.avaliacoes);
            default:
                return "";
        }
    }
}
