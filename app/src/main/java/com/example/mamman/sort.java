package com.example.mamman;

import com.example.mamman.Model.MonAnModel;

import java.util.Comparator;

public class sort implements Comparator<MonAnModel> {
    @Override
    public int compare(MonAnModel o1, MonAnModel o2) {
        float danhgia1 = o1.getDanhgia();
        float danhgia2 = o2.getDanhgia();
        if(danhgia1<danhgia2){
            return 1;
        }else if(danhgia1==danhgia2){
            return 0;
        }else{
            return -1;
        }
    }
}
