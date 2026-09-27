package com.mc1510ty.TekitouVoxelGame.Client;

import java.util.Arrays;

public class Main {
    static void main(String[] args) {
        IO.println("Starting TekitouVoxelGame Client...");
        IO.println("args: " + Arrays.toString(args));

        new Client().start();
    }
}
