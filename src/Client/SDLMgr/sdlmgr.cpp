#include <SDL3/SDL.h>

#include <print>

#include "sdlmgr.hpp"

#include "SDL3/SDL_vulkan.h"

bool SDLMgr::init() {

    std::println("SDLの初期化を開始します");

    std::println("SDL_Initを実行中...");

    if (!SDL_Init(SDL_INIT_VIDEO)) {
        SDL_Log("SDL_Initに失敗しました: %s", SDL_GetError());
        return false;
    }

    std::println("SDL_Initが完了しました");
    std::println("SDL_CreateWindowを実行中...");

    check_vulkan();

    if (isvulkan) {
        flags = SDL_WINDOW_VULKAN | SDL_WINDOW_RESIZABLE;
    } else {
        flags = SDL_WINDOW_OPENGL | SDL_WINDOW_RESIZABLE;
    }

    window = SDL_CreateWindow(windowTitle.c_str(), width, height, flags);

    if (!window) {
        SDL_Log("SDL_CreateWindowに失敗しました: %s", SDL_GetError());
        SDL_Quit();
        return false;
    }

    std::println("SDL_CreateWindowが完了しました");
    std::println("SDLの初期化が完了しました");


    return true;
}

void SDLMgr::exit_sdl() {
    if (window != nullptr) {
        SDL_DestroyWindow(window);
        window = nullptr;
    }
    SDL_Quit();
}

void SDLMgr::check_vulkan() {
    std::println("Vulkanチェック中...");

    if (SDL_Vulkan_LoadLibrary(nullptr)) {
        isvulkan = true;
        std::println("Vulkan対応!");
        SDL_Vulkan_UnloadLibrary();
    } else {
        isvulkan = false;
        std::println("Vulkan非対応!OpenGL(ES)を使用します");
    }
}
