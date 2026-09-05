#pragma once

#include <SDL3/SDL.h>

#include <string>



class SDLMgr {
private:
    SDL_Window* window = nullptr;
    void check_vulkan();
    bool isvulkan = false;
    std::string windowTitle = "TekitouVoxelGame";
    int width = 1080;
    int height = 720;
    SDL_WindowFlags flags;
public:
    bool init();
    void exit_sdl();

    SDL_Window* getWindow() const { return window; }
    bool getIsVulkan() const {return isvulkan;}
};