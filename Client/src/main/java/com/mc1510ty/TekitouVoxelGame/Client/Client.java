package com.mc1510ty.TekitouVoxelGame.Client;

import org.lwjgl.sdl.SDL_Event;

import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_QUIT;
import static org.lwjgl.sdl.SDLEvents.SDL_PollEvent;
import static org.lwjgl.sdl.SDLInit.*;
import static org.lwjgl.sdl.SDLVideo.*;

public class Client {

    long window;
    SDL_Event event;
    boolean running;

    public void start() {
        IO.println("Clientを開始します");
        init();
        loop();
        cleanup();
    }

    private void init() {
        IO.println("初期化を開始します");
        initSDL();
    }

    private void loop() {
        while (running) {
            while (SDL_PollEvent(event)) {
                if (event.type() == SDL_EVENT_QUIT) {
                    running = false;
                }
            }
        }
    }

    private void cleanup() {
        cleanupSDL3();
    }

    private void initSDL() {
        if (!SDL_Init(SDL_INIT_VIDEO)) {
        }

        long windowFlags = SDL_WINDOW_HIGH_PIXEL_DENSITY;

        window = SDL_CreateWindow("TekitouVoxelGame", 1280, 720, windowFlags);
        if (window == 0) {
            SDL_Quit();
        }

        event = SDL_Event.create();
        running = true;
    }

    private void cleanupSDL3() {
        event.free();
        SDL_DestroyWindow(window);
        SDL_Quit();
    }

}
