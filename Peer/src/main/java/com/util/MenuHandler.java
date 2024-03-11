package com.util;

import java.io.BufferedReader;
import java.io.IOException;

public class MenuHandler extends Thread{
    private final BufferedReader br;

    public MenuHandler(BufferedReader br){
        this.br = br;
    }


}
