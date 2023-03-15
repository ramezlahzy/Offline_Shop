package com.example.offlineshopmain.ShopTrace.Interface;

import androidx.fragment.app.FragmentTransaction;

public interface havecontainer{
    public int getcontainerresource();
    public FragmentTransaction gettransaction();
    public void clear();
    public void setmain(boolean flag) ;
}